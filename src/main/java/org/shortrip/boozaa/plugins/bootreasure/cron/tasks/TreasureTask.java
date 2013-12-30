package org.shortrip.boozaa.plugins.bootreasure.cron.tasks;

import it.sauronsoftware.cron4j.TaskExecutionContext;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.cron.CronTask;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.TreasureType;
import org.shortrip.boozaa.plugins.bootreasure.events.chest.TreasureChestAppearEvent;


public class TreasureTask extends CronTask {

	private Plugin plugin;
	private Treasure treasure;
	
	public TreasureTask(Plugin plugin, Treasure treasure) {
		super("Treasure", treasure.get_pattern());
		this.plugin = plugin;
		this.treasure = treasure;
	}
	
	@Override
    public void execute(TaskExecutionContext tec) throws RuntimeException {
        // On lance event selon le type de treasure
		TreasureType type = treasure.get_treasureType();
		// On conserve l'id du CronTask pour pouvoir l'arréter
		//treasure.set_id(this.getId());
		if( type == TreasureType.CHEST ){			
			Bukkit.getServer().getPluginManager().callEvent(new TreasureChestAppearEvent(this.plugin, treasure.get_id()));
		}
		
		
    }

}
