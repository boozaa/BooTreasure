package org.shortrip.boozaa.plugins.bootreasure.managers;


import java.util.Calendar;
import java.util.TimeZone;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronScheduler;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronSchedulerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronTask;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronTaskCollector;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;
import lombok.Getter;


public class MyCron {
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	// The Cron4J Scheduler instance
	@Getter private CronScheduler _scheduler;
	@Getter private CronTaskCollector _taskCollector;
	

	public MyCron(Plugin booTreasure) {
		this.plugin = booTreasure;
		// Instanciate a Cron Scheduler
		_scheduler = new CronScheduler();
		// Add our listener to it
		_scheduler.addSchedulerListener(new CronSchedulerListener());
		// TaskCollector
		_taskCollector = new CronTaskCollector(booTreasure);
		_scheduler.addTaskCollector(_taskCollector);
		// Host system TimeZone
		TimeZone tz = Calendar.getInstance().getTimeZone();
		// Set its TimeZone to this TimeZone
		_scheduler.setTimeZone(tz);
		// Start the scheduler
		_scheduler.start();
		Log.info("Cron Scheduler started with TimeZone set to "
						+ tz.getDisplayName());
	}
	
	
	public void addTask( CronTask task ){		
		_taskCollector.addTask(task);		
	}


	
	public void onDisable() {
				
		_scheduler.removeTaskCollector(_taskCollector);
		_scheduler.stop();
		_scheduler = null;
		_taskCollector = null;
		
	}
	
	
	
}
