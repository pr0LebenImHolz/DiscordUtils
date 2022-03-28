package io.github.pr0lebenimholz.discordutils.linking;

import io.github.pr0lebenimholz.discordutils.util.api.MismatchedVersionException;
import io.github.pr0lebenimholz.discordutils.util.api.SimpleApi;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.UUID;

/**
 * Handles the connection between a Discord bot and this mod
 *
 * @author fivekWBassMachine
 */
public class LinkingApi extends SimpleApi {

    public static final String VERSION = "1.0.0";

    public LinkingApi(Logger logger, boolean tls, String host, int port, String path, String token) throws MismatchedVersionException, IOException {
        super(logger, tls, host, port, path, token);
        //super(logger, tls, host, port, path, "Bearer " + token, VERSION, Endpoint.VERSIONS.method, Endpoint.VERSIONS.path);
    }

    /**
     * Performs a request to the endpoint {@link Endpoint#TAKE_PENDING_LINK} which returns the pending information and
     * makes the Discord Bot to remove the pending link when one exist.
     *
     * @param uuid The UUID of the executing player.
     * @param senderName The name of the executing player.
     * @return The pending information TODO
     */
    @Nullable
    public PlayerEntry takePendingLink(UUID uuid, String senderName) {
        // TODO: 26.03.22 implement
        return null;
    }

    /**
     *
     * @param uuid
     * @param id
     * @param role
     * @return
     */
    public boolean updateRole(UUID uuid, String id, String role) {
        // TODO: 26.03.22 implement
        return false;
    }

    public PlayerEntry getInformation(UUID uuid) {
        // TODO: 26.03.22 implement
        return null;
    }

    public PlayerEntry getInformation(String uuid) {
        // TODO: 26.03.22 implement
        return null;
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
        TAKE_PENDING_LINK(HttpMethod.GET, "1.0.0/take_pending_link"),
        UPDATE_ROLE(HttpMethod.POST, "1.0.0/update_role"),
        GET_INFORMATION(HttpMethod.GET, "1.0.0/get_information");

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
