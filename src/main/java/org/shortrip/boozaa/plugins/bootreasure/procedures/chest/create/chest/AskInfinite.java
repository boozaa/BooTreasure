/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.YesNoPrompt;

/**
 * @author boozaa
 *
 * BooTreasure
 */
public class AskInfinite extends YesNoPrompt {
	
	private ChestTreasure _treasure;
	
	@Override
	public String getPromptText(ConversationContext context) {
		
		if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
			this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
			if( context.getSessionData(Const.CREATE_CHEST_ASK_INFINITE) != null ){
				// On demande si infini
				return context.getSessionData(Const.CREATE_CHEST_ASK_INFINITE).toString();
			}			
		}
		return null;
		
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String in) {
		// Le nom saisi est bien valide
    	context.setSessionData( Const.INFINITE, in );
    	// Récuperation de données
    	if( in.equalsIgnoreCase(Const.CMD_YES) || in.equalsIgnoreCase(Const.CMD_YES.substring(1, 2)) ){
    		this._treasure.set_infinite(true);
    	}else{
    		this._treasure.set_infinite(false);
    	}    	    	
    	return new AskWorld(); 
	}
	
}