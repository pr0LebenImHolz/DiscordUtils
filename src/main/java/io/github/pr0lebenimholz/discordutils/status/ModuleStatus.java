package io.github.pr0lebenimholz.discordutils.status;

import io.github.pr0lebenimholz.discordutils.util.api.MismatchedVersionException;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.EventHandler;
import io.github.pr0lebenimholz.discordutils.util.Module;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.io.IOException;

public class ModuleStatus extends Module {

    public static final String KEY = "status";

    private StatusApi api;

    public ModuleStatus(String loggerName) {
        super(KEY, loggerName);

        try {
            this.api = new StatusApi(
                    this.logger,
                    ConfigHandler.statusApiTls,
                    ConfigHandler.statusApiHost,
                    ConfigHandler.statusApiPort,
                    ConfigHandler.statusApiPath,
                    ConfigHandler.statusApiToken);
        } catch (MismatchedVersionException e) {
            this.logger.error("Unable to connect to API: " + e.getMessage());
        } catch (IOException e) {
            this.logger.error("Unable to check API version - updates are deactivated.", e);
        }

        EventHandler.registerFmlEvent(FMLServerStartedEvent.class, (EventHandler.EventListener<FMLServerStartedEvent>) this::serverStarted);
        EventHandler.registerFmlEvent(FMLServerStoppingEvent.class, (EventHandler.EventListener<FMLServerStoppingEvent>) this::serverStopping);
        EventHandler.registerFmlEvent(FMLServerStoppedEvent.class, (EventHandler.EventListener<FMLServerStoppedEvent>) this::serverStopped);
        EventHandler.registerEvent(PlayerEvent.PlayerLoggedInEvent.class, (EventHandler.EventListener<PlayerEvent.PlayerLoggedInEvent>) this::playerLoggedIn);
        EventHandler.registerEvent(PlayerEvent.PlayerLoggedOutEvent.class, (EventHandler.EventListener<PlayerEvent.PlayerLoggedOutEvent>) this::playerLoggedOut);

        if (this.api != null) this.api.notifyStarting();
    }

    public void serverStarted(FMLServerStartedEvent event) {
        if (this.api != null) this.api.notifyStarted(
                FMLServerHandler.instance().getServer().getMaxPlayers(),
                FMLServerHandler.instance().getServer().getMOTD());
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        if (this.api != null) this.api.notifyStopping();
    }

    public void serverStopped(FMLServerStoppedEvent event) {
        if (this.api != null) this.api.notifyStopped();
    }

    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // The event is fired after the player joins, so the value is correct
        if (this.api != null) this.api.notifyPlayerCount(FMLServerHandler.instance().getServer().getCurrentPlayerCount());
    }

    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        // The event is fired after the player leaves, so the value is offset by 1
        if (this.api != null) this.api.notifyPlayerCount(FMLServerHandler.instance().getServer().getCurrentPlayerCount() - 1);
    }
}
