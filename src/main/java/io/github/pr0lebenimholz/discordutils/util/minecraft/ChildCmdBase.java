package io.github.pr0lebenimholz.discordutils.util.minecraft;

import net.minecraft.command.ICommandSender;

public abstract class ChildCmdBase extends CmdBase {

    private final String parent;

    public ChildCmdBase(String parent, String name, int reqPermLvl) {
        super(name, reqPermLvl);
        this.parent = parent;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands." + this.parent + "." + this.getName() + ".usage";
    }
}
