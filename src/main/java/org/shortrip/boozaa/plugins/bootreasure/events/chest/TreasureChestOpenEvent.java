package org.shortrip.boozaa.plugins.bootreasure.events.chest;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;

public final class TreasureChestOpenEvent extends Events {


	public TreasureChestOpenEvent(HumanEntity humanEntity, final Treasure t){

		super();
		final Player player = (Player)humanEntity;			
		// On lance la méthode found
		Bukkit.getServer().getScheduler().runTask(BooTreasure.getInstance(), new Runnable() {

		@Override
		public void run() {
			Log.debug("Chest opened : " + t.get_name());
			t.found(player);
		} }); ;			
		
	}
	
	
}
