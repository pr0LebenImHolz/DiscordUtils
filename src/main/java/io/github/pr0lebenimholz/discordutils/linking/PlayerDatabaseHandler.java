package io.github.pr0lebenimholz.discordutils.linking;

// TODO: 21.03.22 WIP, maybe merge those 2 DBs
// TODO: 21.03.22 author missing. schwitze?


// TODO: 26.03.22 DONT COMMIT; REAL FILE IS ON REMOTE!!!


import java.util.UUID;

/**
 * Handles the databases located on the server under `/[world]/discordutils/PendingLinks.csv` and
 * `/[world]/discordutils/ConfirmedLinks.csv`
 *
 * @author
 */
public class LinkDatabaseHandler implements IPlayerDatabaseHandler {

    @Override
    public void addEntry(IPlayerEntry entry) {

    }

    @Override
    public void addEntry(UUID mcID, String dcID, IPlayerEntry.Status status) {

    }

    @Override
    public IPlayerEntry getEntry(UUID mcID) {
        return null;
    }

    @Override
    public IPlayerEntry getEntry(String dcID) {
        return null;
    }

    @Override
    public boolean hasEntry(UUID mcID) {
        return false;
    }

    @Override
    public boolean hasEntry(String dcID) {
        return false;
    }

    @Override
    public void removeEntry(UUID mcID) {

    }

    @Override
    public void removeEntry(String dcID) {

    }
}