package io.github.pr0lebenimholz.discordutils.util;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class Util {
    /**
     * Performs a PERFormant POSitive DIVision and Rounds the quotient UP.
     *
     * @param dividend
     * @param divisor
     * @returns Rounded up quotient
     *
     * Credits: https://stackoverflow.com/a/7446742
     */
    public static int perfPosDivRUp(int dividend, int divisor) {
        return (dividend + divisor - 1) / divisor;
    }

    /**
     * Shifts all strings in the array by 1. [a,b,c] -> [b,c]
     *
     * @param s The array to shift
     * @returns A new, shifted array
     */
    public static String[] shiftArgs(@Nonnull String[] s) {
        if(s.length == 1 || s.length == 2) return new String[0];
        return Arrays.copyOfRange(s, 1, s.length);
    }
}
