package org.shortrip.boozaa.plugins.bootreasure;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	// Logger
	private static Logger logger = Logger.getLogger("Minecraft");
	public static void log(Level level, String message) {
		logger.log(level, Const.PLUGIN_NAME + message);
	}
	
	public static void info(String message) {
		
		if( BooTreasure.getGeneralConf().getBoolean("config.debugMode") ) {			
			logger.log(Level.INFO, Const.PLUGIN_NAME + "- " + message);
		}
		
	}
	
	public static void error(String message) {
		
		if( BooTreasure.getGeneralConf().getBoolean("config.debugMode") ) {			
			logger.log(Level.SEVERE, Const.PLUGIN_NAME + "- " + message);
		}
		
	}
	
	// Debug si activé
	public static void debug(String message) {
		
		if( BooTreasure.getGeneralConf().getBoolean("config.debugMode") ) {			
			logger.log(Level.INFO, Const.PLUGIN_NAME + "- DEBUG - " + message);
		}
		
	}
	
	
}
