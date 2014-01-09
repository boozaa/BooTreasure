package org.shortrip.boozaa.plugins.bootreasure;


import lombok.Getter;
import lombok.Setter;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "treasure")
public class TreasureDAO {

	// for QueryBuilder to be able to find the fields	
	public static final String UUID_DATE_FIELD_NAME 		= "uuid";
	public static final String NAME_FIELD_NAME 				= "name";
	public static final String ONLYONSURFACE_FIELD_NAME 	= "only_on_surface";
	public static final String PRESERVECONTENT_FIELD_NAME 	= "preserve_content";
	
	
	//@DatabaseField(generatedId = true)
	//@Getter private int id;	

	@DatabaseField(columnName = UUID_DATE_FIELD_NAME, canBeNull = false, id = true)
	@Getter private String uuid;
	
	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	@Getter @Setter private String name;
	
	@DatabaseField(columnName = ONLYONSURFACE_FIELD_NAME, canBeNull = false)
	@Getter @Setter private Boolean onlyOnSurface;

	@DatabaseField(columnName = PRESERVECONTENT_FIELD_NAME, canBeNull = false)
	@Getter @Setter private Boolean preserveContent;

	
	
	
	
	TreasureDAO(){
		
	}
	
	public TreasureDAO( String uuid, String name, Boolean onlyOnSurface, Boolean preserveContent ){
		this.uuid = uuid;
		this.name = name;
		this.onlyOnSurface = onlyOnSurface;
		this.preserveContent = preserveContent;
	}
	
	
	
	
}
