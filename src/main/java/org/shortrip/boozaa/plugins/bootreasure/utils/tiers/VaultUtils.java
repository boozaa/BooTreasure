package org.shortrip.boozaa.plugins.bootreasure.utils.tiers;

import java.util.logging.Level;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.Log;

public class VaultUtils {

	
	private Plugin _plugin;
	private static Permission _perms;
	
	
	public VaultUtils( Plugin plugin ){
		_plugin = plugin;
		if (_plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
    		BooTreasure.useVault = false;
			Log.warning("Vault seems not here, you can't use permission rewards");
    		return;
        }
    	RegisteredServiceProvider<Permission> permissionProvider = _plugin.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
        	_perms = permissionProvider.getProvider();
        	Log.info("Permissions providing by Vault");
        	BooTreasure.useVault = true;
        }else{
        	Log.warning("Can't hooked Permissions with Vault");
        	BooTreasure.useVault = false;      	
        }
	}
	
	
	private Boolean playerCanAll( Player player ){
		if( BooTreasure.useVault ){
			return _perms.playerHas(player, Const.PLAYER_CAN_ALL);
		}
		return true;
	}
	
	public Boolean playerCanFind( Player player ){
		if( BooTreasure.useVault ){
			if( playerCanAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.PLAYER_CAN_FIND);
			}
		}
		return true;
			
	}
	public Boolean playerCanFind( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerCanAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.PLAYER_CAN_FIND);
			}
		}
		return true;				
	}
	
	public Boolean playerCanBeInformed( Player player ){
		if( BooTreasure.useVault ){
			if( playerCanAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.PLAYER_CAN_KNOW);
			}	
		}
		return true;
			
	}
	public Boolean playerCanBeInformed( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerCanAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.PLAYER_CAN_KNOW);
			}	
		}
		return true;
			
	}
	
	
	
	private Boolean playerRewardAll( Player player ){
		if( BooTreasure.useVault ){
			return _perms.playerHas(player, Const.PLAYER_REWARD_ALL);
		}
		return true;		
	}
	
	public Boolean playerCanRewardXP( Player player ){
		if( BooTreasure.useVault ){
			if( playerRewardAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.PLAYER_REWARD_XP);
			}
		}
		return true;				
	}
	
	public Boolean playerCanRewardXP( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerRewardAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.PLAYER_REWARD_XP);
			}	
		}
		return true;			
	}
	
	public Boolean playerCanRewardCommand( Player player ){
		if( BooTreasure.useVault ){
			if( playerRewardAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.PLAYER_REWARD_CONSOLECOMMAND);
			}
		}
		return true;				
	}
	
	public Boolean playerCanRewardCommand( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerRewardAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.PLAYER_REWARD_CONSOLECOMMAND);
			}
		}
		return true;				
	}
	
	
	
	private Boolean playerAdminAll( Player player ){
		if( BooTreasure.useVault ){
			return _perms.playerHas(player, Const.ADMIN_ALL);
		}
		return true;		
	}
	
	public Boolean playerAdminCreate( Player player ){
		if( BooTreasure.useVault ){
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_CREATE);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminCreate( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_CREATE);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminModify( Player player ){
		if( BooTreasure.useVault ){
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_MODIFY);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminModify( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_MODIFY);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminDelete( Player player ){
		if( BooTreasure.useVault ){
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_DELETE);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminDelete( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_DELETE);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminKnowWhere( Player player ){
		if( BooTreasure.useVault ){
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_KNOW_WHERE);
			}
		}
		return true;				
	}
	
	public Boolean playerAdminKnowWhere( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_KNOW_WHERE);
			}	
		}
		return true;			
	}
	
	public Boolean playerAdminKnowWhat( Player player ){
		if( BooTreasure.useVault ){
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_KNOW_WHAT);
			}	
		}
		return true;			
	}
	
	public Boolean playerAdminKnowWhat( String playerName ){
		if( BooTreasure.useVault ){
			Player player = Bukkit.getPlayer(playerName);
			if( playerAdminAll(player) ){
				return true;
			}else{
				return _perms.playerHas(player, Const.ADMIN_KNOW_WHAT);
			}
		}
		return true;				
	}
	
	
	public Boolean playerInGroup( String groupName, Player player ){
		if( BooTreasure.useVault ){
			return _perms.playerInGroup(player, groupName);
		}
		return true;		
	}
	
	
}
