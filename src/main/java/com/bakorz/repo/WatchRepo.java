package com.bakorz.repo;

import com.bakorz.model.WatchEntry;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing user watch list entries.
 * Defines operations for adding, removing, and querying watch entries.
 * 
 * @author Bakorz
 * @version 1.0
 */
public interface WatchRepo {

    /**
     * Adds a new watch entry.
     * 
     * @param watchEntry WatchEntry object to add
     * @return true if added successfully
     */
    boolean addWatchEntry(WatchEntry watchEntry);

    /**
     * Removes a watch entry by its unique ID.
     * 
     * @param watchId Watch entry ID to remove
     * @return true if removed successfully
     */
    boolean removeWatchEntry(String watchId);

    /**
     * Retrieves all watch entries for a specific user.
     * 
     * @param userId User identifier
     * @return List of WatchEntry objects
     */
    List<WatchEntry> getWatchEntriesByUser(String userId);

    /**
     * Retrieves a watch entry by its unique ID.
     * 
     * @param watchId Watch entry ID
     * @return Optional containing the WatchEntry if found
     */
    Optional<WatchEntry> getWatchEntryById(String watchId);

    /**
     * Retrieves a watch entry by user and media combination.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return Optional containing the WatchEntry if found
     */
    Optional<WatchEntry> getWatchEntryByUserAndMedia(String userId, String mediaId);

    /**
     * Updates an existing watch entry.
     * 
     * @param watchEntry WatchEntry object to update
     * @return true if updated successfully
     */
    boolean update(WatchEntry watchEntry);

    /**
     * Retrieves all watch entries.
     * 
     * @return List of all WatchEntry objects
     */
    List<WatchEntry> getAll();
}
