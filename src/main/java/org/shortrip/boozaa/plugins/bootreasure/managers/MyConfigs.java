package org.shortrip.boozaa.plugins.bootreasure.managers;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.configuration.Configuration;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;
import java.io.File;


public class MyConfigs extends Manager {

	private Plugin plugin;
	@Getter private static Map<String, Configuration> configs;
	
	
	public MyConfigs(BooTreasure booTreasure) throws ConfigNullFileException {
		this.plugin = booTreasure;
		configs = new HashMap<String, Configuration>();
		// Configurations
		load("config.yml");		
		load("messages.yml");
		load("treasures.yml");
	}

	
	public void saveNewTreasureChest( TreasureChest t ){
		String name = t.get_name();
		String pattern = t.get_pattern();
		String id = t.get_id();
		//List<Material> allowedids = t.get_allowedids();
		Boolean infinite = t.get_infinite();
		Boolean onlyonsurface = t.get_onlyonsurface();
		Boolean preservecontent = t.get_preservecontent();
		String world = t.get_world();
		Long duration = t.get_duration();
		
		Configuration config = get("treasures.yml");

		config.set("treasures." + id + ".basics.name", 				name);
		config.set("treasures." + id + ".basics.cronpattern", 		pattern);
		config.set("treasures." + id + ".basics.duration", 			duration);		
		config.set("treasures." + id + ".basics.world", 			world);
		config.set("treasures." + id + ".basics.onlyonsurface", 	onlyonsurface);
		config.set("treasures." + id + ".basics.preservecontent", 	preservecontent);
		config.set("treasures." + id + ".basics.infinite", 			infinite);

		ItemStack[] items = t.get_inventory();
		config.set("treasures." + id + ".setup.contents.items", items);
		
		
		config.save();
		config.load();
		
	}
	
	
	
	
	
	/**
	* Checks to see if the ConfigManager knows about fileName
	*
	* @param fileName file to check
	* @return true if file is loaded, false if not
	*/
	public boolean isFileLoaded(String fileName) {
        return configs.containsKey(fileName);
    }

	    /**
	* Loads a files configuration into Memory
	*
	* @param plugin Plugin to load file from if fileName does not exist in
	* Plugins folder
	* @param fileName File to load
	*/
	public void load(String fileName) throws ConfigNullFileException {
        Log.info("Loading in memory: " + fileName);
		File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                plugin.saveResource(fileName, false);
            } catch (Exception e) {
            	throw new ConfigNullFileException("The resource " + fileName + " don't exists in package");
            }
        }
        if (!isFileLoaded(fileName)) {
            Configuration conf = new Configuration(file);
            conf.load();
        	configs.put(fileName, conf);
        	if( fileName.equalsIgnoreCase("config.yml"))
        		Log.set_debugON(conf.getBoolean("config.debugMode"));
        		
        }
    }

	    /**
	* Gets the FileConfiguration for a specified file
	*
	* @param fileName File to load data from
	* @return File Configuration
	*/
	public Configuration get(String fileName) {
        if (isFileLoaded(fileName)) {
            return configs.get(fileName);
        }
        return null;
    }

	    /**
	* Updates the FileConfiguration at the given path. If path already exists
	* this will return false.
	*
	* @param fileName File to update
	* @param path Path to update
	* @param value Data to set at path
	* @return True if successful, otherwise false
	*/
	public boolean update(String fileName, String path, Object value) {
        if (isFileLoaded(fileName)) {
            if (!configs.get(fileName).contains(path)) {
                configs.get(fileName).set(path, value);
                return true;
            }
        }
        return false;
    }

	    /**
	* Sets data at any given path. If path already exists it will be over
	* written.
	*
	* @param fileName File to update
	* @param path Path to set
	* @param value Data to set at path
	*/
	public void set(String fileName, String path, Object value) {
        if (isFileLoaded(fileName)) {
            configs.get(fileName).set(path, value);
        }
    }


	    /**
	* Removes a path from the FileConfiguration.
	*
	* @param fileName File to update
	* @param path Path to remove
	*/
	public void remove(String fileName, String path) {
        if (isFileLoaded(fileName)) {
            configs.get(fileName).set(path, null);
        }
    }

	    /**
	* Checks if a file has a path.
	*
	* @param fileName File to check
	* @param path Path to check
	* @return True if path exists, otherwise false.
	*/
	public boolean contains(String fileName, String path) {
        if (isFileLoaded(fileName)) {
            return configs.get(fileName).contains(path);
        }
        return false;
    }

	    /**
	* Reload the config from the given Plugin.
	*
	* @param plugin Plugin to get the File from
	* @param fileName File to reload
	*/
	public void reload(String fileName) {
        //File file = new File(plugin.getDataFolder(), fileName);
        if (isFileLoaded(fileName)) {
            try {
                Configuration conf = configs.get(fileName);
                conf.load();
            	if( fileName.equalsIgnoreCase("config.yml"))
            		Log.set_debugON(conf.getBoolean("config.debugMode"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	    /**
	* Save the config for the given plugin
	*
	* @param plugin Plugin dir to save to the file to
	* @param fileName File to save
	*/
	public void save(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (isFileLoaded(fileName)) {
            try {
                configs.get(fileName).save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	
	
	

	public class ConfigNullFileException extends Exception {
		private static final long serialVersionUID = 1L;		
		public ConfigNullFileException(String message) {
	        super(message);
	    }		
	}





	@Override
	public void onDisable() {
		configs = null;
	}
	
}
