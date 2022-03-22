package io.github.pr0lebenimholz.discordutils.util;

import io.github.pr0lebenimholz.discordutils.ranks.ModuleRanks;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.status.ModuleStatus;
import org.apache.logging.log4j.Logger;

/**
 * The handler to load modules when they are enabled in the main config.
 *
 * @author fivekWBassMachine
 */
public class ModuleHandler {

    private final Logger logger;

    public ModuleHandler(Logger logger) {
        this.logger = logger;
    }

    /**
     * Initializes enabled modules.
     */
    public void initEnabledModules() {
        String key;
        key = ModuleStatus.KEY;
        if (ConfigHandler.isModuleEnabled(key)) {
            new ModuleStatus(this.logger.getName());
            this.logger.debug(String.format("Initialized module '%s'", key));
        }
        key = ModuleLinking.KEY;
        if (ConfigHandler.isModuleEnabled(key)) {
            new ModuleLinking(this.logger.getName());
            this.logger.debug(String.format("Initialized module '%s'", key));
        }
        key = ModuleRanks.KEY;
        if (ConfigHandler.isModuleEnabled(key)) {
            new ModuleRanks(this.logger.getName());
            this.logger.debug(String.format("Initialized module '%s'", key));
        }
    }
}
