/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.MaterialPrompt;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.WaitingEndPrompt;

/**
 * @author boozaa
 *
 * BooTreasure
 */
public class AskAllowedIds extends MaterialPrompt {
	
	private List<Material> _materials;
	private ChestTreasure _treasure;
	
	
	public AskAllowedIds(){
		this._materials = new ArrayList<Material>();
	}
		
	@Override
	public String getPromptText(ConversationContext context) {

		if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
			this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);

			if( context.getSessionData(Const.CREATE_CHEST_ASK_ALLOWEDIDS) != null ){
				// On demande les ids socles si besoin
				return context.getSessionData(Const.CREATE_CHEST_ASK_ALLOWEDIDS).toString();
			}			
		}
		return null;
		
	}
	
	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String in) {
		
		// Si on a un Material en in on le stocke
		Material mat = Material.getMaterial(in);
		if( mat != null ){
			// On l'ajoute a la List
			this._materials.add(mat);
			// On informe
			context.getForWhom().sendRawMessage("+" + mat);
		}	
		
		// Si on demande la fin du prompt on stocke et passe à étape suivante
    	if( in.equalsIgnoreCase(Const.CMD_EXIT) ){
    		// On stocke dans le context et dans l'instance de treasure
        	context.setSessionData( Const.ALLOWEDIDS, this._materials );
        	this._treasure.set_allowedids(this._materials);
        	
        	Log.debug( this._treasure.toString() );
        	// Disparition du chest
        	this._treasure.chestDisappear();
        	
    		return new WaitingEndPrompt(Const.CREATE_CHEST_WAITINGEND, Const.CREATE_SUCCESS);
		}
    	
    	// On boucle pour attendre les prochains blocks
    	return this;
    	
	}
	
}