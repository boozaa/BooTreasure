package org.shortrip.boozaa.plugins.bootreasure.configs;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.managers.configuration.Configuration;


public class ConfigManager {
    
	protected Plugin _plugin;
	private Map< String, Configuration> _map;
	
	
	public ConfigManager( Plugin plugin ){
		
		this._plugin = plugin;
		this._map = new HashMap< String, Configuration>();
		
	}
	
	
	public void load( String configFileName ){
		
		// Folder path
		//String folder = this._plugin.getDataFolder().getAbsolutePath();
		File file = new File( this._plugin.getDataFolder() + File.separator + configFileName );
		if( file.exists()  ){
			
			Configuration config = new Configuration( file );
			config.load();
			// Add this config to map
			this._map.put(configFileName, config);
			
		}
		
		
	}
	
	
	public Configuration get( String configFileName ){
		
		if( this._map.containsKey( configFileName ) ){
			return this._map.get( configFileName );
		}
		return null;
		
	}
	
	
	
}
