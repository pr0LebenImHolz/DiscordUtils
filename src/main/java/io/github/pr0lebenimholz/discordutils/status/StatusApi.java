package io.github.pr0lebenimholz.discordutils.status;

import io.github.pr0lebenimholz.discordutils.util.api.MismatchedVersionException;
import io.github.pr0lebenimholz.discordutils.util.api.SimpleApi;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

// TODO: 01.03.22 implement - different version support
public class StatusApi extends SimpleApi {

    public static final String VERSION = "2.0.0";

    public StatusApi(Logger logger,
                     boolean tls,
                     String host,
                     int port,
                     String path,
                     String token) throws MismatchedVersionException, IOException {
        super(logger, tls, host, port, path, "Bearer " + token);
        Response response = this.request(Endpoint.VERSIONS.method, Endpoint.VERSIONS.path);
        List<String> versions = Arrays.asList(response.content.split("\n"));
        this.logger.info("API: DiscordUtils@" + VERSION + " Remote@[" + String.join(",", versions) + "]");
        if (response.code != 200 || !versions.contains(VERSION)) throw new MismatchedVersionException(VERSION, response.content);
    }

    public void notifyStarting() {
        this.request(Endpoint.NOTIFY_STARTING);
    }

    public void notifyStarted(int slots, String motd) {
        try {
            this.request(Endpoint.NOTIFY_STARTED, "?slots=" + slots + "&motd=" + URLEncoder.encode(motd, StandardCharsets.UTF_8.toString()));
        } catch(UnsupportedEncodingException ignored) { /* IllegalState */ }
    }

    public void notifyStopping() {
        this.request(Endpoint.NOTIFY_STOPPING);
    }

    public void notifyStopped() {
        this.request(Endpoint.NOTIFY_STOPPED);
    }

    public void notifyPlayerCount(int player) {
        this.request(Endpoint.NOTIFY_PLAYER_COUNT, "?count=" + player);
    }

    private void request(Endpoint endpoint) {
        try {
            this.request(endpoint.method, endpoint.path);
            this.logger.debug("Updated status");
        } catch (IOException e) {
            this.logger.error("Unable to update status: " + endpoint, e);
        }
    }
    private void request(Endpoint endpoint, String data) {
        try {
            this.request(endpoint.method, endpoint.path + data);
            this.logger.debug("Updated status");
        } catch (IOException e) {
            this.logger.error("Unable to update status: " + endpoint, e);
        }
    }

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
