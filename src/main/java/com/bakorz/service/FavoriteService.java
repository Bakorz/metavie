package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FavoriteService manages user favorites
 * Allows users to mark media as favorite and organize their collection
 */
public class FavoriteService {
    private FileFavoriteRepo favoriteRepo;
    private CatalogService catalogService;

    public FavoriteService(FileFavoriteRepo favoriteRepo, CatalogService catalogService) {
        this.favoriteRepo = favoriteRepo;
        this.catalogService = catalogService;
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

    /**
     * Get all favorites for a user with full media details
     */
    public List<Map<String, Object>> getUserFavoritesWithDetails(String userId) {
        List<Favorite> favorites = getUserFavorites(userId);
        List<Map<String, Object>> favoritesWithDetails = new ArrayList<>();

        for (Favorite favorite : favorites) {
            Map<String, Object> favoriteDetail = new HashMap<>();
            favoriteDetail.put("favorite", favorite);

            // Get full media details from catalog
            Optional<MediaItem> media = catalogService.getById(favorite.getMediaId());
            media.ifPresent(item -> favoriteDetail.put("media", item));

            favoritesWithDetails.add(favoriteDetail);
        }

        return favoritesWithDetails;
    }

    /**
     * Get favorite statistics for a user
     */
    public Map<String, Object> getFavoriteStats(String userId) {
        List<Favorite> favorites = getUserFavorites(userId);
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalFavorites", favorites.size());

        // Count by media type
        Map<String, Long> typeCount = new HashMap<>();
        for (Favorite favorite : favorites) {
            Optional<MediaItem> media = catalogService.getById(favorite.getMediaId(), favorite.getMediaSource());
            if (media.isPresent()) {
                String type = media.get().getMediaType();
                typeCount.put(type, typeCount.getOrDefault(type, 0L) + 1);
            }
        }
        stats.put("byType", typeCount);

        return stats;
    }

    /**
     * Search within user's favorites
     */
    public List<Favorite> searchFavorites(String userId, String query) {
        List<Favorite> allFavorites = getUserFavorites(userId);
        List<Favorite> results = new ArrayList<>();

        for (Favorite favorite : allFavorites) {
            // Search in media title
            Optional<MediaItem> media = catalogService.getById(favorite.getMediaId(), favorite.getMediaSource());
            if (media.isPresent() &&
                    media.get().getTitle().toLowerCase().contains(query.toLowerCase())) {
                results.add(favorite);
            }
        }

        return results;
    }

    /**
     * Export user's favorites to a list of media IDs
     */
    public List<String> exportFavoriteIds(String userId) {
        return getUserFavorites(userId).stream()
                .map(Favorite::getMediaId)
                .collect(Collectors.toList());
    }

    /**
     * Import favorites from a list of media IDs
     */
    public int importFavorites(String userId, List<String> mediaIds) {
        int imported = 0;
        for (String mediaId : mediaIds) {
            if (addFavorite(userId, mediaId)) {
                imported++;
            }
        }
        return imported;
    }

    /**
     * Clear all favorites for a user
     */
    public boolean clearAllFavorites(String userId) {
        List<Favorite> favorites = getUserFavorites(userId);
        int removed = 0;
        for (Favorite favorite : favorites) {
            if (favoriteRepo.removeFavoriteByUserAndMedia(userId, favorite.getMediaId())) {
                removed++;
            }
        }
        return removed == favorites.size();
    }
}
