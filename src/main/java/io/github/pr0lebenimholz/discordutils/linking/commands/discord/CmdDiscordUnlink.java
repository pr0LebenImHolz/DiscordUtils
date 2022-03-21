package io.github.pr0lebenimholz.discordutils.linking.commands.discord;

import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CmdDiscordUnlink extends ChildCmdBase {

    public CmdDiscordUnlink(String parent) {
        super(parent, "unlink", 0);
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
        sender.sendMessage(new TextComponentTranslation("discordutils.command_generic.failure_not_implemented",
                " Unlink CmdDiscordUnlink " + String.join(" ", args)));
    }
}
