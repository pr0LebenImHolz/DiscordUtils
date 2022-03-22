package io.github.pr0lebenimholz.discordutils.util.minecraft;

/**
 * Used to mark the parent of the child command
 *
 * Params
 * - {@link String} value: The classpath of the parent command
 *
 * @author fivekWBassMachine
 */
public @interface ParentCmd {

    /** The classpath of the parent command. */
    String value();
}
