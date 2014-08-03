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
import org.shortrip.boozaa.plugins.bootreasure.NODES;
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
	}
	
	
	@Override
	public void load(File source){
		try {
			
			this.treasureChestSection = getConfigurationSection(ROOT);	
			
		} catch (Exception e) {
			Log.severe( "Error when trying to load config " + source.getName(), e);			
		}		
	}


	public void createFile() throws FileNotFoundException, ConfigLoadException, IOException, InvalidConfigurationException {
		
		this.save(this.source);
		
		String rootNode = ROOT + ".49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.";
		
		if( get(ROOT) == null ) {
			set(rootNode + NODES.CHEST.BASIC.NAME, 			"My First Treasure");
			set(rootNode + NODES.CHEST.BASIC.PATTERN, 		"*/1 * * * *");
			set(rootNode + NODES.CHEST.BASIC.DURATION, 		30);
			set(rootNode + NODES.CHEST.BASIC.WORLD, 			"world");
			set(rootNode + NODES.CHEST.BASIC.ONLYONSURFACE, 	true);
			set(rootNode + NODES.CHEST.BASIC.PRESERVECONTENT, true);
			set(rootNode + NODES.CHEST.BASIC.INFINITE, 		true);

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
			set(rootNode + NODES.CHEST.ITEMS, contentList);

			set(rootNode + NODES.CHEST.MESSAGES.APPEARMESSAGE, 	"&bMy First Treasure appear");
			set(rootNode + NODES.CHEST.MESSAGES.FOUNDMESSAGE, 	"&bMy First Treasure was found");
			set(rootNode + NODES.CHEST.MESSAGES.DISAPPEARMESSAGE, "&bMy First Treasure disappear");
		}
		
		this.save(this.source);
		this.load(this.source);
		
	}
	
	
	public List<TreasureChest> getAllTreasures(){
		
		List<TreasureChest> result = new ArrayList<TreasureChest>();
		
		Set<String> chests = treasureChestSection.getKeys(false);
		for( String ch : chests ){
			
			String chestNode = ROOT + "." + ch;
			
			ConfigurationSection section = getConfigurationSection( chestNode );
			TreasureChest treasure = new TreasureChest(ch,section);
			result.add(treasure);
			
		}
		
		return result;
		
	}
	

}
