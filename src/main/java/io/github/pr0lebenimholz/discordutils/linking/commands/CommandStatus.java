package io.github.pr0lebenimholz.discordutils.linking.commands;

import io.github.pr0lebenimholz.discordutils.util.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStatus extends CommandBase {

    public CommandStatus() {
        super("link_status", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // TODO: 01.03.22 implement
        /*
         * /link_status
         * if (@mc.hasConfirmedLink) return "linked (confirmed)"
         * if (@mc.hasPendingLink) return "linked (pending)"
         * return "unlinked"
         *
         * /link_status @mc
         * if (@p.isOp)
         *   if (@mc.hasConfirmedLink) return "linked (confirmed)"
         *   if (@mc.hasPendingLink) return "linked (pending)"
         *   return "unlinked"
         * throw "insufficient permission"
         */
    }
}
