package org.shortrip.boozaa.plugins.bootreasure.managers.events.chests;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.TreasureDAO;
import org.shortrip.boozaa.plugins.bootreasure.managers.events.Events;
import org.shortrip.boozaa.plugins.bootreasure.treasures.TreasureChest;
import org.shortrip.boozaa.plugins.bootreasure.utils.Log;

import com.j256.ormlite.stmt.QueryBuilder;


public class TreasureChestAppearEvent extends Events {

	private Plugin plugin;
	
	public TreasureChestAppearEvent(Plugin plugin, final String id){
		
		super();
		this.plugin = plugin;
		
		Log.debug("TreasureChestAppearEvent event for id " + id);
		// On prend le Treasure dans le Cache
		if( BooTreasure.getCacheManager().exists(id)){	
			
			Log.debug("Treasure exists in cache: " + id);
			final TreasureChest t = (TreasureChest) BooTreasure.getCacheManager().get_treasureCache().getObject(id);
			if( t.get_found() == false ){
				
				Bukkit.getServer().getScheduler().runTask(this.plugin, new Runnable() {
					@Override
					public void run() {
						
						Log.debug("Name: " + t.get_name());
						try {
							t.appear();
							t.announceAppear();
							
							// DAO
							// check if uuid exists
							QueryBuilder<TreasureDAO, String> statementBuilder = BooTreasure.get_treasureDAO().queryBuilder();
							statementBuilder.where().like(TreasureDAO.UUID_DATE_FIELD_NAME, t.get_id());
							List<TreasureDAO> treasuresDAO = BooTreasure.get_treasureDAO().query(statementBuilder.prepare());
							if( treasuresDAO.isEmpty() == false){
								for( TreasureDAO trDAO : treasuresDAO ){
									trDAO.setAppearDate(new Date() );
									BooTreasure.get_treasureDAO().update(trDAO);
								}
							}else{
								// Add an entry in the database
								TreasureDAO tdao = new TreasureDAO( t.get_id(), t.get_name(), t.get_onlyonsurface(), t.get_preservecontent(), t.get_world(), t.get_x(), t.get_y(), t.get_z() );
								BooTreasure.get_treasureDAO().create( tdao );
							}
							
							
							
							
						} catch (Exception e) {
							e.printStackTrace();
							StringBuilder build = new StringBuilder();
							String nl = System.getProperty("line.separator");
							build.append( "TreasureChestAppearEvent()" );
							build.append(nl);
							build.append( "Id: " + t.get_id() );
							build.append(nl);
							build.append( "Inventory: " + Arrays.toString(t.get_inventory()) );
							Log.warning(build.toString() + "\n" + e);
						}
						
					} }); 
				
			}			
			
		}else{
			Log.debug("Treasure didn't exists in cache: " + id);
		}
		        
	}
	
}
