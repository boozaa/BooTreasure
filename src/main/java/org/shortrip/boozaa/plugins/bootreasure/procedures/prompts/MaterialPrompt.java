package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;

public abstract class MaterialPrompt extends ValidatingPrompt {

	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		Material mat = Material.getMaterial(in);
		String exit = BooTreasure.get_configManager().get("messages.yml").getString("locales.commands.exit");
		if( mat != null || in.equalsIgnoreCase( exit ) ){
			return true;
		}
		return false;
	}
	
}
