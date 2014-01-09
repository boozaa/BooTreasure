package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import it.sauronsoftware.cron4j.SchedulingPattern;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public abstract class CronPatternPrompt extends ValidatingPrompt {

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {

		Log.debug("Validating input, must be a valid CronPattern -> input = " + in);
		// Doit etre valide cronpattern
		if( SchedulingPattern.validate(in) ){
			return true;
		}
    	return false;
	}
			
}
