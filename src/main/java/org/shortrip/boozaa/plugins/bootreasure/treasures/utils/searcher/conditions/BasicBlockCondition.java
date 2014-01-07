package org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BasicBlockCondition extends AbstractBlockCondition {

	
	public BasicBlockCondition() {
		super();	
	}
	
	
	@Override
	public Boolean isBlockValid(Block block) {
			
		if( block.getType() != Material.AIR)
			return false;
		
		if( block.getRelative(BlockFace.DOWN).getType() == Material.CHEST )
			return false;
		
		if( block.getRelative(BlockFace.UP).getType() == Material.CHEST )
			return false;
				
		if( block.getType() == Material.CHEST )
			return false;
		
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
		if( invalidblock.contains( block.getType()) )
			return false;
		
		return true;
		
	}

}
