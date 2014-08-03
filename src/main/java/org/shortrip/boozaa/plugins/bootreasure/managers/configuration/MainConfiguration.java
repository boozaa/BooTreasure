package org.shortrip.boozaa.plugins.bootreasure.managers.configuration;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import lombok.Getter;


/*
 * config:
 * 		debug: true
 * 		version: 0.1
 * 		database:
 * 			type: mysql
 * 			mysql:
 * 				host: localhost
 * 				port: 3306
 * 				name: bootreasure
 * 				user: root
 * 				password: azerty
 * 
 */


public class MainConfiguration extends YamlConfiguration {
	
	protected File source;
	private final String 	ROOT 	= "config";
	
	public static enum DatabaseType{		
		MYSQL,
		SQLITE;		
	}
	
	interface NODES{
		final String DEBUG 						= "debug";
		final String VERSION 					= "version";	
		final String DATABASE_TYPE 				= "database.type";	
		final String DATABASE_MYSQL_HOST 		= "database.mysql.host";
		final String DATABASE_MYSQL_NAME 		= "database.mysql.name";	
		final String DATABASE_MYSQL_USER 		= "database.mysql.user";	
		final String DATABASE_MYSQL_PASSWORD 	= "database.mysql.password";	
		final String DATABASE_MYSQL_PORT 		= "database.mysql.port";	
	}
	
	private Plugin plugin;
	
	@Getter private boolean debug 	= true;
	@Getter private String 	version = "";
	
	@Getter private DatabaseType databaseType = DatabaseType.SQLITE;
	@Getter private String database_host, database_name, database_user, database_password;
	@Getter private int database_port;
	
	
	public MainConfiguration(Plugin plugin) throws FileNotFoundException, ConfigLoadException, IOException, InvalidConfigurationException {
		//super(plugin.getDataFolder() + File.separator + "config.yml");
		this.source = new File(plugin.getDataFolder() + File.separator + "config.yml");
		this.plugin = plugin;
		Log.info("Instanciate MainConfiguration");
		if( !source.exists() ){
			Log.info("This config file doesn't exists, create it");
			createFile();
		}
	}
	
	public void toggleDebug(){		
		try {
			this.debug = this.debug ? false : true;
			this.save(this.source);
			this.load(this.source);
		} catch (Exception e) {
			Log.severe( "Error when trying to change debug state in " + source.getName(), e);
		}
	}
	
	
	@Override
	public void load(File source){
		try {

			Log.info("Enter in MainConfiguration load()");
						
			this.debug 		= getBoolean( 	ROOT + "." + NODES.DEBUG );
			this.version 	= getString( 	ROOT + "." + NODES.VERSION );
			if( getString( ROOT + "." + NODES.DATABASE_TYPE ).equalsIgnoreCase("mysql") ){
				this.databaseType 		= DatabaseType.MYSQL;
				this.database_host 		= getString( 	ROOT + "." + NODES.DATABASE_MYSQL_HOST );
				this.database_name 		= getString( 	ROOT + "." + NODES.DATABASE_MYSQL_NAME );
				this.database_user 		= getString( 	ROOT + "." + NODES.DATABASE_MYSQL_USER );
				this.database_password 	= getString( 	ROOT + "." + NODES.DATABASE_MYSQL_PASSWORD );
				this.database_port 		= getInt( 	ROOT + "." + NODES.DATABASE_MYSQL_PORT );
			}else if( getString( ROOT + "." + NODES.DATABASE_TYPE ).equalsIgnoreCase("sqlite") ){
				this.databaseType = DatabaseType.SQLITE;
			}
			
			
			
		} catch (Exception e) {
			Log.severe( "Error when trying to load config " + source.getName(), e);			
		}		
	}


	public void createFile() throws FileNotFoundException, ConfigLoadException, IOException, InvalidConfigurationException {
		
		this.save(this.source);

		// Version
		if( get(ROOT + "." + NODES.VERSION) == null ) {
			// Il n'existe pas on le fixe
			set( ROOT + "." + NODES.VERSION, this.plugin.getDescription().getVersion());
		}else{
			// On vérifie si a jour
			String oldVersion = getString(ROOT + "." + NODES.VERSION);
			// On vérifie si à jour
			if( !this.plugin.getDescription().getVersion().equalsIgnoreCase(oldVersion) ){
				set( ROOT + "." + NODES.VERSION, this.plugin.getDescription().getVersion());
			}
		}

		// debug
		if( get( ROOT + "." + NODES.DEBUG ) == null ) {
			set( ROOT + "." + NODES.DEBUG , true);
		}

		// database
		if( get(ROOT + "." + NODES.DATABASE_TYPE) == null ) {
			set(ROOT + "." + NODES.DATABASE_TYPE, "sqlite");
		}

		// mysql database
		if( get(ROOT + "." + NODES.DATABASE_MYSQL_HOST) == null ) {
			set(ROOT + "." + NODES.DATABASE_MYSQL_HOST, "localhost");
			set(ROOT + "." + NODES.DATABASE_MYSQL_PORT, "3306");
			set(ROOT + "." + NODES.DATABASE_MYSQL_NAME, "bootreasure");
			set(ROOT + "." + NODES.DATABASE_MYSQL_USER, "root");
			set(ROOT + "." + NODES.DATABASE_MYSQL_PASSWORD, "azerty");
		}
		
		this.save(this.source);
		this.load(this.source);
		
	}
	
	

}
