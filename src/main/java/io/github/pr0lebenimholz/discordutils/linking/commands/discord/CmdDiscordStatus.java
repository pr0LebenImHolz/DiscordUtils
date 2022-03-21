package io.github.pr0lebenimholz.discordutils.linking.commands.discord;

import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class SubCmdDiscordStatus extends ChildCmdBase {

    public SubCmdDiscordStatus() {
        super("status", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // TODO: 01.03.22 implement
        /*
         * /discord status
         * if (@mc.hasConfirmedLink) return "linked (confirmed)"
         * if (@mc.hasPendingLink) return "linked (pending)"
         * return "unlinked"
         *
         * /discord status @mc
         * if (@p.isOp (lvl 4?))
         *   if (@mc.hasConfirmedLink) return "linked (confirmed)"
         *   if (@mc.hasPendingLink) return "linked (pending)"
         *   return "unlinked"
         * throw "insufficient permission"
         */
    }
}
