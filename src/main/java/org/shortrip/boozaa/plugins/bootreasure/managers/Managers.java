package org.shortrip.boozaa.plugins.bootreasure.managers;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.dao.EventsDAO;
import org.shortrip.boozaa.plugins.bootreasure.dao.TreasureDAO;
import org.shortrip.boozaa.plugins.bootreasure.dao.EventsDAO.EventType;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyCache.CacheNotExistException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyCommands.CommandNullException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyConfigs.ConfigNullFileException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyPermissions.PermissionsVaultNullException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyTreasuresManager.TreasuresCleanupException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyTreasuresManager.TreasuresLoadException;
import org.shortrip.boozaa.plugins.bootreasure.managers.cache.Cache;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandFramework;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandParser;
import org.shortrip.boozaa.plugins.bootreasure.managers.configuration.Configuration;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronScheduler;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronSchedulerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronTask;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.CronTaskCollector;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks.TreasureTask;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestAppearEvent;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestDisappearEvent;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.chests.TreasureChestDisappearSilentlyEvent;
import org.shortrip.boozaa.plugins.bootreasure.treasures.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class Managers {

	private Plugin plugin;
	@Getter private MyCache cacheManager;
	@Getter private MyCommands commandsManager;
	@Getter private MyConfigs configsManager;
	@Getter private MyCron cronManager;
	@Getter private MyDatabase databaseManager;
	@Getter private MyEvents eventsManager;
	@Getter private MyPermissions permissionsManager;
	@Getter private MyTreasuresManager treasuresManager;
	
	
	
	public Managers( Plugin plugin ) throws CommandNullException, ConfigNullFileException, SQLException, PermissionsVaultNullException, TreasuresCleanupException, TreasuresLoadException{
		
		this.plugin = plugin;
		cacheManager = new MyCache();
		commandsManager = new MyCommands();
		configsManager = new MyConfigs();
		cronManager = new MyCron();
		databaseManager = new MyDatabase();
		eventsManager = new MyEvents();
		permissionsManager = new MyPermissions();
		treasuresManager = new MyTreasuresManager();
		
	}
	
	
	
	
	
	
	
	
	
	public class MyCache extends Manager {

		// Cache treasures
		@Getter private Cache _treasureCache;

		public MyCache() {
			this._treasureCache = new Cache();
		}
		
		public Boolean exists( String name ){
			return this._treasureCache.exists(name);
		}
		
		public void add( String id, Object obj ) throws CacheExistException{
			if( !exists(id) ){
				this._treasureCache.add(id, obj);
			}else{
				throw new CacheExistException("The treasure " + id + " already exists in cache");
			}
				
		}
		
		public Treasure get( String id ){
			return (Treasure)this._treasureCache.getObject(id);
		}

		
		public HashMap<String, Object> getTreasures(){
			return this._treasureCache.getCache();
		}
		
		
		
		
		public void remove( String id ) throws CacheNotExistException{
			if( exists(id) ){
				this._treasureCache.remove(id);
			}else{
				throw new CacheNotExistException("The treasure " + id + " didn't exists in cache");
			}
				
		}
		
		
		@Override
		public void onDisable() {
			_treasureCache.erase();
		}
		
		
		
		public class CacheExistException extends Exception {
			private static final long serialVersionUID = 1L;		
			public CacheExistException(String message) {
		        super(message);
		    }		
		}
		
		public class CacheNotExistException extends Exception {
			private static final long serialVersionUID = 1L;		
			public CacheNotExistException(String message) {
		        super(message);
		    }		
		}

		
		
	}

	public class MyCommands extends Manager {

		private CommandFramework manager;
		
		public MyCommands() throws CommandNullException {
			manager = new CommandFramework(plugin);
			// Commands
			manager.registerCommands(new CommandParser(plugin));
		}
		
		public void registerCommands(Object obj) throws CommandNullException {
			if( obj != null ){
				manager.registerCommands( obj );
			}else{
				throw new CommandNullException("The command Object is null");
			}				
		}
		
		public Boolean handleCommand( CommandSender sender, Command command, String commandLabel, String[] args ) throws CommandHandlerException{
			try{
				Boolean result = manager.handleCommand(sender, commandLabel, command, args);
				return result;
			}catch( Exception e ){
				throw new CommandHandlerException("The command handler failed.", e);			
			}		
		}
		
		
		
		
		public class CommandNullException extends Exception {
			private static final long serialVersionUID = 1L;		
			public CommandNullException(String message) {
		        super(message);
		    }		
		}
		
		public class CommandHandlerException extends Exception {
			private static final long serialVersionUID = 1L;
			@Getter private Throwable throwable;
			public CommandHandlerException(String message, Throwable t) {
		        super(message);
		        this.throwable = t;
		    }		
		}

		@Override
		public void onDisable() {
			manager = null;
		}

	}

	public class MyConfigs extends Manager {
		
		private Map<String, Configuration> configs;
		
		
		public MyConfigs() throws ConfigNullFileException {
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
			config.set("treasures." + id + ".basics.type", 				"chest");
			config.set("treasures." + id + ".basics.cronpattern", 		pattern);
			config.set("treasures." + id + ".basics.duration", 			duration);		
			config.set("treasures." + id + ".basics.world", 			world);
			config.set("treasures." + id + ".basics.onlyonsurface", 	onlyonsurface);
			config.set("treasures." + id + ".basics.preservecontent", 	preservecontent);
			config.set("treasures." + id + ".basics.infinite", 			infinite);

			if( t.get_inventory() != null ){
				ItemStack[] items = t.get_inventory();
				config.set("treasures." + id + ".setup.contents.items", items);
			}		
			
			config.set("treasures." + id + ".setup.messages.spawn", "&b%name% vient d'apparaitre" );
			config.set("treasures." + id + ".setup.messages.found", "&b%name% a été découvert" );
			config.set("treasures." + id + ".setup.messages.disappear", "&b%name% vient de disparaitre" );
			
			config.save();
			config.load();
			
		}
		
		
		public void deleteTreasure( String name ) throws CacheNotExistException{
			
			Configuration config = get("treasures.yml");
			Set<String> nodes = config.getKeys("treasures");
			for( String treasureId : nodes ){
				String path = "treasures." + treasureId;
				if( config.contains( path + "." + name ) ){
					
					// This is the treasure that we must delete				
					
					// Retrieve the CronTask associated to this treasure
					if( cronManager.get_taskCollector().contains(treasureId) ){
						cronManager.get_taskCollector().removeTaskById(treasureId);
					}
					
					// Remove this treasure from cache
					if( cacheManager.exists(treasureId) )
						cacheManager.remove(treasureId);
					
					// Delete this ConfigurationSection from treasures.yml
					config.set(path + "." + name, null);
					
					config.save();
					config.load();
					
				}
			}
			
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
		
		
		@Override
		public void onDisable() {
			configs = null;
		}
		
		
		
		

		public class ConfigNullFileException extends Exception {
			private static final long serialVersionUID = 1L;		
			public ConfigNullFileException(String message) {
		        super(message);
		    }		
		}


		
	}

	public class MyCron extends Manager {

		// The Cron4J Scheduler instance
		@Getter private CronScheduler _scheduler;
		@Getter private CronTaskCollector _taskCollector;
		

		public MyCron() {
			// Instanciate a Cron Scheduler
			_scheduler = new CronScheduler();
			// Add our listener to it
			_scheduler.addSchedulerListener(new CronSchedulerListener());
			// TaskCollector
			_taskCollector = new CronTaskCollector(plugin);
			_scheduler.addTaskCollector(_taskCollector);
			// Host system TimeZone
			TimeZone tz = Calendar.getInstance().getTimeZone();
			// Set its TimeZone to this TimeZone
			_scheduler.setTimeZone(tz);
			// Start the scheduler
			_scheduler.start();
			Log.info("Cron Scheduler started with TimeZone set to "
							+ tz.getDisplayName());
		}
		
		
		public void addTask( CronTask task ){		
			_taskCollector.addTask(task);		
		}


		@Override
		public void onDisable() {
					
			_scheduler.removeTaskCollector(_taskCollector);
			_scheduler.stop();
			_scheduler = null;
			_taskCollector = null;
			
		}
		
		
		
	}

	public class MyDatabase extends Manager {

		private String databaseUrl;
		private ConnectionSource _connectionSource;
		private Dao<TreasureDAO, String> _treasureDAO;
		private Dao<EventsDAO, String> _eventsDAO;
		
		
		public MyDatabase() throws SQLException{
			
			System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
			//System.setProperty(LocalLog.LOCAL_LOG_FILE_PROPERTY, "plugins/BooTreasure/ormlite_log.out");
						
			Log.debug("Search for database storage type");
			
			Configuration config = configsManager.get("config.yml");
			if( config.contains("config.database") ){
				
				Log.debug("The config have a 'config.database' node");
				
				String dbType = config.getString("config.database");			

				Log.debug("Database type selected: " + dbType);
				
				if( dbType.equalsIgnoreCase("sqlite") ){

					Log.debug("Connection to: " + dbType);
					
					databaseUrl = "jdbc:sqlite:plugins/BooTreasure/bootreasure.db";
					_connectionSource = new JdbcConnectionSource(databaseUrl);
					Log.info("Connected to SQLite database");
					
				}else if( dbType.equalsIgnoreCase("mysql") ){

					Log.debug("Connection to: " + dbType);
					
					String host = config.getString("config.mysql.host");
					int port = config.getInt("config.mysql.port");
					String database = config.getString("config.mysql.database");
					String user = config.getString("config.mysql.user");
					String pass = config.getString("config.mysql.pass");
					databaseUrl = "jdbc:mysql://"+ host + ":" + port + "/" + database;
					_connectionSource = new JdbcConnectionSource(databaseUrl,user,pass);
					Log.info("Connected to MySQL database");
					
				}
				
			}		
			this.initDatabase();
		}
		
		
		private void initDatabase() throws SQLException{
	    	
			// Le DAO pour treasure
			_treasureDAO = DaoManager.createDao(_connectionSource, TreasureDAO.class);
			// Le DAO pour events
			_eventsDAO = DaoManager.createDao(_connectionSource, EventsDAO.class);
				
			// Create the table		
	        TableUtils.createTableIfNotExists(_connectionSource, TreasureDAO.class);
	        TableUtils.createTableIfNotExists(_connectionSource, EventsDAO.class);
		    				
	    }
		
		public void addTreasureToDatabase( TreasureChest treasure ) throws SQLException{
			TreasureDAO tdao = new TreasureDAO( treasure.get_id(), treasure.get_name(), treasure.get_onlyonsurface(), treasure.get_preservecontent() );
			// Create new TreasureDAO in database
			this._treasureDAO.create( tdao );							
		}
		
		
		public void addEventToDatabase( TreasureChest treasure, EventType type ) throws SQLException{
			// Get the associated TreasureDAO
			TreasureDAO trDAO = getTreasureDAO( treasure.get_id() );
			if( trDAO != null ){
				// Create new event for this entry
				EventsDAO event = new EventsDAO( trDAO, type, treasure.get_block().getLocation() );
				// Create this entry
				this._eventsDAO.create(event);
			}else{
				// we must store a TreasureDAO first
				addTreasureToDatabase( treasure );
				// Create new event for this entry
				EventsDAO event = new EventsDAO( trDAO, type, treasure.get_block().getLocation() );
				// Create this entry
				this._eventsDAO.create(event);
			}
		}
		
		public void addEventToDatabase( TreasureChest treasure, Player player, EventType type ) throws SQLException{
			// Get the associated TreasureDAO
			TreasureDAO trDAO = getTreasureDAO( treasure.get_id() );
			if( trDAO != null ){
				// Create new event for this entry
				EventsDAO event = new EventsDAO( trDAO, type, player, treasure.get_block().getLocation() );
				// Create this entry
				this._eventsDAO.create(event);
			}else{
				// we must store a TreasureDAO first
				addTreasureToDatabase( treasure );
				// Create new event for this entry
				EventsDAO event = new EventsDAO( trDAO, type, player, treasure.get_block().getLocation() );
				// Create this entry
				this._eventsDAO.create(event);
			}	
		}
		
		
		public TreasureDAO getTreasureDAO( String uuid ) throws SQLException{		
			QueryBuilder<TreasureDAO, String> statementBuilder = this._treasureDAO.queryBuilder();
			statementBuilder.where().like( TreasureDAO.UUID_DATE_FIELD_NAME, uuid );
			List<TreasureDAO> treasuresDAO = this._treasureDAO.query(statementBuilder.prepare());
			if( treasuresDAO.isEmpty() == false){
				// Here an entry in database exists for this uuid
				for( TreasureDAO trDAO : treasuresDAO ){
					return trDAO;
				}
			}
			return null;
		}
		
		
		
		
		
		@Override
		public void onDisable() {
			// TODO Auto-generated method stub
			
		}

	}

	public class MyEvents extends Manager {

		
		public MyEvents() {
			
		}
		
		
		public void chestAppearEvent( TreasureChest treasure ){
			Bukkit.getServer().getPluginManager().callEvent(new TreasureChestAppearEvent(plugin, treasure.get_id()));
		}
		
		public void chestDisappearDelayedEvent( final TreasureChest treasure ){
			long delay = treasure.get_duration()*20;
	        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	            @Override 
	            public void run() {		                
	            	// Event to disappear
	                TreasureChestDisappearEvent event = new TreasureChestDisappearEvent(plugin, treasure.get_id());
	                // Call the event
	        		Bukkit.getServer().getPluginManager().callEvent(event);		            	
	            }
	        }, delay);
		}
		
		public void chestDisappearSilentlyEvent( TreasureChest treasure ){
			Bukkit.getServer().getPluginManager().callEvent(new TreasureChestDisappearSilentlyEvent(plugin, treasure.get_id()));
		}
		
		

		@Override
		public void onDisable() {
			
		}
		
	}

	public class MyPermissions extends Manager {

		private Permission perms;
		@Getter @Setter private Boolean useVault = false;

		
		public MyPermissions() throws PermissionsVaultNullException {
			
			if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
				throw new PermissionsVaultNullException("Vault seems not here, you can't use permission");
	        }
	    	RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(Permission.class);
	        if (permissionProvider != null) {
	        	perms = permissionProvider.getProvider();
	        	Log.info("Permissions providing by Vault");
	        	useVault = true;
	        }else{
	        	throw new PermissionsVaultNullException("Can't hooked Permissions with Vault");
	        }
	        
		}
		

		private Boolean playerCanAll( Player player ){
			if( useVault ){
				return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_ALL);
			}
			return true;
		}
		
		public Boolean playerCanFind( Player player ){
			if( useVault ){
				if( playerCanAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_FIND);
				}
			}
			return true;
				
		}
		public Boolean playerCanFind( String playerName ){
			if( useVault ){
				Player player = Bukkit.getPlayer(playerName);
				if( playerCanAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_FIND);
				}
			}
			return true;				
		}
		
		public Boolean playerCanBeInformed( Player player ){
			if( useVault ){
				if( playerCanAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_BE_INFORMED);
				}	
			}
			return true;
				
		}
		public Boolean playerCanBeInformed( String playerName ){
			if( useVault ){
				Player player = Bukkit.getPlayer(playerName);
				if( playerCanAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_BE_INFORMED);
				}	
			}
			return true;
				
		}

		
		private Boolean playerAdminAll( Player player ){
			if( useVault ){
				return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_ALL);
			}
			return true;		
		}
		
		public Boolean playerAdminCreate( Player player ){
			if( useVault ){
				if( playerAdminAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_CREATE);
				}
			}
			return true;				
		}
		
		public Boolean playerAdminCreate( String playerName ){
			if( useVault ){
				Player player = Bukkit.getPlayer(playerName);
				if( playerAdminAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_CREATE);
				}
			}
			return true;				
		}
		
		public Boolean playerAdminModify( Player player ){
			if( useVault ){
				if( playerAdminAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_EDIT);
				}
			}
			return true;				
		}
		
		public Boolean playerAdminModify( String playerName ){
			if( useVault ){
				Player player = Bukkit.getPlayer(playerName);
				if( playerAdminAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_EDIT);
				}
			}
			return true;				
		}
		
		public Boolean playerAdminDelete( Player player ){
			if( useVault ){
				if( playerAdminAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_DELETE);
				}
			}
			return true;				
		}
		
		public Boolean playerAdminDelete( String playerName ){
			if( useVault ){
				Player player = Bukkit.getPlayer(playerName);
				if( playerAdminAll(player) ){
					return true;
				}else{
					return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_DELETE);
				}
			}
			return true;				
		}
		
		
		
		public Boolean playerInGroup( String groupName, Player player ){
			if( useVault ){
				return perms.playerInGroup(player, groupName);
			}
			return true;		
		}
		
		

		public class PermissionsVaultNullException extends Exception {
			private static final long serialVersionUID = 1L;		
			public PermissionsVaultNullException(String message) {
		        super(message);
		    }		
		}



		@Override
		public void onDisable() {
			perms = null;
		}
		
		
	}

	public class MyTreasuresManager extends Manager {

		@Getter public final String treasures_file = "treasures.yml";
		
		
		public MyTreasuresManager() throws TreasuresCleanupException, TreasuresLoadException{

			// Make folders
			makeFolders();
			// Cleanup lost treasures
			cleanup();
			// Load treasures
			loadTreasures();
			
		}
		
		private void cleanup() throws TreasuresCleanupException {

			Log.info("Cleanup eventual lost treasures ... ");
			
			/*
			if( BooTreasure.getConfigManager().get( "config.yml" ).getBoolean("config.bukkitserialization") == false ){
				Log.debug("BukkitSerialization is disabled on config.yml");
				return;
			}
			*/
			
			int qty = 0;
			
			try{
				
				File losts = new File( Const.LOST_FOLDER_PATH );
				for (File file : losts.listFiles()) {
					// Si fichier serial on le traite
					
					String filename = file.getName();
					String extension = filename.substring( filename.lastIndexOf(".") + 1, filename.length() );
					
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
			String lost = plugin.getDataFolder() + File.separator + "lost+found" + File.separator;
			File dir = new File(lost);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		
		
		public void loadTreasures() throws TreasuresLoadException {

			Log.info("Loading treasures from treasures.yml ... ");
			int qty = 0;
			
			Log.debug("Search 'treasures' node in treasures.yml");
			
			// On charge le fichier config/treasures.yml		
			Configuration config = configsManager.get("treasures.yml");
			if (config.get("treasures") != null) {

				Log.debug("treasures.yml loaded and 'treasures' node found");
				
				// On va charger les treasures un a un
				Set<String> nodes = config.getKeys("treasures");

				for (String treasureId : nodes) {

					try{

						Log.debug("- parse the treasure with id " + treasureId);
						
						// Partie basics
						ConfigurationSection section = config.getConfigurationSection("treasures." + treasureId);
						
						
						// Depend on TreasureType
						if( section.contains("basics.type") ){
							if( section.getString("basics.type").equalsIgnoreCase("chest") ){
								TreasureChest treasure = new TreasureChest(treasureId,section);
								// Store in cache
								cacheManager.add(treasure.get_id(), treasure);
								
								// Give the new CronTask
								cronManager.addTask(new TreasureTask(plugin, treasure));
								
								// Quantity increment
								qty++;
							}
						}
						
						
					
					} catch (Exception e) {
						throw new TreasuresLoadException("Error when trying to load treasures from treasures.yml", e);
					}

				}

			}
			Log.info(" ... " + qty + " treasure(s) loaded");
		}
		
		
		@Override
		public void onDisable() {
			// TODO Auto-generated method stub
			
		}
		
		
		
		
		

		public class TreasuresCleanupException extends Exception {
			private static final long serialVersionUID = 1L;
			@Getter private Throwable throwable;
			public TreasuresCleanupException(String message, Throwable t) {
		        super(message);
		        this.throwable = t;
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
		
		
	}
	
}
