package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;

public abstract class WorldPrompt extends ValidatingPrompt {

	//protected Treasure _treasure;
	protected List<World> _worlds;
	
	
	public WorldPrompt(){
		this._worlds = Bukkit.getServer().getWorlds();
	}
	

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {
		for( World w : _worlds ){
			if( w.getName().equalsIgnoreCase(in) ){
				return true;
			}
		}		
    	return false;
	}
	
	
}
