package org.shortrip.boozaa.plugins.bootreasure;

public interface NODES {
	
	final String DEBUG 										= "debug";
	final String VERSION 									= "version";	
	final String DATABASE_TYPE 								= "database.type";	
	final String DATABASE_MYSQL_HOST 						= "database.mysql.host";
	final String DATABASE_MYSQL_NAME 						= "database.mysql.name";	
	final String DATABASE_MYSQL_USER 						= "database.mysql.user";	
	final String DATABASE_MYSQL_PASSWORD 					= "database.mysql.password";	
	final String DATABASE_MYSQL_PORT 						= "database.mysql.port";
	
	interface LOCALES{
		
		final String PREFIX					 				= "prefix";
		final String END			 						= "end";
		final String EXIT					 				= "exit";
		final String LIST					 				= "list";	
		
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
		
		
	}
	
	interface CHEST{
		
		interface BASIC{
			final String NAME 								= "basic.name";
			final String PRESERVECONTENT 					= "basic.preservecontent";
			final String WAITINGEND 						= "basic.waitingend";
			final String INFINITE 							= "basic.infinite";
			final String WORLD 								= "basic.world";
			final String PATTERN 							= "basic.cronpattern";
			final String ONLYONSURFACE 						= "basic.onlyonsurface";
			final String DURATION 							= "basic.duration";			
		}
		
		final String ITEMS 									= "contents.items";
		
		interface MESSAGES{
			final String APPEARMESSAGE 						= "messages.appear";
			final String DISAPPEARMESSAGE 					= "messages.disappear";
			final String FOUNDMESSAGE 						= "messages.found";
		}
		
	}
	
}
