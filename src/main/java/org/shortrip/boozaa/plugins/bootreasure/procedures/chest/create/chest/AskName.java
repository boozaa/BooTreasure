/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.NamePrompt;

/**
 * @author boozaa
 *
 * BooTreasure
 */
public class AskName extends NamePrompt {
	
	private ChestTreasure _treasure;
	
	@Override
	public String getPromptText(ConversationContext context) {
		
		if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){			
			this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);			
			return Const.CREATE_CHEST_ASK_NAME;
		}
		return null;
		
	}
	
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String in) {
		// Le nom saisi est bien valide
    	context.setSessionData( Const.NAME, in );
    	// Récuperation de données
    	this._treasure.set_name(in);   	
    	return new AskCron(); 
	}
	
}