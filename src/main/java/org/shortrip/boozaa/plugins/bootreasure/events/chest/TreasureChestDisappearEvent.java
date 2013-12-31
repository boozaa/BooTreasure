package org.shortrip.boozaa.plugins.bootreasure.events.chest;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;

public final class TreasureChestDisappearEvent extends Events {


	public TreasureChestDisappearEvent(Plugin plugin, final String id){

		super();
		// On prend le BooChest dans le Cache
		if( BooTreasure.get_treasureCache().exists(id)){
			// On lance sa méthode disappear
			Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {

				@Override
				public void run() {
					final ChestTreasure t = (ChestTreasure) BooTreasure.get_treasureCache().getObject(id);
					Log.debug("Chest disappear: " + t.get_name());						    															
					try {
						t.disappear();
					} catch (Exception e) {
						e.printStackTrace();
						StringBuilder build = new StringBuilder();
						String nl = System.getProperty("line.separator");
						build.append( "TreasureChestDisappearEvent()" );
						build.append(nl);
						build.append( "Id: " + t.get_id() );
						build.append(nl);
						build.append( "Inventory: " + Arrays.toString(t.get_inventory()) );
						Log.severe(build.toString(), e);
					}
				} }); 			
		}		
		
	}
	
	
}
