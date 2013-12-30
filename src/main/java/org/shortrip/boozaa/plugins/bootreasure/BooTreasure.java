package org.shortrip.boozaa.plugins.bootreasure;

import org.shortrip.boozaa.plugins.bootreasure.persistence.Cache;
import org.shortrip.boozaa.plugins.bootreasure.persistence.Configuration;
import org.shortrip.boozaa.plugins.bootreasure.persistence.MainConfig;
import org.shortrip.boozaa.plugins.bootreasure.persistence.MessageConfig;
import org.shortrip.boozaa.plugins.bootreasure.persistence.TreasureConfig;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.CreateChest;
import org.shortrip.boozaa.plugins.bootreasure.serializer.BukkitSerializer;
import org.shortrip.boozaa.plugins.bootreasure.utils.tiers.VaultUtils;
import org.shortrip.boozaa.plugins.bootreasure.cron.CronScheduler;
import org.shortrip.boozaa.plugins.bootreasure.cron.CronSchedulerListener;
import org.shortrip.boozaa.plugins.bootreasure.cron.CronTaskCollector;
import org.shortrip.boozaa.plugins.bootreasure.cron.tasks.TreasureTask;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.listeners.*;

import java.io.File;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author boozaa
 * 
 *         BooTreasure
 */
public class BooTreasure extends JavaPlugin {

	// Plugin name
	@Getter private static String pluginName;
	// Plugin version
	@Getter private static String pluginVersion;
	// Path to config.yml
	@Getter private static String mainConfigPath;
	// Path to treasures.yml
	@Getter private static String treasuresConfigPath;
	// Path of the folder lost+found
	@Getter private static String lostTreasuresPath;
	
	// The Cron4J Scheduler instance
	@Getter private static CronScheduler _scheduler;
	@Getter private static CronTaskCollector _taskCollector;
	
	// Cache treasures
	@Getter private static Cache _treasureCache;

	// The config.yml configuration representation
	@Getter private static MainConfig _pluginConfiguration;

	// The messages.yml configuration representation
	@Getter private static Configuration _messagesConfiguration;

	// The treasures Configuration
	@Getter private static TreasureConfig _treasuresConfiguration;

	// Vault hook
	@Getter @Setter private static Boolean useVault = false;
	@Getter private static VaultUtils _vaultUtils;
	
	// Boolean if using WorldGuard
	@Getter @Setter private static Boolean useWorldGuard = false;

	
	
	@Override
	public void onEnable() {

		
		pluginName = getDescription().getName();
		pluginVersion = getDescription().getVersion();
		mainConfigPath = getDataFolder() + File.separator + "config.yml";
		treasuresConfigPath = getDataFolder() + File.separator + "treasures.yml";
		lostTreasuresPath = getDataFolder() + File.separator + "lost+found" + File.separator;

		/*
		 * Yaml Configuration This line load the config.yml from the plugin
		 * folder
		 */
		loadConfig();

		/*
		 * Cleanup old treasures sticked on map
		 */
		cleanup();

		/*
		 * Cache Map To cache an object : BooTreasure2.getCache().add(String id,
		 * Object data) To retrieve this object use getter method ex:
		 * BooTreasure2.getCache().getBoolean(id)
		 */
		_treasureCache = new Cache();

		/*
		 * Vault functions
		 */
		_vaultUtils = new VaultUtils(this);

		/*
		 * Registered MyPlayerListener and give it the plugin relation
		 */
		getServer().getPluginManager().registerEvents(
				new MyPlayerListener(this), this);

		// Instanciate a Cron Scheduler
		_scheduler = new CronScheduler();
		// Add our listener to it
		_scheduler.addSchedulerListener(new CronSchedulerListener());
		// TaskCollector
		_taskCollector = new CronTaskCollector(this);
		_scheduler.addTaskCollector(_taskCollector);
		// Host system TimeZone
		TimeZone tz = Calendar.getInstance().getTimeZone();
		// Set its TimeZone to this TimeZone
		_scheduler.setTimeZone(tz);
		// Start the scheduler
		_scheduler.start();
		Log.info("Cron Scheduler started with TimeZone set to "
				+ tz.getDisplayName());

		createTreasuresFile();
		loadTreasureFile();

	}

