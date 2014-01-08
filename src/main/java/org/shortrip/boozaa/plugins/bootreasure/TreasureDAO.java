package org.shortrip.boozaa.plugins.bootreasure;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "treasure")
public class TreasureDAO {

	// for QueryBuilder to be able to find the fields	
	public static final String UUID_DATE_FIELD_NAME 		= "uuid";
	public static final String APPEAR_DATE_FIELD_NAME 		= "appear_date";
	public static final String DISAPPEAR_DATE_FIELD_NAME 	= "disappear_date";
	public static final String NAME_FIELD_NAME 				= "name";
	public static final String ONLYONSURFACE_FIELD_NAME 	= "only_on_surface";
	public static final String PRESERVECONTENT_FIELD_NAME 	= "preserve_content";
	public static final String WORLD_FIELD_NAME 			= "world";
	public static final String X_FIELD_NAME 				= "x";
	public static final String Y_FIELD_NAME 				= "y";
	public static final String Z_FIELD_NAME 				= "z";
	
	
	@DatabaseField(generatedId = true)
	@Getter private int id;	

	@DatabaseField(columnName = UUID_DATE_FIELD_NAME, canBeNull = false)
	@Getter private String uuid;
	
	@DatabaseField(columnName = APPEAR_DATE_FIELD_NAME)
	@Getter @Setter private Date appearDate;

	@DatabaseField(columnName = DISAPPEAR_DATE_FIELD_NAME)
	@Getter @Setter private Date disappearDate;
	
	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	@Getter private String name;
	
	@DatabaseField(columnName = ONLYONSURFACE_FIELD_NAME, canBeNull = false)
	@Getter private Boolean onlyOnSurface;

	@DatabaseField(columnName = PRESERVECONTENT_FIELD_NAME, canBeNull = false)
	@Getter private Boolean preserveContent;

	@DatabaseField(columnName = WORLD_FIELD_NAME, canBeNull = false)
	@Getter private String world;
	@DatabaseField(columnName = X_FIELD_NAME, canBeNull = false)
	@Getter private int x;
	@DatabaseField(columnName = Y_FIELD_NAME, canBeNull = false)
	@Getter private int y;
	@DatabaseField(columnName = Z_FIELD_NAME, canBeNull = false)
	@Getter private int z;
	
	
	
	
	TreasureDAO(){
		
	}
	
	public TreasureDAO( String uuid, String name, Boolean onlyOnSurface, Boolean preserveContent, String world, int x, int y, int z ){
		this.uuid = uuid;
		this.name = name;
		this.onlyOnSurface = onlyOnSurface;
		this.preserveContent = preserveContent;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.appearDate = new Date();
		this.disappearDate = new Date(0000000000);
	}
	
	
	
	
}
