package org.shortrip.boozaa.plugins.bootreasure.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class BukkitSerializer {
	
	public static byte[] serialize( Object obj ){
		
		byte[] objectAsBytes = null;
		ByteArrayOutputStream baos = null;
		try {
			
			baos = new ByteArrayOutputStream();		
			BukkitObjectOutputStream boos = new BukkitObjectOutputStream(baos);
			boos.writeObject(obj);			
			objectAsBytes = baos.toByteArray();
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

		return objectAsBytes;
		
	}
	
	
	public static Object unserialize(byte[] bytes){
		
		//File file = new File(BooTreasure.getInstance().getDataFolder() + File.separator + "lost+found" + File.separator + "item.serialized");
		ByteArrayInputStream bai = null;
		Object item = null;
		
		try {
			
			bai = new ByteArrayInputStream(bytes);
			BukkitObjectInputStream bois = new BukkitObjectInputStream(bai);
			item = bois.readObject();
			bois.close();
 
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (bai != null)
					bai.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		

		return item;
		
		
	}
	
	
}
