package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;

/**
 * Service class for managing user watch list and tracking.
 * Provides operations to add, remove, check, and retrieve watch entries.
 * Handles watch list persistence through FileWatchRepo.
 * Supports tracking currently watching media across different sources.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class TrackingService {
    /** Repository for managing watch list data */
    private FileWatchRepo watchRepo;

    /**
     * Constructor for TrackingService.
     * 
     * @param watchRepo FileWatchRepo instance
     */
    public TrackingService(FileWatchRepo watchRepo) {
        this.watchRepo = watchRepo;
    }

    /**
     * Adds a media item to user's watch list with full details.
     * Prevents duplicate entries for the same user and media.
     * 
     * @param userId      User identifier
     * @param mediaId     Media item identifier
     * @param mediaSource Source of media (MAL, TMDB, FILE)
     * @param status      Watch status (currently unused, kept for future expansion)
     * @return true if watch entry was added successfully, false if already exists
     */
    public boolean addToWatchList(String userId, String mediaId, String mediaSource, String status) {
        Optional<WatchEntry> existing = watchRepo.getWatchEntryByUserAndMedia(userId, mediaId);
        if (existing.isPresent()) {
            System.out.println("Media is already in watch list!");
            return false;
        }

        String watchId = UUID.randomUUID().toString();
        WatchEntry entry = new WatchEntry(watchId, userId, mediaId);
        entry.setMediaSource(mediaSource != null ? mediaSource : "FILE");

        return watchRepo.addWatchEntry(entry);
    }

    /**
     * Adds a media item to user's watch list with default status.
     * 
     * @param userId      User identifier
     * @param mediaId     Media item identifier
     * @param mediaSource Source of media (MAL, TMDB, FILE)
     * @return true if watch entry was added successfully, false if already exists
     */
    public boolean addToWatchList(String userId, String mediaId, String mediaSource) {
        return addToWatchList(userId, mediaId, mediaSource, "PLAN_TO_WATCH");
    }

    /**
     * Adds a media item to user's watch list with FILE as default source.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return true if watch entry was added successfully, false if already exists
     */
    public boolean addToWatchList(String userId, String mediaId) {
        return addToWatchList(userId, mediaId, "FILE", "PLAN_TO_WATCH");
    }

    /**
     * Removes a media item from user's watch list.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return true if watch entry was removed successfully, false if not found
     */
    public boolean removeFromWatchList(String userId, String mediaId) {
        Optional<WatchEntry> entry = watchRepo.getWatchEntryByUserAndMedia(userId, mediaId);
        if (entry.isPresent()) {
            return watchRepo.removeWatchEntry(entry.get().getWatchId());
        }
        return false;
    }

    /**
     * Retrieves all watch entries for a specific user.
     * Returns currently watching media items.
     * 
     * @param userId User identifier
     * @return List of WatchEntry objects belonging to the user
     */
    public List<WatchEntry> getCurrentlyWatching(String userId) {
        return watchRepo.getWatchEntriesByUser(userId);
    }

    /**
     * Checks if a media item is in user's watch list.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return true if media is being watched by user
     */
    public boolean isWatching(String userId, String mediaId) {
        return watchRepo.getWatchEntryByUserAndMedia(userId, mediaId).isPresent();
    }
}
