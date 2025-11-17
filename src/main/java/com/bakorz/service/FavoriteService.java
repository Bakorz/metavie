package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;
import java.util.stream.Collectors;

public class FavoriteService {
    private FileFavoriteRepo favoriteRepo;
    private CatalogService catalogService;

    public FavoriteService(FileFavoriteRepo favoriteRepo, CatalogService catalogService) {
        this.favoriteRepo = favoriteRepo;
        this.catalogService = catalogService;
    }

    public boolean addFavorite(String userId, String mediaId, String mediaSource) {
        if (isFavorited(userId, mediaId)) {
            System.out.println("Media is already in favorites!");
            return false;
        }

        String favoriteId = UUID.randomUUID().toString();
        Favorite favorite = new Favorite(favoriteId, userId, mediaId, mediaSource != null ? mediaSource : "FILE");
        return favoriteRepo.addFavorite(favorite);
    }

    public boolean addFavorite(String userId, String mediaId) {
        return addFavorite(userId, mediaId, "FILE");
    }

    public boolean removeFavorite(String userId, String mediaId) {
        return favoriteRepo.removeFavoriteByUserAndMedia(userId, mediaId);
    }

    public boolean isFavorited(String userId, String mediaId) {
        return favoriteRepo.isFavorited(userId, mediaId);
    }

    public List<Favorite> getUserFavorites(String userId) {
        return favoriteRepo.getFavoritesByUser(userId);
    }

    public List<Map<String, Object>> getUserFavoritesWithDetails(String userId) {
        List<Favorite> favorites = getUserFavorites(userId);
        List<Map<String, Object>> favoritesWithDetails = new ArrayList<>();

        for (Favorite favorite : favorites) {
            Map<String, Object> favoriteDetail = new HashMap<>();
            favoriteDetail.put("favorite", favorite);

            Optional<MediaItem> media = catalogService.getById(favorite.getMediaId());
            media.ifPresent(item -> favoriteDetail.put("media", item));

            favoritesWithDetails.add(favoriteDetail);
        }

        return favoritesWithDetails;
    }

    public Map<String, Object> getFavoriteStats(String userId) {
        List<Favorite> favorites = getUserFavorites(userId);
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalFavorites", favorites.size());

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

    public List<Favorite> searchFavorites(String userId, String query) {
        List<Favorite> allFavorites = getUserFavorites(userId);
        List<Favorite> results = new ArrayList<>();

        for (Favorite favorite : allFavorites) {
            Optional<MediaItem> media = catalogService.getById(favorite.getMediaId(), favorite.getMediaSource());
            if (media.isPresent() &&
                    media.get().getTitle().toLowerCase().contains(query.toLowerCase())) {
                results.add(favorite);
            }
        }

        return results;
    }

    public List<String> exportFavoriteIds(String userId) {
        return getUserFavorites(userId).stream()
                .map(Favorite::getMediaId)
                .collect(Collectors.toList());
    }

    public int importFavorites(String userId, List<String> mediaIds) {
        int imported = 0;
        for (String mediaId : mediaIds) {
            if (addFavorite(userId, mediaId)) {
                imported++;
            }
        }
        return imported;
    }

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
