package io.github.pr0lebenimholz.discordutils.util;

import com.mojang.authlib.GameProfile;
import io.github.pr0lebenimholz.discordutils.util.minecraft.ChildCmdBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.server.FMLServerHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Various small utility methods.
 */
// author belongs into the javadocs of the single methods
public class Util {
    /**
     * A vague RegEx which checks if the String _can_ match a discord name and discriminator
     * <br /><br />
     * See https://discord.com/developers/docs/resources/user#usernames-and-nicknames
     * <br /><br />
     * Not realized:
     * <ul>
     *  <li>Names are sanitized and trimmed of leading, trailing, and excessive internal whitespace.</li>
     *  <li>Usernames cannot contain the following substrings: '@', '#', ':', '```', 'discord'</li>
     *  <li>Usernames cannot be: 'everyone', 'here'</li>
     * </ul>
     *
     * @author fivekWBassMachine
     */
    public static final String REGEX_DISCORD_MENTION = "/^.{3,32}#[0-9]{4}#\\d{4}$/g";

    /**
     * Performs a PERFormant POSitive DIVision and Rounds the quotient UP.
     *
     * @param dividend
     * @param divisor
     * @returns Rounded up quotient
     *
     * @author [Peter Lawrey @ StackOverflow](https://stackoverflow.com/a/7446742)
     */
    public static int perfPosDivRUp(int dividend, int divisor) {
        return (dividend + divisor - 1) / divisor;
    }

    /**
     * Shifts all strings in the array by 1. [a,b,c] -> [b,c]
     *
     * @param s The array to shift
     * @returns A new, shifted array
     *
     * @author fivekWBassMachine
     */
    public static String[] shiftArgs(@Nonnull String[] s) {
        if(s.length == 1 || s.length == 2) return new String[0];
        return Arrays.copyOfRange(s, 1, s.length);
    }

    /**
     * Returns the UUID of the player.
     *
     * @param player The player to retrieve the UUID from.
     * @return The UUID in lowercase long notation (xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)
     * @author fivekWBassMachine
     */
    public static String getPlayersUuid(EntityPlayer player) {
        return player.getGameProfile().getId().toString();
    }

    /**
     * Returns the UUID of the player.
     *
     * @param player The name of the player to retrieve the UUID from.
     * @return The UUID in lowercase long notation (xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx) or null if the player was neither found in the servers cache nor by the Mojang API
     * @author fivekWBassMachine
     */
    @Nullable
    public static String getPlayersUuid(String player) throws PlayerNotFoundException {
        GameProfile profile = FMLServerHandler.instance().getServer().getPlayerProfileCache().getGameProfileForUsername(player);
        return profile == null ? null : profile.getId().toString();
    }

    /**
     * Utilities used in commands.
     */
    public static class Command {

        /**
         * Checks whether the command sender is a player.
         *
         * @param sender The command sender
         * @return The casted command sender
         * @throws CommandException "discordutils.command_generic.failure.player_required" - When the command sender is no player.
         * @author fivekWBassMachine
         */
        public static EntityPlayerMP ensureSenderIsPlayerElseOnlyPlayers(ICommandSender sender) throws CommandException {
            if (sender instanceof EntityPlayerMP) return (EntityPlayerMP) sender;
            throw new CommandException("discordutils.command_generic.failure.player_required");
        }

        /**
         * Checks whether the command sender is a player.
         *
         * @param sender The command sender
         * @return The casted command sender
         * @throws PlayerNotFoundException "commands.generic.player.unspecified" - When the command sender is no player.
         * @author fivekWBassMachine
         */
        public static EntityPlayerMP ensureSenderIsPlayerElseUnspecified(ICommandSender sender) throws CommandException {
            if (sender instanceof EntityPlayerMP) return (EntityPlayerMP) sender;
            throw new PlayerNotFoundException("commands.generic.player.unspecified");
        }

        /**
         * Checks whether the command sender can use this command using {@link ICommandSender#canUseCommand(int, String)}.
         *
         * @param sender The command sender
         * @param reqCmdPermLvl The required permission level to use this command or action.
         * @param cmdName The full name of the command - see {@link ChildCmdBase#getFullString()}
         * @throws CommandException When the command sender has insufficient permission.
         * @author fivekWBassMachine
         */
        public static void ensureSenderHasPermission(ICommandSender sender, int reqCmdPermLvl, String cmdName) throws CommandException {
            if (!sender.canUseCommand(reqCmdPermLvl, cmdName))
                throw new CommandException("commands.generic.permission");
        }

        /**
         * Checks whether the passed argument is a selector (basically, a String starting with '@').
         *
         * @param arg The argument to check
         * @throws SyntaxErrorException When the command sender has used a selector
         * @author fivekWBassMachine
         */
        public static void ensureSenderIsNotUsingSelector(String arg) throws SyntaxErrorException {
            if (arg.charAt(0) == '@')
                throw new SyntaxErrorException("discordutils.command_generic.failure.selector_not_allowed");
        }
    }
}
