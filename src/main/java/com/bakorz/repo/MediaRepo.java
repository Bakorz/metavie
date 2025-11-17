package com.bakorz.repo;

import com.bakorz.model.*;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing media items.
 * Defines standard CRUD operations and query methods for media data.
 * Implemented by specific repository types (MAL, TMDB, File-based).
 * 
 * @author Bakorz
 * @version 1.0
 */
public interface MediaRepo {

    /**
     * Searches for media items by title.
     * 
     * @param title Title search query
     * @return List of matching MediaItems
     */
    List<MediaItem> searchByTitle(String title);

    /**
     * Retrieves a media item by its unique identifier.
     * 
     * @param id Media item ID
     * @return Optional containing the MediaItem if found
     */
    Optional<MediaItem> getById(String id);

    /**
     * Retrieves a movie by its unique identifier.
     * 
     * @param id Movie ID
     * @return Optional containing the Movie if found
     */
    Optional<Movie> getMovieById(String id);

    /**
     * Retrieves a TV show by its unique identifier.
     * 
     * @param id TV show ID
     * @return Optional containing the TVShow if found
     */
    Optional<TVShow> getTVShowById(String id);

    /**
     * Retrieves an anime by its unique identifier.
     * 
     * @param id Anime ID
     * @return Optional containing the Anime if found
     */
    Optional<Anime> getAnimeById(String id);

    /**
     * Retrieves media items by genre.
     * 
     * @param genre Genre name
     * @return List of MediaItems in the specified genre
     */
    List<MediaItem> getByGenre(String genre);

    /**
     * Retrieves top-rated media items.
     * 
     * @param limit Maximum number of results
     * @return List of top-rated MediaItems
     */
    List<MediaItem> getTopRated(int limit);

    /**
     * Retrieves latest movies.
     * 
     * @param limit Maximum number of results
     * @return List of latest Movies
     */
    List<Movie> getLatestMovies(int limit);

    /**
     * Retrieves latest TV shows.
     * 
     * @param limit Maximum number of results
     * @return List of latest TVShows
     */
    List<TVShow> getLatestTVShows(int limit);

    /**
     * Retrieves all media items.
     * 
     * @return List of all MediaItems
     */
    List<MediaItem> getAll();

    /**
     * Saves a new media item.
     * 
     * @param mediaItem MediaItem to save
     * @return true if saved successfully
     */
    boolean save(MediaItem mediaItem);

    /**
     * Updates an existing media item.
     * 
     * @param mediaItem MediaItem to update
     * @return true if updated successfully
     */
    boolean update(MediaItem mediaItem);

    /**
     * Deletes a media item by ID.
     * 
     * @param id Media item ID to delete
     * @return true if deleted successfully
     */
    boolean delete(String id);
}
