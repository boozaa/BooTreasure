package org.shortrip.boozaa.plugins.bootreasure.cron;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import it.sauronsoftware.cron4j.TaskExecutor;

public class CronTaskExecutionContext implements TaskExecutionContext {

	@Override
	public Scheduler getScheduler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskExecutor getTaskExecutor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isStopped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pauseIfRequested() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompleteness(double arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusMessage(String arg0) {
		// TODO Auto-generated method stub
		
	}


	
}
