package io.github.pr0lebenimholz.discordutils.linking;

public class LinkDatabaseHandler {

    /*
     * ./world/@modid/PendingLinks.csv
     * ---
     * @mc,@dc
     * @dc,@mc
     * ---
     * 1st entry is requested by @mc, needs to be confirmed by @dc
     * 2nd entry is requested by @dc, needs to be confirmed by @mc
     *
     * ===
     *
     * ./world/@modid/ConfirmedLinks.csv
     * ---
     * @mc,@dc
     * @dc,@mc
     * ---
     * the order of @mc and @dc is inherited from PendingLinks.csv, but doesn't matter anymore
     *
     * ===
     *
     * because both the discord name (Clyde#1234) and minecraft name can be changed,
     * the discord id (=snowflake) and minecraft id (=uuid) are used
     */
}
