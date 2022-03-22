package io.github.pr0lebenimholz.discordutils.util.data;

import io.github.pr0lebenimholz.discordutils.linking.ModuleLinking;
import io.github.pr0lebenimholz.discordutils.ranks.ModuleRanks;
import io.github.pr0lebenimholz.discordutils.status.ModuleStatus;
import io.github.pr0lebenimholz.discordutils.status.StatusApi;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.HashMap;

/**
 * Handles the main configuration file located under `/config/discordutils.conf`.
 *
 * @author fivekwBassMachine
 */
public class ConfigHandler {

    private static Configuration config;
    private static HashMap<String, Boolean> modules;

    /** Whether to use TLS for the status API. */
    public static boolean statusApiTls;
    /** The IP or FQDN of the remote host for the status API. */
    public static String statusApiHost;
    /** The remote port for the status API. */
    public static int statusApiPort;
    /** The remote basepath for the status API. */
    public static String statusApiPath;
    /** The token for the status API. */
    public static String statusApiToken;

    // TODO: 21.03.22 This needs to be updated. See util.api.SimpleApi, See status.StatusApi
    /** The URL for the linking API including scheme (http(s)), IP or FQDN, port and basepath. e.g. `https://example.com:443/foo/bar/` */
    @Deprecated
    public static String linkingApiUrl;
    /** The token for the status API. */
    public static String linkingApiToken;
    /** Information displayed when entering /discord. */
    public static FallbackEntry<String, String> linkingInfo;

    /** Notifies the (enabled) linking module to update the Discord role when the minecraft role is updated. */
    public static Boolean ranksNotifyDiscord;
    /** Ignores players with unknown ranks */
    public static Boolean ranksIgnoreUnknown;
    /**  */
    // TODO: 21.03.22 What the hell did I wanted to implement??? ~fivek
    public static HashMap<String, Integer> ranksPlaytime;

    /**
     * Initiates and/ or creates the main configuration file.
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
                "This module sends updates to a Discord bot which displays the current server status.\nAPI Version: " + StatusApi.VERSION + "\nSee https://github.com/pr0LebenImHolz/MinecraftServerStatusBot",
                true);
        statusApiTls = config.getBoolean("api_tls", category, true, "Enables Top Level Security (https - highly recommended)");
        statusApiHost = config.getString("api_host", category, "", "The FQDN or IP-Address of the API (e.g. example.com)");
        statusApiPort = config.getInt("api_port", category, 443, 0, 65535, "The port of the API");
        statusApiPath = config.getString("api_path", category, "/", "The path of the API");
        statusApiToken = config.getString("api_token", category, "", "The token for the API");

        category = createModule(
                ModuleLinking.KEY,
                "This module enables the player to link his Discord and Minecraft account",
                true);
        linkingApiUrl = config.getString("api_url", category, "", "The API URL (https://example.com:443/foo/bar)");
        linkingApiToken = config.getString("api_token", category, "", "The token defined in the bots config");
        linkingInfo = new FallbackEntry<>(
                config.getString("info_fallback_content", category, "en_us", "The fallback info to display when entered /discord (should be in english)"),
                config.getString("info_native_content", category, "en_us", "The default info to display when entered /discord (should be in the language most players are using"),
                config.getString("info_native_lang", category, "en_us", "The language code of the default info (Use language codes like for resource packs e.g. en_us - See #Languages > Available Languages (table) > Locale Code > In-Game @ https://minecraft.fandom.com/wiki/Language?oldid=2105545#Languages)")
        );

        category = createModule(
                ModuleRanks.KEY,
                "This module updates ranks (FTB-Utilities) based on playing time",
                false);
        ranksNotifyDiscord = config.getBoolean("notify_link", category, true, "Notifies the (enabled) linking module to update the Discord role");
        ranksIgnoreUnknown = config.getBoolean("ignore_unknown", category, true, "Ignores players with unknown ranks");
        // TODO: 21.03.22 ^
        ranksPlaytime = null;

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
     * Returns the enabled property of passed module (under the hood a category)
     *
     * @param module The module name
     * @return Whether the module is enabled
     */
    public static boolean isModuleEnabled(String module) {
        return modules.get(module);
    }

    /**
     * Data object; comparable to a Map with only a single value with a predefined default value which is returned
     * when the key is not found on the Map.
     * @author fivekWBassMachine
     */
    public static class FallbackEntry<K, V> {
        private final V fallbackValue;
        private final V value;
        private final K key;

        /**
         * @param key
         * @param value The value to use when the passed key equals the defined key
         * @param fallbackValue The default value to use when the passed key differs from the default key
         */
        public FallbackEntry(K key, V value, V fallbackValue) {
            this.key = key;
            this.value = value;
            this.fallbackValue = fallbackValue;
        }

        /**
         * Returns the default value when the key equals the default key or the fallback value otherwise.
         *
         * @param key
         */
        public V get(K key) {
            return this.key.equals(key) ? this.value : this.fallbackValue;
        }
    }
}
