package org.shortrip.boozaa.plugins.bootreasure.treasures;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.utils.ChatMessage;


public abstract class Treasure implements Serializable {

	private transient static final long serialVersionUID = 1L;
	
	private transient final String NAME 					= "basics.name";
	private transient final String CRON_PATTERN 			= "basics.cronpattern";
	private transient final String DURATION 				= "basics.duration";
	private transient final String WORLD 					= "basics.world";
	private transient final String ONLY_ON_SURFACE 			= "basics.onlyonsurface";
	private transient final String INFINITE 				= "basics.infinite";
	private transient final String MESSAGES_SPAWN 			= "setup.messages.spawn";
	private transient final String MESSAGES_FOUND 			= "setup.messages.found";
	private transient final String MESSAGES_DISAPPEAR 		= "setup.messages.disappear";
	private transient final String DEFAULT_SPAWN 			= ChatColor.AQUA + "Un trésor vient d'apparaitre";
	private transient final String DEFAULT_FOUND 			= ChatColor.AQUA + "Un trésor vient d'être découvert";
	private transient final String DEFAULT_DISAPPEAR 		= ChatColor.AQUA + "Un trésor vient de disparaître";
	@Getter protected transient TreasureType _type;
	@Getter @Setter protected transient ConfigurationSection _conf = null;
	@Getter protected Boolean _infinite=true, _onlyonsurface=true, _found=true;	
	@Getter @Setter protected transient String _id, _pattern, _taskId;
	@Getter @Setter protected transient Long _duration;
	
	// In serialized representation
	@Getter @Setter protected String _name, _world, _path;
	
	
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
		this._id =	UUID.randomUUID().toString();
		this._conf	= section;
		if( type.equals(TreasureType.CHEST ) )
			this._path = BooTreasure.get_instance().getDataFolder() + File.separator + "lost+found" + File.separator + this._id + ".chest";
		if( type.equals(TreasureType.SCHEMATIC ) )
			this._path = BooTreasure.get_instance().getDataFolder() + File.separator + "lost+found" + File.separator + this._id + ".schematic";
		this._name 				= this._conf.getString( NAME );
		this._pattern 			= this._conf.getString( CRON_PATTERN );		
		this._duration 			= this._conf.getLong( DURATION );
		this._world 			= this._conf.getString( WORLD );
		this._onlyonsurface 	= this._conf.getBoolean( ONLY_ON_SURFACE );
		this._infinite 			= this._conf.getBoolean( INFINITE );		
	}
	
	public Treasure( TreasureType type, Location loc ){
		this._type = type;this._id	= UUID.randomUUID().toString();
		this._onlyonsurface 	= false;
		this._infinite 			= false;
		this._found 			= false;
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

	

	public void set_infinite(Boolean _infinite) {
		this._infinite = _infinite;
	}

	public void set_onlyonsurface(Boolean _onlyonsurface) {
		this._onlyonsurface = _onlyonsurface;
	}

	public void set_found(Boolean _found) {
		this._found = _found;
	}
	
	public void serialize() {		
		BooTreasure.get_serializationManager().serializeObjectToFile(this, _path);		
	}
	
	
	public Treasure unserialize() {		
		File file = new File( _path );
		if( file.exists() ){		    				
			return (Treasure) BooTreasure.get_serializationManager().unserializeObjectFromFile(file);				    				
		}
		return null;		
	}

	public Treasure unserialize(File f) {
		if( f.exists() ){		    				
			return (Treasure) BooTreasure.get_serializationManager().unserializeObjectFromFile(f);				    				
		}
		return null;
		
	}
	
	public void deleteSerializedFile() {
		File file = new File( this._path );
		if( file.exists() ){		    				
			file.delete();				    				
		}
	}
	
	
	public void announceAppear() {
		if( this._conf != null){
			if( this._conf.contains( MESSAGES_SPAWN ) ){
				ChatMessage.broadcast( replaceVariables( this._conf.getString( MESSAGES_SPAWN ) ) );
			}
		}else{
			ChatMessage.broadcast( DEFAULT_SPAWN );
		}	
			
	}

	public void announceFound() {
		if( this._conf != null){
			if( this._conf.contains( MESSAGES_FOUND ) ){
				ChatMessage.broadcast( replaceVariables( this._conf.getString( MESSAGES_FOUND ) ) );
			}
		}else{
			ChatMessage.broadcast( DEFAULT_FOUND );
		}	
	}

	public void announceDisAppear() {
		if( this._conf != null){
			if( this._conf.contains( MESSAGES_DISAPPEAR ) ){
				ChatMessage.broadcast( replaceVariables( this._conf.getString( MESSAGES_DISAPPEAR ) ) );
			}
		}else{
			ChatMessage.broadcast( DEFAULT_DISAPPEAR );
		}	
	}
	

	// Commons functions
	protected abstract void generateContents();
	protected abstract String replaceVariables( String msg );
	public abstract void appear() throws Exception;
	public abstract void disappear() throws Exception;
	public abstract void found(Player p);
	
	
}
