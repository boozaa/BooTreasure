package org.shortrip.boozaa.plugins.bootreasure;


import org.apache.commons.cli.CommandLineParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class MyCommands {

	private Plugin plugin;
	private CommandLineParser parser;

	/*
	 * In game command
	 */
	public MyCommands(Plugin plugin, CommandSender sender, Command command, String commandLabel, String[] args) {
		this.plugin = plugin;
		Log.info("MyCommands -> commandLabel -> " + commandLabel);
		StringBuilder build = new StringBuilder();
		for( String arg : args ){
			Log.info("MyCommands -> arg -> " + arg);
			build.append(arg + " ");
		}
		Log.info("MyCommands -> args -> " + build.toString().trim() );
			
	}


	/*
	 * Console command
	 */
	public MyCommands(Plugin plugin, String[] args) {
		this.plugin = plugin;
	}

	
	
}
