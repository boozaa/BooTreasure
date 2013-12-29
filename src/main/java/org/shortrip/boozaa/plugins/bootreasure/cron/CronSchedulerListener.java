package org.shortrip.boozaa.plugins.bootreasure.cron;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;

import org.shortrip.boozaa.plugins.bootreasure.Log;

import it.sauronsoftware.cron4j.SchedulerListener;
import it.sauronsoftware.cron4j.TaskExecutor;

/*
* Copyright (C) 2012  boozaa
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
public class CronSchedulerListener implements SchedulerListener {

	@Override
	public void taskFailed(TaskExecutor executor, Throwable error) {
		CronTask task = (CronTask)executor.getTask();
		Log.log(Level.WARNING, "Task '" + task.getName() + "' failed with error :");
		Log.log(Level.WARNING, error.getLocalizedMessage());
		error.printStackTrace();
		if( error.getCause() != null){
			Log.log(Level.WARNING, getStackTrace(error.getCause()));
		}		
	}

	@Override
	public void taskLaunching(TaskExecutor executor) {
		CronTask task = (CronTask)executor.getTask();
		task.setId(executor.getGuid());
		//Core.debug("Cron Task Launched: " + task.getName());
	}

	@Override
	public void taskSucceeded(TaskExecutor executor) {
		//CronTask task = (CronTask)executor.getTask();
		//Core.debug("Cron Task Succeeded: " + task.getName());
	}

	
	public static String getStackTrace(Throwable ex) {
        Writer out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);
        ex.printStackTrace(pw);
        return out.toString();
    }
	
	
}
