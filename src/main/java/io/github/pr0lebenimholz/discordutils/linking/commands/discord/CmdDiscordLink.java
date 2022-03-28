package io.github.pr0lebenimholz.discordutils.linking.commands.discord;

import com.mojang.authlib.GameProfile;
import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.linking.PlayerEntry;
import io.github.pr0lebenimholz.discordutils.util.Util;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ParentCmd;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Links the executing player with the passed discord account when a link request is pending on the Bots DB.
 *
 * @author fivekWBassMachine
 */
@ParentCmd(value = "io.github.pr0lebenimholz.discordutils.linking.commands.CmdDiscord")
public class CmdDiscordLink extends ChildCmdBase {

    public CmdDiscordLink(Logger logger, String parent) {
        super(logger, parent, "link", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP senderP = Util.Command.ensureSenderIsPlayerElseOnlyPlayers(sender);
        if (args.length != 1) throw new WrongUsageException(this.getUsage(sender));
        // TODO: 28.03.22 is this possible?
        if (args[0] == null) throw new CommandException("commands.generic.exception");
        if (!args[0].matches(Util.REGEX_DISCORD_MENTION))
            throw new WrongUsageException("discordutils.command.discord.link.invalid_account");

        GameProfile senderProfile = senderP.getGameProfile();
        PlayerEntry response;
        try {
            response = ModuleLinking.getLinkingApi().takePendingLink(senderProfile.getId(), senderProfile.getName());
        } catch(Exception e) {
            throw new CommandException("discordutils.command.discord.link.no_backend");
        }
        if (response == null) throw new CommandException("discordutils.command.discord.link.no_pending", ConfigHandler.linkingCommandDiscord);
        String senderUuid = senderProfile.getId().toString();
        if (!response.getMcIdAsString().equals(senderUuid) || !response.getDcName().equals(args[0]))
            throw new IllegalArgumentException("The LinkingApi returned an invalid response");

        try {
            ModuleLinking.getLinkingDatabase().addEntry(response.getMcIdAsString(), response.getDcId());
            sender.sendMessage(new TextComponentTranslation("discordutils.command.discord.link.confirmed", response.getDcName()));
        } catch (IOException e) {
            this.logger.error("Error while attempting to link '" + response.getMcIdAsString() + "' ('" + senderProfile.getName() + "') with '" + response.getDcId() + "' ('" + response.getDcName() + "'):", e);
            throw new CommandException("commands.generic.exception");
        }
    }
}
