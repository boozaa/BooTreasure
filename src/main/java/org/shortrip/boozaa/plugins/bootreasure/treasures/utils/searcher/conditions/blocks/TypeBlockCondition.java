package org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions.AbstractBlockCondition;



public class TypeBlockCondition extends AbstractBlockCondition {

	private List<Material> allowedBlocks = new ArrayList<Material>();
	
	
	/*
	 * 	setup:
  	 *		places:
     *			ids:
     *			- '41'
	 */	
	public TypeBlockCondition(List<Material> allowedBlocks) {
		super();
		// Check setup.places.allowedBlocks
		this.allowedBlocks = allowedBlocks;
	}

	
	
	@Override
	public Boolean isBlockValid(Block block) {		
		return !allowedBlocks.contains( block.getRelative(BlockFace.DOWN) );		
	}

}
