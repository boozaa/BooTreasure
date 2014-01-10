package org.shortrip.boozaa.plugins.bootreasure.dao;

import java.util.Date;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "events")
public class EventsDAO {

	
	public enum EventType{
		
		APPEAR("appear"), DISAPPEAR("disappear"), FOUND("found"), CREATED("created"), REMOVED("removed");
		 
		private String event;
		 
		private EventType(String e) {
			event = e;
		}
		 
		public String getType() {
			return event;
		}
		
		
	}
	
	public static final String TREASUREDAO_ID_FIELD_NAME 	= "treasure_uuid";
	public static final String EVENT_TYPE_FIELD_NAME 		= "event_type";
	public static final String DATE_FIELD_NAME 				= "timestamp";
	public static final String PLAYER_FIELD_NAME 			= "player";
	public static final String WORLD_FIELD_NAME 			= "world";
	public static final String X_FIELD_NAME 				= "x";
	public static final String Y_FIELD_NAME 				= "y";
	public static final String Z_FIELD_NAME 				= "z";
	
	
	@DatabaseField(generatedId = true)
	@Getter private int id;
	
	
	@DatabaseField(foreign = true, columnName = TREASUREDAO_ID_FIELD_NAME)
    private TreasureDAO treasureDAO;
	
	@DatabaseField(columnName = EVENT_TYPE_FIELD_NAME)
	@Getter @Setter private String event;
	
	@DatabaseField(columnName = DATE_FIELD_NAME)
	@Getter @Setter private Date timestamp;	

	@DatabaseField(columnName = PLAYER_FIELD_NAME, canBeNull = true)
	@Getter private String player;
	
	@DatabaseField(columnName = WORLD_FIELD_NAME, canBeNull = true)
	@Getter private String world;
	
	@DatabaseField(columnName = X_FIELD_NAME, canBeNull = true)
	@Getter private int x;
	
	@DatabaseField(columnName = Y_FIELD_NAME, canBeNull = true)
	@Getter private int y;
	
	@DatabaseField(columnName = Z_FIELD_NAME, canBeNull = true)
	@Getter private int z;
	
	
	EventsDAO(){
		
	}
	
	
	public EventsDAO( TreasureDAO treasureDAO, EventType type, Location location ){
		this.treasureDAO = treasureDAO;
		this.event = type.getType().toUpperCase();
		this.world = location.getWorld().getName();
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
		this.timestamp = new Date();	
		this.player = "";		
	}

	public EventsDAO( TreasureDAO treasureDAO, EventType type, String playerName, Location location ){
		this.treasureDAO = treasureDAO;
		this.event = type.getType().toUpperCase();
		this.world = location.getWorld().getName();
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
		this.timestamp = new Date();	
		this.player = playerName;		
	}

	public EventsDAO( TreasureDAO treasureDAO, EventType type, Player player, Location location ){
		this.treasureDAO = treasureDAO;
		this.event = type.getType().toUpperCase();
		this.world = location.getWorld().getName();
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
		this.timestamp = new Date();	
		this.player = player.getName();		
	}
	
	
	
}
