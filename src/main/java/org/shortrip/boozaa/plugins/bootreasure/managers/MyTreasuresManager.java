package org.shortrip.boozaa.plugins.bootreasure.managers;

import java.io.File;
import java.util.List;

import lombok.Getter;

import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Managers;
import org.shortrip.boozaa.plugins.bootreasure.managers.cron.tasks.TreasureTask;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public class MyTreasuresManager {

	private Plugin plugin;
	@Getter public final String treasures_file = "treasures.yml";
	
	
	public MyTreasuresManager( Plugin booTreasure ) throws TreasuresCleanupException, TreasuresLoadException{
		this.plugin = booTreasure;
		// Make folders
		makeFolders();
		// Cleanup lost treasures
		cleanup();
		// Load treasures
		loadTreasures();
	}
	
	private void cleanup() throws TreasuresCleanupException {

		Log.info("Cleanup eventual lost treasures ... ");
		
		/*
		if( BooTreasure.getConfigManager().get( "config.yml" ).getBoolean("config.bukkitserialization") == false ){
			Log.debug("BukkitSerialization is disabled on config.yml");
			return;
		}
		*/
		
		int qty = 0;
		
		try{
			
			File losts = new File( "plugins/BooTreasure/lost+found" );
			for (File file : losts.listFiles()) {
				// Si fichier serial on le traite
				
				String filename = file.getName();
				String extension = filename.substring( filename.lastIndexOf(".") + 1, filename.length() );
				
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
		String lost = this.plugin.getDataFolder() + File.separator + "lost+found" + File.separator;
		File dir = new File(lost);
		if (!dir.exists()) {
			dir.mkdirs();
		}
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
				Managers.getCronManager().addTask(new TreasureTask(this.plugin, ch));
				
				// Quantity increment
				qty++;
			}
		
		} catch (Exception e) {
			Log.severe("Error when trying to load treasures from treasures.yml", e);
			//throw new TreasuresLoadException("Error when trying to load treasures from treasures.yml", e);
		}
		
		// On charge le fichier config/treasures.yml		
		//Configuration config = BooTreasure.getConfigManager().get("treasures.yml");
		/*Configuration config = BooTreasure.getTreasuresConfig();
		if (config.get("treasures") != null) {

			Log.debug("treasures.yml loaded and 'treasures' node found");
			
			// On va charger les treasures un a un
			Set<String> nodes = config.getKeys("treasures");

			for (String treasureId : nodes) {

				try{

					Log.debug("- parse the treasure with id " + treasureId);
					
					// Partie basics
					ConfigurationSection section = config.getConfigurationSection("treasures." + treasureId);
					
					
					// Depend on TreasureType
					if( section.contains("basics.type") ){
						if( section.getString("basics.type").equalsIgnoreCase("chest") ){
							TreasureChest treasure = new TreasureChest(treasureId,section);
							// Store in cache
							Managers.getCacheManager().add(treasure.get_id(), treasure);
							
							// Give the new CronTask
							Managers.getCronManager().addTask(new TreasureTask(this.plugin, treasure));
							
							// Quantity increment
							qty++;
						}
					}
					
					
				
				} catch (Exception e) {
					Log.severe("Error when trying to load treasures from treasures.yml", e);
					//throw new TreasuresLoadException("Error when trying to load treasures from treasures.yml", e);
				}

			}

		}*/
		Log.info(" ... " + qty + " treasure(s) loaded");
	}
	
	
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

	public class TreasuresCleanupException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public TreasuresCleanupException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
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
	
	
}
