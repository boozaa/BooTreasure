package org.shortrip.boozaa.plugins.bootreasure.events.chest;

import org.bukkit.Bukkit;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;

public final class TreasureChestDisappearEvent extends Events {


	public TreasureChestDisappearEvent(final String id){

		super();
		// On prend le BooChest dans le Cache
		if( BooTreasure.getTreasureCache().exists(id)){
			// On lance sa méthode disappear
			Bukkit.getServer().getScheduler().runTask(BooTreasure.get_instance(), new Runnable() {

				@Override
				public void run() {
					final Treasure t = (Treasure) BooTreasure.getTreasureCache().getObject(id);
					Log.debug("Chest disappear: " + t.get_name());
					t.disappear();
				} }); 			
		}		
		
	}
	
	
}
