package org.shortrip.boozaa.plugins.bootreasure.managers.cron;


import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.Plugin;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskTable;

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
public class CronTaskCollector implements TaskCollector {

	private Plugin _plugin;
	private String _name;
	private List<CronTask> tasks = new ArrayList<CronTask>();
    
    
    public CronTaskCollector(Plugin plugin){
    	this._plugin = plugin;
    	this._name = _plugin.getName();
    }
   	
    
    public int size(){
    	return tasks.size();
    }
    
    
    public Boolean contains(CronTask task){
    	return tasks.contains(task);
    }
    
    
    public Boolean contains( String id ){
    	for( CronTask t : this.tasks ){
    		if( t.get_Id().equalsIgnoreCase(id) )
    			return true;
    	}
    	return false;
    }
    
    public CronTask getTask( String id ){
    	for( CronTask t : this.tasks ){
    		if( t.get_Id().equalsIgnoreCase(id) )
    			return t;
    	}
    	return null;
    }
    
    
    public void addTask(CronTask task){
    	tasks.add(task); 
    }
	
    
	@Override
	public TaskTable getTasks() {
		
		TaskTable table = new TaskTable();        
        // Add each CronTask to the results TaskTable
        for( CronTask cr : tasks ){
        	table.add(cr.getCronPattern(), cr);
        }        
        return table;
        
	}
	
	public void removeTaskById(String id){
		for( CronTask ct : this.tasks ){
			if( ct.get_Id().equalsIgnoreCase(id)){
				this.tasks.remove(ct);
			}
		}
	}

	
	@Override 
	public String toString() {
		return this._name;
	}
	
}
