package org.shortrip.boozaa.plugins.bootreasure.managers.configuration;

import lombok.Getter;


public class ConfigLoadException extends Exception {
	private static final long serialVersionUID = 1L;
	@Getter private Throwable throwable;
	public ConfigLoadException(String message, Throwable t) {
        super(message);
        this.throwable = t;
    }		
}
