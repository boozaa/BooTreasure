package org.shortrip.boozaa.plugins.bootreasure.managers.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
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


public class LocalesConfiguration extends Configuration {

	private final String 	ROOT 	= "locales";
	
	interface NODES{
		
		interface CHEST{
			interface QUESTIONS{
				final String PRESERVECONTENT 				= "chest.questions.preservecontent";
				final String WAITINGEND 					= "chest.questions.waitingend";
				final String INFINITE		 				= "chest.questions.infinite";
				final String WORLD 							= "chest.questions.world";
				final String PATTERN		 				= "chest.questions.pattern";
				final String NAME			 				= "chest.questions.name";
				final String ONLYONSURFACE	 				= "chest.questions.onlyonsurface";
				final String DURATION		 				= "chest.questions.duration";
				final String APPEARMESSAGE	 				= "chest.questions.appearmessage";
				final String DISAPPEARMESSAGE 				= "chest.questions.disappearmessage";
				final String FOUNDMESSAGE 					= "chest.questions.foundmessage";
			}
			
			interface CREATE{
				final String INTRO			 				= "chest.intro.create";
				final String SUCCESS		 				= "chest.success.create";
				final String FAILURE		 				= "chest.failure.create";
			}
			
			interface EDIT{
				final String INTRO			 				= "chest.intro.edit";
				final String SUCCESS		 				= "chest.success.edit";
				final String FAILURE		 				= "chest.failure.edit";
			}
			
