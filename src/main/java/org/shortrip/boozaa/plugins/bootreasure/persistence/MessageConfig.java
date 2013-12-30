/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;
import org.shortrip.boozaa.plugins.bootreasure.Log;

/**
 * @author boozaa
 *
 * BooTreasure
 */
public class MessageConfig extends Configuration {

	private List<String> messages;
	private Boolean updated;
	private Map<String, Object> map = new HashMap<String, Object>();
	
	
	
	public MessageConfig(String sourcepath) {
		
		super(sourcepath);
		messages = new ArrayList<String>();
		updated = false;
		
		if( !this.exists() ){ this.save();}
		this.load();
		
		this.map.put("locales.commands.agree", 						"oui");
		this.map.put("locales.commands.disagree", 					"non");
		this.map.put("locales.commands.exit", 						"exit");
		this.map.put("locales.commands.end", 						"/end");
		
		this.map.put("locales.create.prefix", 						"&2[BooTreasure] ");
		this.map.put("locales.create.success", 						"&3Trésor configuré avec succés");
		this.map.put("locales.create.error", 						"&4Une erreur est survenue");

		this.map.put("locales.create.chest.intro", 					"&3Création d'un trésor");
		this.map.put("locales.create.chest.ask.name", 				"&3Quel &enom &3donner à ce trésor ?");
		this.map.put("locales.create.chest.ask.pattern", 			"&3Indiques le &ecron pattern&3");
		this.map.put("locales.create.chest.ask.world", 				"&3Indiques le &eMonde&3 ");
		this.map.put("locales.create.chest.ask.infinite", 			"&3Apparition &einfinie&3 ?");
		this.map.put("locales.create.chest.ask.onlyonsurface", 		"&3Uniquement &een surface&3 ?");
		this.map.put("locales.create.chest.ask.preservecontent", 	"&3Conserve son &econtenu&3 ?");
		this.map.put("locales.create.chest.ask.allowedids", 		"&3Blocks particuliers");
		this.map.put("locales.create.chest.ask.waitingend", 		"&3Remplis ce trésor et pour finir tapes: &7");
		this.map.put("locales.create.chest.ask.duration", 			"&3Combien de &etemps&3 doit rester le trésor ?");
		
		this.enable();	
		
		if( updated ) {	
       	 this.save();
       	 this.load();
       	Log.info("Config - " + getName() + " " + BooTreasure.getPluginVersion() + " messages.yml - new options");
            for(String str : messages){
            	Log.info("messages.yml - " + str);
            }
        }
		
	}
	
	
	public Boolean enable(){
		
		for(Entry<String, Object> entry : map.entrySet()) {
			String cle = entry.getKey();
			Object valeur = entry.getValue();			    
			if( !this.contains(cle) ){
				this.set(cle, valeur);
				updated = true;
				messages.add(cle);
			}				
		}		
		return true;
		
	}
	
	
}
