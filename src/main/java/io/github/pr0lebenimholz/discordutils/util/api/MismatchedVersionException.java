package io.github.pr0lebenimholz.discordutils.util.api;

/**
 * Thrown when a {@link SimpleApi} could not connect due to differing versions.
 *
 * @author fivekWBassMachine
 */
public class MismatchedVersionException extends Exception {
    public MismatchedVersionException(String expected, String returned) {
        super("Invalid version. Expected " + expected + ", got " + returned + ".");
    }
}
