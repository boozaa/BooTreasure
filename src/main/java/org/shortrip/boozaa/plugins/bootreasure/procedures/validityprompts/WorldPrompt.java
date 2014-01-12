package org.shortrip.boozaa.plugins.bootreasure.procedures.validityprompts;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;


public abstract class WorldPrompt extends ValidatingPrompt {

	protected List<World> _worlds;
	
	
	public WorldPrompt(){
		this._worlds = Bukkit.getServer().getWorlds();
	}
	

	@Override
	protected boolean isInputValid(ConversationContext context, String in) {

		Log.debug("Validating input, must be a loaded world name -> input = " + in);
		for( World w : _worlds ){
			if( w.getName().equalsIgnoreCase(in) ){
				return true;
			}
		}		
    	return false;
	}
	
	
}
