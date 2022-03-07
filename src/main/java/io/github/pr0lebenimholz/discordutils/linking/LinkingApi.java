package io.github.pr0lebenimholz.discordutils.linking;

import io.github.pr0lebenimholz.discordutils.util.api.SimpleApi;
import org.apache.logging.log4j.Logger;

public class LinkingApi extends SimpleApi {

    public static final String VERSION = "1.0.0";

    public LinkingApi(Logger logger, boolean tls, String host, int port, String path, String authorization) {
        super(logger, tls, host, port, path, "Bearer " + authorization);
    }
}
