package org.shortrip.boozaa.plugins.bootreasure.events.chest;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.events.Events;

public final class TreasureChestCloseEvent extends Events {


	private Plugin plugin;
	
	public TreasureChestCloseEvent(Plugin plugin, final HumanEntity humanEntity, final Treasure t){

		super();
		this.plugin = plugin;

			// On vérifie si le joueur qui l'a trouvé est dans groupes autorisés
			if( t.canBeDiscoveredByPlayer((Player)humanEntity)){
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
		            @Override 
		            public void run() {
		            	
		            	Log.debug("Treasure Chest check content: " + t.get_name());
		            	// On vérifie si le joueur qui l'a trouvé est dans groupes autorisés
		    			if( t.canBeDiscoveredByPlayer((Player)humanEntity)){
		    					
		    					if( t instanceof ChestTreasure ){
		    						ChestTreasure chest = (ChestTreasure) t;
		    						if( chest.get_inventory().length > 0 ){
		    							t.announceFoundButNotEmpty();
		    						}
		    					}		
		    					
		    			}  
		            	  	    			 
		            }
		        }, 120L);			
				
			} 
	}
		
		
	
	
}
