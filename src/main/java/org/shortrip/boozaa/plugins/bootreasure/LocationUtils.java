package org.shortrip.boozaa.plugins.bootreasure;

import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;


public class LocationUtils {

	public static Block getNextFreeSpace(Block block, BlockFace direction) {
        while (block.getType() != Material.AIR && block.getRelative(direction).getType() != Material.AIR) {
            if (!(block.getY() < block.getWorld().getMaxHeight())) {
                break;
            }
            block = block.getRelative(direction);
        }
        return block;
    }
	
	
	public static Location getCenterOfBlock(Block block) {
        return block.getLocation().add(0.5, 1, 0.5);
    }
	
	
	public static Player[] getNearbyPlayers(Location l, int radius) {

        int chunkRadius = radius < 16 ? 1 : radius / 16;
        HashSet<Player> radiusEntities = new HashSet<Player>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
                for (Entity e : new Location(l.getWorld(), x + chX * 16, y, z + chZ * 16).getChunk().getEntities()) {
                    if(!(e instanceof Player))
                        continue;
                    if (e.getLocation().distanceSquared(l) <= radius * radius && e.getLocation().getBlock() != l.getBlock()) {
                        radiusEntities.add((Player) e);
                    }
                }
            }
        }
        return radiusEntities.toArray(new Player[radiusEntities.size()]);
    }
	
	
	public static Chest[] getNearbyChest(Player player, int radius) {

		HashSet<Chest> radiusChests = new HashSet<Chest>();
		for(int x = -radius; x <= radius; x++) {
	        for(int y = -radius; y <= radius; y++) {
	            for(int z = -radius; z <= radius; z++) {
	                if(player.getLocation().getBlock().getRelative(x, y, z).getType() == Material.CHEST){
	                	
	                	Chest chest = (Chest) player.getLocation().getBlock().getRelative(x, y, z).getState();
	                	radiusChests.add(chest);	                	
	                	
	                }
	                      
	            }
	        }
	    }
		return radiusChests.toArray(new Chest[radiusChests.size()]);
       
    }
	
	public static String getCardinalDirection(Player player) {
		double rotation = (player.getLocation().getYaw() - 90) % 360;
		if (rotation < 0) {
		rotation += 360.0;
		}
		if (0 <= rotation && rotation < 22.5) {
		return "N";
		} else if (22.5 <= rotation && rotation < 67.5) {
		return "NE";
		} else if (67.5 <= rotation && rotation < 112.5) {
		return "E";
		} else if (112.5 <= rotation && rotation < 157.5) {
		return "SE";
		} else if (157.5 <= rotation && rotation < 202.5) {
		return "S";
		} else if (202.5 <= rotation && rotation < 247.5) {
		return "SW";
		} else if (247.5 <= rotation && rotation < 292.5) {
		return "W";
		} else if (292.5 <= rotation && rotation < 337.5) {
		return "NW";
		} else if (337.5 <= rotation && rotation < 360.0) {
		return "N";
		} else {
		return null;
		}
	}
	
	public static BlockFace getOppositeCardinal( String card ){
		if( card == "N" )
			return BlockFace.SOUTH;
		if( card == "NE" )
			return BlockFace.SOUTH_SOUTH_WEST;
		if( card == "E" )
			return BlockFace.WEST;
		if( card == "S" )
			return BlockFace.NORTH;
		if( card == "SW" )
			return BlockFace.NORTH_EAST;
		if( card == "W" )
			return BlockFace.EAST;
		if( card == "S" )
			return BlockFace.NORTH_WEST;
		if( card == "SE" )
			return BlockFace.SOUTH_EAST;
		
		return BlockFace.NORTH;
	}
	
}
