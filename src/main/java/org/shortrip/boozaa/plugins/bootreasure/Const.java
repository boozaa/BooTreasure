package org.shortrip.boozaa.plugins.bootreasure;

import java.io.File;

public class Const {

	
	public static final String PERMISSIONS_PLAYER_CAN_ALL 			= "bootreasure.all";
	public static final String PERMISSIONS_PLAYER_CAN_FIND 			= "bootreasure.treasure.find";
	public static final String PERMISSIONS_PLAYER_CAN_BE_INFORMED 	= "bootreasure.treasure.informed";
	
	public static final String PERMISSIONS_ADMIN_CAN_ALL 			= "bootreasure.admin.all";
	public static final String PERMISSIONS_ADMIN_CAN_CREATE 		= "bootreasure.admin.create";
	public static final String PERMISSIONS_ADMIN_CAN_EDIT 			= "bootreasure.admin.edit";
	public static final String PERMISSIONS_ADMIN_CAN_DELETE 		= "bootreasure.admin.delete";
	
	
	public static final String LOST_FOLDER_PATH						= BooTreasure.getInstance().getDataFolder() + File.separator + "lost+found" + File.separator;
	
}
