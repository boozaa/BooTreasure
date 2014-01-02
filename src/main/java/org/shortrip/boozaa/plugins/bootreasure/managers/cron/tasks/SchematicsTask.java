package org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks;

import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronTask;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public class SchematicsTask extends CronTask {

	public SchematicsTask(String pattern) {
		super("Schematics Treasure", pattern);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void execute(TaskExecutionContext tec) throws RuntimeException {
        Log.debug("Schematics Treasure start");
    }

}
