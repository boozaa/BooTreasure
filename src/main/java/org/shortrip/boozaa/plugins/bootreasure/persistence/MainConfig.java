/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.Getter;

import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;



/**
 * @author boozaa
 *
 * BooTreasure
 */
public class MainConfig extends ConfigFile {

	private List<String> messages;
	private Boolean updated;
	@Getter private String version;
	@Getter private Boolean debug, worldguard;
	
	private static String VERSION = 				"config.version";
	private static String DEBUG = 					"config.debugMode";
	private static String FILTER_WORLDGUARD = 		"config.filter.worldguard";
	
	
	/**
	 * @param sourcepath
	 */
	public MainConfig() {
		super(BooTreasure.getMainConfigPath());
	}
	
	@Override
	public void load() {
		
		messages = new ArrayList<String>();
		
		// Create a version node in the config.yml
        if( this._config.get(VERSION) == null ) {    			
            // Doesn't exist so create it and store as new
        	this._config.set(VERSION, BooTreasure.getPluginVersion());
            updated = true;
            messages.add(VERSION + " - the version of the config");
        }else{
            // Exists so check with current version
            String version = this._config.getString(VERSION);
            if( !BooTreasure.getPluginVersion().equalsIgnoreCase(version) ){
            	this._config.set(VERSION, BooTreasure.getPluginVersion());   
                updated = true;
                messages.add(VERSION + " - updated");
            }					
        }
        
        this.version = BooTreasure.getPluginVersion();
		
        // debugMode
  		if( this._config.get(DEBUG) == null ) {
  			this._config.set(DEBUG, true);
  			updated = true;
  			messages.add(DEBUG + " - Set debug mode ON or OFF");
  		}
  		
  		this.debug = this._config.getBoolean(DEBUG);
  		
  		// filters
  		if( this._config.get(FILTER_WORLDGUARD) == null ) {
  			this._config.set(FILTER_WORLDGUARD, true);
  			//_pluginConfiguration.set("config.filter.worldguard", (Boolean)true);
  			updated = true;
  			messages.add(FILTER_WORLDGUARD + " - Take care about WorldGuard region");
  		}

  		this.worldguard = this._config.getBoolean(FILTER_WORLDGUARD); 

  		if( updated ) {	
        	 this._config.save();
        	 this._config.load();
        	 Log.info("Config - " + BooTreasure.getPluginName() + " " + BooTreasure.getPluginVersion() + " config.yml - new options");
             for(String str : messages){
            	 Log.info("config.yml - " + str);
             }
  		}
        
	}
	
	

	

}
