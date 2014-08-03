package org.shortrip.boozaa.plugins.bootreasure;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.shortrip.boozaa.plugins.bootreasure.listeners.MyPlayerListener;
import org.shortrip.boozaa.plugins.bootreasure.managers.MyCommands.CommandHandlerException;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;
import lombok.Getter;


public class BooTreasure  extends JavaPlugin{

	@Getter private Managers managers;



	@Override
	public void onEnable() {

			// Load all stuff
			try {

				Log.info("To enable/disable debug mode use command: bootreasure debug");
				
				managers = new Managers(this);

				getServer().getPluginManager().registerEvents( new MyPlayerListener(this), this );

				Log.debug("The debug mode is activated. To disable it use command: bootreasure debug");
				
				

				/*mainConfig = new Configuration( this.getDataFolder() + File.separator + "config.yml" );
				if( !mainConfig.exists() ){
					mainConfig.save();
		    	}
				mainConfig.load();

				// Version
				if( mainConfig.get("config.version") == null ) {
					// Il n'existe pas on le fixe
					mainConfig.set("config.version", getDescription().getVersion());
				}else{
					// On vérifie si a jour
					String oldVersion = mainConfig.getString("config.version");
					// On vérifie si à jour
					if( !getDescription().getVersion().equalsIgnoreCase(oldVersion) ){
						mainConfig.set("config.version", getDescription().getVersion());
					}
				}

				// debug
				if( mainConfig.get("config.debug") == null ) {
					mainConfig.set("config.debug", true);
				}

				// database
				if( mainConfig.get("config.database") == null ) {
					mainConfig.set("config.database", "sqlite");
				}

				// database
				if( mainConfig.get("config.mysql") == null ) {
					mainConfig.set("config.mysql.host", "localhost");
					mainConfig.set("config.mysql.port", "3306");
					mainConfig.set("config.mysql.database", "bootreasure");
					mainConfig.set("config.mysql.user", "root");
					mainConfig.set("config.mysql.pass", "azerty");
				}
				mainConfig.save();
				mainConfig.load();


				messagesConfig = new Configuration( this.getDataFolder() + File.separator + "messages.yml" );
				if( !messagesConfig.exists() ){
					messagesConfig.save();
		    	}
				messagesConfig.load();

				// locales
				if( messagesConfig.get("locales.create.chest.ask") == null ) {
					messagesConfig.set("locales.create.chest.ask.preservecontent", "&3Did its future &econtents &3must be preserved ?");
					messagesConfig.set("locales.create.chest.ask.waitingend", "&3Fill the chest and when finish type: &7exit");
					messagesConfig.set("locales.create.chest.ask.infinite", "&eInfinite &3Apparition ?");
					messagesConfig.set("locales.create.chest.ask.world", "&3In which &eworld&3 ?");
					messagesConfig.set("locales.create.chest.ask.pattern", "&3Type the &ecron pattern&");
					messagesConfig.set("locales.create.chest.ask.name", "&3What &ename &3this treasure will have ?");
					messagesConfig.set("locales.create.chest.ask.allowedids", "&eParticular block &bon where this treasure must pop");
					messagesConfig.set("locales.create.chest.ask.onlyonsurface", "&3Only appear &eon surface&3 ?");
					messagesConfig.set("locales.create.chest.ask.duration", "&eHow long &3this treasure must be in world ?");
					messagesConfig.set("locales.create.chest.ask.appearmessage", "&eSentence &3for the appear event ?");
					messagesConfig.set("locales.create.chest.ask.disappearmessage", "&eSentence &3for the disappear event ?");
					messagesConfig.set("locales.create.chest.ask.foundmessage", "&eSentence &3for the found event ?");
					messagesConfig.set("locales.create.chest.intro", "&3Treasure creation");
					messagesConfig.set("locales.create.chest.prefix", "&2[BooTreasure] ");
					messagesConfig.set("locales.create.chest.success", "&3Treasure successfully configured");
				}

				if( messagesConfig.get("locales.edit.chest.ask") == null ) {
					messagesConfig.set("locales.edit.chest.ask.listalltreasures", "&3Choose which treasure to &eedit");
					messagesConfig.set("locales.edit.chest.ask.preservecontent", "&3Did its future &econtents &3must be preserved ?");
					messagesConfig.set("locales.edit.chest.ask.waitingend", "&3Fill the chest and when finish type: &7");
					messagesConfig.set("locales.edit.chest.ask.infinite", "&eInfinite &3Apparition ?");
					messagesConfig.set("locales.edit.chest.ask.world", "&3In which &eworld&3 ?");
					messagesConfig.set("locales.edit.chest.ask.pattern", "&3Type the &ecron pattern&3");
					messagesConfig.set("locales.edit.chest.ask.name", "&3What &ename &3this treasure will have ?");
					messagesConfig.set("locales.edit.chest.ask.allowedids", "&eParticular block &bon where this treasure must pop");
					messagesConfig.set("locales.edit.chest.ask.onlyonsurface", "&3Only appear &eon surface&3 ?");
					messagesConfig.set("locales.edit.chest.ask.duration", "&eHow long &3this treasure must be in world ?");
					messagesConfig.set("locales.edit.chest.ask.appearmessage", "&eSentence &3for the appear event ?");
					messagesConfig.set("locales.edit.chest.ask.disappearmessage", "&eSentence &3for the disappear event ?");
					messagesConfig.set("locales.edit.chest.ask.foundmessage", "&eSentence &3for the found event ?");
					messagesConfig.set("locales.edit.chest.intro", "&3Treasure edit");
					messagesConfig.set("locales.edit.chest.prefix", "&2[BooTreasure] ");
					messagesConfig.set("locales.edit.chest.success", "&3Treasure successfully configured");
				}

				if( messagesConfig.get("locales.delete.chest.ask") == null ) {
					messagesConfig.set("locales.delete.chest.ask.listalltreasures", "&3Choose which treasure to &eedit");
					messagesConfig.set("locales.delete.chest.intro", "&3Treasure delete");
					messagesConfig.set("locales.delete.chest.prefix", "&2[BooTreasure] ");
					messagesConfig.set("locales.delete.chest.success", "&3Treasure successfully deleted");
				}

				if( messagesConfig.get("locales.commands") == null ) {
					messagesConfig.set("locales.commands.exit", "exit");
					messagesConfig.set("locales.commands.end", "/end");
				}
				messagesConfig.save();
				messagesConfig.load();



				treasuresConfig = new Configuration( this.getDataFolder() + File.separator + "treasures.yml" );
				if( !treasuresConfig.exists() ){
					treasuresConfig.save();
		    	}
				treasuresConfig.load();

				if( treasuresConfig.get("treasures") == null ) {
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.name", "My First Treasure");
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.type", "chest");
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.cronpattern", "1 1 1 1 1");
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.duration", 30);
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.world", "world");
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.onlyonsurface", true);
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.preservecontent", true);
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.infinite", true);

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
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.setup.contents.items", contentList);

					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.setup.contents.messages.appear", "&bMy First Treasure appear");
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.setup.contents.messages.found", "&bMy First Treasure was found");
					treasuresConfig.set("treasures.49b64cb2-79e6-4ee6-8eb8-2fdd614fabc3.basics.setup.contents.messages.disappear", "&bMy First Treasure disappear");
				}
				treasuresConfig.save();
				treasuresConfig.load();*/



				


			} catch (Exception e) {
				// SEVERE -> disable plugin
				Log.warning("Fatal error occured during startup, the plugin must be disabled");
				Log.severe("onEnable() fatal Exception", e);
			}

	}



	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		Boolean result = true;
		try {
			result = Managers.getCommandsManager().handleCommand(sender, command, commandLabel, args);
		} catch (CommandHandlerException e) {
			// WARNING
			Log.warning("onCommand() fatal error: CommandHandlerException" + e.getThrowable());
		}
		return result;
	}


	@Override
	public void onDisable() {
		/*if( configManager != null )
			configManager = null;*/
		if( managers != null)
			managers.onDisable();
	}

}
