package org.shortrip.boozaa.plugins.bootreasure;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.shortrip.boozaa.plugins.bootreasure.listeners.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCache;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyConfigs;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCron;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyEvents;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyPermissions;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyPermissions.PermissionsVaultNullException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands.CommandHandlerException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands.CommandNullException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyConfigs.ConfigNullFileException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyTreasuresManager;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyTreasuresManager.TreasuresCleanupException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyTreasuresManager.TreasuresLoadException;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import lombok.Getter;


public class BooTreasure  extends JavaPlugin{

	
	@Getter private static BooTreasure instance;
	@Getter private static MyConfigs configManager;
	@Getter private static MyCommands commandsManager;
	@Getter private static MyCron cronManager;
	@Getter private static MyCache cacheManager;
	@Getter private static MyEvents eventsManager;
	@Getter private static MyPermissions permissionsManager;
	@Getter private static MyTreasuresManager treasuresManager;
	
	// Le ConnectionSource de ormlite
	@Getter private static ConnectionSource _connectionSource;
	@Getter private static Dao<TreasureDAO, String> _treasureDAO;
	@Getter private static Dao<EventsDAO, String> _eventsDAO;
	
	
	@Override
	public void onEnable() {
				
		try {
			
			instance = this;
			
			// Instanciate system
			configManager 			= new MyConfigs(this);
			commandsManager 		= new MyCommands(this);
			cronManager 			= new MyCron(this);
			cacheManager 			= new MyCache(this);
			eventsManager			= new MyEvents(this);
			permissionsManager 		= new MyPermissions(this);
			treasuresManager 		= new MyTreasuresManager(this);
			
			// MyPlayerListener
			getServer().getPluginManager().registerEvents( new MyPlayerListener(this), this );
			
			// Database storage
			initDatabase();
			
		} catch (CommandNullException e) {
			// SEVERE -> disable plugin
			Log.severe("onEnable() fatal error: CommandNullException", e);
		} catch (ConfigNullFileException e) {
			// SEVERE -> disable plugin
			Log.severe("onEnable() fatal error: NullFileException", e);
		} catch (PermissionsVaultNullException e) {
			// SEVERE -> disable plugin
			Log.severe("onEnable() fatal error: PermissionsVaultNullException", e);
		} catch (TreasuresCleanupException e) {
			// SEVERE -> disable plugin
			Log.severe("onEnable() fatal error: TreasuresCleanupException", e.getThrowable());
		} catch (TreasuresLoadException e) {
			// SEVERE -> disable plugin
			Log.severe("onEnable() fatal error: TreasuresLoadException", e.getThrowable());
		} catch (SQLException e) {
			// SEVERE -> disable plugin
			Log.severe("onEnable() fatal error: SQLException", e);
		}
				
	}
	
	
	private void initDatabase() throws SQLException{
    	/*
		 * Le ConnectionSource pour ormlite
		 */				
		//String databaseUrl = "jdbc:mysql://192.168.1.25:3306/test";
		//_connectionSource = new JdbcConnectionSource(databaseUrl,"root","120676");	
        
		String databaseUrl = "jdbc:sqlite:plugins/BooTreasure/bootreasure.db";
		_connectionSource = new JdbcConnectionSource(databaseUrl);
		
		// Le DAO pour treasure
		_treasureDAO = DaoManager.createDao(_connectionSource, TreasureDAO.class);
		// Le DAO pour events
		_eventsDAO = DaoManager.createDao(_connectionSource, EventsDAO.class);

		// Create the table
        TableUtils.createTable(_connectionSource, TreasureDAO.class);
        TableUtils.createTable(_connectionSource, EventsDAO.class);
			
    }
	
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Boolean result = true;
		try {
			result = commandsManager.handleCommand(sender, command, commandLabel, args);
		} catch (CommandHandlerException e) {
			// SEVERE -> disable plugin
			Log.severe("onCommand() fatal error: CommandHandlerException", e.getThrowable());
		}
		return result;		
	}
	
	
	@Override
	public void onDisable() {
		
		if( configManager != null)
			configManager.onDisable();
		if( commandsManager != null)
			commandsManager.onDisable();
		if( cronManager != null)
			cronManager.onDisable();
		if( cacheManager != null)
			cacheManager.onDisable();
		if( eventsManager != null)
			eventsManager.onDisable();
		if( permissionsManager != null)
			permissionsManager.onDisable();
		if( treasuresManager != null)
			treasuresManager.onDisable();
		
	}
	
}
