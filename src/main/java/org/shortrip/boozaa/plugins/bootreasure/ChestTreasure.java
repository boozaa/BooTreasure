package org.shortrip.boozaa.plugins.bootreasure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;


@Data
@EqualsAndHashCode(callSuper=true)
public class ChestTreasure extends Treasure {

	private static final long serialVersionUID = 1L;
	
	private static final String PRESERVE_CONTENT 	= "basics.preservecontent";	
	private static final String CONTENTS_ITEMS 		= "setup.contents.items";
	
	
	@Getter @Setter private transient ItemStack[] _inventory;
	@Getter private transient Block _block;	
	@Getter @Setter protected Boolean _preservecontent = true;	
	@Getter @Setter protected List<Material> _placesMaterials;
	@Getter private int _x, _y, _z;
	
	
	public ChestTreasure(){
		super( TreasureType.CHEST);
		populateMessages();
		
	}
	
	public ChestTreasure( ConfigurationSection section ){
		super( TreasureType.CHEST, section);
		this._preservecontent 	= this._conf.getBoolean( PRESERVE_CONTENT );
		this._placesMaterials 	= new ArrayList<Material>();
		populateMessages();
		generateContents();
	}
	
	public ChestTreasure( Location loc ){
		super( TreasureType.CHEST, loc);		
		this._x 	= loc.getBlockX();
		this._y 	= loc.getBlockY();
		this._z 	= loc.getBlockZ();
		this._block = loc.getBlock();		
		populateMessages();
		generateContents();
		
		
	}
	
	public ChestTreasure( File file ){
		super( TreasureType.CHEST, file);
		
		ChestTreasure cht = (ChestTreasure)unserialize(file);
		
		if( cht != null ){
			this._preservecontent 	= cht.get_preservecontent();
			// Le block
			this._x 				= cht.get_x();
			this._y 				= cht.get_y();
			this._z 				= cht.get_z();
			this._block 			= Bukkit.getWorld(this._world).getBlockAt( this._x, this._y, this._z );
			// Places materials
			this._placesMaterials 	= cht.get_placesMaterials();
			// ItemStack[]
			this._inventory 		= cht.get_inventory();

			populateMessages();
		}
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected void generateContents(){
		
		if( this._conf != null ){
			
			if( this._conf.get( CONTENTS_ITEMS ) != null ){
				// Populate _inventory
				List<ItemStack> items = (List<ItemStack>) this._conf.get( CONTENTS_ITEMS );
				this._inventory = items.toArray(new ItemStack[0]);			
			}
			
		}
		
	}
	

	@Override	
	protected String replaceVariables( String msg ){
		String message = "";		
		// Replace pour les codes couleurs
		message = msg.replace("&", "§");
		// Replace des pseudo variables
		message = message.replace("%name%", 			this._name);
		message = message.replace("%pattern%", 			this._pattern);
		message = message.replace("%duration%", 		String.valueOf( this._duration) );
		message = message.replace("%world%", 			this._world);
		message = message.replace("%onlyonsurface%", 	String.valueOf( this._onlyonsurface) );
		message = message.replace("%preservecontent%", 	String.valueOf( this._preservecontent) );
		message = message.replace("%infinite%", 		String.valueOf( this._infinite) );
		message = message.replace("%x%", 				String.valueOf( this._x) );
		message = message.replace("%y%", 				String.valueOf( this._y) );
		message = message.replace("%z%", 				String.valueOf( this._z) );
		message = message.replace("%name%", 			this._name);		
		return message;
	}

	@Override
	public void appear() {
		
		// Search for empty good block
		this._block = BlockSearcher.findGoodBlock(this);
		// TODO: later checking, perhaps useless
		if( this._block == null ){ Log.info("Can't find a good place for spawning this chest on loaded chunks"); return; }
		
		// Store coordinates
		this._x = _block.getLocation().getBlockX();
		this._y = _block.getLocation().getBlockY();
		this._z = _block.getLocation().getBlockZ();
		
		// Make this block as chest
		this._block.setType(Material.CHEST);
		Chest chest = (Chest)this._block.getState();
		
		// Give own inventory to this chest
		chest.getInventory().setContents(this._inventory);
		
		// Metadata store to distinguish chest as ChestTreasure
		chest.setMetadata("BooTreasure-Chest", new FixedMetadataValue(BooTreasure.get_instance(), this._id));
		
		// Serialization and lost treasure will be deleted on next start
		this.serialize();
		
		// Delayed task to disappear on duration fixed
		long delay = this._duration*20;
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask( BooTreasure.get_instance(), new Runnable() {
			
			@Override 
            public void run(){
				
				// Launch synchrone Bukkit event to disappear
				
			}
			
		}, delay);
		
	}
		
}
