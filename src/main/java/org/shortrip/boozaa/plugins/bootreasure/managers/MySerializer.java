package org.shortrip.boozaa.plugins.bootreasure.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;

public class MySerializer extends Manager {
	
	@SuppressWarnings("unused")
	private Plugin plugin;
	
	public MySerializer(BooTreasure booTreasure) {
		this.plugin = booTreasure;
	}
	
	
	public void serializeBukkitObjectToFile( Object obj, String path ){
		
		FileOutputStream baos = null;
		
		try {
			
			// ItemStack
			//baos = new FileOutputStream(BooTreasure.getInstance().getDataFolder() + File.separator + "lost+found" + File.separator + "item.serialized");
			baos = new FileOutputStream(path);			
			BukkitObjectOutputStream boos = new BukkitObjectOutputStream(baos);
			boos.writeObject(obj);
			boos.close();
			
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	
	public Object unserializeBukkitObjectFromFile(File file){
		
		//File file = new File(BooTreasure.getInstance().getDataFolder() + File.separator + "lost+found" + File.separator + "item.serialized");
		FileInputStream fis = null;
		Object item = null;
		
		try {
			
			fis = new FileInputStream(file); 
			System.out.println("Total file size to read (in bytes) : " + fis.available()); 						
			BukkitObjectInputStream bois = new BukkitObjectInputStream(fis);
			item = bois.readObject();
			bois.close();
				
 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		

		return item;
		
		
	}


	@Override
	public void onDisable() {
		
	}
	

}
