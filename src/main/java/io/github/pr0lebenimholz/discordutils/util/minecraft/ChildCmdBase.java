package io.github.pr0lebenimholz.discordutils.util.minecraft;

import net.minecraft.command.ICommandSender;
import org.apache.logging.log4j.Logger;

/**
 * The base class for every child command.
 *
 * @author fivekWBassMachine
 */
public abstract class ChildCmdBase extends CmdBase {

    private final String parent;

    /**
     * @param parent The name of the parent command
     * @param name The name of the child command
     * @param reqPermLvl The required permission level (OP level) to use this command
     */
    public ChildCmdBase(Logger logger, String parent, String name, int reqPermLvl) {
        super(logger, name, reqPermLvl);
        this.parent = parent;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands." + this.parent + "." + this.getName() + ".usage";
    }

    // TODO: 26.03.22 does this work???
    //  see https://github.com/Railcraft/Railcraft/blob/87f617ca76dc6ebe64b55779ab529f86b811dc87/src/main/java/mods/railcraft/common/commands/SubCommand.java#L126
    /**
     * Returns full name (the parent name and the child command) of the command.
     *
     * Used for {@link ICommandSender#canUseCommand}({@link CmdBase#getRequiredPermissionLevel}, {@link #getFullString})`.
     *
     * @return The full name of the command.
     */
    public String getFullString() {
        return this.parent + " " + this.getName();
    }
}
