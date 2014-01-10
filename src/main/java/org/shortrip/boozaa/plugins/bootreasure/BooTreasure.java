package org.shortrip.boozaa.plugins.bootreasure;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.shortrip.boozaa.plugins.bootreasure.listeners.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyCommands.CommandHandlerException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyCommands.CommandNullException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyConfigs.ConfigNullFileException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyPermissions.PermissionsVaultNullException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyTreasuresManager.TreasuresCleanupException;
import org.shortrip.boozaa.plugins.bootreasure.managers.Managers.MyTreasuresManager.TreasuresLoadException;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import lombok.Getter;


public class BooTreasure  extends JavaPlugin{

	
	@Getter private static BooTreasure instance;
	@Getter private static Managers managers;
	/*
	@Getter private static MyConfigs configManager;
	@Getter private static MyCommands commandsManager;
	@Getter private static MyCron cronManager;
	@Getter private static MyCache cacheManager;
	@Getter private static MyEvents eventsManager;
	@Getter private static MyPermissions permissionsManager;
	@Getter private static MyTreasuresManager treasuresManager;
	@Getter private static MyDatabase databaseManager;
	*/
	
	@Override
	public void onEnable() {
		
		try {
			
			instance = this;				
			managers = new Managers(this);
			// MyPlayerListener
			getServer().getPluginManager().registerEvents( new MyPlayerListener(this), this );
							
			
		} catch (
				CommandNullException
				| ConfigNullFileException
				| SQLException
				| PermissionsVaultNullException
				| TreasuresCleanupException
				| TreasuresLoadException e) {
			
			Log.warning("Fatal error occured during startup, the plugin must be disabled");
			Log.severe("The fatal error will be stored in plugins/BooTreasure/errors.txt", e);
		}
			
			/*
			// Instanciate system
			configManager 			= new MyConfigs(this);
			commandsManager 		= new MyCommands(this);
			cronManager 			= new MyCron(this);
			cacheManager 			= new MyCache(this);
			eventsManager			= new MyEvents(this);
			permissionsManager 		= new MyPermissions(this);
			treasuresManager 		= new MyTreasuresManager(this);
			databaseManager			= new MyDatabase(this);
			*/
			
				
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Boolean result = true;
		try {
			result = managers.getCommandsManager().handleCommand(sender, command, commandLabel, args);
		} catch (CommandHandlerException e) {
			// SEVERE -> disable plugin
			Log.severe("onCommand() fatal error: CommandHandlerException", e.getThrowable());
		}
		return result;		
	}
	
	
	@Override
	public void onDisable() {
		
		/*
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
		*/
	}
	
}
