package com.bakorz.repo;

import com.bakorz.model.*;
import java.util.List;
import java.util.Optional;

public interface MediaRepo {

    List<MediaItem> searchByTitle(String title);

    Optional<MediaItem> getById(String id);
    Optional<Movie> getMovieById(String id);
    Optional<TVShow> getTVShowById(String id);
    Optional<Anime> getAnimeById(String id);

    List<MediaItem> getByGenre(String genre);
    List<MediaItem> getTopRated(int limit);
    List<Movie> getLatestMovies(int limit);
    List<TVShow> getLatestTVShows(int limit);
    List<MediaItem> getAll();
    
    boolean save(MediaItem mediaItem);
    boolean update(MediaItem mediaItem);
    boolean delete(String id);
}
