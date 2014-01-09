package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public abstract class YesNoPrompt extends ValidatingPrompt {

	private final String YES 	= BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.agree");
	private final String NO 	= BooTreasure.getConfigManager().get("messages.yml").getString("locales.commands.disagree");
	
	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		
		Log.debug("Validating input, must be " + YES + " or " + NO + " -> input = " + in);
		if( in.equalsIgnoreCase( "true" ) || in.equalsIgnoreCase( "false" ) ){
			return true;
		}
    	return false;
	}
		
}
