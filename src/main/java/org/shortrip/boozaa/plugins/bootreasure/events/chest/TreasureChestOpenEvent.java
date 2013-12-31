package org.shortrip.boozaa.plugins.bootreasure.events.chest;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;

public final class TreasureChestOpenEvent extends Events {

	private Plugin plugin;

	public TreasureChestOpenEvent(Plugin plugin, HumanEntity humanEntity, final Treasure t){

		super();
		this.plugin = plugin;
		final Player player = (Player)humanEntity;			
		// On lance la méthode found
		Bukkit.getServer().getScheduler().runTask(this.plugin, new Runnable() {

			@Override
			public void run() {
				Log.debug("Chest opened : " + t.get_name());
				t.found(player);
			} 
		
		});		
		
	}
	
	
}
