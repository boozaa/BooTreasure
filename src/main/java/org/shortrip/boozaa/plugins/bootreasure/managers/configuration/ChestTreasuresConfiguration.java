package org.shortrip.boozaa.plugins.bootreasure.managers.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

/*
 * chests:
 * 		dfdfdsf-fsdfdsf-sdfdfsdf-fsdfsdfs:
 * 			basic:
 * 				name: bla
 * 				cronpattern: 1 1 1 1 1
 *  			world: world
 * 				infinite: true
 * 				duration: 60
 * 				preservecontent: false
 * 				onlyonsurface: true
 * 			contents:
 * 				items:
 * 			messages:
 * 				appear: '&bMy First Treasure appear'
 * 				found: '&bMy First Treasure was found'
 * 				disappear: '&bMy First Treasure disappear'
 */


public class ChestTreasuresConfiguration extends YamlConfiguration {
	
	protected File source;
	
	interface NODES{
		
		interface BASIC{
			final String NAME 					= "basic.name";
			final String PRESERVECONTENT 		= "basic.preservecontent";
			final String WAITINGEND 			= "basic.waitingend";
			final String INFINITE 				= "basic.infinite";
			final String WORLD 					= "basic.world";
			final String PATTERN 				= "basic.cronpattern";
			final String ONLYONSURFACE 			= "basic.onlyonsurface";
			final String DURATION 				= "basic.duration";			
		}
		
		final String ITEMS 					= "contents.items";
		
		interface MESSAGES{
			final String APPEARMESSAGE 			= "messages.appear";
			final String DISAPPEARMESSAGE 		= "messages.disappear";
			final String FOUNDMESSAGE 			= "messages.found";
		}
		
	}

	@SuppressWarnings("unused")
	private Plugin plugin;
	private final String ROOT = "chests";
	@Getter private ConfigurationSection treasureChestSection = null;
	
	
	
	
	
	public ChestTreasuresConfiguration(Plugin plugin) throws FileNotFoundException, ConfigLoadException, IOException, InvalidConfigurationException {
		this.source = new File(plugin.getDataFolder() + File.separator + "treasures.yml");
		this.plugin = plugin;
		if( !source.exists() ){
			Log.info("This config file doesn't exists, create it");
			createFile();
		}
		this.treasureChestSection = getConfigurationSection(ROOT);
		Log.info("Instanciate MainConfiguration");
	}
	
	
	@Override
	public void load(File source){
		try {
			
			Log.info("Enter in ChestTreasuresConfiguration load()");
			this.treasureChestSection = getConfigurationSection(ROOT);	
			
		} catch (Exception e) {
			Log.severe( "Error when trying to load config " + source.getName(), e);			
		}		
	}


	public void createFile() throws FileNotFoundException, ConfigLoadException, IOException, InvalidConfigurationException {
		
		this.save(this.source);
		
		String rootNode = ROOT + "49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.";
		
		if( get(ROOT) == null ) {
			set(rootNode + NODES.BASIC.NAME, 			"My First Treasure");
			set(rootNode + NODES.BASIC.PATTERN, 		"*/1 * * * *");
			set(rootNode + NODES.BASIC.DURATION, 		30);
			set(rootNode + NODES.BASIC.WORLD, 			"world");
			set(rootNode + NODES.BASIC.ONLYONSURFACE, 	true);
			set(rootNode + NODES.BASIC.PRESERVECONTENT, true);
			set(rootNode + NODES.BASIC.INFINITE, 		true);

			List<String> contentList = new ArrayList<String>();
			contentList.add( "POTION;;16454;;1;;;;;;" );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			contentList.add( null );
			set(rootNode + NODES.ITEMS, contentList);

			set(rootNode + NODES.MESSAGES.APPEARMESSAGE, 	"&bMy First Treasure appear");
			set(rootNode + NODES.MESSAGES.FOUNDMESSAGE, 	"&bMy First Treasure was found");
			set(rootNode + NODES.MESSAGES.DISAPPEARMESSAGE, "&bMy First Treasure disappear");
		}
		
		this.save(this.source);
		this.load(this.source);
		
	}
	
	
	public List<TreasureChest> getAllTreasures(){
		
		List<TreasureChest> result = new ArrayList<TreasureChest>();
		
		Set<String> chests = treasureChestSection.getKeys(false);
		for( String ch : chests ){
			
			String chestNode = new StringBuilder().append(ROOT).append(".").append(ch).append(".").toString();
			String chestName = chestNode + NODES.BASIC.NAME;
			
		}
		
		return result;
		
	}
	

}
