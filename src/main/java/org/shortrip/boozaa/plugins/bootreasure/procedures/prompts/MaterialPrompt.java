package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.Const;

public abstract class MaterialPrompt extends ValidatingPrompt {

	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		Material mat = Material.getMaterial(in);
		if( mat != null || in.equalsIgnoreCase(Const.CMD_EXIT) ){
			return true;
		}
		return false;
	}
	
}
