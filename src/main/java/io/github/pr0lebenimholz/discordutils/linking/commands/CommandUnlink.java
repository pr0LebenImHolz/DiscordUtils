package io.github.pr0lebenimholz.discordutils.linking.commands;

import io.github.pr0lebenimholz.discordutils.util.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandUnlink extends CommandBase {

    public CommandUnlink() {
        super("unlink", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // TODO: 01.03.22 implement
        /*
         * /unlink
         * if (@mc.hasConfirmedLink) return "unlinked (confirmed)"
         * if (@mc.hasPendingLink) return "unlinked (pending)"
         * throw "nothing to unlink"
         *
         */
    }
}
