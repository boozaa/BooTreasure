package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;


public abstract class NamePrompt extends ValidatingPrompt {

	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		return true;
	}
		
	
}
