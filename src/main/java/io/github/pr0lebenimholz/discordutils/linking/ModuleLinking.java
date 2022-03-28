package io.github.pr0lebenimholz.discordutils.linking;

import io.github.pr0lebenimholz.discordutils.linking.commands.CmdDiscord;
import io.github.pr0lebenimholz.discordutils.util.EventHandler;
import io.github.pr0lebenimholz.discordutils.util.Module;
import io.github.pr0lebenimholz.discordutils.util.ModuleName;
import io.github.pr0lebenimholz.discordutils.util.api.MismatchedVersionException;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.IOException;

/**
 * This module handles the linking between Minecraft and Discord accounts.
 *
 * @author fivekWBassMachine
 */
@ModuleName(value = ModuleLinking.KEY)
public class ModuleLinking extends Module {

    public static final String KEY = "linking";

    private static ModuleLinking INSTANCE;
    private LinkingApi linkingApi;
    private final PlayerDatabaseHandler database;

    public static PlayerDatabaseHandler getLinkingDatabase() {
        return INSTANCE.database;
    }

    public ModuleLinking(String loggerName) throws IOException, MismatchedVersionException {
        super(KEY, loggerName);

        INSTANCE = this;

        this.linkingApi = new LinkingApi(this.logger,
                ConfigHandler.linkingApiTls,
                ConfigHandler.linkingApiHost,
                ConfigHandler.linkingApiPort,
                ConfigHandler.linkingApiPath,
                ConfigHandler.linkingApiToken);
        this.database = new PlayerDatabaseHandler();

        EventHandler.registerFmlEvent(FMLServerStartingEvent.class, (EventHandler.EventListener<FMLServerStartingEvent>) this::serverStarting);
    }

    public static LinkingApi getLinkingApi() {
        return INSTANCE.linkingApi;
    }

    public void serverStarting(FMLServerStartingEvent event) {
        this.logger.debug("Server Starting");
        event.registerServerCommand(new CmdDiscord(this.logger));
    }
}
