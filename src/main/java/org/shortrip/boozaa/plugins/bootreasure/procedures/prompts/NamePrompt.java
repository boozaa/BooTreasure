package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;



public abstract class NamePrompt extends ValidatingPrompt {

	//protected Treasure _treasure;


	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		// Au moins 6 caractères le nom
    	return isAlphaNumeric(in);
	}
	
	
	private boolean isAlphaNumeric(String s)
    {
		return s.matches("^[a-zA-Z0-9]*$");
    }
	
	
}
