package org.shortrip.boozaa.plugins.bootreasure;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.shortrip.boozaa.plugins.bootreasure.listeners.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands.CommandHandlerException;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import lombok.Getter;


public class BooTreasure  extends JavaPlugin{

	@Getter private Managers managers;



	@Override
	public void onEnable() {
		
		
		
		// Load all stuff
		try {
			
			
			Log.info("To enable/disable debug mode use command: bootreasure debug");
			
			managers = new Managers(this);

			getServer().getPluginManager().registerEvents( new MyPlayerListener(this), this );

			Log.debug("The debug mode is activated. To disable it use command: bootreasure debug");


		} catch (Exception e) {
			// SEVERE -> disable plugin
			Log.warning("Fatal error occured during startup, the plugin must be disabled");
			Log.severe("onEnable() fatal Exception", e);
		}

	}



	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Boolean result = true;
		try {
			result = Managers.getCommandsManager().handleCommand(sender, command, commandLabel, args);
		} catch (CommandHandlerException e) {
			// WARNING
			Log.warning("onCommand() fatal error: CommandHandlerException" + e.getThrowable());
		}
		return result;
	}


	@Override
	public void onDisable() {
		/*if( configManager != null )
			configManager = null;*/
		if( managers != null)
			managers.onDisable();
	}

}
