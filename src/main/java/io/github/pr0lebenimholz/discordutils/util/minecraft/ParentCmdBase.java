package io.github.pr0lebenimholz.discordutils.util.minecraft;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandWithSubsBase extends CmdBase {

    // no HashMap because I don't need the features but performance
    private final SubCommandBase[] subCommands;
    private final List<String> subCommandNames;

    public CommandWithSubsBase(String name, int reqPermLvl, SubCommandBase[] subCommands) {
        super(name, reqPermLvl);
        this.subCommands = subCommands;
        this.subCommandNames = Arrays.stream(subCommands).map(SubCommandBase::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        // /Cmd <SubCmd> [...]
        if (args.length == 0) {
            // /Cmd
            // player entered nothing - display list of subcommands
            return getListOfStringsMatchingLastWord(args, this.subCommandNames);
        }
        int subCommandPos = this.subCommandNames.indexOf(args[0]);
        if (subCommandPos != -1) {
            // /Cmd <SubCmd> [...]
            // player entered a subcommand - forward call to subcommand without first argument
            return this.subCommands[subCommandPos].getTabCompletions(server, sender, Arrays.copyOfRange(args, 1, args.length), targetPos);
        } else {
            //Cmd <Invalid> [...]
            // player entered nonsense - display no subcommands
            return Collections.emptyList();
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // /Cmd <SubCmd> [...]
        if (args.length == 0) {
            // /Cmd
            // player entered nothing - display list of subcommands
            this.execute(server, sender, args);
        }
        int subCommandPos = this.subCommandNames.indexOf(args[0]);
        if (subCommandPos != -1) {
            // /Cmd <SubCmd> [...]
            // player entered a subcommand - forward call to subcommand without first argument
            this.subCommands[subCommandPos].execute(server, sender, Arrays.copyOfRange(args, 1, args.length));
        } else {
            //Cmd <Invalid> [...]
            // player entered nonsense - display no subcommands
            throw new WrongUsageException(this.getUsage(sender));
        }
    }
}
