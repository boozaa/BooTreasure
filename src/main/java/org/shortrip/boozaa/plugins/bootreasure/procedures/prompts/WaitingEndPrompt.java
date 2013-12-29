package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.Log;


public class WaitingEndPrompt extends ValidatingPrompt {

	protected String _message, _fin;
	
	public WaitingEndPrompt(String message, String fin){
		this._message = message;
		this._fin = fin;
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		return context.getSessionData(this._message).toString();
	}
	
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String in) {
		return new EndPrompt(this._fin); 
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		Log.debug("Enter WaitingEndPrompt isInputValid");
		// oui ou o
		//if( in.equalsIgnoreCase(Const.CMD_EXIT) ){
		//	return true;
		//}
    	return true;
	}
	
	
	
}
