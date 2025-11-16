package com.bakorz.repo;

import com.bakorz.model.Favorite;
import java.util.List;
import java.util.Optional;

/**
 * Interface for favorite repository operations
 */
public interface FavoriteRepo {

    /**
     * Add a favorite for a user
     * 
     * @param favorite The favorite to add
     * @return true if added successfully
     */
    boolean addFavorite(Favorite favorite);

    /**
     * Remove a favorite by ID
     * 
     * @param favoriteId The favorite ID to remove
     * @return true if removed successfully
     */
    boolean removeFavorite(String favoriteId);

    /**
     * Remove a favorite by user ID and media ID
     * 
     * @param userId  The user ID
     * @param mediaId The media ID
     * @return true if removed successfully
     */
    boolean removeFavoriteByUserAndMedia(String userId, String mediaId);

    /**
     * Get all favorites for a user
     * 
     * @param userId The user ID
     * @return List of favorites for the user
     */
    List<Favorite> getFavoritesByUser(String userId);

    /**
     * Get a favorite by ID
     * 
     * @param favoriteId The favorite ID
     * @return Optional containing the favorite if found
     */
    Optional<Favorite> getFavoriteById(String favoriteId);

    /**
     * Check if a media item is favorited by a user
     * 
     * @param userId  The user ID
     * @param mediaId The media ID
     * @return true if the media is favorited by the user
     */
    boolean isFavorited(String userId, String mediaId);

    /**
     * Get all favorites
     * 
     * @return List of all favorites
     */
    List<Favorite> getAll();

    /**
     * Update a favorite
     * 
     * @param favorite The favorite to update
     * @return true if updated successfully
     */
    boolean update(Favorite favorite);
}
