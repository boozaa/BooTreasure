package org.shortrip.boozaa.plugins.bootreasure.procedures.chest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.bukkit.entity.Player;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Managers;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;
import org.shortrip.boozaa.plugins.bootreasure.utils.StringUtils;



public class ChestEditProcedure implements Runnable {


	private volatile TreasureChest treasure;	
	private BooTreasure plugin;
	private Player player;
	private World world;
	private Location chestLocation;
	
	@SuppressWarnings("unused")
	private Map<String, String> treasuresIdAndNameMap = new HashMap<String, String>();
	
	
	public ChestEditProcedure(  BooTreasure plugin, Player p  ){
		
		this.plugin = plugin;
		this.player = p;
		this.world = p.getWorld();
		chestLocation = this.player.getLocation().toVector().add(this.player.getLocation().getDirection().multiply(1)).toLocation(this.world);
		chestLocation.setY(chestLocation.getY()+1);
		
	}
	
	
	
	@Override
	public void run() {
		
		try{
			
			Log.debug("ChestEditProcedure: list available treasure");
			
			// Le ConversationFactory
			ConversationFactory factory = new ConversationFactory(this.plugin);			
						
			// On construit la conversation
			Conversation conv = factory
		            .withFirstPrompt(new AskWhatTreasure())
		            .withEscapeSequence( Managers.getLocalesConfig().getEnd() )
		            .withPrefix(new ConversationPrefix() {	 
		                @Override
		                public String getPrefix(ConversationContext arg0) { 
		                	return StringUtils.colorize( Managers.getLocalesConfig().getPrefix() ); 
		                }	 
		            //}).withInitialSessionData(map).withLocalEcho(true)
					}).withLocalEcho(true)
		            .buildConversation(this.player);
			
			conv.addConversationAbandonedListener(new ConversationAbandonedListener() {	 
		        @Override
		        public void conversationAbandoned(ConversationAbandonedEvent event)
		        {
		            if (event.gracefulExit())
		            {	            	
		            	
		            	Log.debug( "Graceful exit, infos about treasures found:" );
		            	Log.debug( treasure.toString() );		            	
		            	
		            }else{
		            	
		            	Log.debug("Chest edit failed");
	    				player.sendMessage( StringUtils.colorize( Managers.getLocalesConfig().getChest_failure_edit() ) );
		            	
		            }
		        }
		    });		    
		    
		    conv.begin();			
			
		}catch (Exception e){
			Log.severe("An error occured on ChestEditProcedure", e );			
		}		
		
	}

	
	
	/*
	 * First prompt
	 * Ask for a name
	 */
	public class AskWhatTreasure extends ValidatingPrompt {

		private Map<String,String> idToNameMap = new HashMap<String,String>();
		
		@Override
		public String getPromptText(ConversationContext arg0) {
			StringBuilder build = new StringBuilder();
			build.append( StringUtils.colorize( Managers.getLocalesConfig().getList() ) );
			build.append("\n");
			for( Entry<String, Object> entry : Managers.getCacheManager().getTreasures().entrySet() ){
				//String id = entry.getKey();
				TreasureChest tr = (TreasureChest) entry.getValue();
				build.append( tr.get_name() + "\n" );
				idToNameMap.put( tr.get_id(), tr.get_name() );
			}
			return build.toString();
			
		}
		
		@Override
		protected boolean isInputValid(ConversationContext context, String in) {
			for( Entry<String, String> entry : idToNameMap.entrySet() ){
				if( in.equalsIgnoreCase( entry.getValue() ) ){
					treasure = (TreasureChest) Managers.getCacheManager().get( entry.getKey() );
					return true;
				}
			}
			return false;
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {			
			return new ChangesMenu();			
		}
		
	}
	
	
	
	public class ChangesMenu extends ValidatingPrompt {

		private Map<Integer, String> idToAction = new HashMap<Integer, String>();
		
		public ChangesMenu(){
			idToAction.put(0, "cancel");
			idToAction.put(1, "name");
			idToAction.put(2, "onlyOnSurface");
			idToAction.put(3, "preserveContent");
		}
		
		@Override
		public String getPromptText(ConversationContext arg0) {
			
			StringBuilder build = new StringBuilder();
			build.append("Type id to edit:" + "\n");
			for( Entry<Integer, String> entry : idToAction.entrySet() ){
				build.append( ChatColor.GREEN + entry.getKey().toString() + "-" + ChatColor.YELLOW + entry.getValue() + ChatColor.GREEN + "\n");
			}
			return build.toString();
			//return BooTreasure.getConfigManager().get("messages.yml").getString("locales.create.chest.ask.name").replaceAll("&", "§");
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			
			if( idToAction.keySet().contains(Integer.parseInt(input) ) )
				return true;
			
			return false;
		}		

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {			
			
			int choice = Integer.parseInt(in);
			String action = idToAction.get(choice);
			
			Log.debug( in + " -> " + action );
					
			return Prompt.END_OF_CONVERSATION;			
		}

	}
	
	


	public class ChestEditProcedureException extends Exception {
		private static final long serialVersionUID = 1L;
		@Getter private Throwable throwable;
		public ChestEditProcedureException(String message, Throwable t) {
	        super(message);
	        this.throwable = t;
	    }		
	}

	
}
