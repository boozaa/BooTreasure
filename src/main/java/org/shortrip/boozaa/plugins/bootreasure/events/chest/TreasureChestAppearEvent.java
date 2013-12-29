package org.shortrip.boozaa.plugins.bootreasure.events.chest;


import org.bukkit.Bukkit;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;


public final class TreasureChestAppearEvent extends Events {


	public TreasureChestAppearEvent(final String id){
		
		super();
		Log.debug("TreasureChestAppearEvent event for id " + id);
		// On prend le Treasure dans le Cache
		if( BooTreasure.getTreasureCache().exists(id)){	
			Log.debug("Treasure exists in cache: " + id);
			Bukkit.getServer().getScheduler().runTask(BooTreasure.getInstance(), new Runnable() {

				@Override
				public void run() {
					final Treasure t = (Treasure) BooTreasure.getTreasureCache().getObject(id);
					Log.debug("Name: " + t.get_name());
					t.appear();
				} }); 
			
		}else{
			Log.debug("Treasure didn't exists in cache: " + id);
		}
		        
	}
	
	
}