	private void cleanup() {

		Log.info("Cleanup ... ");

		File losts = new File(lostTreasuresPath);
		for (File file : losts.listFiles()) {
			// Si fichier serial on le traite
			String filename = file.getName();
			String extension = filename.substring(
					filename.lastIndexOf(".") + 1, filename.length());

			// ChestTreasures
			if (extension.equalsIgnoreCase("chest")) {
				Log.info("Found old Treasure Chest: " + filename);
				// Deserialize
				ChestTreasure t = (ChestTreasure) BukkitSerializer
						.deserializeFromFile(file);
				
				Log.debug(t.toString());
				
				// Launch disappear method
				t.disappear();
				// Delete serialization file
				t.deleteSerializedFile();
				Log.info("ChestTreasure " + file + " deleted");
			}

		}

		Log.info("All lost Treasures cleared ...");

	}

	private void loadTreasureFile() {

		// Compteur
		int i = 0;

		if (new File(treasuresConfigPath).exists()) {

			Configuration yml = new Configuration(new File(treasuresConfigPath));
			// On charge le fichier
			yml.load();
			// On parcourt tous les treasures enfants de 'treasures:'
			ConfigurationSection treasures = yml
					.getConfigurationSection("treasures");

			if (treasures != null) {

				Set<String> enfants = treasures.getKeys(false);

				for (String enf : enfants) {

					String node = "treasures." + enf;
					// Ici t correspond à un tresor on crée un Treasure avec ca
					Treasure chest = new ChestTreasure(this, yml.getConfigurationSection(node));
					// On stocke en cache
					_treasureCache.add(chest.get_id(), chest);
					// On donne cette tache cron au collector
					_taskCollector.addTask(new TreasureTask(this, chest));
					i++;
				}
				// On annonce le nombre de trésors
				Log.info("Config Treasures Found: " + i);
			} else {
				Log.info("Can't found 'treasures:' initial node in your file " + treasuresConfigPath);
				return;
			}

		} else {
			Log.info("Config Treasures File Not Found: " + treasuresConfigPath);
		}

	}

	private void createTreasuresFile() {

		_treasuresConfiguration = new TreasureConfig();

		/*
		 * // Fichier config/treasures.yml String config = getDataFolder() +
		 * File.separator + "treasures.yml"; File fichier = new File(config);
		 * if( !fichier.exists() ){
		 * 
		 * try { // On crée le fichier fichier.createNewFile();
		 * 
		 * // On le charge en tant que Configuration Yaml Configuration
		 * fichierConf = new Configuration(fichier);
		 * 
		 * // Recherche du World par défaut String world =
		 * getServer().getWorlds().get(0).getName();
		 * 
		 * fichierConf.options().header(
		 * "------------------------------------------------------------------------------------ #"
		 * + System.getProperty("line.separator") + getDescription().getName() +
		 * " v" + getDescription().getVersion() +
		 * System.getProperty("line.separator") +
		 * "This is a basic treasures file, it contains only one treasure." +
		 * System.getProperty("line.separator") +
		 * "The starting node 'treasures:' permit to have more than one treasures in this file."
		 * + System.getProperty("line.separator") +
		 * "The treasure setup here start on line 'My First Treasure' to the end of file."
		 * + System.getProperty("line.separator") +
		 * "If you want setup more treasures simply copy/paste this part keeping the indentations."
		 * + System.getProperty("line.separator") +
		 * "------------------------------------------------------------------------------------ #"
		 * ); fichierConf.set("treasures.My First Treasure.basics.name",
		 * (String)"My First Treasure");
		 * fichierConf.set("treasures.My First Treasure.basics.duration",
		 * (int)600);
		 * fichierConf.set("treasures.My First Treasure.basics.onlyonsurface",
		 * (Boolean)true);
		 * fichierConf.set("treasures.My First Treasure.basics.preservecontent",
		 * (Boolean)true);
		 * fichierConf.set("treasures.My First Treasure.basics.type",
		 * (String)"chest");
		 * fichierConf.set("treasures.My First Treasure.basics.cronpattern",
		 * (String)"");
		 * fichierConf.set("treasures.My First Treasure.basics.world",
		 * (String)world);
		 * 
		 * 
		 * String[] list = {"297"};
		 * fichierConf.set("treasures.My First Treasure.setup.contents.items",
		 * Arrays.asList(list));
		 * fichierConf.set("treasures.My First Treasure.setup.messages.spawn",
		 * (String)"&bUn &4nouveau trésor &bvient d'apparaitre");
		 * fichierConf.set("treasures.My First Treasure.setup.messages.found",
		 * (String)"&bLe trésor vient d'être découvert"); fichierConf.set(
		 * "treasures.My First Treasure.setup.messages.foundbutnotempty",
		 * (String)"&bLe trésor vient d'être découvert mais pas vidé");
		 * fichierConf
		 * .set("treasures.My First Treasure.setup.messages.disappear",
		 * (String)"&bLe trésor vient de disparaître"); // On le sauvegarde
		 * fichierConf.save();
		 * 
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * 
		 * }
		 */
	}

