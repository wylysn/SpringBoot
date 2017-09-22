package com.purang.SpringBoot.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLogger {

	private static final Logger LOGGER = LogManager.getLogger();
	
	private MyLogger() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    public static Logger getLogger(){
        return LOGGER;
    }
    public static void trace(String msg) {
        LOGGER.trace(msg);
    }
    public static void debug(String msg) {
        LOGGER.debug(msg);
    }
    public static void info(String msg) {
        LOGGER.info(msg);
    }
    public static void warn(String msg) {
        LOGGER.warn(msg);
    }
    public static void error(String msg) {
        LOGGER.error(msg);
    }
}
