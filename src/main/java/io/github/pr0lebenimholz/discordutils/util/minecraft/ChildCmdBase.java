package io.github.pr0lebenimholz.discordutils.util.minecraft;

import net.minecraft.command.ICommandSender;

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
    public ChildCmdBase(String parent, String name, int reqPermLvl) {
        super(name, reqPermLvl);
        this.parent = parent;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands." + this.parent + "." + this.getName() + ".usage";
    }
}
