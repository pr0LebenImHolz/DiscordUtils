package io.github.pr0lebenimholz.discordutils.linking.commands.discord;

import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.linking.PlayerEntry;
import io.github.pr0lebenimholz.discordutils.util.Util;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ParentCmd;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/**
 * Deletes a pending or confirmed link request of the executing player.
 *
 * @author fivekWBassMachine
 */
@ParentCmd(value = "io.github.pr0lebenimholz.discordutils.linking.commands.CmdDiscord")
public class CmdDiscordUnlink extends ChildCmdBase {

    public CmdDiscordUnlink(Logger logger, String parent) {
        super(logger, parent, "unlink", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP senderP = Util.Command.ensureSenderIsPlayerElseOnlyPlayers(sender);
        if (args.length != 0) throw new WrongUsageException(this.getUsage(sender));
        UUID targetId = senderP.getGameProfile().getId();
        if (ModuleLinking.getLinkingDatabase().hasEntry(targetId)) {
            boolean confirmed = ModuleLinking.getLinkingDatabase().getEntry(targetId).isConfirmed();
            ModuleLinking.getLinkingDatabase().removeEntry(targetId);
            PlayerEntry response;
            try {
                response = ModuleLinking.getLinkingApi().getInformation(targetId);
            } catch(Exception e) {
                throw new CommandException("discordutils.command.discord.link.no_backend");
            }
            if (response == null) {
                sender.sendMessage(new TextComponentTranslation("discordutils.command.discord.unlink.unknown"));
            } else {
                sender.sendMessage(new TextComponentTranslation(
                        confirmed ? "discordutils.command.discord.unlink.confirmed" :
                                "discordutils.command.discord.unlink.pending",
                        response.getDcName()));
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("discordutils.command.discord.unlink.not_linked"));
        }
    }
}
