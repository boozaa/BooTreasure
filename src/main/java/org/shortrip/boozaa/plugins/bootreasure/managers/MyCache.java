package org.shortrip.boozaa.plugins.bootreasure.managers;

import java.util.HashMap;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.managers.cache.Cache;
import org.shortrip.boozaa.plugins.bootreasure.treasures.Treasure;

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
	
	public void add( String id, Object obj ) throws CacheExistException{
		if( !exists(id) ){
			this._treasureCache.add(id, obj);
		}else{
			throw new CacheExistException("The treasure " + id + " already exists in cache");
		}
			
	}
	
	public Treasure get( String id ){
		return (Treasure)this._treasureCache.getObject(id);
	}

	
	public HashMap<String, Object> getTreasures(){
		return this._treasureCache.getCache();
	}
	
	
	
	
	public void remove( String id ) throws CacheNotExistException{
		if( exists(id) ){
			this._treasureCache.remove(id);
		}else{
			throw new CacheNotExistException("The treasure " + id + " already exists in cache");
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
