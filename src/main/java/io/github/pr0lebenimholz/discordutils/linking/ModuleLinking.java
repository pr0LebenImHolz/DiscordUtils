package io.github.pr0lebenimholz.discordutils.linking;

import io.github.pr0lebenimholz.discordutils.linking.commands.CmdDiscord;
import io.github.pr0lebenimholz.discordutils.util.EventHandler;
import io.github.pr0lebenimholz.discordutils.util.Module;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ModuleLinking extends Module {

    public static final String KEY = "linking";

    public ModuleLinking(String loggerName) {
        super(KEY, loggerName);

        EventHandler.registerFmlEvent(FMLServerStartingEvent.class, (EventHandler.EventListener<FMLServerStartingEvent>) this::serverStarting);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        this.logger.debug("Server Starting");
        event.registerServerCommand(new CmdDiscord());
    }
}
