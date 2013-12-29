package org.shortrip.boozaa.plugins.bootreasure.events.chest;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;

public final class TreasureChestBreakEvent extends Events {


	public TreasureChestBreakEvent(final HumanEntity humanEntity, final Treasure t){

		super();
		if( humanEntity instanceof Player ){
			
			Player player = (Player)humanEntity;			
			// On prend le Treasure dans le Cache
			t.found(player);
			
		}	
				
	}
	
	
	
	
}
