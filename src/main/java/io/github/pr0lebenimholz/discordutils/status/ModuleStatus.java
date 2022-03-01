package io.github.pr0lebenimholz.discordutils.status;

import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.EventHandler;
import io.github.pr0lebenimholz.discordutils.util.Module;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.server.FMLServerHandler;

public class ModuleStatus extends Module {

    public static final String KEY = "status";

    private final StatusApi api;

    public ModuleStatus(String loggerName) {
        super(KEY, loggerName);
        this.api = new StatusApi(this.logger, ConfigHandler.statusApiVersion, ConfigHandler.statusApiUrl, ConfigHandler.statusApiToken);

        EventHandler.registerFmlEvent(FMLServerStartedEvent.class, (EventHandler.EventListener<FMLServerStartedEvent>) this::serverStarted);
        EventHandler.registerFmlEvent(FMLServerStoppingEvent.class, (EventHandler.EventListener<FMLServerStoppingEvent>) this::serverStopping);
        EventHandler.registerFmlEvent(FMLServerStoppedEvent.class, (EventHandler.EventListener<FMLServerStoppedEvent>) this::serverStopped);
        EventHandler.registerEvent(PlayerEvent.PlayerLoggedInEvent.class, (EventHandler.EventListener<PlayerEvent.PlayerLoggedInEvent>) this::playerLoggedIn);
        EventHandler.registerEvent(PlayerEvent.PlayerLoggedOutEvent.class, (EventHandler.EventListener<PlayerEvent.PlayerLoggedOutEvent>) this::playerLoggedOut);

        this.api.notifyStarting();
    }

    public void serverStarted(FMLServerStartedEvent event) {
        this.logger.debug("Server Started");
        this.api.notifyStarted(FMLServerHandler.instance().getServer().getMOTD());
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        this.logger.debug("Server Stopping");
        this.api.notifyStopping();
    }

    public void serverStopped(FMLServerStoppedEvent event) {
        this.logger.debug("Server Stopped");
        this.api.notifyStopped();
    }

    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        this.logger.debug("Player LoggedIn");
        this.api.notifyPlayerCount(FMLServerHandler.instance().getServer().getCurrentPlayerCount());
    }

    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        this.logger.debug("Player LoggedOut");
        this.api.notifyPlayerCount(FMLServerHandler.instance().getServer().getCurrentPlayerCount());
    }
}
