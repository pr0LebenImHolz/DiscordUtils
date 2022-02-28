package io.github.pr0lebenimholz.discordutils.status;

import io.github.pr0lebenimholz.discordutils.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.Module;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.server.FMLServerHandler;

public class ModuleStatus extends Module {

    public static final String KEY = "status";

    private final StatusApi api;

    public ModuleStatus(String loggerName) {
        super(loggerName);
        this.api = new StatusApi(this.logger, ConfigHandler.STATUS_API_VERSION, ConfigHandler.STATUS_API_URL, ConfigHandler.STATUS_API_TOKEN);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.api.notifyStarting();
    }

    @Override
    public void serverStarted(FMLServerStartedEvent event) {
        this.api.notifyStarted(FMLServerHandler.instance().getServer().getMOTD());
    }

    @Override
    public void serverStopping(FMLServerStoppingEvent event) {
        this.api.notifyStopping();
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        this.api.notifyStopped();
    }

    @Override
    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        this.api.notifyPlayerCount(FMLServerHandler.instance().getServer().getCurrentPlayerCount());
    }

    @Override
    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        this.api.notifyPlayerCount(FMLServerHandler.instance().getServer().getCurrentPlayerCount());
    }
}
