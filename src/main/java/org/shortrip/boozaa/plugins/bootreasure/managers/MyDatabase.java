package org.shortrip.boozaa.plugins.bootreasure.managers;

import java.sql.SQLException;
import java.util.List;


import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.EventsDAO;
import org.shortrip.boozaa.plugins.bootreasure.EventsDAO.EventType;
import org.shortrip.boozaa.plugins.bootreasure.TreasureDAO;
import org.shortrip.boozaa.plugins.bootreasure.managers.configuration.Configuration;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class MyDatabase extends Manager {

	@SuppressWarnings("unused")
	private Plugin plugin;
	private String databaseUrl;
	private ConnectionSource _connectionSource;
	private Dao<TreasureDAO, String> _treasureDAO;
	private Dao<EventsDAO, String> _eventsDAO;
	
	
	public MyDatabase(Plugin plugin) throws SQLException{
		this.plugin = plugin;
		
		Log.debug("Search for database storage type");
		
		Configuration config = BooTreasure.getConfigManager().get("config.yml");
		if( config.contains("config.database") ){
			
			Log.debug("The config have a 'config.database' node");
			
			String dbType = config.getString("config.database");			

			Log.debug("Database type selected: " + dbType);
			
			if( dbType.equalsIgnoreCase("sqlite") ){

				Log.debug("Connection to: " + dbType);
				
				databaseUrl = "jdbc:sqlite:plugins/BooTreasure/bootreasure.db";
				_connectionSource = new JdbcConnectionSource(databaseUrl);
				Log.info("Connected to SQLite database");
				
			}else if( dbType.equalsIgnoreCase("mysql") ){

				Log.debug("Connection to: " + dbType);
				
				String host = config.getString("config.mysql.host");
				int port = config.getInt("config.mysql.port");
				String database = config.getString("config.mysql.database");
				String user = config.getString("config.mysql.user");
				String pass = config.getString("config.mysql.pass");
				databaseUrl = "jdbc:mysql://"+ host + ":" + port + "/" + database;
				_connectionSource = new JdbcConnectionSource(databaseUrl,user,pass);
				Log.info("Connected to MySQL database");
				
			}
			
		}
		this.initDatabase();
	}
	
	
	private void initDatabase() throws SQLException{
    	
		// Le DAO pour treasure
		_treasureDAO = DaoManager.createDao(_connectionSource, TreasureDAO.class);
		// Le DAO pour events
		_eventsDAO = DaoManager.createDao(_connectionSource, EventsDAO.class);

		// Create the table
        TableUtils.createTable(_connectionSource, TreasureDAO.class);
        TableUtils.createTable(_connectionSource, EventsDAO.class);
			
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
		}	
	}
	
	
	private TreasureDAO getTreasureDAO( String uuid ) throws SQLException{		
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
	
	
	
	
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

}
