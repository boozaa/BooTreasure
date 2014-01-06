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
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks.TreasureTask;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.ChestCreateProcedure.AskName;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.ChestCreateProcedure.ChestCreateProcedureException;
import org.shortrip.boozaa.plugins.bootreasure.treasures.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.ChatMessage;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;



public class ChestEditProcedure implements Runnable {

	private volatile TreasureChest treasure;	
	private Plugin plugin;
	private Player player;
	private World world;
	private Location chestLocation;

	private final String YES = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.agree");
	@SuppressWarnings("unused")
	private final String NO = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.disagree");
	private final String EXIT = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.exit");
	@SuppressWarnings("unused")
	private final String END = BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.end");
	
	
	public ChestEditProcedure(  Plugin plugin, Player p  ){
		
		this.plugin = plugin;
		this.player = p;
		this.world = p.getWorld();
		chestLocation = this.player.getLocation().toVector().add(this.player.getLocation().getDirection().multiply(1)).toLocation(this.world);
		chestLocation.setY(chestLocation.getY()+1);
		this.treasure = new TreasureChest(chestLocation);
		/*
		// Search treasure in cache
		if( BooTreasure.getCacheManager().exists(treasureId ) ){
			this.treasure = (TreasureChest) BooTreasure.getCacheManager().get(treasureId);
			// Set the new Location of this chest front of the player
			this.treasure.set_block( chestLocation.getBlock() );
		}
		*/
	}
	
	
	@Override
	public void run() {
		
		try{
			
			Log.debug("ChestEditProcedure: list available treasure");

			ChatMessage.forPlayer(player, "List of treasures:");
			for( Entry<String, Object> entry : BooTreasure.getCacheManager().getTreasures().entrySet() ){
				String id = entry.getKey();
				Treasure tr = (Treasure) entry.getValue();
				ChatMessage.forPlayer(player, id + " - " + tr.get_name() );				
			}
			
			// Apparition du Chest devant le player
			//this.treasure.chestAppear();
			
			// Le ConversationFactory
			ConversationFactory factory = new ConversationFactory(this.plugin);
			final Map<Object, Object> map = new HashMap<Object, Object>();
			
			// Le treasure
			map.put( "TreasureChest", this.treasure );
			
			/*
			// On construit la conversation
			Conversation conv = factory
		            .withFirstPrompt(new AskName())
		            .withEscapeSequence( END )
		            .withPrefix(new ConversationPrefix() {	 
		                @Override
		                public String getPrefix(ConversationContext arg0) { return BooTreasure.getConfigManager().get("messages.yml").getString("locales.create.prefix").replaceAll("&", "§") + System.getProperty("line.separator"); }	 
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
		            	BooTreasure.getCacheManager().get_treasureCache().add(treasure.get_id(), treasure);
		            	Log.debug("ChestTreasure stored in cache");
		            	
		            	// Stockage dans treasures.yml
		            	BooTreasure.getConfigManager().saveNewTreasureChest(treasure);
		            	Log.debug("ChestTreasure saved in treasures.yml");
		            		            		            	
		            	// On peut faire disparaitre le coffre aprés l'avoir donné au cron
		            	Log.debug("Launch disappear event");
		            	BooTreasure.getEventsManager().chestDisappearSilentlyEvent(treasure);
		            	
		            	// Give it to the cronManager
		            	BooTreasure.getCronManager().addTask(new TreasureTask(plugin, treasure));
		            	
		            }
		        }
		    });
		    
		    
		    conv.begin();
			*/
			
		}catch (Exception e){
			Log.severe("An error occured on ChestCreateProcedure", 
					new ChestEditProcedureException("An error occured on ChestCreateProcedure", e));			
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
