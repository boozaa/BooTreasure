package org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.*;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;



public class ChestCreateProcedure implements Runnable {

	private volatile TreasureChest treasure;	
	private Plugin plugin;
	private Player player;
	private World world;
	private Location chestLocation;
	
	
	public ChestCreateProcedure(  Plugin plugin, Player p  ){
		this.plugin = plugin;
		this.player = p;
		this.world = p.getWorld();
		chestLocation = this.player.getLocation().toVector().add(this.player.getLocation().getDirection().multiply(1)).toLocation(this.world);
		chestLocation.setY(chestLocation.getY()+1);
		this.treasure = new TreasureChest(chestLocation);
		
	}
	
	
	@Override
	public void run( ) {
		
		// Apparition du Chest devant le player
		this.treasure.chestAppear();
		
		// Le ConversationFactory
		ConversationFactory factory = new ConversationFactory(this.plugin);
		final Map<Object, Object> map = new HashMap<Object, Object>();
		
		map.put( "YesCommand", BooTreasure.get_configManager().get("messages.yml").getString("locales.commands.agree") );
		map.put( "NoCommand", BooTreasure.get_configManager().get("messages.yml").getString("locales.commands.disagree") );
		map.put( "EndCommand", BooTreasure.get_configManager().get("messages.yml").getString("locales.commands.end") );
		map.put( "ExitCommand", BooTreasure.get_configManager().get("messages.yml").getString("locales.commands.exit") );
		// Le treasure
		map.put( "TreasureChest", this.treasure );
		
		// On construit la conversation
		Conversation conv = factory
	            .withFirstPrompt(new AskName())
	            .withEscapeSequence( BooTreasure.get_configManager().get("messages.yml").getString("locales.commands.end") )
	            .withPrefix(new ConversationPrefix() {	 
	                @Override
	                public String getPrefix(ConversationContext arg0) { return BooTreasure.get_configManager().get("messages.yml").getString("locales.create.prefix") + System.getProperty("line.separator"); }	 
	            }).withInitialSessionData(map).withLocalEcho(true)
	            .buildConversation(this.player);
		
		conv.addConversationAbandonedListener(new ConversationAbandonedListener() {	 
	        @Override
	        public void conversationAbandoned(ConversationAbandonedEvent event)
	        {
	            if (event.gracefulExit())
	            {	            	
	            	// On stocke son inventaire
	    			treasure.getChestContents();
	    			
	    			Log.debug( "Serialization ..." );	    			
	    			// On serialize
	    			treasure.serialize();
	    			Log.debug( "... done" );
	            	
	            	Log.debug("ChestTreasure created");
	            	Log.debug( treasure.toString() );
	            	
	            	// Stockage en cache
	            	BooTreasure.get_cacheManager().get_treasureCache().add(treasure.get_id(), treasure);
	            	
	            	// Stockage dans treasures.yml
	            	BooTreasure.get_configManager().saveNewTreasureChest(treasure);
	            	
	            	
	            	//TreasureConfig treasuresConfig = BooTreasure.get_treasuresConfiguration();
	            	//treasuresConfig.createNewTreasure(treasure);
	            	
	            	// On peut faire disparaitre le coffre aprés l'avoir donné au cron
	            	BooTreasure.get_eventsManager().chestDisappearSilentlyEvent(treasure);
	            	
	            }
	        }
	    });
	    
	    
	    conv.begin();
		
		
	}
	
	
	/*
	 * First prompt
	 * Ask for a name
	 */
	public class AskName extends NamePrompt {

		@Override
		public String getPromptText(ConversationContext arg0) {
			return BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.name").replaceAll("&", "§");
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {			
			treasure.set_name(in);			
			return new AskCron();			
		}
		
	}
	

	public class AskCron extends CronPatternPrompt{

		@Override
		public String getPromptText(ConversationContext arg0) {
			return BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.cronpattern").replaceAll("&", "§");
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			treasure.set_pattern(in);   	
	    	return new AskDuration();
		}
		
	}
	
	public class AskDuration extends IntegerPrompt{

		@Override
		public String getPromptText(ConversationContext arg0) {
			return BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.duration").replaceAll("&", "§");
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			treasure.set_duration(Long.parseLong(in) ); 
	    	return new AskInfinite(); 
		}
				
	}

	public class AskInfinite extends YesNoPrompt{

