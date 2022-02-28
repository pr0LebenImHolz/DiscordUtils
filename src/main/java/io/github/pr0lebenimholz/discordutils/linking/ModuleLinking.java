package io.github.pr0lebenimholz.discordutils.linking;

import io.github.pr0lebenimholz.discordutils.linking.commands.CommandLink;
import io.github.pr0lebenimholz.discordutils.linking.commands.CommandUnlink;
import io.github.pr0lebenimholz.discordutils.util.Module;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ModuleLinking extends Module {

    public static final String KEY = "linking";

    public ModuleLinking(String loggerName) {
        super(loggerName);
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandLink());
        event.registerServerCommand(new CommandUnlink());
    }
}
