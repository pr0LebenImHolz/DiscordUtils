package io.github.pr0lebenimholz.discordutils.util;

/**
 * Thrown when a static variable is accessed before it is initialized.
 *
 * @author fivekWBassMachine
 */
public class NotInitializedException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message The detail message
     */
    public NotInitializedException(String message) {
        super(message);
    }
}