		@Override
		public String getPromptText(ConversationContext arg0) {
			return BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.infinite").replaceAll("&", "§");
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			String yes = (String) context.getSessionData("YesCommand");
			if( in.equalsIgnoreCase(yes) || in.equalsIgnoreCase(yes.substring(1, 2)) ){
				treasure.set_infinite(true);
	    	}else{
	    		treasure.set_infinite(false);
	    	}    	    	
	    	return new AskWorld();
		}
				
	}

	public class AskWorld extends WorldPrompt{

		@Override
		public String getPromptText(ConversationContext arg0) {
			StringBuilder str = new StringBuilder();
			str.append(BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.world").replaceAll("&", "§") + ": " + System.getProperty("line.separator")  );
			for( World w : this._worlds ){
				str.append(w.getName() + " ");
			}		
			return str.toString();
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			treasure.set_world(in);  	    	
	    	return new AskOnlyOnSurface();
		}
				
	}
	
	public class AskOnlyOnSurface extends YesNoPrompt {

		@Override
		public String getPromptText(ConversationContext arg0) {
			return BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.onlyonsurface").replaceAll("&", "§");
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			String yes = (String) context.getSessionData("YesCommand");
			if( in.equalsIgnoreCase(yes) || in.equalsIgnoreCase(yes.substring(1, 2)) ){
				treasure.set_onlyonsurface(true);
	    	}else{
	    		treasure.set_onlyonsurface(false);
	    	}    	    	
	    	return new AskPreserveContent(); 
		}
		
		
	}
	
	public class AskPreserveContent extends YesNoPrompt {
		
		@Override
		public String getPromptText(ConversationContext context) {
			return BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.preservecontent").replaceAll("&", "§");
		}
			
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			String yes = (String) context.getSessionData("YesCommand");
			// Récuperation de données
	    	if( in.equalsIgnoreCase(yes) || in.equalsIgnoreCase(yes.substring(1, 2)) ){
	    		treasure.set_preservecontent(true);
	    	}else{
	    		treasure.set_preservecontent(false);
	    	}    	    	
	    	return new AskAllowedIds(); 
		}
		
	}
	
	
	public class AskAllowedIds extends MaterialPrompt {

		private List<Material> _materials;
		
		@Override
		public String getPromptText(ConversationContext arg0) {
			return BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.allowedids").replaceAll("&", "§");
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			// Si on a un Material en in on le stocke
			Material mat = Material.getMaterial(in);
			if( mat != null ){
				// On l'ajoute a la List
				this._materials.add(mat);
				// On informe
				context.getForWhom().sendRawMessage("+" + mat);
			}	
			
			String exit = (String) context.getSessionData("ExitCommand");
			
			// Si on demande la fin du prompt on stocke et passe à étape suivante
	    	if( in.equalsIgnoreCase(exit) ){
	    		// On stocke dans le context et dans l'instance de treasure
	    		treasure.set_placesMaterials(this._materials);
	        	// On lance le WaitingEndPrompt
	    		return new WaitingEndPrompt(
	    				BooTreasure.get_configManager().get("messages.yml").getString("locales.create.chest.ask.waitingend").replaceAll("&", "§"), 
	    				BooTreasure.get_configManager().get("messages.yml").getString("locales.create.success").replaceAll("&", "§"));
			}
	    	
	    	// On boucle pour attendre les prochains blocks
	    	return this;
		}
		
	}
	
	public class WaitingEndPrompt extends ValidatingPrompt {

		protected String _message, _fin;
		
		public WaitingEndPrompt(String message, String fin){
			this._message = message;
			this._fin = fin;
		}
		
		@Override
		public String getPromptText(ConversationContext context) {
			return this._message;
		}
		
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			return new EndPrompt(this._fin); 
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String in) {
			return true;
		}
		
		
	}
	
	public class EndPrompt extends MessagePrompt {

		protected String _message;
		
		
		public EndPrompt(String message){
			this._message = message;
		}
		
		@Override
		public String getPromptText(ConversationContext context) {
			
			Log.debug("Enter EndPrompt getPromptText");				
			return this._message;
		}
		
		@Override
		protected Prompt getNextPrompt(ConversationContext context) {
			// On met fin à cette procédure
			return Prompt.END_OF_CONVERSATION;
		}
		
		
	}
	
	
}
