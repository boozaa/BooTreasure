package org.shortrip.boozaa.plugins.bootreasure.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestOpenEvent;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;



/**
 * @author boozaa
 *
 * BooTreasure
 */
public class MyPlayerListener implements Listener {

    
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	/**
	 * Create a MyPlayerListener
	 *
	 * @param plugin
     *            The plugin caller
	 * @return A new MyPlayerListener
	 */
	public MyPlayerListener( Plugin bplugin ){
		plugin = bplugin;
	}
	
	
	@EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e){
        
		
		//if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest){
		if ( e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest ){    
        	// On vérifie si metadata BooTreasure
        	Chest chest = (Chest) e.getInventory().getHolder();        	
        	if( chest.hasMetadata("BooTreasure-Chest") ){        		
    			String id = chest.getMetadata("BooTreasure-Chest").get(0).asString();
    			// Call the event
				Log.debug("Chest metadata BooTreasure found, this opened chest is a treasure"); 
				Bukkit.getServer().getPluginManager().callEvent(new TreasureChestOpenEvent(this.plugin, (Player) e.getPlayer(), id));			
        	}        	
        }
        
    }
	
	
	@EventHandler
    public void onInventoryCloseEvent(final InventoryCloseEvent e){
		/*
		//if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest){
		if ( e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest ){
            // On vérifie si metadata BooTreasure
        	Chest chest = (Chest) e.getInventory().getHolder();
        	if( chest.hasMetadata("BooTreasure-Chest") ){
    			String id = chest.getMetadata("BooTreasure-Chest").get(0).asString(); 
    			final ChestTreasure t = (ChestTreasure) BooTreasure.get_treasureCache().getObject(id);			
    			// Call the event
    			if( t != null ){
            		Log.debug("Chest metadata BooTreasure found, this closed chest was a treasure");
    				Bukkit.getServer().getPluginManager().callEvent(new TreasureChestCloseEvent(this.plugin, e.getPlayer(), t)); 
    			}
    			   			
        	}        	
        }
        */
    }
	
	@EventHandler
	public void onBlockBreak( BlockBreakEvent event ){
		/*
		// On vérifie si le block en question est un Chest
		if( event.getBlock().getState().getType() == Material.CHEST ){
			Chest chest = (Chest) event.getBlock().getState();
			// On vérifie si ce Chest a la metadata BooTreasure
			if( chest.hasMetadata("BooTreasure-Chest") ){
				// Ok ici c'est un BooChest on lance event BooChestBreakEvent
				String id = chest.getMetadata("BooTreasure-Chest").get(0).asString();
    			final ChestTreasure t = (ChestTreasure) BooTreasure.get_treasureCache().getObject(id);
				// Call the event
    			if( t != null ){
            		Log.debug("Chest metadata BooTreasure found, this breaking chest is a treasure");
    				Bukkit.getServer().getPluginManager().callEvent(new TreasureChestBreakEvent(event.getPlayer(), t)); 
    			}
    			
			}
		}
		*/
	}
	
	
	
	@EventHandler
	public void onPlayerJoin( PlayerJoinEvent event ){
		
		/*
		Player player = event.getPlayer();
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		
		Objective objective = board.registerNewObjective("lives", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("lives");
		
		player.setScoreboard(board);
		*/
		
	}
	
	
	@EventHandler
	public void onPlayerQuit( PlayerQuitEvent event ){
				
	}
	
	/*
	@EventHandler
	public void onPlayerMove( PlayerMoveEvent event ){
		
		
		Player player = event.getPlayer();
		// On récupère les Chests dans le radius de 10
		Chest[] chests = LocationUtils.getNearbyChest(event.getPlayer(), 10);
		for( Chest ch : chests ){
			
			if( ch.hasMetadata("BooTreasure") ){
				
				String id = ch.getMetadata("BooTreasure").get(0).asString();
				
				if( BooTreasure.getTreasureCache().exists(id)){	
					final Treasure treasure = (Treasure) BooTreasure.getTreasureCache().getObject(id);
					if( treasure != null ){
	        			// On lance effet au centre du block
	            		Bukkit.getWorld(treasure.get_world()).playEffect( ch.getBlock().getLocation(), Effect.MOBSPAWNER_FLAMES,10);
	            		
	        		}					
					
				}
				
			}
		}
		
	}
	*/
}
