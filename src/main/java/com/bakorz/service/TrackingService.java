package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;

public class TrackingService {
    private FileWatchRepo watchRepo;
    private CatalogService catalogService;

    public TrackingService(FileWatchRepo watchRepo, CatalogService catalogService) {
        this.watchRepo = watchRepo;
        this.catalogService = catalogService;
    }

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

    public boolean addToWatchList(String userId, String mediaId, String mediaSource) {
        return addToWatchList(userId, mediaId, mediaSource, "PLAN_TO_WATCH");
    }

    public boolean addToWatchList(String userId, String mediaId) {
        return addToWatchList(userId, mediaId, "FILE", "PLAN_TO_WATCH");
    }

    public boolean removeFromWatchList(String userId, String mediaId) {
        Optional<WatchEntry> entry = watchRepo.getWatchEntryByUserAndMedia(userId, mediaId);
        if (entry.isPresent()) {
            return watchRepo.removeWatchEntry(entry.get().getWatchId());
        }
        return false;
    }

    public List<WatchEntry> getUserWatchList(String userId) {
        return watchRepo.getWatchEntriesByUser(userId);
    }

    public List<WatchEntry> getCurrentlyWatching(String userId) {
        return watchRepo.getWatchEntriesByUser(userId);
    }

    public boolean isWatching(String userId, String mediaId) {
        return watchRepo.getWatchEntryByUserAndMedia(userId, mediaId).isPresent();
    }

    public Optional<WatchEntry> getWatchEntry(String userId, String mediaId) {
        return watchRepo.getWatchEntryByUserAndMedia(userId, mediaId);
    }

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
