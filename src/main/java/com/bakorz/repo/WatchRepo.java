package com.bakorz.repo;

import com.bakorz.model.WatchEntry;
import java.util.List;
import java.util.Optional;

/**
 * Interface for watch repository operations
 */
public interface WatchRepo {

    /**
     * Add a watch entry for a user
     * 
     * @param watchEntry The watch entry to add
     * @return true if added successfully
     */
    boolean addWatchEntry(WatchEntry watchEntry);

    /**
     * Remove a watch entry by ID
     * 
     * @param watchId The watch entry ID to remove
     * @return true if removed successfully
     */
    boolean removeWatchEntry(String watchId);

    /**
     * Get all watch entries for a user
     * 
     * @param userId The user ID
     * @return List of watch entries for the user
     */
    List<WatchEntry> getWatchEntriesByUser(String userId);

    /**
     * Get a watch entry by ID
     * 
     * @param watchId The watch entry ID
     * @return Optional containing the watch entry if found
     */
    Optional<WatchEntry> getWatchEntryById(String watchId);

    /**
     * Get a watch entry by user ID and media ID
     * 
     * @param userId  The user ID
     * @param mediaId The media ID
     * @return Optional containing the watch entry if found
     */
    Optional<WatchEntry> getWatchEntryByUserAndMedia(String userId, String mediaId);

    /**
     * Update a watch entry
     * 
     * @param watchEntry The watch entry to update
     * @return true if updated successfully
     */
    boolean update(WatchEntry watchEntry);

    /**
     * Get all watch entries
     * 
     * @return List of all watch entries
     */
    List<WatchEntry> getAll();
}
