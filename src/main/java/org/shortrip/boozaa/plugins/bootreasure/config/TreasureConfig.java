/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.TreasureType;

/**
 * @author boozaa
 *
 * BooTreasure
 */
@Data
@ToString( callSuper=false,exclude={"_type", "_content", "_whitelistId", "_blacklistId"} )
@EqualsAndHashCode( callSuper=false )
public class TreasureConfig extends ConfigFile {

	private String _name, _world, _cronPattern, _spawnMessage, _foundMessage, _disappearMessage, _foundButNotEmptyMessage;
	private Boolean _onlyOnSurface, _preserveContent, _infinite;
	private int _duration;
	private TreasureType _type;
	private List<Material> _content, _whitelistId, _blacklistId;
	
	
	public TreasureConfig(){
		super(BooTreasure.getInstance().getDataFolder() + File.separator + "treasures.yml");
	}
	
	
	public void load(){
		
		// Recherche du World par défaut
		String world = Bukkit.getServer().getWorlds().get(0).getName();
					
		if( this._config.get( "treasures" ) == null ){
			
			this._config.options().header(
					"------------------------------------------------------------------------------------ #"
					+ System.getProperty("line.separator")
					+ BooTreasure.getInstance().getDescription().getName() + " v" + BooTreasure.getInstance().getDescription().getVersion()
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
	
	
}
