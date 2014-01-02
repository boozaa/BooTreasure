package org.shortrip.boozaa.plugins.bootreasure.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;


public class Log {

	@Getter @Setter public static Boolean _debugON;
	private final static String prefix = "[BooTreasure] ";
	// Logger
	private static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	private static File errorFile;
	
	
	public static void info(String message) {
		console.sendMessage(prefix + "- " + message);		
	}
	
	public static void warning(String message) {
		console.sendMessage(prefix + "- WARNING - " +  message);
	}
	
	public static void error(String message) {
		console.sendMessage(prefix + "- ERROR - " + message);
	}
	
	public static void severe(String error, Throwable message) {
		console.sendMessage(prefix + "- SEVERE - Fatal error, the plugin must be disabled: " + message.getMessage());
		writeError(error, message);
		Bukkit.getPluginManager().disablePlugin(BooTreasure.get_instance());
	}
	
	// Debug si activé
	public static void debug(String message) {
		
		if( _debugON ) {			
			console.sendMessage(prefix + "- DEBUG - " + message);
		}
		
	}


	private static void writeError(String error, Throwable message){
		try {
		
			errorFile = new File( "plugins"  + File.separator + "BooTreasure" + File.separator + "errors.txt");
			
			if( !errorFile.exists() )
				errorFile.createNewFile();
			
			PrintStream ps = new PrintStream( new FileOutputStream(errorFile, true) );
			
			Date today = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	        	        
	        String vaultVersion = "";
	        if( Bukkit.getPluginManager().getPlugin("Vault") != null){
	        	vaultVersion = Bukkit.getPluginManager().getPlugin("Vault").getDescription().getVersion();
	        }else{
	        	vaultVersion = "Vault is not installed";
	        }
	        
	        ps.print( "\n" );
	        ps.print( "------------------------------------------------------------------\n" );
	        ps.print( sdf.format(today) + "\n" );
	        ps.print( "Server bukkit Version: " + Bukkit.getServer().getBukkitVersion() + "\n" );
	        ps.print( BooTreasure.get_instance().getName() + " version: " + BooTreasure.get_instance().getDescription().getVersion() + "\n" );
	        ps.print( "Vault Version: " + vaultVersion + "\n" + "\n" + "\n" );
	        ps.print( "Error occured on " + error + "\n" + "\n" );
	        message.printStackTrace(ps);
	        ps.print( "\n" );
	        
	        ps.close();
			
			/*
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
		    out.println( BooTreasure.get_instance().getName() + " version: " + BooTreasure.get_instance().getDescription().getVersion());
		    out.println("Vault Version: " + vaultVersion);
		    out.println(nl);
		    out.println("Error occured on " + error);
		    out.println(nl);
		    out.println(message);
		    out.println(nl);
		    out.println(nl);
		    out.close();
	        
		    info("This error is stored on errors.txt file, please provide its contents if you need to report this issue");
		    
		    */
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
