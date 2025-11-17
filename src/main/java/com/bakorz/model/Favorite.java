package com.bakorz.model;

public class Favorite {
    private String favoriteId;
    private String userId;
    private String mediaId;
    private String mediaSource;

    public Favorite() {
    }

    public Favorite(String favoriteId, String userId, String mediaId, String mediaSource) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.mediaId = mediaId;
        this.mediaSource = mediaSource;
    }

    // Getters and Setters
    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
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
        return "Favorite{" +
                "favoriteId='" + favoriteId + '\'' +
                ", userId='" + userId + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", mediaSource='" + mediaSource + '\'' +
                '}';
    }
}
