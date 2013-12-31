/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.decorators.treasure;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;



/**
 * @author boozaa
 *
 * BooTreasure
 */
@Data
public abstract class Treasure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8838542551125866472L;
	@Getter @Setter protected transient ConfigurationSection _conf;
	@Getter @Setter protected String _name="", _id="", _pattern="", _taskId="", _world="";
	@Getter @Setter @NonNull protected transient Boolean _infinite = false, _onlyonsurface=false, _found=false, _preservecontent = true;
	@Getter @Setter protected TreasureType _treasureType;
	@Getter @Setter protected List<Material> _allowedids;
	@Getter @Setter protected Long duration;
	
	
	public Treasure(){
		this._conf = 				null;
		this._id = 					UUID.randomUUID().toString();
	}
		
	
	protected Treasure( ConfigurationSection conf ){
		this._conf = 				conf;
		this._name = 				this._conf.getString("basics.name");
		this._id = 					UUID.randomUUID().toString();
		this._pattern = 			this._conf.getString("basics.cronpattern");
		this._world = 				this._conf.getString("basics.world");
		this._onlyonsurface = 		this._conf.getBoolean("basics.onlyonsurface");
		this._preservecontent = 	this._conf.getBoolean("basics.preservecontent");
		this.duration = 			this._conf.getLong("basics.duration");
		this._allowedids = 			new ArrayList<Material>();
	}
	
	
		
	
	/**
	 * Is the ConfigurationSection contains all non optional informations
	 * @return true
	 * 		if ConfigurationSection is valid
	 */
	public Boolean isConfValid() {
		int howManyBooleansAreTrue =
		      ( this._conf.contains("config.name") 			? 1 : 0)
		    + ( this._conf.contains("config.cronpattern") 	? 1 : 0)
		    + ( this._conf.contains("config.world") 		? 1 : 0);
		return howManyBooleansAreTrue == 3;
	}
	

	/**
	 * Appear method
	 * @throws Exception 
	 */
	public abstract void appear() throws Exception;
	/**
	 * Disppear method
	 */
	public abstract void disappear();
	/**
	 * Found method
	 */
	public abstract void found(Player p);
	/**
	 * Serialize method
	 */
	protected abstract void serialize();
	/**
	 * Deserialize method
	 */
	protected abstract Object deserialize();
	/**
	 * DeleteSerializedFile method
	 */
	public abstract void deleteSerializedFile();
	
	
	
	/**
	 * Boolean if this treasure can be discovered b players
	 * @param player
	 * 		The player whom check the permission
	 */
	public abstract Boolean canBeDiscoveredByPlayer( Player player );
	
	
	
	/**
	 * Creation of the appear message announce
	 */
	public abstract void announceAppear();
	/**
	 * Creation of the disappear message announce
	 */
	public abstract void announceDisappear();
	/**
	 * Creation of the found message announce
	 */
	public abstract void announceFound();
	/**
	 * Creation of the found but not empty message announce
	 */
	public abstract void announceFoundButNotEmpty();
		
	
	
	
}
