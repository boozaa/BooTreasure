package org.shortrip.boozaa.plugins.bootreasure;


import java.io.OutputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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
		
		parser = new GnuParser();
						
		Options options = new Options();
		
		Option help = new Option( "help", "print this message" );
		options.addOption( help );
		
		// add t option
		options.addOption( "n", "name", false, "Specify the treasure's name" );
		options.addOption( "p", "pattern", false, "Specify the treasure's pattern" );
		

		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "BooTreasure", options );
		
		
		try {
			
			CommandLine cmd = parser.parse( options, args);
			if( cmd.hasOption("-name") || cmd.hasOption("-n")  ) {
				Log.info("MyCommands -> option présente -> -name" );
			}else if( cmd.hasOption("-pattern") || cmd.hasOption("-p") ){
				Log.info("MyCommands -> option présente -> -pattern" );
			}else {
				Log.info("MyCommands -> aucune option présente" );
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/*
	 * Console command
	 */
	public MyCommands(Plugin plugin, String[] args) {
		this.plugin = plugin;
	}

	
	
}
