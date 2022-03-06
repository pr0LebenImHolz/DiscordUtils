package io.github.pr0lebenimholz.discordutils.util.api;

import io.netty.handler.codec.http.HttpMethod;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class SimpleApi {

    protected final Logger logger;
    private final boolean tls;
    private final String host;
    private final int port;
    private final String path;
    private final String authorization;

    public SimpleApi(Logger logger, boolean tls, String host, int port, String path, String authorization) {
        this.logger = logger;
        this.tls = tls;
        this.host = host;
        this.port = port;
        this.path = path;
        this.authorization = authorization;
    }

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

    protected static class Response {

        public final int code;
        public final String content;

        public Response(int code, String content) {
            this.code = code;
            this.content = content;
        }
    }

}
