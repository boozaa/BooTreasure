package org.shortrip.boozaa.plugins.bootreasure.treasures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.shortrip.boozaa.plugins.bootreasure.utils.ChatMessage;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public abstract class Treasure implements Serializable {

	private transient static final long serialVersionUID = 1L;

	private transient final String NAME 					= "basics.name";
	private transient final String CRON_PATTERN 			= "basics.cronpattern";
	private transient final String DURATION 				= "basics.duration";
	private transient final String WORLD 					= "basics.world";
	private transient final String ONLY_ON_SURFACE 			= "basics.onlyonsurface";
	private transient final String INFINITE 				= "basics.infinite";
	private transient final String MESSAGES_APPEAR 			= "setup.messages.appear";
	private transient final String MESSAGES_FOUND 			= "setup.messages.found";
	private transient final String MESSAGES_DISAPPEAR 		= "setup.messages.disappear";
	private transient final String DEFAULT_APPEAR 			= ChatColor.AQUA + "%name% appear in %world% for %duration% seconds";
	private transient final String DEFAULT_FOUND 			= ChatColor.AQUA + "%name% has been discovered in %world% by %player%";
	private transient final String DEFAULT_DISAPPEAR 		= ChatColor.AQUA + "%name% disappear from %world%";
	
	@Getter @Setter protected transient String _appearMessage, _disappearMessage, _foundMessage;
	
	
	@Getter protected transient Player _player;
	@Getter protected transient TreasureType _type;
	@Getter @Setter protected transient ConfigurationSection _conf = null;
	@Getter @Setter protected Boolean _infinite=true, _onlyonsurface=true, _found=false;	
	@Getter @Setter protected transient String _id, _pattern, _taskId;
	@Getter @Setter protected transient Long _duration;
	
	// In serialized representation
	@Getter @Setter protected String _name, _world, _path;
	
	
	public Treasure(TreasureType type){
		this._type = type;
		this._id =	UUID.randomUUID().toString();
		if( type.equals(TreasureType.CHEST ) )
			this._path = "plugins/BooTreasure/lost+found" + this._id + ".chest";
		if( type.equals(TreasureType.SCHEMATIC ) )
			this._path = "plugins/BooTreasure/lost+found" + this._id + ".schematic";
	}
	
	public Treasure( TreasureType type, ConfigurationSection section ){
		this._type = type;
		this._id =	UUID.randomUUID().toString();
		this._conf	= section;
		if( type.equals(TreasureType.CHEST ) )
			this._path = Bukkit.getPluginManager().getPlugin("BooTreasure").getDataFolder() + File.separator + "lost+found" + File.separator + this._id + ".chest";
		if( type.equals(TreasureType.SCHEMATIC ) )
			this._path = Bukkit.getPluginManager().getPlugin("BooTreasure").getDataFolder() + File.separator + "lost+found" + File.separator + this._id + ".schematic";
		this._name 				= this._conf.getString( NAME );
		this._pattern 			= this._conf.getString( CRON_PATTERN );		
		this._duration 			= this._conf.getLong( DURATION );
		this._world 			= this._conf.getString( WORLD );
		this._onlyonsurface 	= this._conf.getBoolean( ONLY_ON_SURFACE );
		this._infinite 			= this._conf.getBoolean( INFINITE );
		
		// Messages
		if( this._conf.contains(MESSAGES_APPEAR) ) {
			this._appearMessage		= this._conf.getString(MESSAGES_APPEAR);
		}else{
			this._appearMessage		= this._conf.getString(DEFAULT_APPEAR);
		}
		
		if( this._conf.contains(MESSAGES_DISAPPEAR) ) {
			this._disappearMessage		= this._conf.getString(MESSAGES_DISAPPEAR);
		}else{
			this._disappearMessage		= this._conf.getString(DEFAULT_DISAPPEAR);
		}
		
		if( this._conf.contains(MESSAGES_FOUND) ) {
			this._foundMessage			= this._conf.getString(MESSAGES_FOUND);
		}else{
			this._foundMessage			= this._conf.getString(DEFAULT_FOUND);
		}
		
		
	}
	
	public Treasure( TreasureType type, Location loc ){
		this._type = type;this._id	= UUID.randomUUID().toString();
		this._onlyonsurface 	= false;
		this._infinite 			= false;
		this._found 			= false;
		if( type.equals(TreasureType.CHEST ) )
			this._path = "plugins/BooTreasure/lost+found" + this._id + ".chest";
		if( type.equals(TreasureType.SCHEMATIC ) )
			this._path = "plugins/BooTreasure/lost+found" + this._id + ".schematic";			
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

	
	public void serialize() {		
		
		FileOutputStream baos = null;
		
		try {
			
			// ItemStack
			baos = new FileOutputStream(this._path);			
			ObjectOutputStream boos = new ObjectOutputStream(baos);
			boos.writeObject(this);
			boos.close();
			
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	
	public Treasure unserialize() {		
		
		Treasure item = null;
		File file = new File( _path );
		if( file.exists() ){		    				
			
			FileInputStream fis = null;
			
			try {
				
				fis = new FileInputStream(file); 
				Log.debug("Unserialization -> Total file size to read (in bytes) : " + fis.available()); 						
				ObjectInputStream bois = new ObjectInputStream(fis);
				item = (Treasure) bois.readObject();
				bois.close();
					
	 
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}				    				
		}
		
		return item;	
		
	}

	public Treasure unserialize(File f) {
		
		Treasure item = null;
		if( f.exists() ){		    				
			
			if( f.exists() ){	
				
				FileInputStream fis = null;
				
				try {
					
					fis = new FileInputStream(f); 						
					ObjectInputStream bois = new ObjectInputStream(fis);
					item = (Treasure) bois.readObject();
					bois.close();
						
		 
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (fis != null)
							fis.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}				    				
			}
					    				
		}

		return item;		
		
	}
	
	public void deleteSerializedFile() {
		File file = new File( this._path );
		if( file.exists() ){		    				
			file.delete();				    				
		}
	}
	
	
	public void announceAppear() {
		if( this._conf != null){
			if( this._conf.contains( MESSAGES_APPEAR ) ){
				ChatMessage.broadcast( replaceVariables( this._conf.getString( MESSAGES_APPEAR ) ) );
			}
		}else{
			ChatMessage.broadcast( replaceVariables( DEFAULT_APPEAR ) );
		}	
			
	}

	public void announceFound() {
		if( this._conf != null){
			if( this._conf.contains( MESSAGES_FOUND ) ){
				ChatMessage.broadcast( replaceVariables( this._conf.getString( MESSAGES_FOUND ) ) );
			}
		}else{
			ChatMessage.broadcast( replaceVariables( DEFAULT_FOUND ) );
		}	
	}

	public void announceDisAppear() {
		if( this._conf != null){
			if( this._conf.contains( MESSAGES_DISAPPEAR ) ){
				ChatMessage.broadcast( replaceVariables( this._conf.getString( MESSAGES_DISAPPEAR ) ) );
			}
		}else{
			ChatMessage.broadcast( replaceVariables( DEFAULT_DISAPPEAR ) );
		}	
	}
	

	// Commons functions
	protected abstract void generateContents();
	protected abstract String replaceVariables( String msg );
	public abstract void appearSilently() throws Exception;
	public abstract void appear() throws Exception;
	public abstract void disAppearSilently() throws Exception;
	public abstract void disappear() throws Exception;
	public abstract void found(Player p);
	
	
}
