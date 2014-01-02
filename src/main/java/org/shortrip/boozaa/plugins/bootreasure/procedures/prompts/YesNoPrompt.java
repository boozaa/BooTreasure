package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;


public abstract class YesNoPrompt extends ValidatingPrompt {

	//protected Treasure _treasure;
	

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		// oui ou o
		String yes = BooTreasure.get_configManager().get("messages.yml").getString("locales.commands.agree");
		String no = BooTreasure.get_configManager().get("messages.yml").getString("locales.commands.disagree");
		
		if( in.equalsIgnoreCase( yes ) || in.equalsIgnoreCase(no) ){
			return true;
		}
    	return false;
	}
	
	
	
	
}
