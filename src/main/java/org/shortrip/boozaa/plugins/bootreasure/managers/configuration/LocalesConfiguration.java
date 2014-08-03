package org.shortrip.boozaa.plugins.bootreasure.managers.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import lombok.Getter;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.NODES;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

/*
 * locales:
 * 		prefix: '[BooTreasure]'
 * 		end: stop
 * 		exit: '/exit'
 * 		list: 'List of treasures'
 * 		chest:
 * 			questions:
 * 				preservecontent: ''
 * 				waitingend: ''
 * 			intro:
 * 				create: ''
 * 				edit: ''
 * 				delete: ''
 * 			success:
 * 				create: ''
 * 				edit: ''
 * 				delete: ''
 */			


public class LocalesConfiguration extends YamlConfiguration {

	protected File source;
	private final String 	ROOT 	= "locales";
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	@Getter private String prefix, end, exit, list;
	
	@Getter private String 	chest_question_preservecontent, 
							chest_question_waitingend, 
							chest_question_infinite, 
							chest_question_world, 
							chest_question_pattern, 
							chest_question_name, 
							chest_question_onlyonsurface,
							chest_question_duration,
							chest_question_appearmessage,
							chest_question_disappearmessage,
							chest_question_foundmessage;
	
	@Getter private String 	chest_intro_create, chest_intro_edit, chest_intro_delete;
	@Getter private String 	chest_success_create, chest_success_edit, chest_success_delete;
	@Getter private String 	chest_failure_create, chest_failure_edit, chest_failure_delete;
	
	
	public LocalesConfiguration(Plugin plugin) throws FileNotFoundException, ConfigLoadException, IOException, InvalidConfigurationException {
		//super(plugin.getDataFolder() + File.separator + "messages.yml");
		this.source = new File(plugin.getDataFolder() + File.separator + "messages.yml");
		this.plugin = plugin;
		Log.info("Instanciate MainConfiguration");
		if( !source.exists() ){
			Log.info("This config file doesn't exists, create it");
			createFile();
		}
	}
	
