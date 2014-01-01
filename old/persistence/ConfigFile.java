/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.persistence;

import java.io.File;
import java.io.IOException;

import org.shortrip.boozaa.plugins.bootreasure.old.Log;

import lombok.Getter;
import lombok.Setter;

/**
 * @author boozaa
 *
 * BooTreasure
 */
public abstract class ConfigFile {

	@Getter @Setter protected String _filepath;
	@Getter protected Configuration _config;
	
	public ConfigFile(String path){
		this._filepath = path;
		this.init();
	}
	
	
	protected void init(){
		File fichier = new File(this._filepath);
		if( !fichier.exists() ){
			// On crée le fichier
			try {
				fichier.createNewFile();				
			} catch (IOException e) {
				Log.error("Error when trying to create file " + this._filepath);
			}
		}	
		this._config = new Configuration(fichier);
		this._config.load();
	}
	
	public Boolean reload(){		
		this._config.save();
   	 	this._config.load();
		return true;
	}
	
	public abstract void load();
	
	
}
