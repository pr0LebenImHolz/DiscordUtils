package io.github.pr0lebenimholz.discordutils.linking.commands;

import io.github.pr0lebenimholz.discordutils.util.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandInfo extends CommandBase {

    public CommandInfo() {
        super("link_info", 0);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // TODO: 01.03.22 implement
        /*
         * /link_status
         * return link_info (Config)
         */
    }
}
