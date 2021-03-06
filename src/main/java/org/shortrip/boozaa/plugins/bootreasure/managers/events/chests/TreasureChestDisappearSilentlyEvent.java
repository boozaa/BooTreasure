package org.shortrip.boozaa.plugins.bootreasure.managers.events.chests;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.Managers;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.Events;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;



public class TreasureChestDisappearSilentlyEvent extends Events {

	private Plugin plugin;
	
	public TreasureChestDisappearSilentlyEvent(final Plugin plugin, final String id){
		
		super();
		this.plugin = plugin;
		Log.debug("TreasureChestDisappearSilentlyEvent event for id " + id);
		// On prend le Treasure dans le Cache
		if( Managers.getCacheManager().exists(id)){
			Log.debug("Treasure exists in cache: " + id);
			Bukkit.getServer().getScheduler().runTask(this.plugin, new Runnable() {
				
				@Override
				public void run() {
					final TreasureChest t = (TreasureChest) Managers.getCacheManager().get_treasureCache().getObject(id);
					t.disAppearSilently();	
				}
				
			}); 			
			
		}else{
			Log.debug("Treasure didn't exists in cache: " + id);
		}
		
	}
	
	
}
