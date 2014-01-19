package org.shortrip.boozaa.plugins.bootreasure.managers;

import org.bukkit.Bukkit;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestAppearEvent;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestDisappearEvent;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestDisappearSilentlyEvent;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;


public class MyEvents {

	private BooTreasure plugin;
	
	public MyEvents(BooTreasure booTreasure) {
		this.plugin = booTreasure;
		
	}
	
	
	public void chestAppearEvent( TreasureChest treasure ){
		Bukkit.getServer().getPluginManager().callEvent(new TreasureChestAppearEvent(this.plugin, treasure.get_id()));
	}
	
	public void chestDisappearDelayedEvent( final TreasureChest treasure ){
		long delay = treasure.get_duration()*20;
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            @Override 
            public void run() {		                
            	// Event to disappear
                TreasureChestDisappearEvent event = new TreasureChestDisappearEvent(plugin, treasure.get_id());
                // Call the event
        		Bukkit.getServer().getPluginManager().callEvent(event);		            	
            }
        }, delay);
	}
	
	public void chestDisappearSilentlyEvent( TreasureChest treasure ){
		Bukkit.getServer().getPluginManager().callEvent(new TreasureChestDisappearSilentlyEvent(this.plugin, treasure.get_id()));
	}
	
	

	public void onDisable() {
		
	}
	
}
