package org.shortrip.boozaa.plugins.bootreasure.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.shortrip.boozaa.plugins.bootreasure.old.Log;


public abstract class Events extends Event implements Cancellable {

	private boolean cancelled;
	private static final HandlerList handlers = new HandlerList();
	
	
	public Events(){
		Log.debug("An Event is launched");
	}
	
	
	/*
	public void found(Object obj, Player player){
		
		if( obj instanceof Treasure ){
			final Treasure treasure = (Treasure) obj;
			if( treasure.canBeDiscoveredByPlayer(player)){
				Log.debug("Player " + player.getName() + " can found treasures");
				treasure.found(player);		   				
			}else{
				Log.debug("Player " + player.getName() + " can't found treasures");
				setCancelled(true);
			}
		}
		
	}
	*/
	/*
	public void appear(Object obj){
		
		if( obj instanceof Treasure ){
			final Treasure treasure = (Treasure) obj;
			treasure.appear();
			Log.debug("Appear Event for " + treasure.toString());
		}
		
	}
	
	public void disappear(Object obj){
		
		if( obj instanceof Treasure ){
			final Treasure treasure = (Treasure) obj;
			treasure.disappear();
			Log.debug("Disappear Event for " + treasure.toString());
		}
		
	}
	*/
	
	

	@Override
	public boolean isCancelled() {
		 return cancelled;
	}

	@Override
	public void setCancelled(boolean bln) {
		this.cancelled = bln;
	}
    
	@Override
    public HandlerList getHandlers() {
        return handlers;
    }
    	
    public static HandlerList getHandlerList() {
        return handlers;
    }
	
}
