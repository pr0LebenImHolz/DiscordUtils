package io.github.pr0lebenimholz.discordutils.util.data;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IPlayerDatabaseHandler {

    void addEntry(IPlayerEntry entry);
    void addEntry(UUID mcID, String dcID, IPlayerEntry.Status status);

    IPlayerEntry getEntry(UUID mcID);
    IPlayerEntry getEntry(String dcID);

    boolean hasEntry(UUID mcID);
    boolean hasEntry(String dcID);

    void removeEntry(UUID mcID);
    void removeEntry(String dcID);

}



    /**
     * Gets the link with the passed Minecraft UUID from the cache if it exists.
     *
     * @param mcId The constant Minecraft UUID
     * @return The Link or null if no such link was found
     */
    @Nullable
    IPlayerLink getLink(UUID mcId);
    /**
     * Gets the link with the passed Discord Snowflake ID from the cache if it exists.
     *
     * @param dcId The constant Discord ID in Snowflake format
     * @return The Link or null if no such link was found
     */
    @Nullable
    IPlayerLink getLink(String dcId);
    /**
     * Gets the link with the passed internal ID from the cache if it exists.
     *
     * @param intId The variable internal ID
     * @return The Link or null if no such link was found
     */
    @Nullable
    IPlayerLink getLink(int intId);

    /**
     * Checks if a link has the passed Minecraft UUID.
     *
     * @param mcId The constant Minecraft UUID
     * @return Whether such link was found
     */
    boolean contains(UUID mcId);
    /**
     * Checks if a link has the passed Discord Snowflake ID.
     *
     * @param dcId The constant Discord ID in Snowflake format
     * @return Whether such link was found
     */
    boolean contains(String dcId);
    /**
     * Checks if a link has the passed Minecraft ID.
     *
     * @param intId The variable internal ID
     * @return Whether such link was found
     */
    boolean contains(int intId);

    /**
     * Adds a link to the database and returns its internal ID.
     *
     * @param link The link to add.
     * @return The variable internal ID (basically the index in the list of links)
     */
    int addLink(IPlayerLink link);

    /**
     * Deletes a link with the passed Minecraft UUID.
     *
     * When the link must not be deleted, because e.g. the user is currently banned, it will be flagged for deletion,
     * a flag will be set
     *
     * @param mcId The constant Minecraft UUID
     * @return Whether the link was deleted
     */
    boolean deleteLink(UUID mcId);
    /**
     * Deletes a link with the passed Minecraft UUID.
     *
     * When the link must not be deleted, because e.g. the user is currently banned, it will be flagged for deletion.
     *
     * @param dcId The constant Discord ID in Snowflake format
     * @return Whether the link was deleted
     */
    boolean deleteLink(String dcId);
    /**
     * Deletes a link with the passed Minecraft UUID.
     *
     * When the link must not be deleted, because e.g. the user is currently banned, it will be flagged for deletion
     *
     * @param intId The variable internal ID
     * @return Whether the link was deleted
     */
    boolean deleteLink(int intId);
}
