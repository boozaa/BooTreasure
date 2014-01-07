package org.shortrip.boozaa.plugins.bootreasure.procedures.chest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.treasures.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;



public class ChestEditProcedure implements Runnable {

	
	@SuppressWarnings("unused")
	private volatile TreasureChest treasure;	
	private Plugin plugin;
	private Player player;
	private World world;
	private Location chestLocation;
	
	@SuppressWarnings("unused")
	private Map<String, String> treasuresIdAndNameMap = new HashMap<String, String>();
	

	@SuppressWarnings("unused")
	private final String YES = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.agree");
	@SuppressWarnings("unused")
	private final String NO = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.disagree");
	@SuppressWarnings("unused")
	private final String EXIT = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.exit");
	private final String END = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.end");
	
	
	
	public ChestEditProcedure(  Plugin plugin, Player p  ){
		
		this.plugin = plugin;
		this.player = p;
		this.world = p.getWorld();
		chestLocation = this.player.getLocation().toVector().add(this.player.getLocation().getDirection().multiply(1)).toLocation(this.world);
		chestLocation.setY(chestLocation.getY()+1);
		
	}
	
	
	
	@Override
	public void run() {
		
		try{
			
			Log.debug("ChestEditProcedure: list available treasure");
			
			// Le ConversationFactory
			ConversationFactory factory = new ConversationFactory(this.plugin);			
						
			// On construit la conversation
			Conversation conv = factory
		            .withFirstPrompt(new AskWhatTreasure())
		            .withEscapeSequence( END )
		            .withPrefix(new ConversationPrefix() {	 
		                @Override
		                public String getPrefix(ConversationContext arg0) { return BooTreasure.getConfigManager().get("messages.yml").getString("locales.edit.chest.prefix").replaceAll("&", "§") + System.getProperty("line.separator"); }	 
		            //}).withInitialSessionData(map).withLocalEcho(true)
					}).withLocalEcho(true)
		            .buildConversation(this.player);
			
			conv.addConversationAbandonedListener(new ConversationAbandonedListener() {	 
		        @Override
		        public void conversationAbandoned(ConversationAbandonedEvent event)
		        {
		            if (event.gracefulExit())
		            {	            	
		            	
		            	Log.debug( "Graceful exit" );
		            	
		            }
		        }
		    });		    
		    
		    conv.begin();			
			
		}catch (Exception e){
			Log.severe("An error occured on ChestEditProcedure", e );			
		}		
		
	}

	
	
	/*
	 * First prompt
	 * Ask for a name
	 */
	public class AskWhatTreasure extends ValidatingPrompt {

		private Map<Integer,String> validNames = new HashMap<Integer,String>();
		
		@Override
		public String getPromptText(ConversationContext arg0) {
			StringBuilder build = new StringBuilder();
			build.append( BooTreasure.getConfigManager().get("messages.yml").getString("locales.edit.chest.ask.listalltreasures") );
			build.append("\n");
			int i = 1;
			for( Entry<String, Object> entry : BooTreasure.getCacheManager().getTreasures().entrySet() ){
				//String id = entry.getKey();
				Treasure tr = (Treasure) entry.getValue();
				build.append( i + " - " + tr.get_name() + "\n" );
				validNames.put( i, tr.get_name() );
				i++;
			}
			return build.toString().replaceAll("&", "§");
		}
		
		@Override
		protected boolean isInputValid(ConversationContext context, String in) {
			for( Entry<Integer, String> entry : validNames.entrySet() ){
				if( in.equalsIgnoreCase( String.valueOf( entry.getKey() ) ) || in.equalsIgnoreCase( entry.getValue() ) ){
					return true;
				}
			}
			return false;
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {			
			return Prompt.END_OF_CONVERSATION;			
		}
		
	}
	
	


	public class ChestEditProcedureException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public ChestEditProcedureException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
	    }		
	}

	
}
