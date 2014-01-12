package org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.UnsupportedIntersectionException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldedit.BlockVector;



public class WorlguardRegionBlockCondition extends AbstractBlockCondition {

	
	private static WorldGuardPlugin wg;
	
	
	public WorlguardRegionBlockCondition() {
		super();
		// Check setup.places.worldguardRegions
	}
	
	

	@Override
	public Boolean isBlockValid(Block block) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@SuppressWarnings("unused")
	private static Block findGoodSpawnInWGRegion( World world, Boolean onlyOnSurface, List<AbstractBlockCondition> conditions, List<String> regionNames ){
		
		// Get all regions for world
		RegionManager regionManager = wg.getRegionManager(world);
		
		// Keep only region with name on regionNames
		List<ProtectedRegion> regions = new ArrayList<ProtectedRegion>();
		for( String regionName : regionNames ){
			if( regionManager.hasRegion(regionName) )
				regions.add( regionManager.getRegion(regionName) );
		}
		
		// Get all loaded chunk of this regions
		Chunk[] chunks = world.getLoadedChunks();
		List<Chunk> chunksInRegion = new ArrayList<Chunk>();
		for( Chunk ch : chunks ){
			for( ProtectedRegion re : regions ){
				if( checkForRegionsInChunk( ch ) ){
					
				}
			}
		}
		
		
		
		
		
		return null;
	}
	
	

	
	
	// Check for Regions in chunk the chunk
    // Returns:
    // True: Regions found within chunk
    // False: No regions found within chunk
    public boolean checkForRegionsInChunk(Location loc) {
            int plocX = loc.getBlockX();
            int plocZ = loc.getBlockZ();
            World world = loc.getWorld();
            
            Chunk chunk = world.getChunkAt(plocX, plocZ);
            int chunkX = chunk.getX();
            int chunkZ = chunk.getZ();
            
            BlockVector minChunk = new BlockVector(chunkX, 0, chunkZ);
            BlockVector maxChunk = new BlockVector(chunkX+15, 128, chunkZ+15);
            
            RegionManager regionManager = wg.getRegionManager(world);
            ProtectedCuboidRegion region = new ProtectedCuboidRegion("wgfactionoverlapcheck", minChunk, maxChunk);
            Map<String, ProtectedRegion> allregions = regionManager.getRegions();
            
            List<ProtectedRegion> allregionslist = new ArrayList<ProtectedRegion>(allregions.values());
            List<ProtectedRegion> overlaps;
            boolean foundregions = false;

            try {
                    overlaps = region.getIntersectingRegions(allregionslist);
                    if(overlaps.isEmpty() || overlaps == null) {
                            foundregions = false;
                    } else {
                            foundregions = true;
                    }
            } catch (UnsupportedIntersectionException e) {
                    e.printStackTrace();
            }

            region = null;
            allregionslist = null;
            overlaps = null;
            
            return foundregions;
    }
	
    public static boolean checkForRegionsInChunk(Chunk chunk) {
        
        World world = chunk.getWorld();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        
        BlockVector minChunk = new BlockVector(chunkX, 0, chunkZ);
        BlockVector maxChunk = new BlockVector(chunkX+15, 128, chunkZ+15);
        
        RegionManager regionManager = wg.getRegionManager(world);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion("wgfactionoverlapcheck", minChunk, maxChunk);
        Map<String, ProtectedRegion> allregions = regionManager.getRegions();
        
        List<ProtectedRegion> allregionslist = new ArrayList<ProtectedRegion>(allregions.values());
        List<ProtectedRegion> overlaps;
        boolean foundregions = false;

        try {
                overlaps = region.getIntersectingRegions(allregionslist);
                if(overlaps.isEmpty() || overlaps == null) {
                        foundregions = false;
                } else {
                        foundregions = true;
                }
        } catch (UnsupportedIntersectionException e) {
                e.printStackTrace();
        }

        region = null;
        allregionslist = null;
        overlaps = null;
        
        return foundregions;
    }
    
    
    @SuppressWarnings("unused")
	public List<ProtectedRegion> getRegionsInChunk(Chunk chunk) {
        
        World world = chunk.getWorld();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        
        BlockVector minChunk = new BlockVector(chunkX, 0, chunkZ);
        BlockVector maxChunk = new BlockVector(chunkX+15, 128, chunkZ+15);
        
        RegionManager regionManager = wg.getRegionManager(world);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion("wgfactionoverlapcheck", minChunk, maxChunk);
        Map<String, ProtectedRegion> allregions = regionManager.getRegions();
        
        List<ProtectedRegion> allregionslist = new ArrayList<ProtectedRegion>(allregions.values());
        List<ProtectedRegion> overlaps;
        boolean foundregions = false;

        try {
                overlaps = region.getIntersectingRegions(allregionslist);
                if( !overlaps.isEmpty() || overlaps != null) {
                     return overlaps;
                }
        } catch (UnsupportedIntersectionException e) {
                e.printStackTrace();
        }
        
        return null;
    }
	
	
}
