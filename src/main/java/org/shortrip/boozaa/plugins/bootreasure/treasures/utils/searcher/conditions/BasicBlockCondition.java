package org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions;

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
		
		
		return true;
		
	}

}
