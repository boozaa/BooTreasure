/**
 * BooTreasure by boozaa
 */
package org.shortrip.boozaa.plugins.bootreasure.config;

import org.shortrip.boozaa.plugins.bootreasure.BooTreasure;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author boozaa
 *
 * BooTreasure
 */
@Data
@EqualsAndHashCode( callSuper=false )
public class MainConfig extends ConfigFile {

	
	public MainConfig() {
		super(BooTreasure.getTreasuresConfigPath());
	}

}
