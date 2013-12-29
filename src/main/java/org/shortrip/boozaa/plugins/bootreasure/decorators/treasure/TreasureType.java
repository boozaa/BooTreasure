package org.shortrip.boozaa.plugins.bootreasure.decorators.treasure;

public enum TreasureType {

	CHEST("chest"),
	SCHEMATIC("schematic");
	
	
	private final String name;
	
	
	TreasureType(String name){
		this.name = name;
	}
	
	public String getName(){ return this.name; }
	
	
}
