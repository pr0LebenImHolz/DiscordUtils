package io.github.pr0lebenimholz.discordutils.linking.commands.discord;

import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CmdDiscordLink extends ChildCmdBase {

    public CmdDiscordLink() {
        super("link", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // TODO: 01.03.22 implement
        /*
         * /link <@dc>
         * if (@mc.hasConfirmedLink) throw "already linked"
         * if (@mc.hasPendingLink)
         *   if (@mc.getPendingLink != this) throw "already pending with different (discord) user"
         *   if (@mc.getPendingLink.initiatedBy == @mc) throw "confirmation required by @dc"
         *   return "linked"
         * if (DiscordApi.getMembers[@dc] == undefined) throw "@dc is not on server"
         * return "pending"
         */
    }
}
