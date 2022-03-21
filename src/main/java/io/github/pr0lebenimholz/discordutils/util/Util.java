package io.github.pr0lebenimholz.discordutils.util;

public class Math {
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
}
