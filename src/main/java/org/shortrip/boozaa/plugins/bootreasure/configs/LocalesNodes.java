package org.shortrip.boozaa.plugins.bootreasure.configs;

public enum LocalesNodes {

	
	YES("true"),
	NO("false"),
	EXIT("exit"),
	END("end");
	
	
	private final String node;
	
	private LocalesNodes(String node) {
		this.node = node;
	}
 
	public String getConfigNode() {
		return node;
	}
	
		
	
	public interface ConfigNodeInterface {
        String getConfigNode();    
    }
	
	
	public enum CreateChest implements ConfigNodeInterface {
		
		CHEST_CREATE_PREFIX					("locales.create.chest.prefix"),
		CHEST_CREATE_ASK_NAME				("locales.create.chest.ask.name"),
		CHEST_CREATE_ASK_CRON				("locales.create.chest.ask.pattern"),
		CHEST_CREATE_ASK_DURATION			("locales.create.chest.ask.duration"),
		CHEST_CREATE_ASK_WORLD				("locales.create.chest.ask.world"),
		CHEST_CREATE_ASK_INFINITE			("locales.create.chest.ask.infinite"),
		CHEST_CREATE_ASK_SURFACE			("locales.create.chest.ask.onlyonsurface"),
		CHEST_CREATE_ASK_PRESERVE			("locales.create.chest.ask.preservecontent"),
		CHEST_CREATE_ASK_WAITINGEND			("locales.create.chest.ask.waitingend"),
		CHEST_CREATE_ASK_ALLOWEDSID			("locales.create.chest.ask.allowedids"),
		CHEST_CREATE_SUCCESS				("locales.create.chest.success"),
		CHEST_CREATE_APPEAR					("locales.create.chest.ask.appearmessage"),
		CHEST_CREATE_DISAPPEAR				("locales.create.chest.ask.disappearmessage"),
		CHEST_CREATE_FOUND					("locales.create.chest.ask.foundmessage");
		
		
		private final String node;
		
		private CreateChest(String node) {
			this.node = node;
		}
	 
		@Override
		public String getConfigNode() {
			return node;
		}
		
	}
	
	public enum EditChest implements ConfigNodeInterface {
		
		CHEST_EDIT_PREFIX			("locales.edit.chest.prefix"),
		CHEST_EDIT_LIST				("locales.edit.chest.ask.listalltreasures"),
		CHEST_EDIT_ASK_NAME			("locales.edit.chest.ask.name"),
		CHEST_EDIT_ASK_CRON			("locales.edit.chest.ask.pattern"),
		CHEST_EDIT_ASK_DURATION		("locales.edit.chest.ask.duration"),
		CHEST_EDIT_ASK_INFINITE		("locales.edit.chest.ask.infinite"),
		CHEST_EDIT_ASK_SURFACE		("locales.edit.chest.ask.onlyonsurface"),
		CHEST_EDIT_ASK_PRESERVE		("locales.edit.chest.ask.preservecontent"),
		CHEST_EDIT_ASK_WAITINGEND	("locales.edit.chest.ask.waitingend"),
		CHEST_EDIT_ASK_ALLOWEDSID	("locales.edit.chest.ask.allowedids"),
		CHEST_EDIT_SUCCESS			("locales.edit.chest.success");
		
		private final String node;
		
		private EditChest(String node) {
			this.node = node;
		}
	 
		@Override
		public String getConfigNode() {
			return node;
		}
		
	}
	

	public enum DeleteChest implements ConfigNodeInterface {
		
		CHEST_DELETE_PREFIX			("locales.create.chest.prefix"),
		CHEST_DELETE_LIST			("locales.delete.chest.ask.listalltreasures");
		
		private final String node;
		
		private DeleteChest(String node) {
			this.node = node;
		}
	 
		@Override
		public String getConfigNode() {
			return node;
		}
		
	}
	
}
