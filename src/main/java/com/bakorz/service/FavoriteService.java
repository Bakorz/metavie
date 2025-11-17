package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;

public class FavoriteService {
    private FileFavoriteRepo favoriteRepo;

    public FavoriteService(FileFavoriteRepo favoriteRepo) {
        this.favoriteRepo = favoriteRepo;
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

}
