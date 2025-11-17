package com.bakorz.model;

/**
 * Represents a watch list entry for a user's currently watching media.
 * Links a user to a media item they are tracking.
 * Includes media source information for cross-platform tracking.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class WatchEntry {
    /** Unique identifier for this watch entry */
    private String watchId;

    /** User ID who is watching the media */
    private String userId;

    /** Media item ID being watched */
    private String mediaId;

    /** Source of the media (MAL, TMDB, FILE) */
    private String mediaSource;

    /**
     * Default constructor for WatchEntry.
     */
    public WatchEntry() {
    }

    /**
     * Constructor for creating a watch entry.
     * 
     * @param watchId Unique watch entry ID
     * @param userId  User ID
     * @param mediaId Media item ID
     */
    public WatchEntry(String watchId, String userId, String mediaId) {
        this.watchId = watchId;
        this.userId = userId;
        this.mediaId = mediaId;
    }

    // Getters and Setters with documentation

    /**
     * Gets the watch entry ID.
     * 
     * @return Unique watch entry identifier
     */
    public String getWatchId() {
        return watchId;
    }

    /**
     * Sets the watch entry ID.
     * 
     * @param watchId Watch entry identifier to set
     */
    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    /**
     * Gets the user ID.
     * 
     * @return User identifier
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * 
     * @param userId User identifier to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the media ID.
     * 
     * @return Media item identifier
     */
    public String getMediaId() {
        return mediaId;
    }

    /**
     * Sets the media ID.
     * 
     * @param mediaId Media item identifier to set
     */
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    /**
     * Gets the media source.
     * 
     * @return Media source (MAL, TMDB, FILE)
     */
    public String getMediaSource() {
        return mediaSource;
    }

    /**
     * Sets the media source.
     * 
     * @param mediaSource Media source to set
     */
    public void setMediaSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }

    /**
     * Returns a string representation of the WatchEntry.
     * 
     * @return String containing watch entry information
     */
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
