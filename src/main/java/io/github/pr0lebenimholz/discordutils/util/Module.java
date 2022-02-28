package io.github.pr0lebenimholz.discordutils.util;

import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Module {

    public static String KEY;

    protected Logger logger;

    public Module(String loggerName) {
        this.logger = LogManager.getLogger(loggerName + "/" + KEY);
    }

    public void preInit(FMLPreInitializationEvent event) {}
    public void init(FMLInitializationEvent event) {}
    public void postInit(FMLPostInitializationEvent event) {}

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {}
    public void serverStarting(FMLServerStartingEvent event) {}
    public void serverStarted(FMLServerStartedEvent event) {}
    public void serverStopping(FMLServerStoppingEvent event) {}
    public void serverStopped(FMLServerStoppedEvent event) {}

    public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {}
    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {}

}
