package org.shortrip.boozaa.plugins.bootreasure;

import lombok.Getter;
import org.shortrip.boozaa.libs.vaultpermission.VaultPermission;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCache;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCron;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyDatabase;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyEvents;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyTreasuresManager;



public class Managers {

	@SuppressWarnings("unused")
	private BooTreasure plugin;
	@Getter private static VaultPermission permission;
	@Getter private static MyCache cacheManager;
	@Getter private static MyCron cronManager;
	@Getter private static MyEvents eventsManager;
	@Getter private static MyDatabase databaseManager;
	@Getter private static MyTreasuresManager treasuresManager;
	@Getter private static MyCommands commandsManager;
	
	
	public Managers( BooTreasure plugin ) throws Exception{
		
		this.plugin = plugin;		
		
		// Commands
		commandsManager = new MyCommands(plugin);
		
		// Permission, 'true' mean required
		permission = new VaultPermission(plugin, true);
		
		// Cache
		cacheManager = new MyCache(plugin);
		
		// eventsManager
		eventsManager = new MyEvents(plugin);
		
		// database
		databaseManager = new MyDatabase(plugin);
		
		// Cron
		cronManager = new MyCron(plugin);
		
		// treasuresManager
		treasuresManager = new MyTreasuresManager(plugin);
		
	}
	
	
	public void onDisable(){
		

		if( commandsManager != null )
			commandsManager.onDisable();

		if( permission != null )
			permission = null;

		if( cacheManager != null )
			cacheManager.onDisable();

		if( eventsManager != null )
			eventsManager.onDisable();

		if( databaseManager != null )
			databaseManager.onDisable();

		if( cronManager != null )
			cronManager.onDisable();			

		if( treasuresManager != null )
			treasuresManager.onDisable();
		
	}
	
}
