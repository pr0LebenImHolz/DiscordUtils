package io.github.pr0lebenimholz.discordutils.util.api;

public class MismatchedVersionException extends Exception {
    public MismatchedVersionException(String expected, String returned) {
        super("Invalid version. Expected " + expected + ", got " + returned + ".");
    }
}
