/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.shortrip.boozaa.plugins.bootreasure.managers.cron;

import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

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
public abstract class CronTask extends Task {

	
	private String name;
	private SchedulingPattern cronPattern;
    @SuppressWarnings("unused")
	private Boolean pauseAllowed = true;
    @SuppressWarnings("unused")
	private Boolean stopAllowed = true;
    private String id;
    
    
    public void setId(String id) {
		this.id = id;
	}

    public String get_Id() {
    	return id;
	}


	public CronTask(String name, String pattern){
    	// If pattern is valid add the task to the list
    	if( SchedulingPattern.validate(pattern) ){
    		this.name = name;
    		this.cronPattern = new SchedulingPattern(pattern);
    	}    	
    }

    
    public String getName(){
    	return this.name;
    }
    

    public void setCronPattern(SchedulingPattern cronPattern) {
        this.cronPattern = cronPattern;
    }



    public SchedulingPattern getCronPattern() {
        return cronPattern;
    }
    
    
    @Override
    public void execute(TaskExecutionContext tec) throws RuntimeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
