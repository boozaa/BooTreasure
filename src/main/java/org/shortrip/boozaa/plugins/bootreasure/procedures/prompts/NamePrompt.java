package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;
import org.shortrip.boozaa.plugins.bootreasure.utils.Utils;

public abstract class NamePrompt extends ValidatingPrompt {

	//protected Treasure _treasure;


	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		// Au moins 6 caractères le nom
    	if( in.length()>=6 ){
    		// Que de l'alphanumeric
    		return Utils.isAlphaNumeric(in);
    	}
    	return false;
	}
	
	
	
}
