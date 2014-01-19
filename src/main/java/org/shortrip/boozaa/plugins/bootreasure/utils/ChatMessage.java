package org.shortrip.boozaa.plugins.bootreasure.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class ChatMessage {

	public static void broadcast(String message ){
		Bukkit.getServer().broadcastMessage(message);
	}
	
	public static void forPlayer(Player player, String message ){
		player.sendMessage(message);
	}
	
	/*
	public static void forGroup(List<String> groups, String message){
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			for( String group : groups ){
				if( BooTreasure.getPermission().getPermission().playerInGroup(player, group) ){
					player.sendMessage(message);
				}
			}			
		}
	}
	*/
	
}
