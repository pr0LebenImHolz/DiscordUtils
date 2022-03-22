package io.github.pr0lebenimholz.discordutils.util.api;

import io.netty.handler.codec.http.HttpMethod;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The base of an HTTP API.
 *
 * @author fivekWBassMachine
 */
public abstract class SimpleApi {

    protected final Logger logger;
    private final boolean tls;
    private final String host;
    private final int port;
    private final String path;
    private final String authorization;

    /**
     *
     * @param logger The logger (The logger of the class using the API can be used)
     * @param tls Whether to use TLS (https://)
     * @param host The remote host (IP or FQDN)
     * @param port The remote port
     * @param path The remote basepath
     * @param authorization The value of the [Authorization header](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Authorization) (&lt;auth-scheme&gt; &lt;credentials&gt;)
     */
    public SimpleApi(Logger logger, boolean tls, String host, int port, String path, String authorization) {
        this.logger = logger;
        this.tls = tls;
        this.host = host;
        this.port = port;
        this.path = path;
        this.authorization = authorization;
    }

    /**
     * Performs an HTTP request with the specified method and path.
     *
     * @param method The [HTTP Request Method](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods)
     * @param path The remote path, query and hash
     * @return The response from the server
     * @throws IOException Thrown by:
     *  {@link URL#openConnection()},
     *  {@link HttpURLConnection#setRequestMethod(String)},
     *  {@link HttpURLConnection#getInputStream()},
     *  {@link BufferedReader#read()},
     *  {@link BufferedReader#close()},
     *  {@link HttpURLConnection#getResponseCode()}
     */
    protected Response request(HttpMethod method, String path) throws IOException {
        URL url = new URL(this.tls ? "https" : "http", this.host, this.port, path == null ? this.path : this.path + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Authorization", this.authorization);
        con.setRequestMethod(method.name());
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder builder = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            builder.append((char) c);
        }
        reader.close();
        return new Response(con.getResponseCode(), builder.toString());
    }

    /**
     * Contains important parts of the HTTP response.
     */
    protected static class Response {

        /**
         * The HTTP response code.
         */
        public final int code;
        /**
         * The HTTP response body.
         */
        public final String content;

        public Response(int code, String content) {
            this.code = code;
            this.content = content;
        }
    }

}
