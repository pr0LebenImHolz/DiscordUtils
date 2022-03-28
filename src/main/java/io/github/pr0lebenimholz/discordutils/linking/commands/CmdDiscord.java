package io.github.pr0lebenimholz.discordutils.linking.commands;

import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordLink;
import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordStatus;
import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordUnlink;
import io.github.pr0lebenimholz.discordutils.util.Util;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import io.github.pr0lebenimholz.discordutils.util.minecraft.MsgFallbackTranslation;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ParentCmdBase;
import io.github.pr0lebenimholz.discordutils.util.network.NetworkManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.apache.logging.log4j.Logger;

/**
 * Parent for 'link', 'status', 'unlink' and general information
 * (which is configurable - see {@link io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler#linkingInfo})
 *
 * @author fivekWBassMachine
 */
public class CmdDiscord extends ParentCmdBase {

    public CmdDiscord(Logger logger) {
        this(logger, "discord");
    }

    private CmdDiscord(Logger logger, String name) {
        super(logger, name, 0, new ChildCmdBase[] {
                new CmdDiscordLink(logger, name),
                new CmdDiscordStatus(logger, name),
                new CmdDiscordUnlink(logger, name)
        });
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender) throws CommandException {
        EntityPlayerMP senderP = Util.Command.ensureSenderIsPlayerElseOnlyPlayers(sender);
        IMessage message = new MsgFallbackTranslation(ConfigHandler.linkingInfo);
        NetworkManager.getNetworkChannel().sendTo(message, senderP);
    }
}
