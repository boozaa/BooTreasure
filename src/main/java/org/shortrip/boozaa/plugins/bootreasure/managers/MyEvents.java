package org.shortrip.boozaa.plugins.bootreasure.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestAppearEvent;


public class MyEvents extends Manager {

	private Plugin plugin;
	
	public MyEvents(BooTreasure booTreasure) {
		this.plugin = booTreasure;
		// MyPlayerListener
		this.plugin.getServer().getPluginManager().registerEvents( new MyPlayerListener(this.plugin), this.plugin );
	}
	
	
	public void chestAppearEvent( TreasureChest treasure ){
		Bukkit.getServer().getPluginManager().callEvent(new TreasureChestAppearEvent(this.plugin, treasure.get_id()));
	}
	
	
	

	@Override
	public void onDisable() {
		
	}
	
}
