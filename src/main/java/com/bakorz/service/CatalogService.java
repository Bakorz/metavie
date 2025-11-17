package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing media catalog operations.
 * Integrates multiple media repositories (MAL, TMDB) and provides unified
 * search and retrieval.
 * Handles caching of media items to improve performance and reduce API calls.
 * Manages composite keys to prevent ID collisions between different media
 * sources.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class CatalogService {
    /** Repository for MyAnimeList anime data */
    private MalMediaRepo malRepo;

    /** Repository for TMDB movie and TV show data */
    private TmdbMediaRepo tmdbRepo;

    /** Repository for local file-based media caching */
    private FileMediaRepo fileRepo;

    /**
     * Constructor for CatalogService.
     * 
     * @param malRepo  MyAnimeList repository
     * @param tmdbRepo TMDB repository
     * @param fileRepo File-based cache repository
     */
    public CatalogService(MalMediaRepo malRepo, TmdbMediaRepo tmdbRepo, FileMediaRepo fileRepo) {
        this.malRepo = malRepo;
        this.tmdbRepo = tmdbRepo;
        this.fileRepo = fileRepo;
    }

    /**
     * Searches across all media sources (MAL and TMDB) for items matching the
     * query.
     * Results are cached for future retrieval.
     * 
     * @param query Search query string
     * @return List of matching MediaItems from all sources
     */
    public List<MediaItem> searchAll(String query) {
        List<MediaItem> results = new ArrayList<>();

        // Search MAL for anime
        try {
            List<MediaItem> animeResults = malRepo.searchByTitle(query);
            for (MediaItem item : animeResults) {
                cacheMediaItem(item);
            }
            results.addAll(animeResults);
        } catch (Exception e) {
            System.err.println("Error searching MAL: " + e.getMessage());
        }

        // Search TMDB for movies and TV shows
        try {
            List<MediaItem> tmdbResults = tmdbRepo.searchByTitle(query);
            for (MediaItem item : tmdbResults) {
                cacheMediaItem(item);
            }
            results.addAll(tmdbResults);
        } catch (Exception e) {
            System.err.println("Error searching TMDB: " + e.getMessage());
        }

        return results;
    }

    /**
     * Caches a media item in the local file repository.
     * Uses composite keys (source prefix + ID) to prevent collisions between
     * different sources.
     * Only caches if the item doesn't already exist in the cache.
     * 
     * @param item The MediaItem to cache
     */
    private void cacheMediaItem(MediaItem item) {
        if (item == null || item.getId() == null) {
            return;
        }

        // Create a unique cache key by prefixing with source
        String cacheKey = getCacheKey(item);
        Optional<MediaItem> existing = fileRepo.getById(cacheKey);
        if (!existing.isPresent()) {
            // Temporarily change the ID to include source prefix for caching
            String originalId = item.getId();
            item.setId(cacheKey);
            fileRepo.save(item);
            // Restore original ID
            item.setId(originalId);
        }
    }

    /**
     * Generates a unique cache key for a media item.
     * Format: SOURCE_TYPE:ID
     * Examples: "MAL:123", "TMDB_MOVIE:456", "TMDB_TV:789"
     * This prevents ID collisions between different media sources and types.
     * 
     * @param item The MediaItem to generate a key for
     * @return Composite cache key string
     */
    private String getCacheKey(MediaItem item) {
        String prefix;
        if (item instanceof Anime) {
            prefix = "MAL";
        } else if (item instanceof Movie) {
            prefix = "TMDB_MOVIE";
        } else if (item instanceof TVShow) {
            prefix = "TMDB_TV";
        } else {
            prefix = "FILE";
        }
        return prefix + ":" + item.getId();
    }

    /**
     * Retrieves a media item by ID, trying MAL first then TMDB.
     * 
     * @param id Media item ID
     * @return Optional containing the MediaItem if found
     */
    public Optional<MediaItem> getById(String id) {
        try {
            Optional<MediaItem> anime = malRepo.getById(id);
            if (anime.isPresent()) {
                return anime;
            }
        } catch (Exception e) {
        }

        try {
            return tmdbRepo.getById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves a media item by ID and source (backward compatibility wrapper).
     * 
     * @param id          Media item ID
     * @param mediaSource Source of the media (MAL, TMDB, FILE)
     * @return Optional containing the MediaItem if found
     */
    public Optional<MediaItem> getById(String id, String mediaSource) {
        return getById(id, mediaSource, null);
    }

    /**
     * Retrieves a media item by ID, source, and optionally type.
     * Tries API first, then falls back to cache.
     * For TMDB items, uses mediaType to look up correct cache key (MOVIE vs
     * TV_SHOW).
     * Restores original ID after cache retrieval to ensure consistency.
     * 
     * @param id          Media item ID
     * @param mediaSource Source of the media (MAL, TMDB, FILE)
     * @param mediaType   Optional type of media (ANIME, MOVIE, TV_SHOW)
     * @return Optional containing the MediaItem if found
     */
    public Optional<MediaItem> getById(String id, String mediaSource, String mediaType) {
        try {
            switch (mediaSource) {
                case "MAL":
                    Optional<MediaItem> malResult = malRepo.getById(id);
                    if (malResult.isPresent()) {
                        return malResult;
                    }
                    // Try cache with composite key
                    Optional<MediaItem> cachedMal = fileRepo.getById("MAL:" + id);
                    if (cachedMal.isPresent()) {
                        // Restore original ID without prefix
                        cachedMal.get().setId(id);
                        return cachedMal;
                    }
                    return Optional.empty();
                case "TMDB":
                    // If mediaType is specified, try the specific cache key first
                    if (mediaType != null) {
                        String cacheKey = null;
                        if ("MOVIE".equals(mediaType)) {
                            cacheKey = "TMDB_MOVIE:" + id;
                        } else if ("TV_SHOW".equals(mediaType)) {
                            cacheKey = "TMDB_TV:" + id;
                        }

                        if (cacheKey != null) {
                            Optional<MediaItem> cachedItem = fileRepo.getById(cacheKey);
                            if (cachedItem.isPresent()) {
                                // Restore original ID without prefix
                                cachedItem.get().setId(id);
                                return cachedItem;
                            }
                        }
                    }

                    // Try API
                    Optional<MediaItem> tmdbResult = tmdbRepo.getById(id);
                    if (tmdbResult.isPresent()) {
                        return tmdbResult;
                    }

                    // Fallback: try both cache keys if mediaType wasn't specified
                    if (mediaType == null) {
                        Optional<MediaItem> cachedMovie = fileRepo.getById("TMDB_MOVIE:" + id);
                        if (cachedMovie.isPresent()) {
                            cachedMovie.get().setId(id);
                            return cachedMovie;
                        }
                        Optional<MediaItem> cachedTV = fileRepo.getById("TMDB_TV:" + id);
                        if (cachedTV.isPresent()) {
                            cachedTV.get().setId(id);
                            return cachedTV;
                        }
                    }
                    return Optional.empty();
                case "FILE":
                    return fileRepo.getById(id);
                default:
                    return Optional.empty();
            }
        } catch (Exception e) {
            System.err.println("Error getting media by ID from " + mediaSource + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Retrieves top-rated anime from MyAnimeList.
     * Results are cached for future retrieval.
     * 
     * @param limit  Maximum number of results
     * @param offset Starting position for pagination
     * @return List of top-rated Anime
     */
    public List<Anime> getTopRatedAnime(int limit, int offset) {
        List<MediaItem> animeResults = malRepo.getTopRated(limit, offset);
        for (MediaItem item : animeResults) {
            cacheMediaItem(item);
        }
        return animeResults.stream()
                .filter(item -> item instanceof Anime)
                .map(item -> (Anime) item)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves top-rated movies and TV shows from TMDB.
     * Results are cached for future retrieval.
     * 
     * @param limit Maximum number of results
     * @param page  Page number for pagination
     * @return List of top-rated MediaItems (Movies and TVShows)
     */
    public List<MediaItem> getTopRatedMoviesAndTV(int limit, int page) {
        List<MediaItem> tmdbResults = tmdbRepo.getTopRated(limit, page);
        for (MediaItem item : tmdbResults) {
            cacheMediaItem(item);
        }
        return tmdbResults;
    }

    /**
     * Retrieves latest movies from TMDB.
     * Results are cached for future retrieval.
     * 
     * @param limit Maximum number of results
     * @param page  Page number for pagination
     * @return List of latest Movies
     */
    public List<Movie> getLatestMovies(int limit, int page) {
        List<Movie> movies = tmdbRepo.getLatestMovies(limit, page);
        for (MediaItem item : movies) {
            cacheMediaItem(item);
        }
        return movies;
    }

    /**
     * Retrieves latest TV shows from TMDB.
     * Results are cached for future retrieval.
     * 
     * @param limit Maximum number of results
     * @param page  Page number for pagination
     * @return List of latest TVShows
     */
    public List<TVShow> getLatestTVShows(int limit, int page) {
        List<TVShow> tvShows = tmdbRepo.getLatestTVShows(limit, page);
        for (MediaItem item : tvShows) {
            cacheMediaItem(item);
        }
        return tvShows;
    }

    /**
     * Retrieves latest anime from MyAnimeList.
     * Results are cached for future retrieval.
     * 
     * @param limit Maximum number of results
     * @return List of latest Anime
     */
    public List<Anime> getLatestAnime(int limit) {
        List<Anime> anime = malRepo.getLatestAnime(limit);
        for (MediaItem item : anime) {
            cacheMediaItem(item);
        }
        return anime;
    }
}