			interface DELETE{
				final String INTRO			 				= "chest.intro.delete";
				final String SUCCESS		 				= "chest.success.delete";
				final String FAILURE		 				= "chest.failure.delete";
			}
					
		}
		final String PREFIX					 				= "prefix";
		final String END			 						= "end";
		final String EXIT					 				= "exit";
		final String LIST					 				= "list";
			
	}
	
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
		super(plugin.getDataFolder() + File.separator + "messages.yml");
		this.plugin = plugin;
	}
	
	@Override
	public void load(){
		
		try {
			
			this.load(this.source);

			this.prefix 							= getString( ROOT + "." + NODES.PREFIX );
			this.end 								= getString( ROOT + "." + NODES.END );
			this.exit 								= getString( ROOT + "." + NODES.EXIT );
			this.list 								= getString( ROOT + "." + NODES.LIST );
			
			this.chest_intro_create 				= getString( ROOT + "." + NODES.CHEST.CREATE.INTRO );
			this.chest_intro_edit 					= getString( ROOT + "." + NODES.CHEST.EDIT.INTRO );
			this.chest_intro_delete 				= getString( ROOT + "." + NODES.CHEST.DELETE.INTRO );

			this.chest_success_create 				= getString( ROOT + "." + NODES.CHEST.CREATE.SUCCESS );
			this.chest_success_edit 				= getString( ROOT + "." + NODES.CHEST.EDIT.SUCCESS );
			this.chest_success_delete 				= getString( ROOT + "." + NODES.CHEST.DELETE.SUCCESS );

			this.chest_failure_create 				= getString( ROOT + "." + NODES.CHEST.CREATE.FAILURE );
			this.chest_failure_edit 				= getString( ROOT + "." + NODES.CHEST.EDIT.FAILURE );
			this.chest_failure_delete 				= getString( ROOT + "." + NODES.CHEST.DELETE.FAILURE );

			this.chest_question_preservecontent 	= getString( ROOT + "." + NODES.CHEST.QUESTIONS.PRESERVECONTENT );
			this.chest_question_waitingend 			= getString( ROOT + "." + NODES.CHEST.QUESTIONS.WAITINGEND );
			this.chest_question_infinite 			= getString( ROOT + "." + NODES.CHEST.QUESTIONS.INFINITE );
			this.chest_question_world 				= getString( ROOT + "." + NODES.CHEST.QUESTIONS.WORLD );
			this.chest_question_pattern 			= getString( ROOT + "." + NODES.CHEST.QUESTIONS.PATTERN );
			this.chest_question_name 				= getString( ROOT + "." + NODES.CHEST.QUESTIONS.NAME );
			this.chest_question_onlyonsurface 		= getString( ROOT + "." + NODES.CHEST.QUESTIONS.ONLYONSURFACE );
			this.chest_question_duration 			= getString( ROOT + "." + NODES.CHEST.QUESTIONS.DURATION );
			this.chest_question_appearmessage 		= getString( ROOT + "." + NODES.CHEST.QUESTIONS.APPEARMESSAGE );
			this.chest_question_disappearmessage 	= getString( ROOT + "." + NODES.CHEST.QUESTIONS.DISAPPEARMESSAGE );
			this.chest_question_foundmessage 		= getString( ROOT + "." + NODES.CHEST.QUESTIONS.FOUNDMESSAGE );
			
			
		} catch (Exception e) {
			Log.severe( "Error when trying to load config " + source.getName(), e);			
		}	
				
	}
	

	@Override
	public void createFile() throws FileNotFoundException, ConfigLoadException, IOException, InvalidConfigurationException {
		
		this.source.createNewFile();
		reload();
		
		if( get( ROOT + "." + NODES.PREFIX ) == null ) {
			set( ROOT + "." + NODES.PREFIX , (String)"[BooTreasure]");
		}
		
		if( get( ROOT + "." + NODES.END ) == null ) {
			set( ROOT + "." + NODES.END , (String)"stop");
		}
		
		if( get( ROOT + "." + NODES.EXIT ) == null ) {
			set( ROOT + "." + NODES.EXIT , (String)"/exit");
		}
		
		if( get( ROOT + "." + NODES.LIST ) == null ) {
			set( ROOT + "." + NODES.LIST , (String)"List of treasures");
		}
		
		
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.PRESERVECONTENT ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.PRESERVECONTENT , (String)"&3Did its future &econtents &3must be preserved ?");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.WAITINGEND ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.WAITINGEND , (String)"&3Fill the chest and when finish type: &7exit");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.INFINITE ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.INFINITE , (String)"&eInfinite &3Apparition ?");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.WORLD ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.WORLD , (String)"&3In which &eworld&3 ?");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.PATTERN ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.PATTERN , (String)"&3Type the &ecron pattern");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.NAME ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.NAME , (String)"&3What &ename &3this treasure will have ?");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.ONLYONSURFACE ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.ONLYONSURFACE , (String)"&3Only appear &eon surface&3 ?");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.DURATION ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.DURATION , (String)"&eHow long &3this treasure must be in world ?");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.APPEARMESSAGE ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.APPEARMESSAGE , (String)"&eSentence &3for the appear event ?");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.DISAPPEARMESSAGE ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.DISAPPEARMESSAGE , (String)"&eSentence &3for the disappear event ?");
		}
		
		if( get( ROOT + "." + NODES.CHEST.QUESTIONS.FOUNDMESSAGE ) == null ) {
			set( ROOT + "." + NODES.CHEST.QUESTIONS.FOUNDMESSAGE , (String)"&eSentence &3for the found event ?");
		}
		
		
		
		if( get( ROOT + "." + NODES.CHEST.CREATE.INTRO ) == null ) {
			set( ROOT + "." + NODES.CHEST.CREATE.INTRO , (String)"&3Treasure creation");
		}
		if( get( ROOT + "." + NODES.CHEST.EDIT.INTRO ) == null ) {
			set( ROOT + "." + NODES.CHEST.EDIT.INTRO , (String)"&3Treasure edition");
		}
		if( get( ROOT + "." + NODES.CHEST.DELETE.INTRO ) == null ) {
			set( ROOT + "." + NODES.CHEST.DELETE.INTRO , (String)"&3Treasure deletion");
		}
		
		
		if( get( ROOT + "." + NODES.CHEST.CREATE.SUCCESS ) == null ) {
			set( ROOT + "." + NODES.CHEST.CREATE.SUCCESS , (String)"&3Treasure created");
		}
		if( get( ROOT + "." + NODES.CHEST.EDIT.SUCCESS ) == null ) {
			set( ROOT + "." + NODES.CHEST.EDIT.SUCCESS , (String)"&3Treasure edited");
		}
		if( get( ROOT + "." + NODES.CHEST.DELETE.SUCCESS ) == null ) {
			set( ROOT + "." + NODES.CHEST.DELETE.SUCCESS , (String)"&3Treasure deleted");
		}
		
		
		if( get( ROOT + "." + NODES.CHEST.CREATE.FAILURE ) == null ) {
			set( ROOT + "." + NODES.CHEST.CREATE.FAILURE , (String)"&3Treasure creation failed");
		}
		if( get( ROOT + "." + NODES.CHEST.EDIT.FAILURE ) == null ) {
			set( ROOT + "." + NODES.CHEST.EDIT.FAILURE , (String)"&3Treasure edition failed");
		}
		if( get( ROOT + "." + NODES.CHEST.DELETE.FAILURE ) == null ) {
			set( ROOT + "." + NODES.CHEST.DELETE.FAILURE , (String)"&3Treasure deletion failed");
		}
		
		
		
		reload();
	}

}
