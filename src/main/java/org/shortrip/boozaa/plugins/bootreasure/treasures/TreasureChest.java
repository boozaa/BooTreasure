package org.shortrip.boozaa.plugins.bootreasure.treasures;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.treasures.utils.BlockSearcher;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


@Data
@EqualsAndHashCode(callSuper=true)
public class TreasureChest extends Treasure {

	private static final long serialVersionUID = 1L;
	
	private static final String PRESERVE_CONTENT 	= "basics.preservecontent";	
	private static final String CONTENTS_ITEMS 		= "setup.contents.items";
	
	
	@Getter @Setter private transient ItemStack[] _inventory;
	@Getter private transient Block _block;	
	@Getter @Setter protected Boolean _preservecontent=true;	
	@Getter @Setter protected List<Material> _placesMaterials;
	@Getter private int _x, _y, _z;
	
	
	public TreasureChest(){
		super( TreasureType.CHEST);
		
	}
	
	public TreasureChest( ConfigurationSection section ){
		super( TreasureType.CHEST, section);
		this._preservecontent 	= this._conf.getBoolean( PRESERVE_CONTENT );
		this._placesMaterials 	= new ArrayList<Material>();
		generateContents();
	}
	
	public TreasureChest( Location loc ){
		super( TreasureType.CHEST, loc);		
		this._x 	= loc.getBlockX();
		this._y 	= loc.getBlockY();
		this._z 	= loc.getBlockZ();
		this._block = loc.getBlock();	
		this._placesMaterials 	= new ArrayList<Material>();
		generateContents();
		
		
	}
	
	/*
	 * Constructor from file
	 * Only to get this from serialization and make it disappears
	 */
	public TreasureChest( File file ){
		super( TreasureType.CHEST, file);
		
		TreasureChest cht = (TreasureChest)unserialize(file);
		
		if( cht != null ){
			this._preservecontent 	= cht.get_preservecontent();
			// Le block
			this._x 				= cht.get_x();
			this._y 				= cht.get_y();
			this._z 				= cht.get_z();
			this._block 			= Bukkit.getWorld(this._world).getBlockAt( this._x, this._y, this._z );
			/*
			// Places materials			
			if( cht.get_placesMaterials() != null ){
				this._placesMaterials 	= cht.get_placesMaterials();
			}else{
				this._placesMaterials 	= new ArrayList<Material>();
			}
			
			// ItemStack[]
			this._inventory 		= cht.get_inventory();
			*/
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
	
	
	public void chestAppear(){
		if( this._block == null ){
			this._block = Bukkit.getWorld(this._world).getBlockAt( this._x, this._y, this._z );
		}
		this._block.setType(Material.CHEST);
	}
	
	
	public void chestDisappear(){
		if( this._block == null ){
			this._block = Bukkit.getWorld(this._world).getBlockAt( this._x, this._y, this._z );
		}
		this._block.setType(Material.AIR);
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
		BooTreasure.get_eventsManager().chestDisappearDelayedEvent(this);
				
	}
	
	
	@Override
	public void disappear() {
		
		if( this._block == null ){	
			this._block = Bukkit.getWorld(this._world).getBlockAt( this._x, this._y, this._z );
		}
		
		if( this._block.getState().getType().equals(Material.CHEST) ){
			
			Chest chest = (Chest)this._block.getState();			
			// Clear its inventory
			chest.getInventory().clear();			
						
		}		
		

		if( Log.get_debugON() ){
			// Debug set glowstone in place
			this._block.setType(Material.GLOWSTONE);
		}else{
			// Set dedicated block as AIR
			this._block.setType(Material.AIR);
		}
					
		// Remove unecessary serialization representation
		this.deleteSerializedFile();				
		this._found = false;
		
	}
	
	
	@Override
	public void found(Player p) {
		
		Log.debug(p.getName() + " has discover this treasure " + this._name);
		
		if( !this._found ){
			this._found = true;
			this.announceFound();
		}
		
	}
	
	
	public void getChestContents(){
		Chest chest = (Chest)this._block.getState();
		this._inventory = chest.getInventory().getContents();
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
	public String toString() {
		
		StringBuilder build = new StringBuilder();
		String nl = System.getProperty("line.separator");
		
		build.append(nl);
		build.append(ChatColor.GOLD + "ChestTreasure -" + ChatColor.GREEN + this._id);
		build.append(nl);
		build.append(ChatColor.RESET + "  - Name: " + ChatColor.AQUA + this._name);
		build.append(nl);
		build.append(ChatColor.RESET + "  - World: " + ChatColor.AQUA + this._world);
		build.append(nl);
		build.append(ChatColor.RESET + "  - Cron Pattern: " + ChatColor.AQUA + this._pattern);
		build.append(nl);
		build.append(ChatColor.RESET + "  - Duration: " + ChatColor.AQUA + this._duration);
		build.append(nl);
		build.append(ChatColor.RESET + "  - X: " + ChatColor.AQUA + this._x + ChatColor.RESET + ", Y: " + ChatColor.AQUA + this._y + ChatColor.RESET + ", Z: " + ChatColor.AQUA + this._z  );
		build.append(nl);
		build.append(ChatColor.RESET + "  - Infinite: " + ChatColor.AQUA + this._infinite + ChatColor.RESET + ", Only On Surface: " + ChatColor.AQUA + this._onlyonsurface + ChatColor.RESET + ", Preserve Content: " + ChatColor.AQUA + this._preservecontent  );
		build.append(nl);
		build.append(ChatColor.RESET + "  - Contents: " + ChatColor.AQUA + Arrays.toString(_inventory));
		build.append(nl);
		
		return build.toString();
	}
		
}
