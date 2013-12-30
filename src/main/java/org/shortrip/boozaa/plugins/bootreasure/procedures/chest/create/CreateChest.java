package org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.Const;
import org.shortrip.boozaa.plugins.bootreasure.Log;
import org.shortrip.boozaa.plugins.bootreasure.decorators.treasure.treasures.ChestTreasure;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest.AskAllowedIds;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest.AskCron;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest.AskDuration;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest.AskInfinite;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest.AskOnlyOnSurface;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest.AskPreserveContent;
import org.shortrip.boozaa.plugins.bootreasure.procedures.chest.create.chest.AskWorld;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.CronPatternPrompt;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.MaterialPrompt;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.NamePrompt;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.WaitingEndPrompt;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.WorldPrompt;
import org.shortrip.boozaa.plugins.bootreasure.procedures.prompts.YesNoPrompt;


public class CreateChest implements Runnable {

	private volatile ChestTreasure _treasure;
	private Plugin _plugin;
	private Player _player;
	
	
	public CreateChest( Plugin plugin, Player p ){
		
		//this._treasure = new ChestTreasure();		
		this._plugin = plugin;
		this._player = p;		
		Location chestLoc = this._player.getLocation().toVector().add(this._player.getLocation().getDirection().multiply(1)).toLocation(this._player.getWorld());
		chestLoc.setY(chestLoc.getY()+1);
		this._treasure = new ChestTreasure(chestLoc);
		// Lancement de la procédure
		//this.run();			
		
	}
	
	
	
	@Override
	public void run() {
		
		this._treasure.chestAppear();
		
		/*
		 * AskName()->AskCron()->AskDuration()->AskInfinite()->AskWorld()->AskOnlyOnSurface()->AskPreserveContent()->AskAllowedIds()->WaitingEndPrompt
		 */
		
		
		ConversationFactory factory = new ConversationFactory(_plugin);
		final Map<Object, Object> map = new HashMap<Object, Object>();
		
		// Le treasure
		map.put( Const.TREASURE, 								this._treasure ); 								// Cache contenant les infos du trésor saisis par joueur
		
		Conversation conv = factory
	            .withFirstPrompt(new AskName())
	            .withEscapeSequence(Const.CMD_END)
	            .withPrefix(new ConversationPrefix() {	 
	                @Override
	                public String getPrefix(ConversationContext arg0) { return Const.CREATE_PREFIX + System.getProperty("line.separator"); }	 
	            }).withInitialSessionData(map).withLocalEcho(true)
	            .buildConversation(this._player);
	    
	    conv.addConversationAbandonedListener(new ConversationAbandonedListener() {	 
	        @Override
	        public void conversationAbandoned(ConversationAbandonedEvent event)
	        {
	            if (event.gracefulExit())
	            {
	            	Log.debug("Graceful exit from CreateChest procedure");
	            }
	        }
	    });
	    
	    conv.begin();
		
	}

	



	class AskName extends NamePrompt {
	
		private ChestTreasure _treasure;
		
