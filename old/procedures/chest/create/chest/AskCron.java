/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.old.Const;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.CronPatternPrompt;

/**
 * @author boozaa
 *
 * BooTreasure
 */
public class AskCron extends CronPatternPrompt {
	
	private ChestTreasure _treasure;
	
	@Override
	public String getPromptText(ConversationContext context) {
		
		if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
			this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
			return Const.CREATE_CHEST_ASK_PATTERN;
		}
		return null;
		
	}
	
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String in) {
		// Le nom saisi est bien valide
    	context.setSessionData( Const.PATTERN, in );
    	// Récuperation de données
    	this._treasure.set_pattern(in);   	
    	return new AskDuration(); 
	}	
		
}