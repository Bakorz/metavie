package com.bakorz.model;

/**
 * Represents a watch history entry for a user and media item
 */
public class WatchEntry {
    private String watchId;
    private String userId;
    private String mediaId;
    private String mediaSource; // "MAL", "TMDB", or "FILE" to track which API

    public WatchEntry() {
    }

    public WatchEntry(String watchId, String userId, String mediaId) {
        this.watchId = watchId;
        this.userId = userId;
        this.mediaId = mediaId;
    }

    // Getters and Setters
    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaSource() {
        return mediaSource;
    }

    public void setMediaSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }

    @Override
    public String toString() {
        return "WatchEntry{" +
                "watchId='" + watchId + '\'' +
                ", userId='" + userId + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", mediaSource='" + mediaSource + '\'' +
                '}';
    }
}
