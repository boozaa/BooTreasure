package org.shortrip.boozaa.plugins.bootreasure;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import net.gravitydevelopment.updater.Updater;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.shortrip.boozaa.plugins.bootreasure.dao.EventsDAO;
import org.shortrip.boozaa.plugins.bootreasure.dao.TreasureDAO;
import org.shortrip.boozaa.plugins.bootreasure.listeners.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyEvents;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands.CommandHandlerException;
import org.shortrip.boozaa.plugins.bootreasure.managers.cache.Cache;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandFramework;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandParser;
import org.shortrip.boozaa.plugins.bootreasure.managers.configuration.ChestTreasuresConfiguration;
import org.shortrip.boozaa.plugins.bootreasure.managers.configuration.LocalesConfiguration;
import org.shortrip.boozaa.plugins.bootreasure.managers.configuration.MainConfiguration;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronScheduler;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronSchedulerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronTaskCollector;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks.TreasureTask;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.support.ConnectionSource;

import lombok.Getter;


public class BooTreasure extends JavaPlugin{
	
	private CommandFramework 					commandManager;
	private String 								databaseUrl;
	@SuppressWarnings("unused")
	private ConnectionSource 					connectionSource;
	@SuppressWarnings("unused")
	private Dao<TreasureDAO, String> 			treasureDAO;
	@SuppressWarnings("unused")
	private Dao<EventsDAO, String> 				eventsDAO;
	
	//@Getter private Managers managers;
	
	@Getter private MainConfiguration 			mainConfig;
	@Getter private ChestTreasuresConfiguration treasuresConfig;
	@Getter private LocalesConfiguration 		localesConfig;
	
	@Getter private Cache 						treasureCache;
	@Getter private CronScheduler 				cronScheduler;
	@Getter private CronTaskCollector 			cronTaskCollector;
	@Getter private MyEvents 					eventsManager;
	


	@Override
	public void onEnable() {
			
		try {
			
			// Configurations files
			mainConfig 		= new MainConfiguration( this );
			treasuresConfig = new ChestTreasuresConfiguration( this );
			localesConfig 	= new LocalesConfiguration( this );
			
			// Cache creation
			treasureCache = new Cache();
			
			// eventsManager
			eventsManager = new MyEvents(this);
			
			// Cron scheduler
			cronScheduler = new CronScheduler();
			// Add our listener to it
			cronScheduler.addSchedulerListener(new CronSchedulerListener());
			// TaskCollector
			cronTaskCollector = new CronTaskCollector(this);
			cronScheduler.addTaskCollector(cronTaskCollector);
			// Host system TimeZone
			TimeZone tz = Calendar.getInstance().getTimeZone();
			// Set its TimeZone to this TimeZone
			cronScheduler.setTimeZone(tz);
			// Start the scheduler
			cronScheduler.start();
			
			// Database creation
			System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
			System.setProperty(LocalLog.LOCAL_LOG_FILE_PROPERTY, getDataFolder() + File.separator + "ormlite_log.out");
			switch( mainConfig.getDatabaseType() ) {		
				case MYSQL:
					this.databaseUrl = "jdbc:mysql://"+ mainConfig.getDatabase_host() + ":" + mainConfig.getDatabase_port() + "/" + mainConfig.getDatabase_name();
					this.connectionSource = new JdbcConnectionSource(databaseUrl, mainConfig.getDatabase_user(), mainConfig.getDatabase_password() );
					Log.info("Connected to MySQL database");
					break;
				case SQLITE:
					this.databaseUrl = "jdbc:sqlite:" + getDataFolder() + File.separator + "bootreasure.db";
					this.connectionSource = new JdbcConnectionSource(databaseUrl);
					Log.info("Connected to SQLite database");
					break;
				default:
					this.databaseUrl = "jdbc:sqlite:" + getDataFolder() + File.separator + "bootreasure.db";
					this.connectionSource = new JdbcConnectionSource(databaseUrl);
					Log.info("Connected to SQLite database");
					break;		
			}
			
			// Commands
			commandManager = new CommandFramework(this);
			commandManager.registerCommands(new CommandParser(this));
			
			
			checkUpdate();

			// Load all stuff
			//managers = new Managers(this);

			getServer().getPluginManager().registerEvents( new MyPlayerListener(this), this );
			
			// Make needed folders
			makeFolders();
			//Clean all losts treasures
			cleanup();
			//Load treasures
			loadTreasures();
			
			Log.debug("The debug mode is activated. To disable it use command: bootreasure debug");


		} catch (Exception e) {
			// SEVERE -> disable plugin
			Log.warning("Fatal error occured during startup");
			Log.severe("onEnable() fatal Exception", e);
		}

	}
	
	
	private void checkUpdate(){
		
		Updater updater = new Updater(this, 52623, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
		Updater.UpdateResult result = updater.getResult();
		switch(result)
        {
            case UPDATE_AVAILABLE:
            	// There was an update found, but because you had the UpdateType set to NO_DOWNLOAD, it was not downloaded.
            	Log.info("A new version is available please see: http://dev.bukkit.org/bukkit-plugins/boo-treasure/files/");
			default:
				break;
        }
	}



	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Boolean result = true;
		result = commandManager.handleCommand(sender, commandLabel, command, args);
		return result;
	}


	@Override
	public void onDisable() {
		/*if( managers != null)
			managers.onDisable();*/
	}
	

	public void loadTreasures() throws TreasuresLoadException {
	
		Log.info("Loading treasures from treasures.yml ... ");
		int qty = 0;
		
		try{
			
			Log.debug("Search 'treasures' node in treasures.yml");
					
			List<TreasureChest> chestTreasures = treasuresConfig.getAllTreasures();
			for( TreasureChest ch : chestTreasures ){
				// Store in cache
				treasureCache.add(ch.get_id(), ch);				
				// Give the new CronTask
				cronTaskCollector.addTask(new TreasureTask(this, ch));				
				// Quantity increment
				qty++;
			}
		
		} catch (Exception e) {
			throw new TreasuresLoadException("Error when trying to load treasures from treasures.yml", e);
		}
		Log.info(" ... " + qty + " treasure(s) loaded");
		
	}
	
	

	private void cleanup() throws TreasuresCleanupException {

		Log.info("Cleanup eventual lost treasures ... ");
		int qty = 0;
		
		try{
			
			File losts = new File( this.getDataFolder() + "lost+found" );
			for (File file : losts.listFiles()) {
				
				String filename 	= file.getName();
				String extension 	= filename.substring( filename.lastIndexOf(".") + 1, filename.length() );
				
				// ChestTreasures
				if (extension.equalsIgnoreCase("chest")) {					
					Log.info("Found old Treasure Chest: " + filename);
					// Deserialize
					TreasureChest t = new TreasureChest(file);
					
					if( t != null ){
						Log.debug(t.toString());
						// Launch disappear method
						t.disappear();
						// Delete serialization file
						t.deleteSerializedFile();
						Log.info("TreasureChest " + file + " deleted");
						// Increment counter
						qty++;
					}					
				}				
			}			
			Log.info(" ... " + qty + " lost Treasures cleared");

		}catch( Exception e){
			throw new TreasuresCleanupException("Exception during cleanup", e);
		}
		
	}
	
	

	private void makeFolders() {
		String lost = getDataFolder() + File.separator + "lost+found" + File.separator;
		File dir = new File(lost);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	
	
	
	
	
	public class TreasuresLoadException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public TreasuresLoadException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
	    }		
	}
	

	public class TreasuresCleanupException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public TreasuresCleanupException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
	    }		
	}
	

}


