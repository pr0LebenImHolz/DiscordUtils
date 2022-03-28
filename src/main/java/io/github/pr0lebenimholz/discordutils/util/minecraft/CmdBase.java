package io.github.pr0lebenimholz.discordutils.util.minecraft;

import net.minecraft.command.ICommandSender;
import org.apache.logging.log4j.Logger;

/**
 * The base class for every command and child command.
 *
 * @author fivekWBassMachine
 */
public abstract class CmdBase extends net.minecraft.command.CommandBase {

    protected final Logger logger;
    private final String name;
    private final int reqPermLvl;

    /**
     * @param logger The logger for the command (should be the logger of the module)
     * @param name The name of the command
     * @param reqPermLvl The required permission level (OP level) to use this command
     */
    public CmdBase(Logger logger, String name, int reqPermLvl) {
        this.logger = logger;
        this.name = name;
        this.reqPermLvl = reqPermLvl;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands." + this.name + ".usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return reqPermLvl;
    }
}
