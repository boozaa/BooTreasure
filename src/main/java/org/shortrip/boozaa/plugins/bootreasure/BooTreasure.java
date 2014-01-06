package org.shortrip.boozaa.plugins.bootreasure;

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

import lombok.Getter;


public class BooTreasure  extends JavaPlugin{

	
	@Getter private static BooTreasure _instance;
	@Getter private static MyConfigs _configManager;
	@Getter private static MyCommands _commandsManager;
	@Getter private static MyCron _cronManager;
	@Getter private static MyCache _cacheManager;
	@Getter private static MyEvents _eventsManager;
	@Getter private static MyPermissions _permissionsManager;
	@Getter private static MyTreasuresManager _treasuresManager;
	
	
	
	@Override
	public void onEnable() {
				
		try {
			
			_instance = this;
			
			// Instanciate system
			_configManager 			= new MyConfigs(this);
			_commandsManager 		= new MyCommands(this);
			_cronManager 			= new MyCron(this);
			_cacheManager 			= new MyCache(this);
			_eventsManager			= new MyEvents(this);
			_permissionsManager 	= new MyPermissions(this);
			_treasuresManager 		= new MyTreasuresManager(this);
			
			// MyPlayerListener
			getServer().getPluginManager().registerEvents( new MyPlayerListener(this), this );
			
			
			
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
		}
				
	}
	
	
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Boolean result = true;
		try {
			result = _commandsManager.handleCommand(sender, command, commandLabel, args);
		} catch (CommandHandlerException e) {
			// SEVERE -> disable plugin
			Log.severe("onCommand() fatal error: CommandHandlerException", e.getThrowable());
		}
		return result;		
	}
	
	
	@Override
	public void onDisable() {
		
		if( _configManager != null)
			_configManager.onDisable();
		if( _commandsManager != null)
			_commandsManager.onDisable();
		if( _cronManager != null)
			_cronManager.onDisable();
		if( _cacheManager != null)
			_cacheManager.onDisable();
		if( _eventsManager != null)
			_eventsManager.onDisable();
		if( _permissionsManager != null)
			_permissionsManager.onDisable();
		if( _treasuresManager != null)
			_treasuresManager.onDisable();
		
	}
	
}
