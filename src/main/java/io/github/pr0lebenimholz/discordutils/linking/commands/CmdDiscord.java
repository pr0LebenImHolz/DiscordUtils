package io.github.pr0lebenimholz.discordutils.linking.commands;

import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordLink;
import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordStatus;
import io.github.pr0lebenimholz.discordutils.linking.commands.discord.CmdDiscordUnlink;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ParentCmdBase;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

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
        sender.sendMessage(new TextComponentTranslation("discordutils.command_generic.failure_not_implemented", " CmdDiscord"));
    }
}
