package org.shortrip.boozaa.plugins.bootreasure.old;

import org.bukkit.ChatColor;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;



public class Const {

	// Plugin related
	public static final String PLUGIN_NAME = 				"[BooTreasure] ";
	public static final String PLUGIN = 					"BooTreasure";
	public final static Boolean USE_WORLDGUARD = 			BooTreasure.get_pluginConfiguration().getWorldguard();
	
	
	// Texte de commandes
	public static String CMD_YES =							BooTreasure.get_messagesConfiguration().getString("locales.commands.agree");
	public static String CMD_NO =							BooTreasure.get_messagesConfiguration().getString("locales.commands.disagree");
	public static String CMD_EXIT =							BooTreasure.get_messagesConfiguration().getString("locales.commands.exit");
	public static String CMD_END =							BooTreasure.get_messagesConfiguration().getString("locales.commands.end");
	
	
	// Valeurs stockées
	public static final String NAME = 						"NAME";
	public static final String PATTERN = 					"PATTERN";
	public static final String INFINITE = 					"INFINITE";
	public static final String WORLD = 						"WORLD";
	public static final String ONLYONSURFACE = 				"ONLYONSURFACE";
	public static final String PRESERVECONTENT = 			"PRESERVECONTENT";
	public static final String ALLOWEDIDS = 				"ALLOWEDIDS";
	public static final String PLAYER = 					"PLAYER";
	public static final String TREASURE = 					"TREASURE";
	public static final String DURATION = 					"DURATION";
	
	// Prompts
	public static final String CREATE_PREFIX = 				ChatColor.translateAlternateColorCodes('&', BooTreasure.get_messagesConfiguration().getString("locales.create.prefix") );
	
	public static final String CREATE_INTRO = 				"CREATE_INTRO";
	public static String CREATE_INTRO_PHRASE = 				BooTreasure.get_messagesConfiguration().getString("locales.create.chest.intro").replaceAll("&", "§");
	
	public static final String CREATE_CHEST_ASK_NAME = 		"CREATE_CHEST_ASK_NAME";
	public static String CREATE_CHEST_ASK_NAME_PHRASE = 	BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.name").replaceAll("&", "§");

	public static final String CREATE_CHEST_ASK_PATTERN = 	"CREATE_CHEST_ASK_PATTERN";
	public static String CREATE_CHEST_ASK_PATTERN_PHRASE = 	BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.pattern").replaceAll("&", "§");

	public static final String CREATE_CHEST_ASK_WORLD = 	"CREATE_CHEST_ASK_WORLD";
	public static String CREATE_CHEST_ASK_WORLD_PHRASE = 	BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.world").replaceAll("&", "§");

	
	
	public static final String CREATE_CHEST_ASK_INFINITE = 	"CREATE_CHEST_ASK_INFINITE";
	public static String CREATE_CHEST_ASK_INFINITE_PHRASE =	BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.infinite").replaceAll("&", "§") + " (" + CMD_YES + " | " + CMD_NO + ")";

	public static final String CREATE_CHEST_ASK_ONLYONSURFACE = 	"CREATE_CHEST_ASK_ONLYONSURFACE";
	public static String CREATE_CHEST_ASK_ONLYONSURFACE_PHRASE =	BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.onlyonsurface").replaceAll("&", "§") + " (" + CMD_YES + " | " + CMD_NO + ")";

	public static final String CREATE_CHEST_ASK_DURATION = 	"CREATE_CHEST_ASK_ONLYONSURFACE";
	public static String CREATE_CHEST_ASK_DURATION_PHRASE =	BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.duration").replaceAll("&", "§");

	public static final String CREATE_CHEST_ASK_PRESERVECONTENT = 	"CREATE_CHEST_ASK_PRESERVECONTENT";
	public static String CREATE_CHEST_ASK_PRESERVECONTENT_PHRASE =	BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.preservecontent").replaceAll("&", "§") + " (" + CMD_YES + " | " + CMD_NO + ")";


	public static final String CREATE_CHEST_ASK_ALLOWEDIDS = 		"CREATE_CHEST_ASK_ALLOWEDIDS";
	public static String CREATE_CHEST_ASK_ALLOWEDIDS_PHRASE =		BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.allowedids").replaceAll("&", "§") + " (" + CMD_EXIT + " )";

	
	
	public static final String CREATE_CHEST_WAITINGEND = 			"CREATE_CHEST_WAITINGEND";
	public static String CREATE_CHEST_WAITINGEND_PHRASE =			BooTreasure.get_messagesConfiguration().getString("locales.create.chest.ask.waitingend").replaceAll("&", "§") + CMD_EXIT;

	
	
