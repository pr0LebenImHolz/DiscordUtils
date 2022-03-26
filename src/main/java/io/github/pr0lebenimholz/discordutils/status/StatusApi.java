package io.github.pr0lebenimholz.discordutils.status;

import io.github.pr0lebenimholz.discordutils.util.api.MismatchedVersionException;
import io.github.pr0lebenimholz.discordutils.util.api.SimpleApi;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
/**
 * Handles the connection between the Discord bot (https://github.com/pr0LebenImHolz/MinecraftServerStatusBot - API Version 2.0.0) and this mod
 *
 * @author fivekWBassMachine
 */
public class StatusApi extends SimpleApi {

    public static final String VERSION = "2.0.0";

    public StatusApi(Logger logger,
                     boolean tls,
                     String host,
                     int port,
                     String path,
                     String token) throws MismatchedVersionException, IOException {
        super(logger, tls, host, port, path, "Bearer " + token, VERSION, Endpoint.VERSIONS.method, Endpoint.VERSIONS.path);
    }

    /**
     * Called to display the bot as starting.
     *
     * @api-versions 2.0.0
     */
    public void notifyStarting() {
        this.request(Endpoint.NOTIFY_STARTING);
    }

    // TODO: 21.03.22 Which MOTD to use? pretty, raw, escaped, ...?
    /**
     * Called to display the bot as started/ online.
     *
     * @api-versions 2.0.0
     * @param slots The slots the server has
     * @param motd The MOTD configured in the server.properties
     */
    public void notifyStarted(int slots, String motd) {
        try {
            this.request(Endpoint.NOTIFY_STARTED, "?slots=" + slots + "&motd=" + URLEncoder.encode(motd, StandardCharsets.UTF_8.toString()));
        } catch(UnsupportedEncodingException ignored) { /* IllegalState */ }
    }

    /**
     * Called to display the bot as stopping.
     *
     * @api-versions 2.0.0
     */
    public void notifyStopping() {
        this.request(Endpoint.NOTIFY_STOPPING);
    }

    /**
     * Called to display the bot as stopped.
     *
     * @api-versions 2.0.0
     */
    public void notifyStopped() {
        this.request(Endpoint.NOTIFY_STOPPED);
    }

    /**
     * Called to display a new player count.
     *
     * @param count The amount of players currently playing on the server
     * @api-versions 2.0.0
     */
    public void notifyPlayerCount(int count) {
        this.request(Endpoint.NOTIFY_PLAYER_COUNT, "?count=" + count);
    }

    /**
     * Main request method; all public endpoints are passed to this method
     *
     * @param endpoint The endpoint to perform the request on
     */
    private void request(Endpoint endpoint) {
        try {
            this.request(endpoint.method, endpoint.path);
            this.logger.debug("Updated status");
        } catch (IOException e) {
            this.logger.error("Unable to update status: " + endpoint, e);
        }
    }
    /**
     * Main request method; all public endpoints are passed to this method
     *
     * @param endpoint The endpoint to perform the request on
     * @param data The data to pass with the request (i.e. the query string)
     */
    private void request(Endpoint endpoint, String data) {
        try {
            this.request(endpoint.method, endpoint.path + data);
            this.logger.debug("Updated status");
        } catch (IOException e) {
            this.logger.error("Unable to update status: " + endpoint, e);
        }
    }

    /**
     * This holds all valid endpoints currently supported. An endpoint holds the HTTP request method and path.
     *
     * @author fivekWBassMachine
     */
    private enum Endpoint {
        VERSIONS(HttpMethod.GET, "versions"),
        NOTIFY_STARTING(HttpMethod.POST, "2.0.0/notify/starting"),
        NOTIFY_STARTED(HttpMethod.POST, "2.0.0/notify/started"),
        NOTIFY_STOPPING(HttpMethod.POST, "2.0.0/notify/stopping"),
        NOTIFY_STOPPED(HttpMethod.POST, "2.0.0/notify/stopped"),
        NOTIFY_PLAYER_COUNT(HttpMethod.POST, "2.0.0/notify/player");

        public final HttpMethod method;
        public final String path;

        Endpoint(HttpMethod method, String path) {
            this.method = method;
            this.path = path;
        }

        @Override
        public String toString() {
            return "[" + this.method.toString() + " " + this.path + "]";
        }
    }
}
