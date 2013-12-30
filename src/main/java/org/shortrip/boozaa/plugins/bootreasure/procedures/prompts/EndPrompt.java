package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;

public class EndPrompt extends MessagePrompt {

	protected String _message;
	
	
	public EndPrompt(String message){
		this._message = message;
	}
	
	@Override
	public String getPromptText(ConversationContext context) {
		Log.debug("Enter EndPrompt getPromptText");
		
		ChestTreasure treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
		
		// On stocke son inventaire
		treasure.fillContents();
		
		Log.debug( treasure.toString() );
		
		Log.debug( "Serialization ..." );
		
		// On serialize
		treasure.serialize();

		Log.debug( "... done" );
		
		return context.getSessionData(this._message).toString();
	}
	
	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		// On met fin à cette procédure
		return Prompt.END_OF_CONVERSATION;
	}
	
	
}
