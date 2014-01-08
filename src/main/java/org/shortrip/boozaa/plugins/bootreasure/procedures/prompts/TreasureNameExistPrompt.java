package org.shortrip.boozaa.plugins.bootreasure.procedures.prompts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ValidatingPrompt;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;


public abstract class TreasureNameExistPrompt extends ValidatingPrompt {

	List<String> treasureNames = new ArrayList<String>();
	
	
	
	public TreasureNameExistPrompt(){
		for( Entry<String, Object> entry : BooTreasure.getCacheManager().getTreasures().entrySet() ){
			this.treasureNames.add(entry.getKey());
		}
	}
	
	
	@Override
	protected boolean isInputValid(ConversationContext context, String in) {		
		if( this.treasureNames.contains(in) )
			return true;
		
		return false;		
	}
		
	
}
