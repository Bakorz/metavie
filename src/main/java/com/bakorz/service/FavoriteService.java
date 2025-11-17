package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;

/**
 * FavoriteService manages user favorites
 * Allows users to mark media as favorite and organize their collection
 */
public class FavoriteService {
    private FileFavoriteRepo favoriteRepo;

    public FavoriteService(FileFavoriteRepo favoriteRepo) {
        this.favoriteRepo = favoriteRepo;
    }

    /**
     * Add a media item to user's favorites
     */
    public boolean addFavorite(String userId, String mediaId, String mediaSource) {
        if (isFavorited(userId, mediaId)) {
            System.out.println("Media is already in favorites!");
            return false;
        }

        String favoriteId = UUID.randomUUID().toString();
        Favorite favorite = new Favorite(favoriteId, userId, mediaId, mediaSource != null ? mediaSource : "FILE");
        return favoriteRepo.addFavorite(favorite);
    }

    /**
     * Add a media item to favorites (backward compatibility)
     */
    public boolean addFavorite(String userId, String mediaId) {
        return addFavorite(userId, mediaId, "FILE");
    }

    /**
     * Remove a media item from user's favorites
     */
    public boolean removeFavorite(String userId, String mediaId) {
        return favoriteRepo.removeFavoriteByUserAndMedia(userId, mediaId);
    }

    /**
     * Check if a media item is in user's favorites
     */
    public boolean isFavorited(String userId, String mediaId) {
        return favoriteRepo.isFavorited(userId, mediaId);
    }

    /**
     * Get all favorites for a user
     */
    public List<Favorite> getUserFavorites(String userId) {
        return favoriteRepo.getFavoritesByUser(userId);
    }

}
