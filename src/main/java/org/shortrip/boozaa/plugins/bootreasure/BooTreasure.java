package org.shortrip.boozaa.plugins.bootreasure;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.shortrip.boozaa.plugins.bootreasure.listeners.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCache;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands.CommandHandlerException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands.CommandNullException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyConfigs;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyConfigs.ConfigNullFileException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCron;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyDatabase;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyEvents;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyPermissions;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyPermissions.PermissionsVaultNullException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyTreasuresManager;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyTreasuresManager.TreasuresCleanupException;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyTreasuresManager.TreasuresLoadException;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import lombok.Getter;


public class BooTreasure  extends JavaPlugin{

	/*
	 * Static access to the Plugin instance
	 */
	@Getter private static BooTreasure instance;
	/*
	 * All the static Managers
	 */
	@Getter private static MyCache cacheManager;
	@Getter private static MyCommands commandsManager;
	@Getter private static MyConfigs configsManager;
	@Getter private static MyCron cronManager;
	@Getter private static MyDatabase databaseManager;
	@Getter private static MyEvents eventsManager;
	@Getter private static MyPermissions permissionsManager;
	@Getter private static MyTreasuresManager treasuresManager;
	

	
	
	@Override
	public void onEnable() {
	
			instance = this;
			// Load all stuff
			try {
				
				configsManager 		= new MyConfigs(this);
				databaseManager 	= new MyDatabase(this);
				cacheManager 		= new MyCache(this);
				commandsManager 	= new MyCommands(this);
				cronManager 		= new MyCron(this);
				eventsManager 		= new MyEvents(this);
				permissionsManager 	= new MyPermissions(this);
				treasuresManager 	= new MyTreasuresManager(this);
				
				
				// MyPlayerListener
				getServer().getPluginManager().registerEvents( new MyPlayerListener(this), this );

			
				
			} catch (ConfigNullFileException e) {
				// SEVERE -> disable plugin
				Log.warning("Fatal error occured during startup, the plugin must be disabled");
				Log.severe("onEnable() fatal ConfigNullFileException", e);
			} catch (SQLException e) {
				// SEVERE -> disable plugin
				Log.warning("Fatal error occured during startup, the plugin must be disabled");
				Log.severe("onEnable() fatal SQLException", e);
			} catch (CommandNullException e) {
				// SEVERE -> disable plugin
				Log.warning("Fatal error occured during startup, the plugin must be disabled");
				Log.severe("onEnable() fatal CommandNullException", e);
			} catch (PermissionsVaultNullException e) {
				// SEVERE -> disable plugin
				Log.warning("Fatal error occured during startup, the plugin must be disabled");
				Log.severe("onEnable() fatal PermissionsVaultNullException", e);
			} catch (TreasuresCleanupException e) {
				// SEVERE -> disable plugin
				Log.warning("Fatal error occured during startup, the plugin must be disabled");
				Log.severe("onEnable() fatal TreasuresCleanupException", e);
			} catch (TreasuresLoadException e) {
				// SEVERE -> disable plugin
				Log.warning("Fatal error occured during startup, the plugin must be disabled");
				Log.severe("onEnable() fatal TreasuresLoadException", e);
			}
				
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Boolean result = true;
		try {
			result = commandsManager.handleCommand(sender, command, commandLabel, args);
		} catch (CommandHandlerException e) {
			// WARNING
			Log.warning("onCommand() fatal error: CommandHandlerException" + e.getThrowable());
		}
		return result;		
	}
	
	
	@Override
	public void onDisable() {
		
		// Launch all managers onDisable()
		if( treasuresManager != null )
			treasuresManager.onDisable();
		
		if( permissionsManager != null )
			permissionsManager.onDisable();
		
		if( eventsManager != null )
			eventsManager.onDisable();
		
		if( cronManager != null )
			cronManager.onDisable();
		
		if( commandsManager != null )
			commandsManager.onDisable();
		
		if( cacheManager != null )
			cacheManager.onDisable();
		
		if( databaseManager != null )
			databaseManager.onDisable();
		
		if( configsManager != null )
			configsManager.onDisable();
		
	}
	
}
