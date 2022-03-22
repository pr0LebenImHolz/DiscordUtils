package io.github.pr0lebenimholz.discordutils.linking.commands.discord;

import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ParentCmd;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Retrieves the current linking status of the executing player.
 *
 * @author fivekWBassMachine
 */
@ParentCmd(value = "io.github.pr0lebenimholz.discordutils.linking.commands.CmdDiscord")
public class CmdDiscordStatus extends ChildCmdBase {

    public CmdDiscordStatus(String parent) {
        super(parent, "status", 0);
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
        sender.sendMessage(new TextComponentTranslation("discordutils.command_generic.failure_not_implemented",
                " CmdDiscordStatus " + String.join(" Status ", args)));
    }
}
