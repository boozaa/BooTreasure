package org.shortrip.boozaa.plugins.bootreasure.managers;

import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.MyPlayerListener;


public class MyEvents extends Manager {

	private Plugin plugin;
	
	public MyEvents(BooTreasure booTreasure) {
		this.plugin = booTreasure;
		// MyPlayerListener
		this.plugin.getServer().getPluginManager().registerEvents( new MyPlayerListener(this.plugin), this.plugin );
	}

	@Override
	public void onDisable() {
		
	}
	
}
