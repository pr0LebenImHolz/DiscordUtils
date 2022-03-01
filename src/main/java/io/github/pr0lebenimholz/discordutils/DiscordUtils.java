package io.github.pr0lebenimholz.discordutils;

import io.github.pr0lebenimholz.discordutils.util.Constants;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.EventHandler;
import io.github.pr0lebenimholz.discordutils.util.ModuleHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
@Mod.EventBusSubscriber(value = Side.SERVER)
public class DiscordUtils {

    private static DiscordUtils instance;
    private static Logger logger;

    @Mod.EventHandler
    @SideOnly(Side.SERVER)
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        logger = event.getModLog();

        ConfigHandler.init(event.getSuggestedConfigurationFile());
        EventHandler.init();
        ModuleHandler moduleHandler = new ModuleHandler(logger);

        moduleHandler.initEnabledModules();
    }

    /*
     * removed unused listeners for performance
    @EventHandler
    @SideOnly(Side.SERVER)
    public void init(FMLInitializationEvent event) {
        FMLEventHandler.execute(event);
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void postInit(FMLPostInitializationEvent event) {
        FMLEventHandler.execute(event);
    }

    @EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        FMLEventHandler.execute(event);
    }
     */

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        EventHandler.execute(event);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        EventHandler.execute(event);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        EventHandler.execute(event);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        EventHandler.execute(event);
    }

    @SubscribeEvent
    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        EventHandler.execute(event);
    }

    @SubscribeEvent
    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        EventHandler.execute(event);
    }
}
