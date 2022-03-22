package io.github.pr0lebenimholz.discordutils.util.minecraft;

import net.minecraft.command.ICommandSender;

/**
 * The base class for every command and child command.
 *
 * @author fivekWBassMachine
 */
public abstract class CmdBase extends net.minecraft.command.CommandBase {

    private final String name;
    private final int reqPermLvl;

    public CmdBase(String name, int reqPermLvl) {
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
