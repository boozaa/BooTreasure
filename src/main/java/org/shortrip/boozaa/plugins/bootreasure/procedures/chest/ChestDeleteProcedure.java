package org.shortrip.boozaa.plugins.bootreasure.procedures.chest;

import java.util.HashMap;
import java.util.List;
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
import org.shortrip.boozaa.plugins.bootreasure.EventsDAO;
import org.shortrip.boozaa.plugins.bootreasure.TreasureDAO;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import com.j256.ormlite.stmt.QueryBuilder;

public class ChestDeleteProcedure implements Runnable {

	private volatile TreasureChest treasure;	
	private Plugin plugin;
	private Player player;
	private World world;
	private Location chestLocation;
	
	private final String END = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.end");
	
	
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
		            .withEscapeSequence( END )
		            .withPrefix(new ConversationPrefix() {	 
		                @Override
		                public String getPrefix(ConversationContext arg0) { return BooTreasure.getConfigManager().get("messages.yml").getString("locales.edit.chest.prefix").replaceAll("&", "§") + System.getProperty("line.separator"); }	 
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
		            	
		            		
		            		// Store in database
							// check if uuid exists
							QueryBuilder<TreasureDAO, String> statementBuilder = BooTreasure.get_treasureDAO().queryBuilder();
							statementBuilder.where().like( TreasureDAO.UUID_DATE_FIELD_NAME, treasure.get_id() );
							List<TreasureDAO> treasuresDAO = BooTreasure.get_treasureDAO().query(statementBuilder.prepare());
							if( treasuresDAO.isEmpty() == false){
								// Here an entry in database exists for this uuid
								for( TreasureDAO trDAO : treasuresDAO ){
									// Create new event for this entry
									EventsDAO e = new EventsDAO( trDAO, EventsDAO.EventType.REMOVED, player.getName(), treasure.get_block().getLocation() );
									// Create this entry
									BooTreasure.get_eventsDAO().create(e);
								}
							}else{
								// Add an entry in the database
								TreasureDAO tdao = new TreasureDAO( treasure.get_id(), treasure.get_name(), treasure.get_onlyonsurface(), treasure.get_preservecontent() );
								// Create new TreasureDAO in database
								BooTreasure.get_treasureDAO().create( tdao );
								// Associate new event entry
								EventsDAO e = new EventsDAO( tdao, EventsDAO.EventType.REMOVED, player.getName(), treasure.get_block().getLocation() );
								// Create this EventsDAO in database
								BooTreasure.get_eventsDAO().create(e);
							}
		            		
		            	
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
			build.append( BooTreasure.getConfigManager().get("messages.yml").getString("locales.delete.chest.ask.listalltreasures") );
			build.append("\n");
			for( Entry<String, Object> entry : BooTreasure.getCacheManager().getTreasures().entrySet() ){
				//String id = entry.getKey();
				TreasureChest tr = (TreasureChest) entry.getValue();
				build.append( tr.get_name() + "\n" );
			}
			return build.toString().replaceAll("&", "§");
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
