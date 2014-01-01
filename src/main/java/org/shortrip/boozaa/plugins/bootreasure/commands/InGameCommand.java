package org.shortrip.boozaa.plugins.bootreasure.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.commands.CommandFramework.Command;
import org.shortrip.boozaa.plugins.bootreasure.commands.CommandFramework.CommandArgs;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.ChestCreateProcedure;


public class InGameCommand {

	private Plugin plugin;
	
	public InGameCommand( Plugin plugin ){
		this.plugin = plugin;
	}
	
	
	@Command(name = "bootreasure", 
			aliases = { "bootreasure" }, 
			description = "This is bootreasure command", 
			usage = "This is how you use it")
	public void bootreasure(CommandArgs args) {
		args.getSender().sendMessage("This is bootreasure command");
	}
	
	
	@Command(name = "bootreasure.test", aliases = { "bootreasure.testing" }, description = "This is a test command", usage = "This is how you use it")
	public void test(CommandArgs args) {
		args.getSender().sendMessage("This is a test command");
	}
	

	@Command(name = "bootreasure.chest.new", aliases = { "bootreasure.chest.new" }, description = "This is a test command", usage = "This is how you use it")
	public void createChest(CommandArgs args) {
		args.getSender().sendMessage("This is the chest creation procedure");
		Bukkit.getScheduler().runTask(plugin, new ChestCreateProcedure( plugin, (Player) args.getSender() ) );
	}
	
}
