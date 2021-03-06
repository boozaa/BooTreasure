package org.shortrip.boozaa.plugins.bootreasure.managers;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandFramework;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandParser;


public class MyCommands {

	private Plugin plugin;
	private CommandFramework manager;
	
	public MyCommands(Plugin booTreasure) throws CommandNullException {
		this.plugin = booTreasure;
		manager = new CommandFramework(this.plugin);
		// Commands
		manager.registerCommands(new CommandParser(plugin));
	}
	
	public void registerCommands(Object obj) throws CommandNullException {
		if( obj != null ){
			manager.registerCommands( obj );
		}else{
			throw new CommandNullException("The command Object is null");
		}				
	}
	
	public Boolean handleCommand( CommandSender sender, Command command, String commandLabel, String[] args ) throws CommandHandlerException{
		try{
			Boolean result = manager.handleCommand(sender, commandLabel, command, args);
			return result;
		}catch( Exception e ){
			throw new CommandHandlerException("The command handler failed.", e);			
		}		
	}
	
	
	
	
	public class CommandNullException extends Exception {
		private static final long serialVersionUID = 1L;		
		public CommandNullException(String message) {
	        super(message);
	    }		
	}
	
	public class CommandHandlerException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public CommandHandlerException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
	    }		
	}

	
	public void onDisable() {
		manager = null;
	}

}
