package org.shortrip.boozaa.plugins.bootreasure.events.chest;


import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;


public final class TreasureChestAppearEvent extends Events {

	private Plugin plugin;
	

	public TreasureChestAppearEvent(Plugin plugin, final String id){
		
		super();
		this.plugin = plugin;
		Log.debug("TreasureChestAppearEvent event for id " + id);
		// On prend le Treasure dans le Cache
		if( BooTreasure.get_treasureCache().exists(id)){	
			Log.debug("Treasure exists in cache: " + id);
			Bukkit.getServer().getScheduler().runTask(this.plugin, new Runnable() {

				@Override
				public void run() {
					final Treasure t = (Treasure) BooTreasure.get_treasureCache().getObject(id);
					Log.debug("Name: " + t.get_name());
					t.appear();
				} }); 
			
		}else{
			Log.debug("Treasure didn't exists in cache: " + id);
		}
		        
	}
	
	
}
