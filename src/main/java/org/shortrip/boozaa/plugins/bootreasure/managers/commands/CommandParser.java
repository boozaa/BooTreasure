package org.shortrip.boozaa.plugins.bootreasure.managers.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandFramework.Command;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandFramework.CommandArgs;
import org.shortrip.boozaa.plugins.bootreasure.managers.commands.CommandFramework.Completer;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.ChestCreateProcedure;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.ChestEditProcedure;
import org.shortrip.boozaa.plugins.bootreasure.utils.DataUtils;
import org.shortrip.boozaa.plugins.bootreasure.utils.DataUtils.DataUtilParserException;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public class CommandParser {

	private BooTreasure plugin;
	
	public CommandParser( BooTreasure plugin ){
		this.plugin = plugin;
	}
	
	@Command( name = "bootreasure.debug", aliases = { "bootreasure.debug" } )
	public void consoleToggleDebug(CommandArgs args) {
		if( !( args.getSender() instanceof Player ) ) {
			this.plugin.getMainConfig().toggleDebug();
		}		
	}
	
	
	@Command(name = "bootreasure", aliases = { "bootreasure" }, description = "This is bootreasure command", usage = "This is how you use it")
	public void bootreasure(CommandArgs args) {
		args.getSender().sendMessage("bootreasure commands:");
		args.getSender().sendMessage("debug - toggle debug mode");
	}
	

	@Command(name = "bootreasure.test", aliases = { "bootreasure.test" }, description = "This is bootreasure command", usage = "This is how you use it")
	public void bootreasureTest(CommandArgs args) throws DataUtilParserException {
		
		Log.debug("Test inventory");
		Player player = (Player) args.getSender();
		ItemStack itemInHand = player.getItemInHand();
		String representation = DataUtils.toString(itemInHand);
		Log.debug( "String representation for " + itemInHand.toString() + " -> " + representation );
		Log.debug( "';;;'.split(';').length -> " + ";;;".split(";").length );
		Log.debug( representation + " to ItemStack -> ");
		Log.debug( DataUtils.fromString( representation ).toString()  );
		
		
		if( itemInHand.getType() == Material.ENCHANTED_BOOK ){
			Log.debug("ENCHANTED_BOOK: ");
			
			Log.debug( "itemInHand.getData(): " + itemInHand.getData().toString() );
			
			for( Entry<Enchantment, Integer> enchant : itemInHand.getEnchantments().entrySet() ){
				Log.debug( "Enchantment: " + enchant.getKey().toString() + " - " + enchant.getValue() );
			}
			
			ItemMeta metas = itemInHand.getItemMeta();
			Log.debug( "itemInHand.getItemMeta(): " + metas.toString() );
			
			
		}
		
		
	}
	
	
	
	@Command(name = "bootreasure.chest", aliases = { "bootreasure.chest" }, description = "This is bootreasure command", usage = "This is how you use it")
	public void chestMenu(final CommandArgs args) {
		
		ChestMenu menu = new ChestMenu("Chest Treasure Menu", 9, new ChestMenu.OptionClickEventHandler() {
	        @Override
	        public void onOptionClick(ChestMenu.OptionClickEvent event) {
	            String action = event.getName();
	            if( action.equalsIgnoreCase("Create") ){
	            	Bukkit.getScheduler().runTask(plugin, new ChestCreateProcedure( plugin, (Player) args.getSender() ) );
	            	event.setWillClose(true);
				}
	            if( action.equalsIgnoreCase("Edit") ){
	            	Bukkit.getScheduler().runTask(plugin, new ChestEditProcedure( plugin, (Player) args.getSender() ) );
	            	event.setWillClose(true);
				}	            	            
	        }
	    }, plugin)
	    .setOption(0, new ItemStack(Material.CHEST, 1), "Create", "Create a new Chest Treasure")
	    .setOption(1, new ItemStack(Material.CHEST, 1), "Edit", "Edit a Chest Treasure")
	    .setOption(2, new ItemStack(Material.CHEST, 1), "Remove", "Remove a Chest Treasure");
		
		menu.open(args.getPlayer());
		
	}


	@Command(name = "bootreasure.chest.create", aliases = { "bootreasure.create" }, 
			description = "Start Chest Treasure creation procedure", usage = "/bootreasure chest create", 
			permission = "bootreasure.chest.create", noPerm = "You can't do that")
	public void createChest(CommandArgs args) {
		args.getSender().sendMessage("This is the chest creation procedure");
		Bukkit.getScheduler().runTask(plugin, new ChestCreateProcedure( plugin, (Player) args.getSender() ) );
	}
	
	
	@Command(name = "bootreasure.chest.edit", aliases = { "bootreasure.edit" }, 
			description = "Start Chest Treasure edit procedure", usage = "/bootreasure chest edit", 
			permission = "bootreasure.chest.edit", noPerm = "You can't do that")
	public void editChest(CommandArgs args) {
		args.getSender().sendMessage("This is the chest edit procedure");
		Bukkit.getScheduler().runTask(plugin, new ChestEditProcedure( plugin, (Player) args.getSender() ) );
	}
	
	
	@Completer(name = "bootreasure.chest", aliases = { "bootreasure.chest" })
	public List<String> chestCompleter(CommandArgs args) {
		List<String> list = new ArrayList<String>();
		list.add("list");
		list.add("create");
		list.add("edit <treasure id>");
		list.add("delete <treasure id>");
		return list;
	}
	
	
}
