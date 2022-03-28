package io.github.pr0lebenimholz.discordutils.util;

import io.github.pr0lebenimholz.discordutils.ranks.ModuleRanks;
import io.github.pr0lebenimholz.discordutils.util.api.MismatchedVersionException;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.status.ModuleStatus;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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
            try {
                new ModuleStatus(this.logger.getName());
                this.logger.info("Initialized module '" + key + "'");
            } catch(MismatchedVersionException e) {
                this.logger.error("Unable to connect to API: " + e.getMessage());
            } catch(IOException e) {
                this.logger.error("Unable to check API version.", e);
            } catch(Exception e) {
                this.logger.error("Error while initializing module '" + key + "'.", e);
            }
        }
        key = ModuleLinking.KEY;
        if (ConfigHandler.isModuleEnabled(key)) {
            try {
                new ModuleLinking(this.logger.getName());
                this.logger.info("Initialized module '" + key + "'");
            } catch(MismatchedVersionException e) {
                this.logger.error("Error while initializing module '" + key + "': Unable to connect to API: " + e.getMessage());
            } catch(IOException e) {
                this.logger.error("Error while initializing module '" + key + "': Unable to check API version.", e);
            } catch(Exception e) {
                this.logger.error("Error while initializing module '" + key + "'.", e);
            }
        }
        key = ModuleRanks.KEY;
        if (ConfigHandler.isModuleEnabled(key)) {
            //try {
                new ModuleRanks(this.logger.getName());
                this.logger.debug(String.format("Initialized module '%s'", key));
            /*} catch(Exception e) {
                this.logger.error("Error while initializing module '" + key + "'.", e);
            }*/
        }
    }
}
