package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CatalogService combines multiple media repositories (MAL and TMDB)
 * to provide unified search and retrieval across all media types
 */
public class CatalogService {
    private MalMediaRepo malRepo;
    private TmdbMediaRepo tmdbRepo;
    private FileMediaRepo fileRepo;

    public CatalogService(MalMediaRepo malRepo, TmdbMediaRepo tmdbRepo, FileMediaRepo fileRepo) {
        this.malRepo = malRepo;
        this.tmdbRepo = tmdbRepo;
        this.fileRepo = fileRepo;
    }

    /**
     * Search across all repositories (Anime from MAL, Movies/TV from TMDB)
     * Results are automatically cached to local storage
     */
    public List<MediaItem> searchAll(String query) {
        List<MediaItem> results = new ArrayList<>();

        // Search anime from MAL
        try {
            List<MediaItem> animeResults = malRepo.searchByTitle(query);
            // Cache each result
            for (MediaItem item : animeResults) {
                cacheMediaItem(item);
            }
            results.addAll(animeResults);
        } catch (Exception e) {
            System.err.println("Error searching MAL: " + e.getMessage());
        }

        // Search movies and TV shows from TMDB
        try {
            List<MediaItem> tmdbResults = tmdbRepo.searchByTitle(query);
            // Cache each result
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
     * Cache a media item to local storage if not already cached
     */
    private void cacheMediaItem(MediaItem item) {
        if (item == null || item.getId() == null) {
            return;
        }

        // Check if already cached
        Optional<MediaItem> existing = fileRepo.getById(item.getId());
        if (!existing.isPresent()) {
            fileRepo.save(item);
        }
    }

    /**
     * Get media item by ID from any repository
     * Try MAL first, then TMDB
     */
    public Optional<MediaItem> getById(String id) {
        // Try MAL (anime IDs are numeric)
        try {
            Optional<MediaItem> anime = malRepo.getById(id);
            if (anime.isPresent()) {
                return anime;
            }
        } catch (Exception e) {
            // Continue to TMDB
        }

        // Try TMDB
        try {
            return tmdbRepo.getById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Get media item by ID from a specific source
     * This avoids ID conflicts between MAL and TMDB
     */
    public Optional<MediaItem> getById(String id, String mediaSource) {
        try {
            switch (mediaSource) {
                case "MAL":
                    return malRepo.getById(id);
                case "TMDB":
                    return tmdbRepo.getById(id);
                case "FILE":
                    return fileRepo.getById(id);
                default:
                    // Fall back to the old behavior
                    return getById(id);
            }
        } catch (Exception e) {
            System.err.println("Error getting media by ID from " + mediaSource + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Get top-rated anime only
     */
    /**
     * Get top-rated anime with pagination support
     */
    public List<Anime> getTopRatedAnime(int limit, int offset) {
        List<MediaItem> animeResults = malRepo.getTopRated(limit, offset);
        // Cache results
        for (MediaItem item : animeResults) {
            cacheMediaItem(item);
        }
        return animeResults.stream()
                .filter(item -> item instanceof Anime)
                .map(item -> (Anime) item)
                .collect(Collectors.toList());
    }

    /**
     * Get top-rated movies and TV shows with pagination support
     */
    public List<MediaItem> getTopRatedMoviesAndTV(int limit, int page) {
        List<MediaItem> tmdbResults = tmdbRepo.getTopRated(limit, page);
        // Cache results
        for (MediaItem item : tmdbResults) {
            cacheMediaItem(item);
        }
        return tmdbResults;
    }

    /**
     * Get latest/newest movies currently in theaters or recently released
     */
    public List<Movie> getLatestMovies(int limit, int page) {
        List<Movie> movies = tmdbRepo.getLatestMovies(limit, page);
        // Cache results
        for (MediaItem item : movies) {
            cacheMediaItem(item);
        }
        return movies;
    }

    /**
     * Get latest/newest TV shows currently airing
     */
    public List<TVShow> getLatestTVShows(int limit, int page) {
        List<TVShow> tvShows = tmdbRepo.getLatestTVShows(limit, page);
        // Cache results
        for (MediaItem item : tvShows) {
            cacheMediaItem(item);
        }
        return tvShows;
    }

    /**
     * Get latest/seasonal anime currently airing
     */
    public List<Anime> getLatestAnime(int limit) {
        List<Anime> anime = malRepo.getLatestAnime(limit);
        // Cache results
        for (MediaItem item : anime) {
            cacheMediaItem(item);
        }
        return anime;
    }
}
