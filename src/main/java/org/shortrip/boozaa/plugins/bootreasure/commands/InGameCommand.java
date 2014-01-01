package org.shortrip.boozaa.plugins.bootreasure.commands;

import org.shortrip.boozaa.plugins.bootreasure.commands.CommandFramework.Command;
import org.shortrip.boozaa.plugins.bootreasure.commands.CommandFramework.CommandArgs;


public class InGameCommand {

	
	@Command(name = "test", aliases = { "testing" }, description = "This is a test command", usage = "This is how you use it")
	public void test(CommandArgs args) {
		args.getSender().sendMessage("This is a test command");
	}
	
	
}
