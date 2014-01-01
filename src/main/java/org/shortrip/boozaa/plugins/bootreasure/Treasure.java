package org.shortrip.boozaa.plugins.bootreasure;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;


public abstract class Treasure implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String NAME 				= "basics.name";
	private static final String CRON_PATTERN 		= "basics.cronpattern";
	private static final String DURATION 			= "basics.duration";
	private static final String WORLD 				= "basics.world";
	private static final String ONLY_ON_SURFACE 	= "basics.onlyonsurface";
	private static final String INFINITE 			= "basics.infinite";
	private static final String MESSAGES_SPAWN 		= "setup.messages.spawn";
	private static final String MESSAGES_FOUND 		= "setup.messages.found";
	private static final String MESSAGES_DISAPPEAR 	= "setup.messages.disappear";
	
	
	@Getter protected transient TreasureType _type;
	@Getter @Setter protected transient ConfigurationSection _conf = null;
	@Getter protected transient String _path;
	@Getter @Setter protected Boolean _infinite = false, _onlyonsurface=false, _found=false;
	@Getter @Setter protected String _name="", _id="", _pattern="", _taskId="", _world="";
	@Getter @Setter protected Long _duration;
	@Getter protected Map<String, String> _messagesMap;
	
	
	public Treasure(TreasureType type){
		this._type = type;
		this._id =	UUID.randomUUID().toString();
		if( type.equals(TreasureType.CHEST ) )
			this._path = BooTreasure.get_treasuresManager().lost_folder_path + this._id + ".chest";
		if( type.equals(TreasureType.SCHEMATIC ) )
			this._path = BooTreasure.get_treasuresManager().lost_folder_path + this._id + ".schematic";
	}
	
	public Treasure( TreasureType type, ConfigurationSection section ){
		this._type = type;
		this._conf	= section;
		if( type.equals(TreasureType.CHEST ) )
			this._path = BooTreasure.get_treasuresManager().lost_folder_path + this._id + ".chest";
		if( type.equals(TreasureType.SCHEMATIC ) )
			this._path = BooTreasure.get_treasuresManager().lost_folder_path + this._id + ".schematic";
		this._id 				= UUID.randomUUID().toString();
		this._name 				= this._conf.getString( NAME );
		this._pattern 			= this._conf.getString( CRON_PATTERN );		
		this._duration 			= this._conf.getLong( DURATION );
		this._world 			= this._conf.getString( WORLD );
		this._onlyonsurface 	= this._conf.getBoolean( ONLY_ON_SURFACE );
		this._infinite 			= this._conf.getBoolean( INFINITE );		
	}
	
	public Treasure( TreasureType type, Location loc ){
		this._type = type;this._id	= UUID.randomUUID().toString();
		if( type.equals(TreasureType.CHEST ) )
			this._path = BooTreasure.get_treasuresManager().lost_folder_path + this._id + ".chest";
		if( type.equals(TreasureType.SCHEMATIC ) )
			this._path = BooTreasure.get_treasuresManager().lost_folder_path + this._id + ".schematic";			
	}
	
	public Treasure( TreasureType type, File file ){
		this._type = type;
		Treasure cht = unserialize(file);
		
		if( cht != null ){
			this._id 				= cht.get_id();
			this._path 				= cht.get_path();
			this._name 				= cht.get_name();
			this._pattern 			= cht.get_pattern();		
			this._duration 			= cht.get_duration();
			this._world 			= cht.get_world();
			this._onlyonsurface 	= cht.get_onlyonsurface();
			this._infinite 			= cht.get_infinite();
		}
	}
	
	
	
	protected void populateMessages(){
		
		/*
		 * Store the messages
		 * If the _messagesMap already contains sentences -> do nothing
		 * If not -> take sentences from _conf if it isn't null
		 * If null -> store default messages
		 */
		
		if( this._messagesMap == null )
			this._messagesMap = new HashMap<String, String>();
		
		if( this._conf != null ){
			
			if( this._conf.get( MESSAGES_SPAWN ) != null && !this._messagesMap.containsKey(MESSAGES_SPAWN) ){
				this._messagesMap.put(MESSAGES_SPAWN, replaceVariables( this._conf.getString( MESSAGES_SPAWN ) ) );
			}else{
				this._messagesMap.put(MESSAGES_SPAWN, "A new treasure spawned" );
			}


			if( this._conf.get( MESSAGES_FOUND ) != null && !this._messagesMap.containsKey(MESSAGES_FOUND) ){
				this._messagesMap.put(MESSAGES_FOUND, replaceVariables( this._conf.getString( MESSAGES_FOUND ) ) );
			}else{
				this._messagesMap.put(MESSAGES_FOUND, "A treasure has been founded" );
			}


			if( this._conf.get( MESSAGES_DISAPPEAR ) != null && !this._messagesMap.containsKey(MESSAGES_DISAPPEAR) ){
				this._messagesMap.put(MESSAGES_DISAPPEAR, replaceVariables( this._conf.getString( MESSAGES_DISAPPEAR ) ) );
			}else{
				this._messagesMap.put(MESSAGES_DISAPPEAR, "A treasure disappear" );
			}
			
		}
		
	}

	
	protected void serialize() {		
		BooTreasure.get_serializationManager().serializeBukkitObjectToFile(this, _path);		
	}
	
	
	protected Treasure unserialize() {		
		File file = new File( _path );
		if( file.exists() ){		    				
			return (Treasure) BooTreasure.get_serializationManager().unserializeBukkitObjectFromFile(file);				    				
		}
		return null;		
	}

	protected Treasure unserialize(File f) {
		
		if( f.exists() ){		    				
			return (Treasure) BooTreasure.get_serializationManager().unserializeBukkitObjectFromFile(f);				    				
		}
		return null;
		
	}
	
	protected void deleteSerializedFile() {
		File file = new File( this._path );
		if( file.exists() ){		    				
			file.delete();				    				
		}
	}
	
	
	public void announceAppear() {
		ChatMessage.broadcast(this._messagesMap.get(MESSAGES_SPAWN));
	}

	public void announceFound() {
		ChatMessage.broadcast(this._messagesMap.get(MESSAGES_FOUND));
	}

	public void announceDisAppear() {
		ChatMessage.broadcast(this._messagesMap.get(MESSAGES_DISAPPEAR));
	}
	

	// Commons functions
	protected abstract void generateContents();
	protected abstract String replaceVariables( String msg );
	public abstract void appear();
	public abstract void disappear();
	
	
}
