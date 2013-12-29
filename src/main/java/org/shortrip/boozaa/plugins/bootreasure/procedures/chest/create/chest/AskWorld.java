/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest;

import org.bukkit.World;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.WorldPrompt;

/**
 * @author boozaa
 *
 * BooTreasure
 */
public class AskWorld extends WorldPrompt {
	
	private ChestTreasure _treasure;
	
	@Override
	public String getPromptText(ConversationContext context) {

		if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
			this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);

			if( context.getSessionData(Const.CREATE_CHEST_ASK_WORLD) != null ){
				// On demande le Monde
				StringBuilder str = new StringBuilder();
				str.append(context.getSessionData(Const.CREATE_CHEST_ASK_WORLD).toString() + ": " + System.getProperty("line.separator")  );
				for( World w : this._worlds ){
					str.append(w.getName() + " ");
				}		
				return str.toString();
			}
						
		}
		return null;
		
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String in) {
		// Le nom saisi est bien valide
    	context.setSessionData( Const.WORLD, in );
    	// Récuperation de données
    	this._treasure.set_world(in);  	    	
    	return new AskOnlyOnSurface(); 
	}
	
}
