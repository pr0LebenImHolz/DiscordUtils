package io.github.pr0lebenimholz.discordutils;

import io.github.pr0lebenimholz.discordutils.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.status.ModuleStatus;
import io.github.pr0lebenimholz.discordutils.util.ModuleHashMap;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class DiscordUtils {

    private static DiscordUtils instance;
    public static Logger logger;

    private ModuleHashMap modules;

    @EventHandler
    @SideOnly(Side.SERVER)
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        logger = event.getModLog();
        this.modules = new ModuleHashMap();
        ConfigHandler.init(event.getSuggestedConfigurationFile());

        String key;

        key = ModuleStatus.KEY;
        if (ConfigHandler.isModuleEnabled(key)) {
            this.modules.put(key, new ModuleStatus(logger.getName()));
            logger.info("Enabled module '" + key + "'");
        }

        key = ModuleLinking.KEY;
        if (ConfigHandler.isModuleEnabled(key)) {
            this.modules.put(key, new ModuleLinking(logger.getName()));
            logger.info("Enabled module '" + key + "'");
        }

        this.modules.get(ModuleStatus.KEY).preInit(event);
    }

    /*
     * removed unused listeners for performance
    @EventHandler
    @SideOnly(Side.SERVER)
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
    }
     */

    @EventHandler
    @SideOnly(Side.SERVER)
    public void serverStarting(FMLServerStartingEvent event) {
        this.modules.get(ModuleLinking.KEY).serverStarting(event);
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void serverStarted(FMLServerStartedEvent event) {
        this.modules.get(ModuleStatus.KEY).serverStarted(event);
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void serverStopping(FMLServerStoppingEvent event) {
        this.modules.get(ModuleStatus.KEY).serverStopping(event);
    }

    @EventHandler
    @SideOnly(Side.SERVER)
    public void serverStopped(FMLServerStoppedEvent event) {
        this.modules.get(ModuleStatus.KEY).serverStopped(event);
    }

    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        instance.modules.get(ModuleStatus.KEY).playerLoggedIn(event);
    }

    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        instance.modules.get(ModuleStatus.KEY).playerLoggedOut(event);
    }
}
