package org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks;

import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.Managers;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronTask;
import org.shortrip.boozaa.plugins.bootreasure.treasures.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureType;


public class TreasureTask extends CronTask {
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	private Treasure treasure;
	
	public TreasureTask(Plugin plugin, Treasure treasure) {
		super(treasure.get_id(), treasure.get_pattern());
		this.plugin = plugin;
		this.treasure = treasure;
	}
	
	@Override
    public void execute(TaskExecutionContext tec) throws RuntimeException {
        // On lance event selon le type de treasure
		TreasureType type = treasure.get_type();
		if( type == TreasureType.CHEST ){			
			
			TreasureChest ch = (TreasureChest)treasure;			
			// Launch chest appear event
			Managers.getEventsManager().chestAppearEvent(ch);
			
		}
    }

}