		@Override
		public String getPromptText(ConversationContext context) {
			
			//if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){			
				this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);			
				if( context.getSessionData(Const.CREATE_CHEST_ASK_NAME) != null ){
					// On demande le nom de ce trésor
					return context.getSessionData(Const.CREATE_CHEST_ASK_NAME).toString();
				}			
			//}
			return null;
			
		}
		
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			// Le nom saisi est bien valide
	    	context.setSessionData( Const.NAME, in );
	    	// Récuperation de données
	    	this._treasure.set_name(in);   	
	    	return new AskCron(); 
		}
		
	}




	class AskCron extends CronPatternPrompt {
	
		private ChestTreasure _treasure;
		
		@Override
		public String getPromptText(ConversationContext context) {
			
			//if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
				this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
				if( context.getSessionData(Const.CREATE_CHEST_ASK_PATTERN) != null ){
					// On demande le cron pattern
					return context.getSessionData(Const.CREATE_CHEST_ASK_PATTERN).toString();
				}			
			//}
			return null;
			
		}
		
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			// Le nom saisi est bien valide
	    	context.setSessionData( Const.PATTERN, in );
	    	// Récuperation de données
	    	this._treasure.set_pattern(in);   	
	    	return new AskInfinite(); 
		}	
			
	}



	class AskInfinite extends YesNoPrompt {
	
		private ChestTreasure _treasure;
		
		@Override
		public String getPromptText(ConversationContext context) {
			
			//if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
				this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
				if( context.getSessionData(Const.CREATE_CHEST_ASK_INFINITE) != null ){
					// On demande si infini
					return context.getSessionData(Const.CREATE_CHEST_ASK_INFINITE).toString();
				}			
			//}
			return null;
			
		}
	
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			// Le nom saisi est bien valide
	    	context.setSessionData( Const.INFINITE, in );
	    	// Récuperation de données
	    	if( in.equalsIgnoreCase(Const.CMD_YES) || in.equalsIgnoreCase(Const.CMD_YES.substring(1, 2)) ){
	    		this._treasure.set_infinite(true);
	    	}else{
	    		this._treasure.set_infinite(false);
	    	}    	    	
	    	return new AskWorld(); 
		}
		
	}



	class AskWorld extends WorldPrompt {
	
		private ChestTreasure _treasure;
		
		@Override
		public String getPromptText(ConversationContext context) {
	
			//if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
				this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
	
				if( context.getSessionData(Const.CREATE_CHEST_ASK_WORLD) != null ){
					// On demande le Monde
					StringBuilder str = new StringBuilder();
					str.append(context.getSessionData(Const.CREATE_CHEST_ASK_WORLD).toString() + ": " + System.getProperty("line.separator")  );
					for( World w : this._worlds ){
						str.append(w.getName() + " ");
					}		
					return str.toString();
				}
							
			//}
			return null;
			
		}
	
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			// Le nom saisi est bien valide
	    	context.setSessionData( Const.WORLD, in );
	    	// Récuperation de données
	    	this._treasure.set_world(in);  	    	
	    	return new AskOnlyOnSurface(); 
		}
		
	}


	class AskOnlyOnSurface extends YesNoPrompt {
	
		private ChestTreasure _treasure;
		
		@Override
		public String getPromptText(ConversationContext context) {
	
			//if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
				this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
				if( context.getSessionData(Const.CREATE_CHEST_ASK_ONLYONSURFACE) != null ){
					// On demande si ONLYONSURFACE
					return context.getSessionData(Const.CREATE_CHEST_ASK_ONLYONSURFACE).toString();
				}			
			//}
			return null;
			
		}
		
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			// Le nom saisi est bien valide
	    	context.setSessionData( Const.INFINITE, in );
	    	// Récuperation de données
	    	if( in.equalsIgnoreCase(Const.CMD_YES) || in.equalsIgnoreCase(Const.CMD_YES.substring(1, 2)) ){
	    		this._treasure.set_onlyonsurface(true);
	    	}else{
	    		this._treasure.set_onlyonsurface(false);
	    	}    	    	
	    	return new AskPreserveContent(); 
		}
		
	}



	class AskPreserveContent extends YesNoPrompt {
	
		private ChestTreasure _treasure;
		
		@Override
		public String getPromptText(ConversationContext context) {
	
			//if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
				this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
	
				if( context.getSessionData(Const.CREATE_CHEST_ASK_PRESERVECONTENT) != null ){
					// On demande si PRESERVECONTENT
					return context.getSessionData(Const.CREATE_CHEST_ASK_PRESERVECONTENT).toString();
				}			
			//}
			return null;
			
		}
			
		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String in) {
			// Le nom saisi est bien valide
	    	context.setSessionData( Const.PRESERVECONTENT, in );
	    	// Récuperation de données
	    	if( in.equalsIgnoreCase(Const.CMD_YES) || in.equalsIgnoreCase(Const.CMD_YES.substring(1, 2)) ){
	    		this._treasure.set_preservecontent(true);
	    	}else{
	    		this._treasure.set_preservecontent(false);
	    	}    	    	
	    	return new AskAllowedIds(); 
		}
		
	}



	class AskAllowedIds extends MaterialPrompt {
	
		private List<Material> _materials;
		private ChestTreasure _treasure;
		
		
		public AskAllowedIds(){
			this._materials = new ArrayList<Material>();
		}
			
		@Override
		public String getPromptText(ConversationContext context) {
	
			//if( context.getSessionData(Const.TREASURE) instanceof ChestTreasure ){
				this._treasure = (ChestTreasure) context.getSessionData(Const.TREASURE);
	
				if( context.getSessionData(Const.CREATE_CHEST_ASK_ALLOWEDIDS) != null ){
					// On demande les ids socles si besoin
					return context.getSessionData(Const.CREATE_CHEST_ASK_ALLOWEDIDS).toString();
				}			
			//}
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
	    		return new WaitingEndPrompt(Const.CREATE_CHEST_WAITINGEND, Const.CREATE_SUCCESS);
			}
	    	
	    	// On boucle pour attendre les prochains blocks
	    	return this;
	    	
		}
		
	}


	
	
}




