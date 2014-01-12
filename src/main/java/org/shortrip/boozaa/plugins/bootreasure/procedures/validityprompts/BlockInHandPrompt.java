package org.shortrip.boozaa.plugins.bootreasure.procedures.validityprompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;

public abstract class BlockInHandPrompt extends ValidatingPrompt {

	
	
	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {			
		
		if( in.equalsIgnoreCase("add") ){			
			Player player = (Player) context.getForWhom();
			return player.getItemInHand().getType().isBlock();			
		}
		
		return false;		
	}
	
}
