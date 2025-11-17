package com.bakorz.repo;

import com.bakorz.model.Favorite;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing user favorites.
 * Defines operations for adding, removing, and querying favorite media items.
 * 
 * @author Bakorz
 * @version 1.0
 */
public interface FavoriteRepo {

    /**
     * Adds a new favorite entry.
     * 
     * @param favorite Favorite object to add
     * @return true if added successfully
     */
    boolean addFavorite(Favorite favorite);

    /**
     * Removes a favorite by its unique ID.
     * 
     * @param favoriteId Favorite ID to remove
     * @return true if removed successfully
     */
    boolean removeFavorite(String favoriteId);

    /**
     * Removes a favorite by user and media combination.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return true if removed successfully
     */
    boolean removeFavoriteByUserAndMedia(String userId, String mediaId);

    /**
     * Retrieves all favorites for a specific user.
     * 
     * @param userId User identifier
     * @return List of Favorite objects
     */
    List<Favorite> getFavoritesByUser(String userId);

    /**
     * Retrieves a favorite by its unique ID.
     * 
     * @param favoriteId Favorite ID
     * @return Optional containing the Favorite if found
     */
    Optional<Favorite> getFavoriteById(String favoriteId);

    /**
     * Checks if a media item is favorited by a user.
     * 
     * @param userId  User identifier
     * @param mediaId Media item identifier
     * @return true if favorited
     */
    boolean isFavorited(String userId, String mediaId);

    /**
     * Retrieves all favorites.
     * 
     * @return List of all Favorite objects
     */
    List<Favorite> getAll();

    /**
     * Updates an existing favorite.
     * 
     * @param favorite Favorite object to update
     * @return true if updated successfully
     */
    boolean update(Favorite favorite);
}
