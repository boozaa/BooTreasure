/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

/**
 * @author boozaa
 *
 * BooTreasure
 */
public class BukkitSerializer {

	
	public static void serializeToFile( Object obj, String path ){
		
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
	
	
	public static Object deserializeFromFile(File file){
		
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
	
	
	
}
