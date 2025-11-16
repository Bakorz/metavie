package com.bakorz.repo;

import com.bakorz.model.*;
import java.util.List;
import java.util.Optional;

/**
 * Interface for media repository operations
 * Provides methods to retrieve metadata for movies, TV shows, and anime
 */
public interface MediaRepo {

    /**
     * Search for media items by title
     * 
     * @param title The title to search for
     * @return List of matching media items
     */
    List<MediaItem> searchByTitle(String title);

    /**
     * Get a media item by its ID
     * 
     * @param id The media item ID
     * @return Optional containing the media item if found
     */
    Optional<MediaItem> getById(String id);

    /**
     * Get a movie by its ID
     * 
     * @param id The movie ID
     * @return Optional containing the movie if found
     */
    Optional<Movie> getMovieById(String id);

    /**
     * Get a TV show by its ID
     * 
     * @param id The TV show ID
     * @return Optional containing the TV show if found
     */
    Optional<TVShow> getTVShowById(String id);

    /**
     * Get an anime by its ID
     * 
     * @param id The anime ID
     * @return Optional containing the anime if found
     */
    Optional<Anime> getAnimeById(String id);

    /**
     * Get media items by genre
     * 
     * @param genre The genre to filter by
     * @return List of media items matching the genre
     */
    List<MediaItem> getByGenre(String genre);

    /**
     * Get top rated media items
     * 
     * @param limit Maximum number of items to return
     * @return List of top rated media items
     */
    List<MediaItem> getTopRated(int limit);

    /**
     * Get latest/newest released movies
     * 
     * @param limit Maximum number of items to return
     * @return List of latest movies sorted by release date
     */
    List<Movie> getLatestMovies(int limit);

    /**
     * Get latest/newest released TV shows
     * 
     * @param limit Maximum number of items to return
     * @return List of latest TV shows sorted by air date
     */
    List<TVShow> getLatestTVShows(int limit);

    /**
     * Get all media items
     * 
     * @return List of all media items
     */
    List<MediaItem> getAll();

    /**
     * Save a media item to the repository
     * 
     * @param mediaItem The media item to save
     * @return true if saved successfully
     */
    boolean save(MediaItem mediaItem);

    /**
     * Update an existing media item
     * 
     * @param mediaItem The media item to update
     * @return true if updated successfully
     */
    boolean update(MediaItem mediaItem);

    /**
     * Delete a media item by ID
     * 
     * @param id The media item ID to delete
     * @return true if deleted successfully
     */
    boolean delete(String id);
}