	private void loadConfig() {

		// Fichier config.yml
		makeConfig();
		// Creation des dossiers de travail si nécessaire
		makeFolders();

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {

		// Pas d'arguments dans la commande on sort
		if (args.length == 0) {
			return false;
		}
		try {
			if (command.getName().equalsIgnoreCase("bootreasure")) {

				if (args[0].equalsIgnoreCase("s")) {

					if (sender instanceof Player) {

						Player player = (Player) sender;
						ItemStack iteminhand = player.getItemInHand();
						Log.debug("Tentative de serialisation d'un ItemStack");
						if (iteminhand != null) {
							Log.debug("Tentative de serialisation d'un ItemStack");
							BukkitSerializer.serializeToFile(iteminhand,
									getDataFolder() + File.separator
											+ "lost+found" + File.separator
											+ "item.serialized");
						}
						Log.debug("Fin de serialisation d'un ItemStack");
					}

				} else if (args[0].equalsIgnoreCase("d")) {

					if (sender instanceof Player) {

						Log.debug("Tentative de deserialisation d'un ItemStack");
						Player player = (Player) sender;
						File file = new File(getDataFolder() + File.separator
								+ "lost+found" + File.separator
								+ "item.serialized");
						if (file.exists()) {
							ItemStack iteminhand = (ItemStack) BukkitSerializer
									.deserializeFromFile(file);
							player.setItemInHand(iteminhand);
						}
						Log.debug("Fin de deserialisation d'un ItemStack");

					}
					
				} else if (args[0].equalsIgnoreCase("new")) {
					
					if (sender instanceof Player) {
						
						Bukkit.getScheduler().runTask(this, new CreateChest(this, (Player) sender));
						
						//Thread t = new Thread( new CreateChest(this, (Player) sender) );
						//t.start();
						// test
						//new CreateChest(this, (Player) sender);
					}
					
				} else {
					
				}

			}
		} catch (CommandException e) {
		}
		return true;

	}

	@Override
	public void onDisable() {

		// Set all static variable to null
		_treasureCache = null;
		_pluginConfiguration = null;
		_messagesConfiguration = null;
		_vaultUtils = null;

	}

	private void makeConfig() {

		String configPath = getDataFolder() + File.separator + "config.yml";
		_pluginConfiguration = new MainConfig();
		_pluginConfiguration.load();

		configPath = getDataFolder() + File.separator + "messages.yml";
		_messagesConfiguration = new MessageConfig(configPath);
		

	}

	private void makeFolders() {

		/*
		 * String treasures = getDataFolder() + File.separator + "treasures" +
		 * File.separator; File dir = new File ( treasures ); if( !dir.exists()
		 * ){ dir.mkdirs(); }
		 */

		String lost = getDataFolder() + File.separator + "lost+found"
				+ File.separator;
		File dir = new File(lost);
		if (!dir.exists()) {
			dir.mkdirs();
		}

	}

	public void loadTreasures() {

		// On charge le fichier config/treasures.yml
		File treasuresFile = new File(this.getDataFolder() + File.separator
				+ "config" + File.separator + "treasures.yml");
		if (!treasuresFile.exists()) {
			treasuresFile.mkdirs();
		}
		Configuration config = new Configuration(treasuresFile);
		if (config.get(Const.TASKS) != null) {

			// On va charger les treasures un a un
			Set<String> nodes = config.getKeys(Const.TASKS);

			for (String treasure : nodes) {

				// Partie basics
				ConfigurationSection section = config
						.getConfigurationSection(Const.TASKS + "."
								+ Const.TASKS_BASICS + "." + treasure);
				new ChestTreasure(this, section);

			}

		}

	}

}
