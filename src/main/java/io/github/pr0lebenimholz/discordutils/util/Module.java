package io.github.pr0lebenimholz.discordutils.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Module {

    protected Logger logger;

    public Module(String key, String loggerName) {
        this.logger = LogManager.getLogger(loggerName + "/" + key);

    }
}
