package io.github.pr0lebenimholz.discordutils.util;

import net.minecraft.command.ICommandSender;

public abstract class CommandBase extends net.minecraft.command.CommandBase {

    private final String name;
    private final int reqPermLvl;

    public CommandBase(String name, int reqPermLvl) {
        this.name = name;
        this.reqPermLvl = reqPermLvl;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands." + name + ".usage";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return reqPermLvl;
    }
}
