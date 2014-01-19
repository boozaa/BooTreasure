package org.shortrip.boozaa.plugins.bootreasure.configs;


public enum PermsNodes {

	PLAYER_CAN_SEE_TREASURE("bootreasure.player.can.see"),
	PLAYER_CAN_BEINFORMED("bootreasure.player.can.beinformed");
	
	private final String perm;
	
	private PermsNodes(String perm) {
		this.perm = perm;
	}
 
	public String getNode() {
		return perm;
	}
	
	
}
