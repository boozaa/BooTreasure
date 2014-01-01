/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.SupportSearch;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.TreasureType;
import org.shortrip.boozaa.plugins.bootreasure.events.chest.TreasureChestDisappearEvent;
import org.shortrip.boozaa.plugins.bootreasure.old.ChatMessage;
import org.shortrip.boozaa.plugins.bootreasure.old.Const;
import org.shortrip.boozaa.plugins.bootreasure.old.Log;
import org.shortrip.boozaa.plugins.bootreasure.serializer.BukkitSerializer;



/**
 * @author boozaa
 *
 * BooTreasure
 */
@EqualsAndHashCode(callSuper=true)
public class ChestTreasure extends Treasure {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9020703576614376604L;
	private transient Plugin plugin;
	@Getter private transient String path;
	@Getter @Setter private transient ItemStack[] _inventory;
	@Getter @Setter private transient Boolean _infinite;
	@Getter private transient Block _block;	
	private int _x, _y, _z;
	private transient Location chestLocation;
	@Getter private transient Chest chest;
	
	/**
	 * Instanciation of a new ChestTreasure
	 * @param conf
	 * 		A ConfigurationSection to create this ChestTreasure
	 */
	public ChestTreasure(Plugin plugin, ConfigurationSection conf) throws Exception {
		super(conf);
		this.plugin = plugin;
		this._treasureType = TreasureType.CHEST;
		this.path = BooTreasure.getLostTreasuresPath() + this._id + ".chest";
		
		if( conf.get("setup.contents.items") != null ){
			Log.debug(conf.get("setup.contents.items").toString());
			List<ItemStack> items = (List<ItemStack>) conf.get("setup.contents.items");
			this._inventory = items.toArray(new ItemStack[0]);
			/*
			int i = 0;
			for( ItemStack stack : items ){
				Log.debug(stack.toString());
				this._inventory[i] = stack;
				i++;
			}
			
			int i = 0;
			for(String itemS : conf.getConfigurationSection("setup.contents.items").getKeys(false)) {				 
				Log.debug(itemS);
				this._inventory[i] = conf.getItemStack("setup.contents.items." + itemS);
				i++;				
			}
			*/
		}
	}

	
	
	
	/**
	 * 
	 */
	public ChestTreasure(Location loc) {
		super();
		this._treasureType = TreasureType.CHEST;
		chestLocation = loc;
		this._x = loc.getBlockX();
		this._y = loc.getBlockY();
		this._z = loc.getBlockZ();
		this._block = loc.getBlock();
		this._treasureType = TreasureType.CHEST;
		this.path = BooTreasure.getLostTreasuresPath() + this._id + ".chest";
	}


