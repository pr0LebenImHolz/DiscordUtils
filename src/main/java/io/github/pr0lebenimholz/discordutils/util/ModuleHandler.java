package io.github.pr0lebenimholz.discordutils.util;

import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.status.ModuleStatus;
import org.apache.logging.log4j.Logger;

public class ModuleHandler {

    private final Logger logger;

    public ModuleHandler(Logger logger) {
        this.logger = logger;
    }

    public void initEnabledModules() {
        if (ConfigHandler.isModuleEnabled(ModuleStatus.KEY)) {
            new ModuleStatus(this.logger.getName());
            this.logger.debug(String.format("Initialized module '%s'", ModuleStatus.KEY));
        }
        if (ConfigHandler.isModuleEnabled(ModuleLinking.KEY)) {
            new ModuleLinking(this.logger.getName());
            this.logger.debug(String.format("Initialized module '%s'", ModuleLinking.KEY));
        }
    }
}
