package org.shortrip.boozaa.plugins.bootreasure.procedures.validityprompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

public abstract class IntegerPrompt extends ValidatingPrompt {

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {

		Log.debug("Validating input, must be integer -> input = " + in);
		if( isInt(in) ){
			return true;
		}
    	return false;
	}
	
	
	private static Boolean isInt( String chaine ){		
		for(int i=0; i<chaine.length(); i++)
		{
			if(!Character.isDigit(chaine.charAt(i))){
				return false;
			}			   
		}		
		return true;
	}
	
	
    
	
	
	
}
