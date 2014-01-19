package org.shortrip.boozaa.plugins.bootreasure.configs;

public enum ConfigNodes {

	VERSION			("config.version"),
	DEBUG			("config.debug"),
	DATABASE_TYPE	("config.database"),
	MYSQL_HOST		("config.mysql.host"),
	MYSQL_PORT		("config.mysql.port"),
	MYSQL_DATABASE	("config.mysql.database"),
	MYSQL_USER		("config.mysql.user"),
	MYSQL_PASS		("config.mysql.pass");
	
	
	private final String node;
	
	private ConfigNodes(String node) {
		this.node = node;
	}
 
	public String getNode(){
		return node;
	}
	
	
}
