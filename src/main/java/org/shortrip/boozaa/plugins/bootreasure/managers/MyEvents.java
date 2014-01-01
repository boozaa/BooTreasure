package org.shortrip.boozaa.plugins.bootreasure.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestAppearEvent;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestDisappearEvent;


public class MyEvents extends Manager {

	private Plugin plugin;
	
	public MyEvents(BooTreasure booTreasure) {
		this.plugin = booTreasure;
		// MyPlayerListener
		this.plugin.getServer().getPluginManager().registerEvents( new MyPlayerListener(this.plugin), this.plugin );
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
	

	@Override
	public void onDisable() {
		
	}
	
}
