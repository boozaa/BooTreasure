package org.shortrip.boozaa.plugins.bootreasure.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;


public class Utils {

	public static Boolean isInt( String chaine ){		
		for(int i=0; i<chaine.length(); i++)
		{
			if(!Character.isDigit(chaine.charAt(i))){
				return false;
			}			   
		}		
		return true;
	}
	
	public static String generateString(int length)
	{
	    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
	    String pass = "";
	    for(int x=0;x<length;x++)
	    {
	       int i = (int)Math.floor(Math.random() * 62); // Si tu supprimes des lettres tu diminues ce nb
	       pass += chars.charAt(i);
	    }
	    return pass;
	}
	
	public static boolean isAlphaNumeric(String s)
    {
		return s.matches("^[a-zA-Z0-9]*$");
    }
	
	
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	
	public static Material getMaterial( String name ){
		Material mat = null;		
		if( Material.matchMaterial(name) != null){
			mat = Material.matchMaterial(name);
		}		
		return mat;
	}
	
	
	public static void addLoreToItem( ItemStack item, String lorename ){
		
		ItemMeta i = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(lorename);
		i.setLore(lore);
		
	}
	
	public static List<MetadataValue> getBlockMetadata( Block block ){
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			BukkitObjectOutputStream boos = new BukkitObjectOutputStream(baos);
			boos.writeObject(block);
			boos.close();
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
		
		
		return null;
		
	}
	
	
	public static void serializeItemStack( ItemStack item ){
		
		FileOutputStream baos = null;
		
		try {
			
			// ItemStack
			baos = new FileOutputStream(BooTreasure.getInstance().getDataFolder() + File.separator + "lost+found" + File.separator + "item.serialized");			
			BukkitObjectOutputStream boos = new BukkitObjectOutputStream(baos);
			boos.writeObject(item);
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
	
	public static ItemStack deserializeItemStack(){
		
		File file = new File(BooTreasure.getInstance().getDataFolder() + File.separator + "lost+found" + File.separator + "item.serialized");
		FileInputStream fis = null;
		Object itemstack = null;
		
		try {
			
			fis = new FileInputStream(file); 
			System.out.println("Total file size to read (in bytes) : " + fis.available()); 						
			BukkitObjectInputStream bois = new BukkitObjectInputStream(fis);
			itemstack = bois.readObject();
			bois.close();
			
			ItemStack item = (ItemStack) itemstack;			
			return item;	
 
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
		
		
		return null;
		
		
	}
	
	
	public static void serializeBlock( Block block ){
		
		FileOutputStream baos = null;
		
		try {
			
			baos = new FileOutputStream("block.serialized");
			
			BukkitObjectOutputStream boos = new BukkitObjectOutputStream(baos);
			boos.writeObject(block);
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
	
	
	public static Block deserializeBlock( ByteArrayOutputStream baos ){
		
		File file = new File("block.serialized");
		FileInputStream fis = null;
		Object backFromTheDead = null;
		
		try {
			
			fis = new FileInputStream(file);
 
			System.out.println("Total file size to read (in bytes) : " + fis.available()); 
						
			BukkitObjectInputStream bois = new BukkitObjectInputStream(fis);
			backFromTheDead = bois.readObject();
			bois.close();
			
			return (Block) backFromTheDead;
			
 
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
		
		
		return null;
		
		
	}
	
	
	
}
