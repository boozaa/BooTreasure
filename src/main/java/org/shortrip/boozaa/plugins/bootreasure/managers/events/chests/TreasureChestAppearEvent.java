package org.shortrip.boozaa.plugins.bootreasure.managers.events.chests;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.Events;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public class TreasureChestAppearEvent extends Events {

	private Plugin plugin;
	
	public TreasureChestAppearEvent(Plugin plugin, final String id){
		
		super();
		this.plugin = plugin;
		Log.debug("TreasureChestAppearEvent event for id " + id);
		// On prend le Treasure dans le Cache
		if( BooTreasure.get_cacheManager().exists(id)){	
			Log.debug("Treasure exists in cache: " + id);
			Bukkit.getServer().getScheduler().runTask(this.plugin, new Runnable() {

				@Override
				public void run() {
					final TreasureChest t = (TreasureChest) BooTreasure.get_cacheManager().get_treasureCache().getObject(id);
					Log.debug("Name: " + t.get_name());
					try {
						t.appear();
						t.announceAppear();
					} catch (Exception e) {
						e.printStackTrace();
						StringBuilder build = new StringBuilder();
						String nl = System.getProperty("line.separator");
						build.append( "TreasureChestAppearEvent()" );
						build.append(nl);
						build.append( "Id: " + t.get_id() );
						build.append(nl);
						build.append( "Inventory: " + Arrays.toString(t.get_inventory()) );
						Log.severe(build.toString(), e);
					}
				} }); 
			
		}else{
			Log.debug("Treasure didn't exists in cache: " + id);
		}
		        
	}
	
}
