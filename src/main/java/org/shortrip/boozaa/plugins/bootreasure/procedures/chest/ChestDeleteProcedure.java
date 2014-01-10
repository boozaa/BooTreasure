package org.shortrip.boozaa.plugins.bootreasure.procedures.chest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import org.shortrip.boozaa.plugins.bootreasure.constants.Const;
import org.shortrip.boozaa.plugins.bootreasure.dao.EventsDAO.EventType;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

public class ChestDeleteProcedure implements Runnable {

	private volatile TreasureChest treasure;	
	private Plugin plugin;
	private Player player;
	private World world;
	private Location chestLocation;
	
	
	public ChestDeleteProcedure(  Plugin plugin, Player p  ){
		this.plugin = plugin;
		this.player = p;
		this.world = p.getWorld();	
		chestLocation = this.player.getLocation().toVector().add(this.player.getLocation().getDirection().multiply(1)).toLocation(this.world);
		chestLocation.setY(chestLocation.getY()+1);
		this.treasure = new TreasureChest(chestLocation);
	}


	@Override
	public void run() {
		
		try{
			
			Log.debug("ChestDeleteProcedure: list available treasure");
			
			// Le ConversationFactory
			ConversationFactory factory = new ConversationFactory(this.plugin);
			final Map<Object, Object> map = new HashMap<Object, Object>();
			
			// Le treasure
			map.put( "TreasureChest", this.treasure );
			
			
			// On construit la conversation
			Conversation conv = factory
		            .withFirstPrompt(new AskWhatTreasure())
		            .withEscapeSequence( Const.END )
		            .withPrefix(new ConversationPrefix() {	 
		                @Override
		                public String getPrefix(ConversationContext arg0) { return Const.CHEST_DELETE_PREFIX; }	 
		            }).withInitialSessionData(map).withLocalEcho(true)
		            .buildConversation(this.player);
			
			conv.addConversationAbandonedListener(new ConversationAbandonedListener() {	 
		        @Override
		        public void conversationAbandoned(ConversationAbandonedEvent event)
		        {
		            if (event.gracefulExit())
		            {	            	
		            	
		            	Log.debug( "Graceful exit" );
		            	
		            	try{
		            	
		            		
		            		// Store event in database
		            		BooTreasure.getDatabaseManager().addEventToDatabase(treasure, player, EventType.REMOVED);
		            		
		            	
		            	}catch( Exception e){
		        			
		    				Log.warning("ChestDeleteProcedure -> run()" + e);
		    			
		    			}
		            	
		            }
		        }
		    });
		    
		    
		    conv.begin();
			
			

		}catch (Exception e){
			Log.severe("An error occured on ChestDeleteProcedure", e);			
		}
		
	}
	
	
	
	
	/*
	 * First prompt
	 * Ask for a name
	 */
	public class AskWhatTreasure extends ValidatingPrompt {

		
		@Override
		public String getPromptText(ConversationContext arg0) {
			StringBuilder build = new StringBuilder();
			build.append( Const.CHEST_DELETE_LIST );
			build.append("\n");
			for( Entry<String, Object> entry : BooTreasure.getCacheManager().getTreasures().entrySet() ){
				//String id = entry.getKey();
				TreasureChest tr = (TreasureChest) entry.getValue();
				build.append( tr.get_name() + "\n" );
			}
			return build.toString();
		}
		
		@Override
		protected boolean isInputValid(ConversationContext context, String in) {
			
			for( Entry<String, Object> entr : BooTreasure.getCacheManager().getTreasures().entrySet() ){
				// If string sent is a name of a cached treasure
				if( entr.getKey().equalsIgnoreCase(in) ){
					
				}
				treasure = (TreasureChest) entr.getValue();
				
				return true;
			}		
			
			return false;
			
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {			
			
			return Prompt.END_OF_CONVERSATION;			
		}
		
	}
	
	
	
	
	
	
	
}
