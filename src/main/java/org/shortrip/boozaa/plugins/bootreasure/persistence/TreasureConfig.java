/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.persistence;

import java.util.Arrays;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.TreasureType;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;

/**
 * @author boozaa
 *
 * BooTreasure
 */
@ToString( callSuper=false,exclude={"_type", "_content", "_whitelistId", "_blacklistId"} )
@EqualsAndHashCode( callSuper=false )
public class TreasureConfig extends ConfigFile {

	@Getter private String _name, _world, _cronPattern, _spawnMessage, _foundMessage, _disappearMessage, _foundButNotEmptyMessage;
	@Getter private Boolean _onlyOnSurface, _preserveContent, _infinite;
	@Getter private int _duration;
	@Getter private TreasureType _type;
	@Getter private List<Material> _content, _whitelistId, _blacklistId;
	
	
	public TreasureConfig(){
		super(BooTreasure.getTreasuresConfigPath());
	}
	

	@Override
	public void load(){
		
		// Recherche du World par défaut
		String world = Bukkit.getServer().getWorlds().get(0).getName();
					
		if( this._config.get( "treasures" ) == null ){
			
			this._config.options().header(
					"------------------------------------------------------------------------------------ #"
					+ System.getProperty("line.separator")
					+ BooTreasure.getPluginName() + " v" + BooTreasure.getPluginVersion()
					+ System.getProperty("line.separator") 
					+ "This is a basic treasures file, it contains only one treasure."
					+ System.getProperty("line.separator")
					+ "The starting node 'treasures:' permit to have more than one treasures in this file."
					+ System.getProperty("line.separator") 
					+ "The treasure setup here start on line 'My First Treasure' to the end of file."
					+ System.getProperty("line.separator") 
					+ "If you want setup more treasures simply copy/paste this part keeping the indentations."
					+ System.getProperty("line.separator")
					+ "------------------------------------------------------------------------------------ #"
			);
			
			this._config.set("treasures.My First Treasure.basics.name", "My First Treasure");
			this._config.set("treasures.My First Treasure.basics.duration", 600);
			this._config.set("treasures.My First Treasure.basics.onlyonsurface", true);
			this._config.set("treasures.My First Treasure.basics.preservecontent", true);
			this._config.set("treasures.My First Treasure.basics.type", "chest");
			this._config.set("treasures.My First Treasure.basics.cronpattern", "*/15 * * * *");
			this._config.set("treasures.My First Treasure.basics.world", world);

			
			String[] list = {"297"};
			this._config.set("treasures.My First Treasure.setup.contents.items", Arrays.asList(list));
			this._config.set("treasures.My First Treasure.setup.messages.spawn", "&bUn &4nouveau trésor &bvient d'apparaitre");
			this._config.set("treasures.My First Treasure.setup.messages.found", "&bLe trésor vient d'être découvert");
			this._config.set("treasures.My First Treasure.setup.messages.foundbutnotempty", "&bLe trésor vient d'être découvert mais pas vidé");
			this._config.set("treasures.My First Treasure.setup.messages.disappear", "&bLe trésor vient de disparaître");
			// On le sauvegarde
			this._config.save();
			
		}
		
		this._name = 		this._config.getString("treasures.My First Treasure.basics.name");
		this._world = 		this._config.getString("treasures.My First Treasure.basics.world");
		this._cronPattern = this._config.getString("treasures.My First Treasure.basics.cronpattern");
		this._spawnMessage = this._config.getString("treasures.My First Treasure.basics.name");
		this._name = this._config.getString("treasures.My First Treasure.basics.name");
		this._name = this._config.getString("treasures.My First Treasure.basics.name");
		
		
	}
	
	
	public void createNewTreasure( Treasure t ){
		String name = t.get_name();
		String pattern = t.get_pattern();
		String id = t.get_id();
		//List<Material> allowedids = t.get_allowedids();
		Boolean infinite = t.get_infinite();
		Boolean onlyonsurface = t.get_onlyonsurface();
		Boolean preservecontent = t.get_preservecontent();
		String world = t.get_world();
		Long duration = t.getDuration();

		this._config.set("treasures." + id + ".basics.name", 				name);
		this._config.set("treasures." + id + ".basics.cronpattern", 		pattern);
		this._config.set("treasures." + id + ".basics.duration", 			duration);		
		this._config.set("treasures." + id + ".basics.world", 				world);
		this._config.set("treasures." + id + ".basics.onlyonsurface", 		onlyonsurface);
		this._config.set("treasures." + id + ".basics.preservecontent", 	preservecontent);
		this._config.set("treasures." + id + ".basics.infinite", 			infinite);

		if( t instanceof ChestTreasure ){
			ChestTreasure ct = (ChestTreasure) t;
			ItemStack[] items = ct.get_inventory();
			this._config.set("treasures." + id + ".setup.contents.items", 			items);
		}
		
		
		
		this._config.save();
		this._config.load();
		
	}
	
	
}
