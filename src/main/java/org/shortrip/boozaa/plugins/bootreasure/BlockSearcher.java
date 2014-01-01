package org.shortrip.boozaa.plugins.bootreasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Synchronized;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockSearcher {

	@Synchronized
	public static Block findGoodBlock(TreasureChest treasure){
		
		Log.debug("Search valid block to support treasure on:");
		Log.debug("world: " + treasure.get_world());
		Log.debug("onsurface: " + treasure.get_onlyonsurface());
		
		World world = Bukkit.getWorld(treasure.get_world());
				
		// Treasure on surface without allowed ids fixed
		if( treasure.get_onlyonsurface() && treasure.get_placesMaterials().isEmpty() ){
			return findGoodSpawn(world, true);
		}
		
		
		// Treasure on surface with allowed ids fixed
		if( treasure.get_onlyonsurface() && !treasure.get_placesMaterials().isEmpty() ){
			return findGoodSpawn(world, treasure.get_placesMaterials(), true);
		}
		
		
		// Treasure without allowed ids fixed
		if( !treasure.get_onlyonsurface() && treasure.get_placesMaterials().isEmpty() ){
			return findGoodSpawn(world, false);
		}
		
		
		// Treasure with allowed ids fixed
		if( !treasure.get_onlyonsurface() && !treasure.get_placesMaterials().isEmpty() ){
			return findGoodSpawn(world, treasure.get_placesMaterials(), false);
		}
		
		return null;
		
	}
	
	
	
	@Synchronized
	private static Block findGoodSpawn(World world, Boolean onsurface){
		
		Chunk[] chunks = world.getLoadedChunks();
		Block block = null;
		Random random = new Random();
		// On prends un chunk au hasard
		if( chunks.length > 0 ){
			
			//do{	
			do{
				Chunk chunk = chunks[random.nextInt(chunks.length)];
				// On prend ces points au hasard
				int X = chunk.getX() * 16 + random.nextInt(16);
				int Z = chunk.getZ() * 16 + random.nextInt(16);
				int Y;
				if( onsurface ){
					Y = world.getHighestBlockYAt(new Location(world, X, random.nextInt(world.getMaxHeight()), Z));
				}else{
					Y = random.nextInt(world.getMaxHeight());
				}				
				block = LocationUtils.getNextFreeSpace(world.getBlockAt(new Location(world, X, Y, Z)), BlockFace.UP);
			}while( !validSupport(block) );
						
			//}while( !Core.getWG().canSpawnHere(block) );
			
		}
		
		Log.debug("Block found randomly in " + world + ": " + block.getX() + " " + block.getY() + " " + block.getZ());
		return block;
	}
	
	
	@Synchronized
	private static Block findGoodSpawn(World world, List<Material> allowedids, Boolean onsurface){
		
		Chunk[] chunks = world.getLoadedChunks();		
		Block block = null;
		Random random = new Random();
		// On prends un chunk au hasard
		if( chunks.length > 0 ){
			
			//do{	
			do{
				Chunk chunk = chunks[random.nextInt(chunks.length)];
				// On prend ces points au hasard
				int X = chunk.getX() * 16 + random.nextInt(16);
				int Z = chunk.getZ() * 16 + random.nextInt(16);
				int Y;
				if( onsurface ){
					Y = world.getHighestBlockYAt(new Location(world, X, random.nextInt(world.getMaxHeight()), Z));
				}else{
					Y = random.nextInt(world.getMaxHeight());
				}
				block = LocationUtils.getNextFreeSpace(world.getBlockAt(new Location(world, X, Y, Z)), BlockFace.UP);
			}while( !allowedids.contains( block.getRelative(BlockFace.DOWN)) && block.getType() != Material.CHEST && block.getType() != Material.AIR );
						
			//}while( !Core.getWG().canSpawnHere(block) );
			
		}
		
		Log.debug("Block found randomly in " + world + ": " + block.getX() + " " + block.getY() + " " + block.getZ());
		return block;
	}
	
	
	private static Boolean validSupport(Block block){
		
		List<Material> invalidblock = new ArrayList<Material>();
		invalidblock.add(Material.AIR);
		invalidblock.add(Material.SAND);
		invalidblock.add(Material.WATER);
		invalidblock.add(Material.LAVA);
		invalidblock.add(Material.GRAVEL);
		invalidblock.add(Material.VINE);
		invalidblock.add(Material.BED);
		invalidblock.add(Material.FENCE);
		invalidblock.add(Material.LEAVES);
				
		return !invalidblock.contains(block.getType());
	}
	
}
