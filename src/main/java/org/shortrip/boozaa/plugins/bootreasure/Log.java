package org.shortrip.boozaa.plugins.bootreasure;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Log {

	// Logger
	private static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	
	public static void info(String message) {
		console.sendMessage(Const.PLUGIN_NAME + "- " + message);		
	}
	
	public static void warning(String message) {
		console.sendMessage(Const.PLUGIN_NAME + "WARNING - " +  message);
	}
	
	public static void error(String message) {
		
		if( BooTreasure.getGeneralConf().getBoolean("config.debugMode") ) {			
			console.sendMessage(Const.PLUGIN_NAME + "- " + message);
		}
		
	}
	
	// Debug si activé
	public static void debug(String message) {
		
		if( BooTreasure.getGeneralConf().getBoolean("config.debugMode") ) {			
			console.sendMessage(Const.PLUGIN_NAME + "- DEBUG - " + message);
		}
		
	}
	
	
}
