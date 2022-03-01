package io.github.pr0lebenimholz.discordutils.linking.commands;

import io.github.pr0lebenimholz.discordutils.util.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandLink extends CommandBase {

    public CommandLink() {
        super("link", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // TODO: 01.03.22 implement
    }
}
