package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;


public abstract class YesNoPrompt extends ValidatingPrompt {

	//protected Treasure _treasure;
	

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		// oui ou o
		String yes = (String) context.getSessionData("YesCommand");
		String no = (String) context.getSessionData("NoCommand");
		
		if( ( in.equalsIgnoreCase( yes ) || in.equalsIgnoreCase(yes.substring(1, 2)) ) || 
				( in.equalsIgnoreCase(no) || in.equalsIgnoreCase(no.substring(1, 2)) )  ){
			return true;
		}
    	return false;
	}
	
	
	
	
}
