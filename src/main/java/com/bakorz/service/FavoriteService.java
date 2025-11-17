package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;

/**
 * Service class for managing user favorite media items.
 * Provides operations to add, remove, check, and retrieve favorites.
 * Handles favorite persistence through FileFavoriteRepo.
 * Supports media type tracking to prevent ID collisions between different
 * sources.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class FavoriteService {
    /** Repository for managing favorite data */
    private FileFavoriteRepo favoriteRepo;

    /**
     * Constructor for FavoriteService.
     * 
     * @param favoriteRepo FileFavoriteRepo instance
     */
    public FavoriteService(FileFavoriteRepo favoriteRepo) {
        this.favoriteRepo = favoriteRepo;
    }

    /**
     * Adds a media item to user's favorites with full details.
     * Prevents duplicate favorites for the same user and media.
     * 
     * @param userId      User identifier
     * @param mediaId     Media item identifier
     * @param mediaSource Source of media (MAL, TMDB, FILE)
     * @param mediaType   Type of media (ANIME, MOVIE, TV_SHOW)
     * @return true if favorite was added successfully, false if already exists
     */
    public boolean addFavorite(String userId, String mediaId, String mediaSource, String mediaType) {
        if (isFavorited(userId, mediaId)) {
            System.out.println("Media is already in favorites!");
            return false;
        }

        String favoriteId = UUID.randomUUID().toString();
        Favorite favorite = new Favorite(favoriteId, userId, mediaId,
                mediaSource != null ? mediaSource : "FILE",
                mediaType);
        return favoriteRepo.addFavorite(favorite);
    }

    /**
     * Adds a media item to user's favorites without media type.
     * 
     * @param userId      User identifier
     * @param mediaId     Media item identifier
     * @param mediaSource Source of media (MAL, TMDB, FILE)
     * @return true if favorite was added successfully, false if already exists
     */
    public boolean addFavorite(String userId, String mediaId, String mediaSource) {
        return addFavorite(userId, mediaId, mediaSource, null);
    }

    /**
     * Adds a media item to user's favorites with FILE as default source.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return true if favorite was added successfully, false if already exists
     */
    public boolean addFavorite(String userId, String mediaId) {
        return addFavorite(userId, mediaId, "FILE", null);
    }

    /**
     * Removes a media item from user's favorites.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return true if favorite was removed successfully, false if not found
     */
    public boolean removeFavorite(String userId, String mediaId) {
        return favoriteRepo.removeFavoriteByUserAndMedia(userId, mediaId);
    }

    /**
     * Checks if a media item is in user's favorites.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return true if media is favorited by user
     */
    public boolean isFavorited(String userId, String mediaId) {
        return favoriteRepo.isFavorited(userId, mediaId);
    }

    /**
     * Retrieves all favorite entries for a specific user.
     * 
     * @param userId User identifier
     * @return List of Favorite objects belonging to the user
     */
    public List<Favorite> getUserFavorites(String userId) {
        return favoriteRepo.getFavoritesByUser(userId);
    }

}
