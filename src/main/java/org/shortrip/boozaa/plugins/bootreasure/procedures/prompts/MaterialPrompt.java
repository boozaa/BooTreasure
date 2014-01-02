package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;

public abstract class MaterialPrompt extends ValidatingPrompt {

	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		Material mat = Material.getMaterial(in);
		if( mat != null || in.equalsIgnoreCase( (String) context.getSessionData("ExitCommand") ) ){
			return true;
		}
		return false;
	}
	
}
