/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.config;

import java.io.File;
import java.io.IOException;

import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.persistence.Configuration;

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
	
	protected ConfigFile(String path){
		this._filepath = path;
		this.init();
	}
	
	
	protected void init(){
		File fichier = new File(this._filepath);
		if( !fichier.exists() ){
			// On crée le fichier
			try {
				fichier.createNewFile();
				this._config = new Configuration(fichier);	
			} catch (IOException e) {
				Log.error("Error when trying to create file " + this._filepath);
			}
		}			
	}
	
}
