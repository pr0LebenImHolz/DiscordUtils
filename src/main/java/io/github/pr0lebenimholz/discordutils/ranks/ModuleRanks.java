package io.github.pr0lebenimholz.discordutils.ranks;

import io.github.pr0lebenimholz.discordutils.util.Module;
import org.apache.commons.lang3.NotImplementedException;

public class ModuleRanks extends Module {

    public static final String KEY = "ranks";

    public ModuleRanks(String loggerName) {
        super(KEY, loggerName);
        throw new NotImplementedException("This module is not implemented yet. Disable it in the config.");
    }

    // TODO: 01.03.22 @5kw implement (via command or IMC)
    // TODO: 01.03.22 register command for triggering update
}
