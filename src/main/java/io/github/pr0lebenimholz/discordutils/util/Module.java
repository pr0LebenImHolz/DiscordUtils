package io.github.pr0lebenimholz.discordutils.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The base class for every module.
 *
 * @author fivekWBassMachine
 */
public class Module {

    protected Logger logger;

    /**
     * @param key The name of the module
     * @param loggerName The name of the logger
     */
    public Module(String key, String loggerName) {
        this.logger = LogManager.getLogger(loggerName + "/" + key);

    }
}
