package org.shortrip.boozaa.plugins.bootreasure.managers;

import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.cache.Cache;

import lombok.Getter;


public class MyCache extends Manager {

	@SuppressWarnings("unused")
	private Plugin plugin;
	// Cache treasures
	@Getter private Cache _treasureCache;

	public MyCache(BooTreasure booTreasure) {
		this.plugin = booTreasure;
		this._treasureCache = new Cache();
	}
	
	public Boolean exists( String name ){
		return this._treasureCache.exists(name);
	}
	
	public void add( String name, Object obj ) throws CacheExistException{
		if( !exists(name) ){
			this._treasureCache.add(name, obj);
		}else{
			throw new CacheExistException("The object called " + name + " already exists in cache");
		}
			
	}

	public void remove( String name ) throws CacheNotExistException{
		if( exists(name) ){
			this._treasureCache.remove(name);
		}else{
			throw new CacheNotExistException("The object called " + name + " already exists in cache");
		}
			
	}
	
	
	
	
	
	
	public class CacheExistException extends Exception {
		private static final long serialVersionUID = 1L;		
		public CacheExistException(String message) {
	        super(message);
	    }		
	}
	
	public class CacheNotExistException extends Exception {
		private static final long serialVersionUID = 1L;		
		public CacheNotExistException(String message) {
	        super(message);
	    }		
	}

	@Override
	public void onDisable() {
		_treasureCache.erase();
	}
	
}
