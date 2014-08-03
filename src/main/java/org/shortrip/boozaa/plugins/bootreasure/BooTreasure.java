package org.shortrip.boozaa.plugins.bootreasure;

import java.io.File;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.shortrip.boozaa.plugins.bootreasure.listeners.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands.CommandHandlerException;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks.TreasureTask;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import lombok.Getter;


public class BooTreasure extends JavaPlugin{

	@Getter private Managers managers;



	@Override
	public void onEnable() {
			
		try {

			// Load all stuff
			managers = new Managers(this);

			getServer().getPluginManager().registerEvents( new MyPlayerListener(this), this );
			
			// Make needed folders
			makeFolders();
			//Clean all losts treasures
			cleanup();
			//Load treasures
			loadTreasures();
			
			Log.debug("The debug mode is activated. To disable it use command: bootreasure debug");


		} catch (Exception e) {
			// SEVERE -> disable plugin
			Log.warning("Fatal error occured during startup");
			Log.severe("onEnable() fatal Exception", e);
		}

	}



	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Boolean result = true;
		try {
			result = Managers.getCommandsManager().handleCommand(sender, command, commandLabel, args);
		} catch (CommandHandlerException e) {
			// WARNING
			Log.warning("onCommand() error: CommandHandlerException " + e.getThrowable());
		}
		return result;
	}


	@Override
	public void onDisable() {
		if( managers != null)
			managers.onDisable();
	}
	

	public void loadTreasures() throws TreasuresLoadException {
		
		Log.info("Loading treasures from treasures.yml ... ");
		int qty = 0;
		
		try{
			
			Log.debug("Search 'treasures' node in treasures.yml");
					
			List<TreasureChest> chestTreasures = Managers.getTreasuresConfig().getAllTreasures();
			for( TreasureChest ch : chestTreasures ){
				// Store in cache
				Managers.getCacheManager().add(ch.get_id(), ch);				
				// Give the new CronTask
				Managers.getCronManager().addTask(new TreasureTask(this, ch));				
				// Quantity increment
				qty++;
			}
		
		} catch (Exception e) {
			throw new TreasuresLoadException("Error when trying to load treasures from treasures.yml", e);
		}
		Log.info(" ... " + qty + " treasure(s) loaded");
		
	}
	
	

	private void cleanup() throws TreasuresCleanupException {

		Log.info("Cleanup eventual lost treasures ... ");
		int qty = 0;
		
		try{
			
			File losts = new File( this.getDataFolder() + "lost+found" );
			for (File file : losts.listFiles()) {
				
				String filename 	= file.getName();
				String extension 	= filename.substring( filename.lastIndexOf(".") + 1, filename.length() );
				
				// ChestTreasures
				if (extension.equalsIgnoreCase("chest")) {					
					Log.info("Found old Treasure Chest: " + filename);
					// Deserialize
					TreasureChest t = new TreasureChest(file);
					
					if( t != null ){
						Log.debug(t.toString());
						// Launch disappear method
						t.disappear();
						// Delete serialization file
						t.deleteSerializedFile();
						Log.info("TreasureChest " + file + " deleted");
						// Increment counter
						qty++;
					}					
				}				
			}			
			Log.info(" ... " + qty + " lost Treasures cleared");

		}catch( Exception e){
			throw new TreasuresCleanupException("Exception during cleanup", e);
		}
		
	}
	
	

	private void makeFolders() {
		String lost = getDataFolder() + File.separator + "lost+found" + File.separator;
		File dir = new File(lost);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	
	
	
	
	
	public class TreasuresLoadException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public TreasuresLoadException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
	    }		
	}
	

	public class TreasuresCleanupException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public TreasuresCleanupException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
	    }		
	}
	

}


