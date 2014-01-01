package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.old.Const;


public abstract class YesNoPrompt extends ValidatingPrompt {

	//protected Treasure _treasure;
	

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		// oui ou o
		if( ( in.equalsIgnoreCase(Const.CMD_YES) || in.equalsIgnoreCase(Const.CMD_YES.substring(1, 2)) ) || ( in.equalsIgnoreCase(Const.CMD_NO) || in.equalsIgnoreCase(Const.CMD_NO.substring(1, 2)) )  ){
			return true;
		}
    	return false;
	}
	
	
	
	
}