	public static final String CREATE_SUCCESS = 			"CREATE_SUCCESS";
	public static String CREATE_SUCCESS_PHRASE =			BooTreasure.get_messagesConfiguration().getString("locales.create.success").replaceAll("&", "§");

	public static final String CREATE_ERROR = 				"CREATE_ERROR";
	public static String CREATE_ERROR_PHRASE =				BooTreasure.get_messagesConfiguration().getString("locales.create.error").replaceAll("&", "§");
	
	
	
	
	
	
	
	// Configuration de trésors
	public final static String TASKS = "treasures";	
	public final static String TASKS_BASICS = "basics";
	public final static String TASKS_SETUP = "setup";
	// basics
	public final static String TASK_BASICS_NAME = "basics.name";
	public final static String TASK_BASICS_DURATION = "basics.duration";
	public final static String TASK_BASICS_TREASURETYPE = "basics.type";
	public final static String TASK_BASICS_ONLYONSURFACE = "basics.onlyonsurface";
	public final static String TASK_BASICS_PRESERVECONTENT = "basics.preservecontent";
	public final static String TASK_BASICS_CRONPATTERN = "basics.cronpattern";
	public final static String TASK_BASICS_WORLD = "basics.world";
	public final static String TASK_BASICS_WITHEFFECT = "basics.witheffect";
	
	// limits
	public final static String TASK_LIMITS_FIXEDREGION = "setup.limits.fixedregion";
	public final static String TASK_LIMITS_WORLDGUARD = "setup.limits.worldguard";
	public final static String TASK_LIMITS_WORLDGUARD_FORCEONREGIONS = "setup.limits.worldguard.forceonregions";
	public final static String TASK_LIMITS_WORLDGUARD_ALLOWEDREGIONS = "setup.limits.worldguard.allowedregions";
	public final static String TASK_LIMITS_WORLDGUARD_DENIEDREGIONS = "setup.limits.worldguard.deniedregions";
	
	public final static String TASK_LIMITS_PRECIOUSSTONES = "setup.limits.preciousstones";
	public final static String TASK_LIMITS_PRECIOUSSTONES_FORCEONFIELDS = "setup.limits.preciousstones.forceonfields";
	public final static String TASK_LIMITS_PRECIOUSSTONES_ALLOWEDFIELDS = "setup.limits.preciousstones.allowedfields";
	public final static String TASK_LIMITS_PRECIOUSSTONES_DENIEDFIELDS = "setup.limits.preciousstones.deniedfields";
	
	// permissions
	public final static String TASK_PERMISSIONS_GROUPS = "setup.permissions.groups";
	
	// places
	public final static String TASK_PLACES_ITEMS = "setup.places.ids";
	
	// contents
	public final static String TASK_CONTENTS_ITEMS = "setup.contents.items";
	public final static String TASK_CONTENTS_RANDOMS = "setup.contents.randoms";
	
	// messages
	public final static String TASK_MESSAGES_SPAWN = "setup.messages.spawn";
	public final static String TASK_MESSAGES_FOUND = "setup.messages.found";
	public final static String TASK_MESSAGES_FOUNDBUTNOTEMPTY = "setup.messages.foundbutnotempty";
	public final static String TASK_MESSAGES_DISAPPEAR = "setup.messages.disappear";
	
	
	// Permissions
	public final static String PLAYER_CAN_ALL 				= "bootreasure.players.all";
	public final static String PLAYER_CAN_FIND 				= "bootreasure.players.can.find";
	public final static String PLAYER_CAN_KNOW 				= "bootreasure.players.can.know";
	public final static String PLAYER_CAN_INFORM_SUCCESS 	= "bootreasure.players.can.informsuccess";
	
	public final static String PLAYER_REWARD_ALL 			= "bootreasure.players.reward.all";
	public final static String PLAYER_REWARD_XP 			= "bootreasure.players.reward.xp";
	public final static String PLAYER_REWARD_CONSOLECOMMAND = "bootreasure.players.reward.consolecommand";
	
	public final static String ADMIN_ALL 					= "bootreasure.admins.all";
	public final static String ADMIN_CREATE 				= "bootreasure.admins.create";
	public final static String ADMIN_DELETE 				= "bootreasure.admins.delete";
	public final static String ADMIN_MODIFY 				= "bootreasure.admins.modify";
	public final static String ADMIN_KNOW_WHERE 			= "bootreasure.admins.know.where";
	public final static String ADMIN_KNOW_WHAT 				= "bootreasure.admins.know.what";
	
}
