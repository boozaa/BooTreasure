package org.shortrip.boozaa.plugins.bootreasure.events.chest;

import java.util.Arrays;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;

public final class TreasureChestBreakEvent extends Events {


	public TreasureChestBreakEvent(final HumanEntity humanEntity, final ChestTreasure t){

		super();
		if( humanEntity instanceof Player ){
			
			Player player = (Player)humanEntity;			
			// On prend le Treasure dans le Cache
			try {
				t.found(player);
			} catch (Exception e) {
				e.printStackTrace();
				StringBuilder build = new StringBuilder();
				String nl = System.getProperty("line.separator");
				build.append( "Error during TreasureChestAppearEvent" );
				build.append(nl);
				build.append( "Id: " + t.get_id() );
				build.append(nl);
				build.append( "Inventory: " + Arrays.toString(t.get_inventory()) );
				Log.severe(build.toString(), e);
			}
			
		}	
				
	}
	
	
	
	
}
