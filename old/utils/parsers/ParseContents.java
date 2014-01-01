package org.shortrip.boozaa.plugins.bootreasure.utils.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.shortrip.boozaa.plugins.bootreasure.old.Const;


public class ParseContents {

	private Chest chest;
	private List<ItemStack> lastInventory = new ArrayList<ItemStack>();
	private ConfigurationSection conf;
	
	
	public ParseContents(Chest chest, List<ItemStack> lastInventory2, ConfigurationSection conf){
		this.chest = chest;
		this.lastInventory = lastInventory2;
		this.conf = conf;
	}	

	
	public void parse(){		

		// On ajoute l'ancien inventaire si besoin
		if( conf.getBoolean(Const.TASK_BASICS_PRESERVECONTENT) ){			
			if( !this.lastInventory.isEmpty() ){
				for( ItemStack item : this.lastInventory ){
					// On doit conserver cet intemstack
					addItemFix(chest.getInventory(), item.getTypeId(), item.getAmount(), item.getDurability());
				}
			}			
		}
		
		ItemStack item;
		
		if( conf.get(Const.TASK_CONTENTS_ITEMS) != null ){
			List<String> newItems = conf.getStringList(Const.TASK_CONTENTS_ITEMS);    	
	    	for( String p : newItems) {
	    		item = processItem(p);		    		
	    		// On stocke dans l'inventaire
	    		if( item != null ){		    			
	    			addItemFix(chest.getInventory(), item.getTypeId(), item.getAmount(), item.getDurability());
	    		}	    		
	    	}
		}
		
		if ( conf.get(Const.TASK_CONTENTS_RANDOMS) != null ){
			List<String> newItems = conf.getStringList(Const.TASK_CONTENTS_RANDOMS);  
			item = processItem(newItems.get( (int)(Math.random()*newItems.size() ) ) );
			// On stocke dans l'inventaire
    		if( item != null  ){		    			
    			addItemFix(chest.getInventory(), item.getTypeId(), item.getAmount(), item.getDurability());
    		}		
		}
		
		
		
	}
	
	
	private void addItemFix(Inventory inv, int ID, int amount, short dur) {

        if (inv.contains(ID)) {
            
        	HashMap<Integer, ? extends ItemStack> invItems = inv.all(ID);

            int restAmount = amount;
            for (Map.Entry<Integer, ? extends ItemStack> entry : invItems.entrySet()) {

                int index = entry.getKey();
                ItemStack item = entry.getValue();
                int stackAmount = item.getAmount();
              
                if (stackAmount < 64) {
                    // Add to stack
                    int canGiveAmount = 64 - stackAmount;
                    int giveAmount;

                    if (canGiveAmount >= restAmount) {
                        giveAmount = restAmount;
                        restAmount = 0;
                    } else {
                        giveAmount = canGiveAmount;
                        restAmount = restAmount - giveAmount;
                    }

                    inv.setItem(index, new ItemStack(ID, stackAmount + giveAmount, dur));

                }
                
            }
            // If there is still a rest, add the rest to the inventory
            if (restAmount > 0) {
                int emptySlot = inv.firstEmpty();
                inv.setItem(emptySlot, new ItemStack(ID, restAmount, dur));
            }
        } else {
            // Standard usage of addItem
        	inv.addItem(new ItemStack(ID, amount, dur));
        }
    }
	
	
	
	
	private ItemStack processItem(String p){
		
		ItemStack item;		
		String[] arr = p.split(":");
		
		if( p.isEmpty() ){
			return null;
		}
		
		
		// Construction itemId/damage:qty:enchantId:multiplier
		if( p.contains(":")) {			
			if( arr.length >= 2 ){ // Au moins itemId/damage:qty				
				// Damage
				if( arr[0].contains("/")){
					String[] itemWithDamage = arr[0].split("/");					
					int itemId = Integer.parseInt(itemWithDamage[0]);
					int damage = Integer.parseInt(itemWithDamage[1]);
					int qty = Integer.parseInt(arr[1]);
					// On pr�pare l'item tel quel au player en stack
	    			item = new ItemStack(itemId, qty, (short) damage);	    								
				}else{
					// Pas de damage
					int itemId = Integer.parseInt(arr[0]); 
	    			int qty = Integer.parseInt(arr[1]);	    			
	    			// On pr�pare l'item tel quel au player en stack
	    			item = new ItemStack(itemId,qty);	    			
				}
				
				
				// Avec enchant
    			if( arr.length == 4) {	    				
    				int enchantId = Integer.parseInt(arr[2]); 
		    		int multiplier = Integer.parseInt(arr[3]);
    				item.addEnchantment(Enchantment.getById(enchantId), multiplier);
	    			// On donne l'item avec enchantement
	    			return item;		    			
    			}
				
    			// On donne l'item
    			return item;	    				
    			
			}				
		
		}
		
		// item unique avec Damage ?
		if( p.contains("/")){					
			
			String[] itemWithDamage = p.split("/");
			int itemId = Integer.parseInt(itemWithDamage[0]);
			int damage = Integer.parseInt(itemWithDamage[1]);
			
			// On pr�pare l'item tel quel au player en stack
			item = new ItemStack(itemId, 1, (short) damage);
			
		}else{
			
			// On pr�pare l'item tel quel au player en stack
			item = new ItemStack( Integer.parseInt(p) );
			
		}
				
		// On donne l'item tel quel au player
		return item;
		
	}
	
	
	
	@SuppressWarnings("unused")
	private Boolean isInventoryEmptyOrNull( ItemStack[] inventaire ){
		
		if( inventaire == null ){
			return true;
		}
		
		for(ItemStack item : inventaire)
		{
		    if(item != null)
		      return false;
		}
		return true;
	}
	
	
	@SuppressWarnings("unused")
	private static Boolean launchTheDice(int max, int proba){
		Random rand = new Random();
		int randomNumber = rand.nextInt(max); // un random  dans la limite de max
		if(randomNumber <= proba){			
			return true;
		}
		return false;
	}






	
	
}
