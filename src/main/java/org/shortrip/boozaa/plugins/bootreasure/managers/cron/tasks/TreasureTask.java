package org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks;

import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.TreasureType;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronTask;


public class TreasureTask extends CronTask {

	@SuppressWarnings("unused")
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
		TreasureType type = treasure.get_type();
		if( type == TreasureType.CHEST ){			
			
			TreasureChest ch = (TreasureChest)treasure;			
			// Launch chest appear event
			BooTreasure.get_eventsManager().chestAppearEvent(ch);
			
		}
    }

}