	@Override
	public void load(File source){
		
		try {
			
			Log.info("Enter in LocalesConfiguration load()");

			this.prefix 							= getString( ROOT + "." + NODES.LOCALES.PREFIX );
			this.end 								= getString( ROOT + "." + NODES.LOCALES.END );
			this.exit 								= getString( ROOT + "." + NODES.LOCALES.EXIT );
			this.list 								= getString( ROOT + "." + NODES.LOCALES.LIST );
			
			this.chest_intro_create 				= getString( ROOT + "." + NODES.LOCALES.CHEST.CREATE.INTRO );
			this.chest_intro_edit 					= getString( ROOT + "." + NODES.LOCALES.CHEST.EDIT.INTRO );
			this.chest_intro_delete 				= getString( ROOT + "." + NODES.LOCALES.CHEST.DELETE.INTRO );

			this.chest_success_create 				= getString( ROOT + "." + NODES.LOCALES.CHEST.CREATE.SUCCESS );
			this.chest_success_edit 				= getString( ROOT + "." + NODES.LOCALES.CHEST.EDIT.SUCCESS );
			this.chest_success_delete 				= getString( ROOT + "." + NODES.LOCALES.CHEST.DELETE.SUCCESS );

			this.chest_failure_create 				= getString( ROOT + "." + NODES.LOCALES.CHEST.CREATE.FAILURE );
			this.chest_failure_edit 				= getString( ROOT + "." + NODES.LOCALES.CHEST.EDIT.FAILURE );
			this.chest_failure_delete 				= getString( ROOT + "." + NODES.LOCALES.CHEST.DELETE.FAILURE );

			this.chest_question_preservecontent 	= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.PRESERVECONTENT );
			this.chest_question_waitingend 			= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.WAITINGEND );
			this.chest_question_infinite 			= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.INFINITE );
			this.chest_question_world 				= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.WORLD );
			this.chest_question_pattern 			= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.PATTERN );
			this.chest_question_name 				= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.NAME );
			this.chest_question_onlyonsurface 		= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.ONLYONSURFACE );
			this.chest_question_duration 			= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.DURATION );
			this.chest_question_appearmessage 		= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.APPEARMESSAGE );
			this.chest_question_disappearmessage 	= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.DISAPPEARMESSAGE );
			this.chest_question_foundmessage 		= getString( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.FOUNDMESSAGE );
			
			
		} catch (Exception e) {
			Log.severe( "Error when trying to load config " + source.getName(), e);			
		}	
				
	}
	

	public void createFile() throws FileNotFoundException, ConfigLoadException, IOException, InvalidConfigurationException {
		
		this.save(this.source);
		
		if( get( ROOT + "." + NODES.LOCALES.PREFIX ) == null ) {
			set( ROOT + "." + NODES.LOCALES.PREFIX , (String)"[BooTreasure]");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.END ) == null ) {
			set( ROOT + "." + NODES.LOCALES.END , (String)"stop");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.EXIT ) == null ) {
			set( ROOT + "." + NODES.LOCALES.EXIT , (String)"/exit");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.LIST ) == null ) {
			set( ROOT + "." + NODES.LOCALES.LIST , (String)"List of treasures");
		}
		
		
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.PRESERVECONTENT ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.PRESERVECONTENT , (String)"&3Did its future &econtents &3must be preserved ?");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.WAITINGEND ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.WAITINGEND , (String)"&3Fill the chest and when finish type: &7exit");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.INFINITE ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.INFINITE , (String)"&eInfinite &3Apparition ?");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.WORLD ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.WORLD , (String)"&3In which &eworld&3 ?");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.PATTERN ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.PATTERN , (String)"&3Type the &ecron pattern");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.NAME ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.NAME , (String)"&3What &ename &3this treasure will have ?");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.ONLYONSURFACE ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.ONLYONSURFACE , (String)"&3Only appear &eon surface&3 ?");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.DURATION ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.DURATION , (String)"&eHow long &3this treasure must be in world ?");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.APPEARMESSAGE ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.APPEARMESSAGE , (String)"&eSentence &3for the appear event ?");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.DISAPPEARMESSAGE ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.DISAPPEARMESSAGE , (String)"&eSentence &3for the disappear event ?");
		}
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.FOUNDMESSAGE ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.QUESTIONS.FOUNDMESSAGE , (String)"&eSentence &3for the found event ?");
		}
		
		
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.CREATE.INTRO ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.CREATE.INTRO , (String)"&3Treasure creation");
		}
		if( get( ROOT + "." + NODES.LOCALES.CHEST.EDIT.INTRO ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.EDIT.INTRO , (String)"&3Treasure edition");
		}
		if( get( ROOT + "." + NODES.LOCALES.CHEST.DELETE.INTRO ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.DELETE.INTRO , (String)"&3Treasure deletion");
		}
		
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.CREATE.SUCCESS ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.CREATE.SUCCESS , (String)"&3Treasure created");
		}
		if( get( ROOT + "." + NODES.LOCALES.CHEST.EDIT.SUCCESS ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.EDIT.SUCCESS , (String)"&3Treasure edited");
		}
		if( get( ROOT + "." + NODES.LOCALES.CHEST.DELETE.SUCCESS ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.DELETE.SUCCESS , (String)"&3Treasure deleted");
		}
		
		
		if( get( ROOT + "." + NODES.LOCALES.CHEST.CREATE.FAILURE ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.CREATE.FAILURE , (String)"&3Treasure creation failed");
		}
		if( get( ROOT + "." + NODES.LOCALES.CHEST.EDIT.FAILURE ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.EDIT.FAILURE , (String)"&3Treasure edition failed");
		}
		if( get( ROOT + "." + NODES.LOCALES.CHEST.DELETE.FAILURE ) == null ) {
			set( ROOT + "." + NODES.LOCALES.CHEST.DELETE.FAILURE , (String)"&3Treasure deletion failed");
		}
		
		this.save(this.source);
		this.load(this.source);
		
	}

}
