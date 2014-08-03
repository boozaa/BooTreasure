package org.shortrip.boozaa.plugins.bootreasure.managers;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.Managers;
import org.shortrip.boozaa.plugins.bootreasure.dao.EventsDAO;
import org.shortrip.boozaa.plugins.bootreasure.dao.EventsDAO.EventType;
import org.shortrip.boozaa.plugins.bootreasure.dao.TreasureDAO;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class MyDatabase {

	
	@SuppressWarnings("unused")
	private Plugin plugin;
	private String databaseUrl;
	private ConnectionSource _connectionSource;
	private Dao<TreasureDAO, String> _treasureDAO;
	private Dao<EventsDAO, String> _eventsDAO;
	
	
	public MyDatabase(Plugin plugin) throws SQLException{
		
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
		System.setProperty(LocalLog.LOCAL_LOG_FILE_PROPERTY, "plugins/BooTreasure/ormlite_log.out");
		
		this.plugin = plugin;
		
		Log.debug("Search for database storage type");
			
		switch( Managers.getMainConfig().getDatabaseType() ) {		
			case MYSQL:
				this.databaseUrl = "jdbc:mysql://"+ Managers.getMainConfig().getDatabase_host() + ":" + Managers.getMainConfig().getDatabase_port() + "/" + Managers.getMainConfig().getDatabase_name();
				this._connectionSource = new JdbcConnectionSource(databaseUrl,Managers.getMainConfig().getDatabase_user(),Managers.getMainConfig().getDatabase_password());
				Log.info("Connected to MySQL database");
				break;
			case SQLITE:
				this.databaseUrl = "jdbc:sqlite:" + plugin.getDataFolder() + File.separator + "bootreasure.db";
				this._connectionSource = new JdbcConnectionSource(databaseUrl);
				Log.info("Connected to SQLite database");
				break;
			default:
				this.databaseUrl = "jdbc:sqlite:" + plugin.getDataFolder() + File.separator + "bootreasure.db";
				this._connectionSource = new JdbcConnectionSource(databaseUrl);
				Log.info("Connected to SQLite database");
				break;		
		}				
		this.initDatabase();
	}
	
	
	private void initDatabase() throws SQLException{
    	
		// Le DAO pour treasure
		_treasureDAO = DaoManager.createDao(_connectionSource, TreasureDAO.class);
		// Le DAO pour events
		_eventsDAO = DaoManager.createDao(_connectionSource, EventsDAO.class);
			
		// Create the table		
        TableUtils.createTableIfNotExists(_connectionSource, TreasureDAO.class);
        TableUtils.createTableIfNotExists(_connectionSource, EventsDAO.class);
	    				
    }
	
	public void addTreasureToDatabase( TreasureChest treasure ) throws SQLException{
		TreasureDAO tdao = new TreasureDAO( treasure.get_id(), treasure.get_name(), treasure.get_onlyonsurface(), treasure.get_preservecontent() );
		// Create new TreasureDAO in database
		this._treasureDAO.create( tdao );							
	}
	
	
	public void addEventToDatabase( TreasureChest treasure, EventType type ) throws SQLException{
		// Get the associated TreasureDAO
		TreasureDAO trDAO = getTreasureDAO( treasure.get_id() );
		if( trDAO != null ){
			// Create new event for this entry
			EventsDAO event = new EventsDAO( trDAO, type, treasure.get_block().getLocation() );
			// Create this entry
			this._eventsDAO.create(event);
		}else{
			// we must store a TreasureDAO first
			addTreasureToDatabase( treasure );
			// Create new event for this entry
			EventsDAO event = new EventsDAO( trDAO, type, treasure.get_block().getLocation() );
			// Create this entry
			this._eventsDAO.create(event);
		}
	}
	
	public void addEventToDatabase( TreasureChest treasure, Player player, EventType type ) throws SQLException{
		// Get the associated TreasureDAO
		TreasureDAO trDAO = getTreasureDAO( treasure.get_id() );
		if( trDAO != null ){
			// Create new event for this entry
			EventsDAO event = new EventsDAO( trDAO, type, player, treasure.get_block().getLocation() );
			// Create this entry
			this._eventsDAO.create(event);
		}else{
			// we must store a TreasureDAO first
			addTreasureToDatabase( treasure );
			// Create new event for this entry
			EventsDAO event = new EventsDAO( trDAO, type, player, treasure.get_block().getLocation() );
			// Create this entry
			this._eventsDAO.create(event);
		}	
	}
	
	
	public TreasureDAO getTreasureDAO( String uuid ) throws SQLException{		
		QueryBuilder<TreasureDAO, String> statementBuilder = this._treasureDAO.queryBuilder();
		statementBuilder.where().like( TreasureDAO.UUID_DATE_FIELD_NAME, uuid );
		List<TreasureDAO> treasuresDAO = this._treasureDAO.query(statementBuilder.prepare());
		if( treasuresDAO.isEmpty() == false){
			// Here an entry in database exists for this uuid
			for( TreasureDAO trDAO : treasuresDAO ){
				return trDAO;
			}
		}
		return null;
	}
	
	
	
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

}
