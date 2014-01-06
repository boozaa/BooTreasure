package org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BasicBlockCondition extends AbstractBlockCondition {

	
	public BasicBlockCondition() {
		super();	
	}
	
	
	@Override
	public Boolean isBlockValid(Block block) {
		return block.getType() != Material.CHEST && block.getType() != Material.AIR;
	}

}
