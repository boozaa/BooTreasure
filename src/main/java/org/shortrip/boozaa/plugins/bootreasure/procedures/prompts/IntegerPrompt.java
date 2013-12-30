package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.utils.Utils;

public abstract class IntegerPrompt extends ValidatingPrompt {

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		// oui ou o
		if( Utils.isInt(in) ){
			return true;
		}
    	return false;
	}
	
	
}
