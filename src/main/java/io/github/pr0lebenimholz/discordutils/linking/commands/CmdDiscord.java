package io.github.pr0lebenimholz.discordutils.linking.commands;

import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordLink;
import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordStatus;
import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordUnlink;
import io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ParentCmd;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ParentCmdBase;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * Parent for 'link', 'status', 'unlink' and general information
 * (which is configurable - see {@link io.github.pr0lebenimholz.discordutils.util.data.ConfigHandler#linkingInfo})
 *
 * @author fivekWBassMachine
 */
public class CmdDiscord extends ParentCmdBase {

    public CmdDiscord() {
        this("discord");
    }

    private CmdDiscord(String name) {
        super(name, 0, new ChildCmdBase[] {
                new CmdDiscordLink(name),
                new CmdDiscordStatus(name),
                new CmdDiscordUnlink(name)
        });
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender) {
        // TODO: 21.03.22 currently only the default info or fallback info (when the default info is not en_us) is
        //  supported; is this even possible (keyword privacy)? :facepalm:
        sender.sendMessage(new TextComponentString(ConfigHandler.linkingInfo.get("en_us")));
    }
}
