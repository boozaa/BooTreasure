package org.shortrip.boozaa.plugins.bootreasure.utils.ribesg;

import org.bukkit.Material;
import java.lang.reflect.Field;


public class MaterialUtil {

	/**
	* Gets a Material from a String, if able to recognize anything in the
	* String. For now, only checks for ID and Material enum value.
	* Note: For now, there is no real gain of this over using
	* {@link org.bukkit.Material#matchMaterial(String)}.
	*
	* @param idString the String representing a Material
	*
	* @return the associated Material or null if not found
	*/
	public static Material getMaterial(final String idString) throws InventoryUtilException {
		try {
			final int id = Integer.parseInt(idString);
			for (final Material m : Material.values()) {
				if (m.getId() == id && !isMaterialDeprecated(m)) {
					return m;
				}
			}
			return null;
		} catch (final NumberFormatException e) {
			final String filtered = idString.toUpperCase().replaceAll("\\s+", "_").replaceAll("\\W", "");
			return Material.getMaterial(filtered);
		}
	}
	
	/**
	* Checks if a Material is deprecated.
	*
	* @param material the Material to check
	*
	* @return true if deprecated, false otherwise
	*/
	public static boolean isMaterialDeprecated(final Material material) throws InventoryUtilException {
		try {
			final Field f = Material.class.getField(material.name());
			return f.isAnnotationPresent(Deprecated.class);
		} catch (final NoSuchFieldException e) {
			throw new InventoryUtilException("Material not found: " + material.name(), e);
		}
	}

}
