package io.github.pr0lebenimholz.discordutils.status;

import org.apache.logging.log4j.Logger;

// TODO: 01.03.22 implement - different version support
public class StatusApi {

    private final Logger logger;
    private final String version;
    private final String url;
    private final String token;

    public StatusApi(Logger logger, String version, String url, String token) {
        this.logger = logger;
        this.version = version;
        this.url = url;
        this.token = token;

        this.checkVersion();
    }

    public void notifyStarting() {

    }

    public void notifyStarted(String motd) {

    }

    public void notifyStopping() {

    }

    public void notifyStopped() {

    }

    public void notifyPlayerCount(int player) {

    }

    private boolean checkVersion() {
        return false;
    }

    private void request() {
        // TODO: 28.02.22 implement
        switch(this.version) {
            case "1.0.1":
                break;
            // 1.0.0
            default:
                break;
        }
    }
}
