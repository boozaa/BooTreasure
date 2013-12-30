package org.shortrip.boozaa.plugins.bootreasure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;


public class Log {

	// Logger
	private static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	private static File errorFile;
	
	
	public static void info(String message) {
		console.sendMessage(Const.PLUGIN_NAME + "- " + message);		
	}
	
	public static void warning(String message) {
		console.sendMessage(Const.PLUGIN_NAME + "WARNING - " +  message);
	}
	
	public static void error(String message) {
		console.sendMessage(Const.PLUGIN_NAME + "ERROR - " + message);
	}
	
	public static void severe(String error, Throwable message) {
		console.sendMessage(Const.PLUGIN_NAME + "SEVERE - " +  message);
		writeError(error, message);
	}
	
	// Debug si activé
	public static void debug(String message) {
		
		if( BooTreasure.get_pluginConfiguration().getDebug() ) {			
			console.sendMessage(Const.PLUGIN_NAME + "- DEBUG - " + message);
		}
		
	}
	

	private static void writeError(String error, Throwable message){
		try {
		
			errorFile = new File( "plugins"  + File.separator + "BooTreasure" + File.separator + "errors.txt");
			
			if( !errorFile.exists() )
				errorFile.createNewFile();
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(errorFile, true)));	
			String nl = System.getProperty("line.separator");
			if( !errorFile.exists() ){
				out.println("If you have errors here please reports them on http://dev.bukkit.org/server-mods/bootreasure/" + nl);
			}
			
			Date today = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        	        
	        String vaultVersion = "";
	        if( Bukkit.getPluginManager().getPlugin("Vault") != null){
	        	vaultVersion = Bukkit.getPluginManager().getPlugin("Vault").getDescription().getVersion();
	        }else{
	        	vaultVersion = "Vault is not installed";
	        }
	        	        
	        out.println("------------------------------------------------------------------");
		    out.println(sdf.format(today));
		    out.println("Server bukkit Version: " + Bukkit.getServer().getBukkitVersion());
		    out.println( BooTreasure.getPluginName() + " version: " + BooTreasure.getPluginVersion());
		    out.println("Vault Version: " + vaultVersion);
		    out.println(nl);
		    out.println("Error occured on " + error);
		    out.println(nl);
		    out.println(message); 
		    out.close();
	        
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
