package io.github.pr0lebenimholz.discordutils;

import io.github.pr0lebenimholz.discordutils.util.Constants;
import io.github.pr0lebenimholz.discordutils.util.EventHandler;
import io.github.pr0lebenimholz.discordutils.util.ModuleHandler;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.network.NetworkManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

/**
 * DiscordUtils
 *
 * @author fivekWBassMachine
 */
@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
@Mod.EventBusSubscriber(value = Side.SERVER)
public class DiscordUtils {

    private static DiscordUtils instance;
    private static Logger logger;

    /**
     * Development purposes only. Will spam the console.
     *
     * @return The logger of this mod
     */
    public static Logger getLogger() {
        logger.fatal("DiscordUtils::getLogger is a development method and must not be used in production!");
        return logger;
    }

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

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkManager.init();

        if (event.getSide() == Side.SERVER) EventHandler.execute(event);
    }

    /* removed unused listeners for performance
    @EventHandler
    @SideOnly(Side.SERVER)
    public void postInit(FMLPostInitializationEvent event) {
        FMLEventHandler.execute(event);
    }*/

    /* removed unused listeners for performance
    @EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        FMLEventHandler.execute(event);
    }*/

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
