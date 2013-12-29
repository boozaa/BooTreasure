/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;



/**
 * @author boozaa
 *
 * BooTreasure
 */
public class MainConfig extends Configuration {

	private List<String> messages;
	private Boolean updated;
	
	private static String VERSION = 				"config.version";
	private static String DEBUG = 					"config.debugMode";
	private static String FILTER_WORLDGUARD = 		"config.filter.worldguard";
	
	
	/**
	 * @param sourcepath
	 */
	public MainConfig(String sourcepath) {
		super(sourcepath);
		messages = new ArrayList<String>();
		updated = false;
		
		if( !this.exists() ){ this.save();}
		this.load();		
		
		this.enable();		
	}
	
	
	public Boolean enable(){
		
		// Create a version node in the config.yml
        if( this.get(VERSION) == null ) {    			
            // Doesn't exist so create it and store as new
        	this.set(VERSION, (String)BooTreasure.getInstance().getDescription().getVersion());
            updated = true;
            messages.add(VERSION + " - the version of the config");
        }else{
            // Exists so check with current version
            String version = this.getString(VERSION);
            if( !BooTreasure.getInstance().getDescription().getVersion().equalsIgnoreCase(version) ){
            	this.set(VERSION, (String)BooTreasure.getInstance().getDescription().getVersion());   
                updated = true;
                messages.add(VERSION + " - updated");
            }					
        }
		
        // debugMode
  		if( this.get(DEBUG) == null ) {
  			this.set(DEBUG, (Boolean)true);
  			updated = true;
  			messages.add(DEBUG + " - Set debug mode ON or OFF");
  		}
  		
  		// filters
  		if( this.get(FILTER_WORLDGUARD) == null ) {
  			this.set(FILTER_WORLDGUARD, (Boolean)true);
  			//_pluginConfiguration.set("config.filter.worldguard", (Boolean)true);
  			updated = true;
  			messages.add(FILTER_WORLDGUARD + " - Take care about WorldGuard region");
  		}
  		        

         if( updated ) {	
        	 this.save();
        	 this.load();
        	 Log.log(Level.INFO, "- Config - " + getName() + " " + BooTreasure.getInstance().getDescription().getVersion() + " config.yml - new options");
             for(String str : messages){
            	 Log.log(Level.INFO, "- config.yml - " + str);
             }
         }
        
		
		return true;
	}
	
	
	public Boolean reload(){		
		this.save();
   	 	this.load();
		return true;
	}
	

	
	public String getVersion(){
		return this.getString(VERSION);
	}
	
	public Boolean isDebug(){
		return this.getBoolean(DEBUG);
	}
	
	public Boolean isWorldGuardFilter(){
		return this.getBoolean(FILTER_WORLDGUARD);
	}

	

}
