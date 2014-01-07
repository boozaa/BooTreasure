package org.shortrip.boozaa.plugins.bootreasure.managers.events.chests;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.Events;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

public class TreasureChestOpenEvent extends Events {

	private Plugin plugin;
	
	public TreasureChestOpenEvent(Plugin plugin, final Player player, final String id ) {
		
		super();
		this.plugin = plugin;
		Log.debug("TreasureChestOpenEvent event for id " + id);
		
		// On prend le Treasure dans le Cache
		if( BooTreasure.getCacheManager().exists(id)){	
			Log.debug("Treasure exists in cache: " + id);
			Bukkit.getServer().getScheduler().runTask(this.plugin, new Runnable() {

				@Override
				public void run() {
					final TreasureChest t = (TreasureChest) BooTreasure.getCacheManager().get_treasureCache().getObject(id);
					Log.debug("Name: " + t.get_name());
					try {
						t.found(player);
						//t.announceAppear();
					} catch (Exception e) {
						e.printStackTrace();
						StringBuilder build = new StringBuilder();
						String nl = System.getProperty("line.separator");
						build.append( "TreasureChestOpenEvent()" );
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