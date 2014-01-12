package org.shortrip.boozaa.plugins.bootreasure.procedures.validityprompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public abstract class YesNoPrompt extends ValidatingPrompt {

	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		
		Log.debug("Validating input, must be true or false -> input = " + in);
		if( in.equalsIgnoreCase( "true" ) || in.equalsIgnoreCase( "false" ) ){
			return true;
		}
    	return false;
	}
		
}
