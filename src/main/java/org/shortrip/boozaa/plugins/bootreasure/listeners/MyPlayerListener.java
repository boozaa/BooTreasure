package org.shortrip.boozaa.plugins.bootreasure.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestOpenEvent;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.treasures.utils.LocationUtils;
import org.shortrip.boozaa.plugins.bootreasure.utils.ParticleEffects;



/**
 * @author boozaa
 *
 * BooTreasure
 */
public class MyPlayerListener implements Listener {

    
	private Plugin plugin;
	
	/**
	 * Create a MyPlayerListener
	 *
	 * @param plugin The plugin caller
	 */
	public MyPlayerListener( Plugin plugin ){
		this.plugin = plugin;
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
		
		// On vérifie si le block en question est un Chest
		if( (event.getBlock().getState().getType() == Material.CHEST) && ( event.getPlayer() instanceof Player ) ){
			// On vérifie si metadata BooTreasure
        	Chest chest = (Chest) event.getBlock().getState();        	
        	if( chest.hasMetadata("BooTreasure-Chest") ){        		
    			String id = chest.getMetadata("BooTreasure-Chest").get(0).asString();
    			// Call the event				
				Bukkit.getServer().getPluginManager().callEvent(new TreasureChestOpenEvent(this.plugin, event.getPlayer(), id));			
        	}  
		}
		
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
	
	
	@EventHandler
	public void onPlayerMove( PlayerMoveEvent event ){
		
		
		Player player = event.getPlayer();
		// On récupère les Chests dans le radius de 10
		Chest[] chests = LocationUtils.getNearbyChest(event.getPlayer(), 10);
		for( Chest ch : chests ){
			
			if( ch.hasMetadata("BooTreasure-Chest") ){
				
				String id = ch.getMetadata("BooTreasure-Chest").get(0).asString();
				
				if( BooTreasure.getCacheManager().exists(id)){	
					final TreasureChest treasure = (TreasureChest) BooTreasure.getCacheManager().get(id);
					if( treasure != null ){
	        			
						Bukkit.getScheduler().runTask(BooTreasure.getInstance(), new Runnable(){

							@Override
							public void run() {
								ParticleEffects.sendToLocation(ParticleEffects.ENDER, treasure.get_block().getLocation(), 1.0F, 1.0F, 1.0F, 0, 20);
							}
							
						});
	            		
	        		}					
					
				}
				
			}
		}
		
	}

}
