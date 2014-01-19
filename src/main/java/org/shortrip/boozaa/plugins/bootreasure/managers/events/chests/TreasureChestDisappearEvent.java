package org.shortrip.boozaa.plugins.bootreasure.managers.events.chests;

import org.bukkit.Bukkit;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Managers;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.Events;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

public class TreasureChestDisappearEvent extends Events {

	public TreasureChestDisappearEvent(final BooTreasure plugin, final String id){

		super();
		// On prend le BooChest dans le Cache
		if( Managers.getCacheManager().get_treasureCache().exists(id)){
			// On lance sa méthode disappear
			Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {

				@Override
				public void run() {
					final TreasureChest t = (TreasureChest) Managers.getCacheManager().get_treasureCache().getObject(id);
					Log.debug("Chest disappear: " + t.get_name());						    															
					try {
						
						// If not already found 
						if( t.get_found() == false){
							// Disappear and send message
							t.disappear();
							t.announceDisAppear();
						}else{
							// Only disappear
							t.disappear();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						StringBuilder build = new StringBuilder();
						String nl = System.getProperty("line.separator");
						build.append( "TreasureChestDisappearEvent()" );
						build.append(nl);
						build.append( "Id: " + t.get_id() );
						build.append(nl);
						build.append( "Inventory: " + t.get_inventory().toString() );
						Log.severe(plugin, build.toString(), e);
					}
				} }); 			
		}		
		
	}
	
}
