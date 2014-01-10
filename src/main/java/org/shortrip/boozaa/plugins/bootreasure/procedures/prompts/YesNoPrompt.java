package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public abstract class YesNoPrompt extends ValidatingPrompt {

	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		
		Log.debug("Validating input, must be " + Const.YES + " or " + Const.NO + " -> input = " + in);
		if( in.equalsIgnoreCase( "true" ) || in.equalsIgnoreCase( "false" ) ){
			return true;
		}
    	return false;
	}
		
}
