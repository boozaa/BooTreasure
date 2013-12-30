package org.shortrip.boozaa.plugins.bootreasure.events.chest;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;

public class TreasureChestDisappearSilentlyEvent extends Events {

	private Plugin plugin;
	
	public TreasureChestDisappearSilentlyEvent(Plugin plugin, final String id){

		super();
		this.plugin = plugin;
		// On prend le BooChest dans le Cache
		if( BooTreasure.get_treasureCache().exists(id)){
			// On lance sa méthode disappear
			Bukkit.getServer().getScheduler().runTask(this.plugin, new Runnable() {

				@Override
				public void run() {
					final ChestTreasure t = (ChestTreasure) BooTreasure.get_treasureCache().getObject(id);
					Log.debug("Chest disappear: " + t.get_name());
					t.chestDisappear();
				} }); 			
		}		
		
	}
	
}
