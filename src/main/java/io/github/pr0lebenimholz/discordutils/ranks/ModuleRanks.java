package io.github.pr0lebenimholz.discordutils.ranks;

import io.github.pr0lebenimholz.discordutils.util.Module;
import org.apache.commons.lang3.NotImplementedException;

// TODO: 21.03.22 WIP _what_ ranks are synchronized?
//  FTBU? (yes)
//  executing configurable commands? (maybe)
//  passing classpaths?
// TODO: 21.03.22 when supporting FTBU - is it possible to dynamically check (depending on module enabled) if FTBU is
//  present?
/**
 * This module handles the synchronization between Discord roles and Minecraft ranks.
 *
 * @author fivekWBassMachine
 */
public class ModuleRanks extends Module {

    public static final String KEY = "ranks";

    public ModuleRanks(String loggerName) {
        super(KEY, loggerName);
        throw new NotImplementedException("This module is not implemented yet. Disable it in the config.");
    }

    // TODO: 01.03.22 @5kw implement (via command or IMC)
    // TODO: 01.03.22 register command for triggering update
}
