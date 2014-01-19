package org.shortrip.boozaa.plugins.bootreasure.procedures.validityprompts;

import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.configs.LocalesNodes;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public abstract class MaterialPrompt extends ValidatingPrompt {
	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {

		Log.debug("Validating input, must be a Material -> input = " + in);
		Material mat = Material.getMaterial(in);
		if( mat != null || in.equalsIgnoreCase( LocalesNodes.EXIT.getConfigNode() ) ){
			return true;
		}
		return false;
	}
	
}
