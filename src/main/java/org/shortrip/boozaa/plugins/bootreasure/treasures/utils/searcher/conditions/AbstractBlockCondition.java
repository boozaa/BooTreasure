package org.shortrip.boozaa.plugins.bootreasure.treasures.utils.searcher.conditions;

import lombok.Getter;

import org.bukkit.block.Block;

public abstract class AbstractBlockCondition {

		
	public AbstractBlockCondition(){
		
	}
	
	
	public abstract Boolean isBlockValid(Block block);
	
}
