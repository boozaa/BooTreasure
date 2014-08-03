package org.shortrip.boozaa.plugins.bootreasure.procedures.chest;

import lombok.Getter;
import org.bukkit.Location;
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
import org.shortrip.boozaa.plugins.bootreasure.Managers;
import org.shortrip.boozaa.plugins.bootreasure.dao.EventsDAO.EventType;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks.TreasureTask;
import org.shortrip.boozaa.plugins.bootreasure.procedures.validityprompts.*;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;
import org.shortrip.boozaa.plugins.bootreasure.utils.StringUtils;



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
		
		try{
		
			// Apparition du Chest devant le player
			this.treasure.appearSilently();
			
			// Le ConversationFactory
			ConversationFactory factory = new ConversationFactory(this.plugin);
			
			// On construit la conversation
			Conversation conv = factory
		            .withFirstPrompt(new AskName())
		            .withEscapeSequence( Managers.getLocalesConfig().getEnd() )
		            .withPrefix(new ConversationPrefix() {	 
		                @Override
		                public String getPrefix(ConversationContext arg0) { 
		                	return StringUtils.colorize( Managers.getLocalesConfig().getPrefix() ); 
		                }	 
		            }).withLocalEcho(true)
		            .buildConversation(this.player);
			
			conv.addConversationAbandonedListener(new ConversationAbandonedListener() {	 
		        @Override
		        public void conversationAbandoned(ConversationAbandonedEvent event)
		        {
		            if (event.gracefulExit())
		            {	            	
		            	try{
			            	// On stocke son inventaire
			    			treasure.getChestContents();
			    			
			    			Log.debug( "Serialization ..." );	    			
			    			// On serialize
			    			treasure.serialize();
			    			Log.debug( "... done" );
			            	
			            	Log.debug("ChestTreasure created");
			            	Log.debug( treasure.toString() );
			            	
			            	// Stockage en cache
			            	Managers.getCacheManager().get_treasureCache().add(treasure.get_id(), treasure);
			            	Log.debug("ChestTreasure stored in cache");
			            	
			            	// TODO correct this
			            	/*
			            	// Stockage dans treasures.yml
			            	BooTreasure.getConfigManager().saveNewTreasureChest(treasure);
			            	Log.debug("ChestTreasure saved in treasures.yml");
			            	*/
			            	
			            	// Create new TreasureDAO in database
			            	Managers.getDatabaseManager().addTreasureToDatabase( treasure );							
							
							// Store event in database
			            	Managers.getDatabaseManager().addEventToDatabase(treasure, player, EventType.CREATED);
							Log.debug("Created event stored in database");
			            		            		            	
							
			            	// On peut faire disparaitre le coffre aprés l'avoir donné au cron
			            	Log.debug("Launch disappear event silently for creation chest");
			            	Managers.getEventsManager().chestDisappearSilentlyEvent(treasure);
			            	
			            	// Give it to the cronManager
			            	Log.debug("Give a TreasureTask of this treasure to the CronManager");
			            	Managers.getCronManager().addTask(new TreasureTask(plugin, treasure));
			            	
		            	}catch( Exception e){
		        			
		    				Log.warning("ChestCreateProcedure -> run()" + e);
		    				
		    			}
		            	
		            }else{
		            	
		            	Log.debug("Chest creation failed");
	    				player.sendMessage( StringUtils.colorize( Managers.getLocalesConfig().getChest_failure_create() ) );
		            	
		            }
		            
		        }
		        
		    });
		    
		    
		    conv.begin();
		
		}catch (Exception e){
			e.printStackTrace();
			Log.severe("An error occured on ChestCreateProcedure", e);			
		}
	
		
	}
	
	
	/*
	 * First prompt
	 * Ask for a name
	 */
	public class AskName extends ValidatingPrompt {

		@Override
		public String getPromptText(ConversationContext arg0) {			
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_name() );
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {			
			treasure.set_name(in);			
			return new AskCron();			
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			return true;
		}
		
	}
	

	public class AskCron extends CronPatternPrompt{

		@Override
		public String getPromptText(ConversationContext arg0) {
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_pattern() );
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
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_duration() );
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
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_infinite() );
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			
			if( in.equalsIgnoreCase("true") ){
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
			str.append( StringUtils.colorize( Managers.getLocalesConfig().getChest_question_world() ) );
			str.append( System.getProperty("line.separator")  );
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
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_onlyonsurface() );
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			if( in.equalsIgnoreCase("true") ){
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
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_preservecontent() );
		}
			
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			// Récuperation de données
	    	if( in.equalsIgnoreCase("true") ){
	    		treasure.set_preservecontent(true);
	    	}else{
	    		treasure.set_preservecontent(false);
	    	}    	    	
	    	return new WaitingEndPrompt( StringUtils.colorize( Managers.getLocalesConfig().getChest_question_waitingend() ), 
	    			StringUtils.colorize( Managers.getLocalesConfig().getChest_success_create() ) ); 
		}
		
	}
	
	
	/*public class AskAllowedIds extends MaterialPrompt {

		private List<Material> _materials;
		
		@Override
		public String getPromptText(ConversationContext arg0) {
			return messageConfig.getString( LocalesNodes.CreateChest.CHEST_CREATE_ASK_ALLOWEDSID.getConfigNode() );
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
			
			// Si on demande la fin du prompt on stocke et passe à étape suivante
	    	if( in.equalsIgnoreCase( messageConfig.getString( LocalesNodes.EXIT.getConfigNode() ) ) ){
	    		// On stocke dans le context et dans l'instance de treasure
	    		treasure.set_placesMaterials(this._materials);
	        	// On lance le WaitingEndPrompt
	    		return new AskAppearSentence();
			}
	    	
	    	// On boucle pour attendre les prochains blocks
	    	return this;
		}
		
	}*/
	
	
	public class AskAppearSentence extends ValidatingPrompt{

		@Override
		public String getPromptText(ConversationContext context) {
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_appearmessage() );
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			return true;
		}
		
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String input) {
			
			treasure.set_appearMessage(input);
			return new AskDisAppearSentence();
		}

		
	}

	public class AskDisAppearSentence extends ValidatingPrompt{

		@Override
		public String getPromptText(ConversationContext context) {
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_disappearmessage() );
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			return true;
		}
		
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String input) {
			
			treasure.set_disappearMessage(input);
			return new AskFoundSentence();
		}

		
	}

	public class AskFoundSentence extends ValidatingPrompt{

		@Override
		public String getPromptText(ConversationContext context) {
			return StringUtils.colorize( Managers.getLocalesConfig().getChest_question_foundmessage() );
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			return true;
		}
		
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String input) {
			
			treasure.set_foundMessage(input);
			return new WaitingEndPrompt( 
					StringUtils.colorize( Managers.getLocalesConfig().getChest_question_waitingend() ), 
	    			StringUtils.colorize( Managers.getLocalesConfig().getChest_success_create() ) ); 
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
	
	
	

	public class ChestCreateProcedureException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public ChestCreateProcedureException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
	    }		
	}

	
	
}
