package io.github.pr0lebenimholz.discordutils.data;

import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.status.ModuleStatus;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashMap;

public class ConfigHandler {

    private static Configuration config;
    private static HashMap<String, Boolean> modules;

    // TODO: 01.03.22 lowercase
    public static String STATUS_API_VERSION;
    public static String STATUS_API_URL;
    public static String STATUS_API_TOKEN;

    public static String LINKING_API_VERSION;
    public static String LINKING_API_URL;
    public static String LINKING_API_TOKEN;

    // TODO: 01.03.22 maybe new module for ranks update?

    /*
    // farmworld - implement when required
    public static String FARMWORLD_NAME;
    public static String FARMWORLD_RESET;
    */

    /**
     * Initiates and/ or creates the config file.
     *
     * @param mainConfig The main configuration file
     */
    public static void init(File mainConfig) {
        config = new Configuration(mainConfig);
        modules = new HashMap<>();
        String category;
        // passing an array to get*(... validValues) does not append those values to the comment - see ConfigHandler#createComment
        String[] validValues;

        category = createModule(
                ModuleStatus.KEY,
                "This module sends updates to a Discord bot which displays the current server status. See https://github.com/pr0LebenImHolz/MinecraftServerStatusBot",
                true);
        validValues = new String[] {"1.0.0", "1.0.1"};
        STATUS_API_VERSION = config.getString("api_version", category, "1.0.0", createComment("The API version to use.", validValues), validValues);
        STATUS_API_URL = config.getString("api_url", category, "", "The API URL (https://example.com:443/foo/bar)");
        STATUS_API_TOKEN = config.getString("api_token", category, "", "The token defined in the bots config");

        category = createModule(
                ModuleLinking.KEY,
                "This module enables the player to link his Discord and Minecraft account",
                true);
        validValues = new String[] {"1.0.0", "1.0.1"};
        LINKING_API_VERSION = config.getString("api_version", category, "1.0.0", createComment("The API version to use", validValues), validValues);
        LINKING_API_URL = config.getString("api_url", category, "", "The API URL (https://example.com:443/foo/bar)");
        LINKING_API_TOKEN = config.getString("api_token", category, "", "The token defined in the bots config");

        /*
        // farmworld - implement when required
        category = createModule(
                Module.FARMWORLD.key,
                "This module creates a dimension for mining/ farming which can be reset regularly and ensures that no players are in this dimension when resetting",
                true);
        FARMWORLD_NAME = config.getString("name", category, "Mining", "The name of the dimension");
        // default value is 02:00 am every monday
        FARMWORLD_RESET = config.getString("reset", category, "0 2 * * 0", "The reset cycle (like cron job; supported values: 5 × [*|,|-] unsupported values: [?|L|W|#] (See also https://en.wikipedia.org/wiki/Cron#CRON_expression)");
        */
        config.save();
    }

    /**
     * 'Creates' a new module (under the hood a category)
     *
     * @param category The module/ category name
     * @param comment The comment above the category
     * @param enabledByDefault Whether the module is enabled by default or not. Usually (i.e. for non-special modules) this is true
     * @return The module/ category name
     */
    private static String createModule(String category, String comment, boolean enabledByDefault) {
        config.addCustomCategoryComment(category, comment);
        config.getBoolean("enabled", category, enabledByDefault, "Enables this Module");
        modules.put(category, enabledByDefault);
        return category;
    }

    /**
     * Appends valid values to the comment because forge isn't doing its job.
     *
     * @param comment The comment to modify
     * @param validValues The valid values
     * @return The modified comment
     */
    private static String createComment(String comment, String[] validValues) {
        return comment + "Valid values: [" + String.join("|", validValues) + "]";
    }

    /**
     * Returns the enabled property of passed module (under the hood a category)
     *
     * @param module The module name
     * @return Whether the module is enabled
     */
    public static boolean isModuleEnabled(String module) {
        return modules.get(module);
    }
}
