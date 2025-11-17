package com.bakorz.repo;

import com.bakorz.model.Favorite;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepo {

    boolean addFavorite(Favorite favorite);
    boolean removeFavorite(String favoriteId);
    boolean removeFavoriteByUserAndMedia(String userId, String mediaId);
    List<Favorite> getFavoritesByUser(String userId);
    Optional<Favorite> getFavoriteById(String favoriteId);
    boolean isFavorited(String userId, String mediaId);
    List<Favorite> getAll();
    boolean update(Favorite favorite);
}
