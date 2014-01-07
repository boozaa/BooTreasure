package org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Synchronized;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.treasures.utils.LocationUtils;
import org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions.AbstractBlockCondition;
import org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions.BasicBlockCondition;
import org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions.TypeBlockCondition;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

public class BlockSearcher {

	@Synchronized
	public static Block findGoodBlock(TreasureChest treasure){
				
		
		World world = Bukkit.getWorld(treasure.get_world());
		Boolean onlyOnSurface = treasure.get_onlyonsurface();
		
		List<AbstractBlockCondition> conditions = new ArrayList<AbstractBlockCondition>();
		
		// Not chest and air
		conditions.add(new BasicBlockCondition() );
			
		// places conditions, block allowed
		Boolean hasPlacesMaterials = false;	
		if( treasure.get_placesMaterials() !=null && !treasure.get_placesMaterials().isEmpty() ){
			hasPlacesMaterials = true;
			conditions.add(new TypeBlockCondition( treasure.get_placesMaterials() ) );
		}
		
		
		Log.debug( "Search valid block to support treasure on:" );
		Log.debug( "world: " + world.getName() );
		Log.debug( "onsurface: " + treasure.get_onlyonsurface() );
		Log.debug( "hasPlacesMaterials: " + hasPlacesMaterials );
		
		
		return findGoodSpawn(world, onlyOnSurface, conditions);
				
	}
	
	
	@Synchronized
	private static Block findGoodSpawn(World world, Boolean onlyOnSurface, List<AbstractBlockCondition> conditions) {
		
		Chunk[] chunks = world.getLoadedChunks();
		Block block = null;
		Random random = new Random();
		// On prends un chunk au hasard
		if( chunks.length > 0 ){
			
			do{
				
				Chunk chunk = chunks[random.nextInt(chunks.length)];
				// On prend ces points au hasard
				int X = chunk.getX() * 16 + random.nextInt(16);
				int Z = chunk.getZ() * 16 + random.nextInt(16);
				int Y;
				if( onlyOnSurface ){
					Y = world.getHighestBlockYAt(new Location(world, X, random.nextInt(world.getMaxHeight()), Z));
				}else{
					Y = random.nextInt(world.getMaxHeight());
				}				
				block = LocationUtils.getNextFreeSpace(world.getBlockAt(new Location(world, X, Y, Z)), BlockFace.UP);				
				
			}while( !validSupport(conditions, block) );			
			
		}
		
		Log.debug("Block found randomly in " + world + ": " + block.getX() + " " + block.getY() + " " + block.getZ());
		return block;
		
	}

	
	
	private static boolean validSupport( List<AbstractBlockCondition> conditions, Block block ) {
				
		if( block.getRelative(BlockFace.DOWN).isBlockPowered() || block.getRelative(BlockFace.UP).isBlockPowered()  )
			return false;
		
		
		if( block.getRelative(BlockFace.DOWN).isLiquid() || block.getRelative(BlockFace.UP).isLiquid() )
			return false;
		
		
		for( AbstractBlockCondition c : conditions ){
			if( !c.isBlockValid(block) ){
				return false;
			}
		}
		
		return true;
	}


	
	
	/*
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
	*/
	
}
