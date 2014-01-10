package org.shortrip.boozaa.plugins.bootreasure.constants;

import java.io.File;

import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.configuration.Configuration;


public class Const {

	/*
	 * Get the lost+found path
	 * @Deprecated 
	 */
	public static final String LOST_FOLDER_PATH						= BooTreasure.getInstance().getDataFolder() + File.separator + "lost+found" + File.separator;
	
	
	public static final String PERMISSIONS_PLAYER_CAN_ALL 			= "bootreasure.all";
	public static final String PERMISSIONS_PLAYER_CAN_FIND 			= "bootreasure.treasure.find";
	public static final String PERMISSIONS_PLAYER_CAN_BE_INFORMED 	= "bootreasure.treasure.informed";
	
	public static final String PERMISSIONS_ADMIN_CAN_ALL 			= "bootreasure.admin.all";
	public static final String PERMISSIONS_ADMIN_CAN_CREATE 		= "bootreasure.admin.create";
	public static final String PERMISSIONS_ADMIN_CAN_EDIT 			= "bootreasure.admin.edit";
	public static final String PERMISSIONS_ADMIN_CAN_DELETE 		= "bootreasure.admin.delete";
	
	// messages.yml
	private static Configuration getMessagesConfig(){
		return BooTreasure.getConfigsManager().get("messages.yml");
	}
	public static String YES			= getMessagesConfig().getString("locales.commands.agree");
	public static String NO 			= getMessagesConfig().getString("locales.commands.disagree");
	public static String EXIT			= getMessagesConfig().getString("locales.commands.exit");
	public static String END			= getMessagesConfig().getString("locales.commands.end");
	
	// Chest create
	public static String CHEST_CREATE_PREFIX 		= getMessagesConfig().getString("locales.create.chest.prefix").replaceAll("&", "§") + System.getProperty("line.separator");
	public static String CHEST_CREATE_ASK_NAME		= getMessagesConfig().getString("locales.create.chest.ask.name").replaceAll("&", "§");
	public static String CHEST_CREATE_ASK_CRON		= getMessagesConfig().getString("locales.create.chest.ask.pattern").replaceAll("&", "§");
	public static String CHEST_CREATE_ASK_DURATION	= getMessagesConfig().getString("locales.create.chest.ask.duration").replaceAll("&", "§");
	public static String CHEST_CREATE_ASK_INFINITE	= getMessagesConfig().getString("locales.create.chest.ask.infinite").replaceAll("&", "§");
	public static String CHEST_CREATE_ASK_SURFACE 	= getMessagesConfig().getString("locales.create.chest.ask.onlyonsurface").replaceAll("&", "§");
	public static String CHEST_CREATE_ASK_PRESERVE	= getMessagesConfig().getString("locales.create.chest.ask.preservecontent").replaceAll("&", "§");
	public static String CHEST_CREATE_ASK_WAITINGEND= getMessagesConfig().getString("locales.create.chest.ask.waitingend").replaceAll("&", "§");
	public static String CHEST_CREATE_ASK_ALLOWEDSID= getMessagesConfig().getString("locales.create.chest.ask.allowedids").replaceAll("&", "§");
	public static String CHEST_CREATE_SUCCESS 		= getMessagesConfig().getString("locales.create.chest.success").replaceAll("&", "§");

	// Chest edit
	public static String CHEST_EDIT_PREFIX 			= getMessagesConfig().getString("locales.edit.chest.prefix").replaceAll("&", "§") + System.getProperty("line.separator");
	public static String CHEST_EDIT_LIST			= getMessagesConfig().getString("locales.edit.chest.ask.listalltreasures").replaceAll("&", "§");
	public static String CHEST_EDIT_ASK_NAME		= getMessagesConfig().getString("locales.edit.chest.ask.name").replaceAll("&", "§");
	public static String CHEST_EDIT_ASK_CRON		= getMessagesConfig().getString("locales.edit.chest.ask.pattern").replaceAll("&", "§");
	public static String CHEST_EDIT_ASK_DURATION	= getMessagesConfig().getString("locales.edit.chest.ask.duration").replaceAll("&", "§");
	public static String CHEST_EDIT_ASK_INFINITE	= getMessagesConfig().getString("locales.edit.chest.ask.infinite").replaceAll("&", "§");
	public static String CHEST_EDIT_ASK_SURFACE 	= getMessagesConfig().getString("locales.edit.chest.ask.onlyonsurface").replaceAll("&", "§");
	public static String CHEST_EDIT_ASK_PRESERVE	= getMessagesConfig().getString("locales.edit.chest.ask.preservecontent").replaceAll("&", "§");
	public static String CHEST_EDIT_ASK_WAITINGEND= getMessagesConfig().getString("locales.edit.chest.ask.waitingend").replaceAll("&", "§");
	public static String CHEST_EDIT_ASK_ALLOWEDSID= getMessagesConfig().getString("locales.edit.chest.ask.allowedids").replaceAll("&", "§");
	public static String CHEST_EDIT_SUCCESS 		= getMessagesConfig().getString("locales.edit.chest.success").replaceAll("&", "§");
	
	// Chest delete
	public static String CHEST_DELETE_PREFIX 		= getMessagesConfig().getString("locales.create.chest.prefix").replaceAll("&", "§") + System.getProperty("line.separator");
	public static String CHEST_DELETE_LIST			= getMessagesConfig().getString("locales.delete.chest.ask.listalltreasures").replaceAll("&", "§");
	
	
	
}
