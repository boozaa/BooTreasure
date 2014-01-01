package org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.old.Const;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.IntegerPrompt;

public class AskDuration extends IntegerPrompt {

	private ChestTreasure _treasure;

	@Override
	public String getPromptText(ConversationContext context) {
		
		if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
			this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
			// On demande le Monde
			return Const.CREATE_CHEST_ASK_DURATION_PHRASE;		
		}
		return null;
		
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String in) {
		// La chaine saisie est bien un entier
    	context.setSessionData( Const.DURATION, Long.parseLong(in) );
    	// Récuperation de données
    	this._treasure.setDuration(Long.parseLong(in) ); 
    	return new AskInfinite(); 
	}
	
	
}
