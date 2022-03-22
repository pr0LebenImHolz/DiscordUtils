package io.github.pr0lebenimholz.discordutils.util.minecraft;

import io.github.pr0lebenimholz.discordutils.util.Util;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The base class for each command.
 *
 * @author fivekWBassMachine
 */
public abstract class ParentCmdBase extends CmdBase {

    // no HashMap because we don't need the features but performance
    private final ChildCmdBase[] childCommands;
    private final List<String> childCommandNames;

    // the name of the auto added 'help' child command
    private static final String CHILD_HELP = "help";
    // child command usages per help page (7 usages + header + footer = at least 9 lines)
    private static final int USAGES_PER_PAGE = 7;

    /**
     * @param name The name of the command
     * @param reqPermLvl The required permission level (OP level) to use this command
     * @param childCommands All child commands to be registered
     */
    public ParentCmdBase(String name, int reqPermLvl, ChildCmdBase[] childCommands) {
        super(name, reqPermLvl);
        this.childCommands = childCommands;
        this.childCommandNames = Arrays.stream(childCommands)
                .map(ChildCmdBase::getName)
                .collect(Collectors.toList());
        this.childCommandNames.add(CHILD_HELP);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        // `/<Cmd> <SubCmd>[ ...]`
        switch(args.length) {
            case 1:
                // `/<Cmd> `
                // player entered nothing - display list of child commands
                return getListOfStringsMatchingLastWord(args, this.childCommandNames);
            case 2:
                // `/<Cmd> <Child>[ ...]`
                if (Objects.equals(args[0], CHILD_HELP)) {
                    // `/<Cmd> help `
                    // player entered help - display all page numbers
                    int pageCount = Util.perfPosDivRUp(this.childCommands.length, USAGES_PER_PAGE);
                    return pageCount == 1 ? Collections.emptyList() : IntStream.range(0, pageCount)
                            .mapToObj(Integer::toString)
                            .collect(Collectors.toList());
                }
                int childIndex = this.childCommandNames.indexOf(args[0]);
                // `/<Cmd> <Invalid>[ ...]`
                if (childIndex != -1) {
                    // /`<Cmd> <SubCmd>[ ...]`
                    // player entered a subcommand - forward call to child command without first argument
                    return this.childCommands[childIndex].getTabCompletions(server, sender, Util.shiftArgs(args), targetPos);
                }
                // no break; return empty list on IndexOutOfBoundsException
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // `/<Cmd> <SubCmd>[ ...]`
        if (args.length == 0) {
            // `/<Cmd>`
            // player entered nothing - display list of child commands
            this.execute(server, sender);
        } else if (Objects.equals(args[0], CHILD_HELP)) {
            // `/<Cmd> help[ page=0]`
            // player entered help - display usages of all child commands
            if (args.length == 1 || args.length == 2) {
                int page = 0;
                if (args.length == 2) {
                    // `/<Cmd> help <page>`
                    try {
                        page = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        throw new NumberInvalidException();
                    }
                }
                // TODO: 12.03.22 (after 1st release or so...)
                //  THIS IS NO DIRTY CODE! (or, at least not much dirtier than
                //  `net.minecraft.command.CommandHelp::execute` @ `Gradle: net.minecraftforge:forge:`...) so it must be
                //  OK.
                //  `net.minecraft.util.text.TextComponentTranslation` (Gradle: same) has support for child-texts.
                //  This may be a better solution than sending thousands (OK, 9) of messages :/
                ITextComponent[] lines = this.getHelp(server, sender, page);
                for (ITextComponent line : lines) if (line != null) sender.sendMessage(line);
            } else {
                throw new WrongUsageException(this.getUsage(sender));
            }
        } else {
            int subCommandPos = this.childCommandNames.indexOf(args[0]);
            if (subCommandPos != -1) {
                // /<Cmd> <SubCmd> [...]
                // player entered a subcommand - forward call to child command without first argument
                this.childCommands[subCommandPos].execute(server, sender, Util.shiftArgs(args));
            } else {
                //<Cmd> <Invalid> [...]
                // player entered nonsense - display no child commands
                throw new WrongUsageException(this.getUsage(sender));
            }
        }
    }

    /**
     * Parses the help texts for the command and each child command.
     *
     * @param server
     * @param sender The requester of this help page
     * @param page The requested help page [1...pages.length]
     * @return
     */
    protected ITextComponent[] getHelp(MinecraftServer server, ICommandSender sender, int page) throws NumberInvalidException {
        // usages of all commands, sorted alphabetically
        // TODO: 12.03.22 (after 1st release or so...)
        //  This sorts the translation keys alphabetically. This will only work when all translation keys are in the
        //  same format and the translations are equal. This should be no problem(TM) because the translations should
        //  look like `/<Cmd> <Child>: <Description>` and the important part ends at the colon (:).
        List<TextComponentTranslation> usages = Arrays.stream(this.childCommands)
                .map(it -> it.getUsage(sender))
                .sorted()
                .map(TextComponentTranslation::new)
                .collect(Collectors.toList());
        int pageCount = Util.perfPosDivRUp(usages.size(), USAGES_PER_PAGE);
        // TODO: 21.03.22 how to tell the player which range of numbers he can pass?
        if (page < 1 || page > pageCount) throw new NumberInvalidException();
        // array of each line of the help page
        ITextComponent[] text = new ITextComponent[9];
        // header
        text[0] = new TextComponentTranslation("discordutils.commands.help.header", page, pageCount, this.getName());
        text[0].getStyle().setColor(TextFormatting.DARK_GREEN);
        // start pos (including) = page (selected page) * 7 (usages per page)
        // TODO: 21.03.22 check whats the 1st page; 0 or 1? maybe adjust (should be OK like this(TM))
        int startPos = (page - 1) * USAGES_PER_PAGE;
        // end pos (excluding) = start pos + 7 (usages per page)
        int endPos = startPos + USAGES_PER_PAGE;
        for (int i = 1, j = startPos; i < USAGES_PER_PAGE + 1 && j < usages.size(); i++, j++)
            text[i] = usages.get(j);
        // footer
        text[8] = new TextComponentTranslation("discordutils.commands.help.footer");
        text[8].getStyle().setColor(TextFormatting.DARK_GREEN);

        return text;
    }

    public void execute(MinecraftServer server, ICommandSender sender) {}
}
