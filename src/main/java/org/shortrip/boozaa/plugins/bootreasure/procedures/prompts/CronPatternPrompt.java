package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import it.sauronsoftware.cron4j.SchedulingPattern;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.Treasure;


public abstract class CronPatternPrompt extends ValidatingPrompt {

	//protected Treasure _treasure;
	

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		// Doit etre valide cronpattern
		if( SchedulingPattern.validate(in) ){
			return true;
		}
    	return false;
	}
		
	
}
