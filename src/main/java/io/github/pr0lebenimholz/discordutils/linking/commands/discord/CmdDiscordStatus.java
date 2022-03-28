package io.github.pr0lebenimholz.discordutils.linking.commands.discord;

import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.linking.PlayerEntry;
import io.github.pr0lebenimholz.discordutils.util.Util;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ParentCmd;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.Logger;

/**
 * Retrieves the current linking status of the executing player.
 *
 * @author fivekWBassMachine
 */
@ParentCmd(value = "io.github.pr0lebenimholz.discordutils.linking.commands.CmdDiscord")
public class CmdDiscordStatus extends ChildCmdBase {

    public CmdDiscordStatus(Logger logger, String parent) {
        super(logger, parent, "status", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String targetUuid;
        if (args.length == 0) {
            // get own status - no permission check required
            targetUuid = Util.getPlayersUuid(Util.Command.ensureSenderIsPlayerElseUnspecified(sender));
        } else if (args.length == 1) {
            // get others status - permission check
            Util.Command.ensureSenderHasPermission(sender,
                    ConfigHandler.linkingSeeOtherStatusPermissionLvl,
                    this.getFullString());
            // TODO: 28.03.22 is this possible?
            if (args[0] == null) throw new CommandException("commands.generic.exception");
            Util.Command.ensureSenderIsNotUsingSelector(args[0]);
            targetUuid = Util.getPlayersUuid(args[0]);
            if (targetUuid == null) throw new PlayerNotFoundException("commands.generic.player.notFound", args[0]);
        } else {
            throw new WrongUsageException(this.getUsage(sender));
        }
        PlayerEntry localInfo = ModuleLinking.getLinkingDatabase().getEntry(targetUuid);
        PlayerEntry remoteInfo = ModuleLinking.getLinkingApi().getInformation(targetUuid);
        if (localInfo == null) {
            sender.sendMessage(new TextComponentTranslation("discordutils.command.discord.status.local.unknown"));
        } else {
            sender.sendMessage(new TextComponentTranslation("discordutils.command.discord.status.local.confirmed", localInfo.getMcIdAsString(), localInfo.getDcId()));
        }
        if (remoteInfo == null) {
            sender.sendMessage(new TextComponentTranslation("discordutils.command.discord.status.remote.unknown"));
        } else {
            sender.sendMessage(new TextComponentTranslation("discordutils.command.discord.status.remote.pending", remoteInfo.getMcIdAsString(), remoteInfo.getDcId()));
        }
    }
}
