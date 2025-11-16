package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;

/**
 * TrackingService manages user's watch history
 * Tracks what users are currently watching
 */
public class TrackingService {
    private FileWatchRepo watchRepo;
    private CatalogService catalogService;

    public TrackingService(FileWatchRepo watchRepo, CatalogService catalogService) {
        this.watchRepo = watchRepo;
        this.catalogService = catalogService;
    }

    /**
     * Add a media item to user's watch list
     */
    public boolean addToWatchList(String userId, String mediaId, String mediaSource, String status) {
        // Check if already exists
        Optional<WatchEntry> existing = watchRepo.getWatchEntryByUserAndMedia(userId, mediaId);
        if (existing.isPresent()) {
            System.out.println("Media is already in watch list!");
            return false;
        }

        // Create new watch entry
        String watchId = UUID.randomUUID().toString();
        WatchEntry entry = new WatchEntry(watchId, userId, mediaId);
        entry.setMediaSource(mediaSource != null ? mediaSource : "FILE");

        return watchRepo.addWatchEntry(entry);
    }

    /**
     * Add to watch list with default "PLAN_TO_WATCH" status
     */
    public boolean addToWatchList(String userId, String mediaId, String mediaSource) {
        return addToWatchList(userId, mediaId, mediaSource, "PLAN_TO_WATCH");
    }

    /**
     * Add to watch list with default source and status (backward compatibility)
     */
    public boolean addToWatchList(String userId, String mediaId) {
        return addToWatchList(userId, mediaId, "FILE", "PLAN_TO_WATCH");
    }

    /**
     * Remove from watch list
     */
    public boolean removeFromWatchList(String userId, String mediaId) {
        // Find the watch entry first
        Optional<WatchEntry> entry = watchRepo.getWatchEntryByUserAndMedia(userId, mediaId);
        if (entry.isPresent()) {
            return watchRepo.removeWatchEntry(entry.get().getWatchId());
        }
        return false;
    }

    /**
     * Get all watch entries for a user
     */
    public List<WatchEntry> getUserWatchList(String userId) {
        return watchRepo.getWatchEntriesByUser(userId);
    }

    /**
     * Get currently watching (returns all watch entries for user)
     */
    public List<WatchEntry> getCurrentlyWatching(String userId) {
        return watchRepo.getWatchEntriesByUser(userId);
    }

    /**
     * Check if user is watching a specific media
     */
    public boolean isWatching(String userId, String mediaId) {
        return watchRepo.getWatchEntryByUserAndMedia(userId, mediaId).isPresent();
    }

    /**
     * Get watch entry for a specific media and user
     */
    public Optional<WatchEntry> getWatchEntry(String userId, String mediaId) {
        return watchRepo.getWatchEntryByUserAndMedia(userId, mediaId);
    }

    /**
     * Get enriched watch list with media details
     */
    public List<MediaItem> getWatchListWithDetails(String userId) {
        List<MediaItem> result = new ArrayList<>();
        List<WatchEntry> entries = watchRepo.getWatchEntriesByUser(userId);

        for (WatchEntry entry : entries) {
            Optional<MediaItem> media = catalogService.getById(entry.getMediaId());
            media.ifPresent(result::add);
        }

        return result;
    }
}