	/*
	 * Apparition du chest pour remplissage
	 */
	public void chestAppear(){
		Block block = chestLocation.getWorld().getBlockAt(chestLocation);
		block.setType(Material.CHEST);
		chest = (Chest) block.getState();
	}
	
	
	public void fillContents(){
		_inventory = chest.getInventory().getContents();
	}
	
	
	/*
	 * Disparition du chest
	 */
	public void chestDisappear(){
		chestLocation.getWorld().getBlockAt(chestLocation).setType(Material.AIR);
	}




	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#appear()
	 */
	@Override
	public void appear() throws Exception {
				
		// Search for empty good block
		this._block = SupportSearch.findGoodBlock(this);
		if( this._block == null ){ Log.info("Can't find a good place for spawning this chest on loaded chunks"); return; }
		
		// On stocke ses coordonnées
		this._x = _block.getLocation().getBlockX();
		this._y = _block.getLocation().getBlockY();
		this._z = _block.getLocation().getBlockZ();
		
		// Make this block as chest
		this._block.setType(Material.CHEST);
		this.chest = (Chest)this._block.getState();
		// Give own inventory to this chest
		this.chest.getInventory().setContents(this._inventory);
		
		// Metadata store to distinguish chest as ChestTreasure
		this.chest.setMetadata("BooTreasure-Chest", new FixedMetadataValue(this.plugin, this._id));
		
		Log.info("appear() in " + this._world + " on " + this._x + " " + this._y + " " + this._z + " for ChestTreasure " + this.toString() );
		
		// Appear message
		this.announceAppear();
		
		// Serialization
		this.serialize();
		
		// On lance un effet
		//makeEffect();
		
		// On prepare une task delayed pour le faire disparaitre
		long delay = this.duration*20;
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            @Override 
            public void run() {		                
            	// On lance event BooChestAppearEvent
                TreasureChestDisappearEvent event = new TreasureChestDisappearEvent(plugin, _id);
                // Call the event
        		Bukkit.getServer().getPluginManager().callEvent(event);		            	
            }
        }, delay);
		
	}

	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#disappear()
	 */
	@Override
	public void disappear() {
		
		if( this._block == null ){	
			this.chestLocation = Bukkit.getWorld(this._world).getBlockAt(_x, _y, _z).getLocation();
			this._block = Bukkit.getWorld(this._world).getBlockAt(this.chestLocation );
		}
		
		if( this._block.getState().getType().equals(Material.CHEST) ){

			this.chest = (Chest)this._block.getState();
			
			try {
				
				//Chest chest = (Chest) _block.getState();				
				
				/*
				if( this._preservecontent ){
					Log.debug("This Chest was found and its content must be preserved for the next apparition.");					
					if( this.chest.getInventory().getContents().length > 0 ){
						this._inventory = chest.getInventory().getContents().clone();
					}					
				}
				*/
				// Et on le supprimme du chest
				this.chest.getInventory().clear();
						
				// On supprime le chest si pas deja fait		
				this._block.setType(Material.AIR);
				
				// On envoit message de disparation
				this.announceDisappear();
				// On retire sa version sérializé			
				this.deleteSerializedFile();
				Log.debug("Treasure Chest '"  + this._name + "' disappear ["  + this._id + ".chest]");
								
				// On lance un effet
				//makeEffect();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}		
		
		// On replace le booleen found à false pour les suivants
		this._found = false;
		
	}
	
	
	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#found()
	 */
	@Override
	public void found(Player p) {
		
		Log.debug(p.getName() + " has discover this treasure " + this._name);
		
		if( !this._found ){
			this._found = true;
			this.announceFound();
		}
		
		//this.disappear();
		
	}
	





	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#serialize()
	 */
	@Override
	public void serialize() {
		/*
		 *  On serialize le treasurechest
		 *  Serialisation vers un fichier au nom de _id
		 */
		BukkitSerializer.serializeToFile(this, path);
		
	}

	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#deserialize()
	 */
	@Override
	public Object deserialize() {
		File file = new File( path );
		if( file.exists() ){		    				
			return BukkitSerializer.deserializeFromFile(file);				    				
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#deleteSerializedFile()
	 */
	@Override
	public void deleteSerializedFile() {
		File file = new File( path );
		if( file.exists() ){		    				
			file.delete();				    				
		}
	}

	
	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#canBeDiscoveredByPlayer()
	 */
	@Override
	public Boolean canBeDiscoveredByPlayer( Player player ) {
		if( this._conf != null ){
			if( this._conf.get(Const.TASK_PERMISSIONS_GROUPS) != null ) {
				if( this._conf.getBoolean(Const.TASK_PERMISSIONS_GROUPS) ){
					if( BooTreasure.get_vaultUtils().playerCanFind(player)){
						return true;
					}
					return false;
				}
			}	
		}					
		return true;
	}

	
	
	

	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#announceAppear()
	 */
	@Override
	public void announceAppear() {
		if( this._conf != null ){
			String message = this._conf.getString(Const.TASK_MESSAGES_SPAWN).replaceAll("&", "§");
			// groupes autorisés de fixés
			if( this._conf.getString(Const.TASK_PERMISSIONS_GROUPS) != null  ){
				List<String> groups = this._conf.getStringList(Const.TASK_PERMISSIONS_GROUPS);				
				ChatMessage.forGroup(groups, message);
			}else{				
				ChatMessage.broadcast(message);
			}
		}else{
			ChatMessage.broadcast("§bUn nouveau trésor vient d'apparaître");
		}	
	}

	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#announceDisappear()
	 */
	@Override
	public void announceDisappear() {
		if( this._conf != null ){
			String message = this._conf.getString(Const.TASK_MESSAGES_DISAPPEAR).replaceAll("&", "§");
			if( this._conf.getString(Const.TASK_PERMISSIONS_GROUPS) != null  ){
				List<String> groups = this._conf.getStringList(Const.TASK_PERMISSIONS_GROUPS);
				ChatMessage.forGroup(groups, message);
			}else{
				ChatMessage.broadcast(message);
			}
		}else{
			ChatMessage.broadcast("§bLe trésor vient de disparaître");
		}	
	}

	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#announceFound()
	 */
	@Override
	public void announceFound() {
		if( this._conf != null ){
			String message = this._conf.getString(Const.TASK_MESSAGES_FOUND).replaceAll("&", "§");
			if( this._conf.getString(Const.TASK_PERMISSIONS_GROUPS) != null  ){
				List<String> groups = this._conf.getStringList(Const.TASK_PERMISSIONS_GROUPS);
				ChatMessage.forGroup(groups, message);
			}else{
				ChatMessage.broadcast(message);
			}
		}else{
			ChatMessage.broadcast("§bLe trésor vient d'être trouvé");
		}	
	}

	/* (non-Javadoc)
	 * @see org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure#announceFoundButNotEmpty()
	 */
	@Override
	public void announceFoundButNotEmpty() {
		if( this._conf != null ){
			String message = this._conf.getString(Const.TASK_MESSAGES_FOUNDBUTNOTEMPTY).replaceAll("&", "§");
			if( this._conf.getString(Const.TASK_PERMISSIONS_GROUPS) != null  ){
				List<String> groups = this._conf.getStringList(Const.TASK_PERMISSIONS_GROUPS);
				ChatMessage.forGroup(groups, message);
			}else{
				ChatMessage.broadcast(message);
			}
		}else{
			ChatMessage.broadcast("§bLe trésor vient d'être trouvé mais pas vidé");
		}	
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
		build.append(ChatColor.RESET + "  - Duration: " + ChatColor.AQUA + this.duration);
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
