package com.bakorz.model;

/**
 * Represents a user's favorite media item in the Metavie application.
 * Stores the relationship between a user and their favorited media.
 * Includes media source and type information to handle cross-platform data
 * correctly.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class Favorite {
    /** Unique identifier for this favorite entry */
    private String favoriteId;

    /** User ID who favorited the media */
    private String userId;

    /** Media item ID */
    private String mediaId;

    /** Source of the media (MAL, TMDB, FILE) */
    private String mediaSource;

    /** Type of media (ANIME, MOVIE, TV_SHOW, or OTHER) */
    private String mediaType;

    /**
     * Default constructor for Favorite.
     */
    public Favorite() {
    }

    /**
     * Constructor without media type (backward compatibility).
     * 
     * @param favoriteId  Unique favorite ID
     * @param userId      User ID
     * @param mediaId     Media item ID
     * @param mediaSource Media source
     */
    public Favorite(String favoriteId, String userId, String mediaId, String mediaSource) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.mediaId = mediaId;
        this.mediaSource = mediaSource;
    }

    /**
     * Full constructor with all fields.
     * 
     * @param favoriteId  Unique favorite ID
     * @param userId      User ID
     * @param mediaId     Media item ID
     * @param mediaSource Media source (MAL, TMDB, FILE)
     * @param mediaType   Media type (ANIME, MOVIE, TV_SHOW)
     */
    public Favorite(String favoriteId, String userId, String mediaId, String mediaSource, String mediaType) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.mediaId = mediaId;
        this.mediaSource = mediaSource;
        this.mediaType = mediaType;
    }

    // Getters and Setters with documentation

    /**
     * Gets the favorite ID.
     * 
     * @return Unique favorite identifier
     */
    public String getFavoriteId() {
        return favoriteId;
    }

    /**
     * Sets the favorite ID.
     * 
     * @param favoriteId Favorite identifier to set
     */
    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
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
     * Gets the media type.
     * 
     * @return Media type (ANIME, MOVIE, TV_SHOW, OTHER)
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Sets the media type.
     * 
     * @param mediaType Media type to set
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Returns a string representation of the Favorite.
     * 
     * @return String containing favorite information
     */
    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteId='" + favoriteId + '\'' +
                ", userId='" + userId + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", mediaSource='" + mediaSource + '\'' +
                ", mediaType='" + mediaType + '\'' +
                '}';
    }
}
