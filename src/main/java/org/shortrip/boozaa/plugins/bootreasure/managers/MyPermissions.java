package org.shortrip.boozaa.plugins.bootreasure.managers;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import lombok.Getter;
import lombok.Setter;


public class MyPermissions extends Manager {

	
	private Plugin plugin;
	private Permission perms;
	@Getter @Setter private static Boolean useVault = false;

	
	public MyPermissions(BooTreasure booTreasure) throws PermissionsVaultNullException {
		this.plugin = booTreasure;
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			throw new PermissionsVaultNullException("Vault seems not here, you can't use permission");
        }
    	RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
        	perms = permissionProvider.getProvider();
        	Log.info("Permissions providing by Vault");
        	useVault = true;
        }else{
        	throw new PermissionsVaultNullException("Can't hooked Permissions with Vault");
        }
	}
	

	private Boolean playerCanAll( Player player ){
		if( useVault ){
			return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_ALL);
		}
		return true;
	}
	
	public Boolean playerCanFind( Player player ){
		if( useVault ){
			if( playerCanAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_FIND);
			}
		}
		return true;
			
	}
	public Boolean playerCanFind( String playerName ){
		if( useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerCanAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_FIND);
			}
		}
		return true;				
	}
	
	public Boolean playerCanBeInformed( Player player ){
		if( useVault ){
			if( playerCanAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_BE_INFORMED);
			}	
		}
		return true;
			
	}
	public Boolean playerCanBeInformed( String playerName ){
		if( useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerCanAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_PLAYER_CAN_BE_INFORMED);
			}	
		}
		return true;
			
	}

	
	private Boolean playerAdminAll( Player player ){
		if( useVault ){
			return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_ALL);
		}
		return true;		
	}
	
	public Boolean playerAdminCreate( Player player ){
		if( useVault ){
			if( playerAdminAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_CREATE);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminCreate( String playerName ){
		if( useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerAdminAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_CREATE);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminModify( Player player ){
		if( useVault ){
			if( playerAdminAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_EDIT);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminModify( String playerName ){
		if( useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerAdminAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_EDIT);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminDelete( Player player ){
		if( useVault ){
			if( playerAdminAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_DELETE);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminDelete( String playerName ){
		if( useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerAdminAll(player) ){
				return true;
			}else{
				return perms.playerHas(player, Const.PERMISSIONS_ADMIN_CAN_DELETE);
			}
		}
		return true;				
	}
	
	
	
	public Boolean playerInGroup( String groupName, Player player ){
		if( useVault ){
			return perms.playerInGroup(player, groupName);
		}
		return true;		
	}
	
	

	public class PermissionsVaultNullException extends Exception {
		private static final long serialVersionUID = 1L;		
		public PermissionsVaultNullException(String message) {
	        super(message);
	    }		
	}



	@Override
	public void onDisable() {
		perms = null;
	}
	
	
}
