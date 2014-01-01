package org.shortrip.boozaa.plugins.bootreasure.procedures.exceptions;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;

public class NamePromptException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3285152146492188667L;

	
	public NamePromptException(ConversationContext context, String message){
		Player player = (Player) context.getForWhom();
		player.sendMessage(message);
	}
	
	
}
