package org.shortrip.boozaa.plugins.bootreasure.managers.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.shortrip.boozaa.plugins.bootreasure.Log;

public class Events extends Event implements Cancellable {


	private boolean cancelled;
	private static final HandlerList handlers = new HandlerList();
	
	
	public Events(){
		Log.debug("An Event is launched");
	}

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
