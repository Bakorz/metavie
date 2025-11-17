package com.bakorz.service;

import com.bakorz.model.*;
import com.bakorz.repo.*;
import java.util.*;
import java.util.stream.Collectors;

public class CatalogService {
    private MalMediaRepo malRepo;
    private TmdbMediaRepo tmdbRepo;
    private FileMediaRepo fileRepo;

    public CatalogService(MalMediaRepo malRepo, TmdbMediaRepo tmdbRepo, FileMediaRepo fileRepo) {
        this.malRepo = malRepo;
        this.tmdbRepo = tmdbRepo;
        this.fileRepo = fileRepo;
    }

    public List<MediaItem> searchAll(String query) {
        List<MediaItem> results = new ArrayList<>();

        try {
            List<MediaItem> animeResults = malRepo.searchByTitle(query);
            for (MediaItem item : animeResults) {
                cacheMediaItem(item);
            }
            results.addAll(animeResults);
        } catch (Exception e) {
            System.err.println("Error searching MAL: " + e.getMessage());
        }

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

    public Optional<MediaItem> getById(String id, String mediaSource) {
        return getById(id, mediaSource, null);
    }

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

    public List<MediaItem> getTopRatedMoviesAndTV(int limit, int page) {
        List<MediaItem> tmdbResults = tmdbRepo.getTopRated(limit, page);
        for (MediaItem item : tmdbResults) {
            cacheMediaItem(item);
        }
        return tmdbResults;
    }

    public List<Movie> getLatestMovies(int limit, int page) {
        List<Movie> movies = tmdbRepo.getLatestMovies(limit, page);
        for (MediaItem item : movies) {
            cacheMediaItem(item);
        }
        return movies;
    }

    public List<TVShow> getLatestTVShows(int limit, int page) {
        List<TVShow> tvShows = tmdbRepo.getLatestTVShows(limit, page);
        for (MediaItem item : tvShows) {
            cacheMediaItem(item);
        }
        return tvShows;
    }

    public List<Anime> getLatestAnime(int limit) {
        List<Anime> anime = malRepo.getLatestAnime(limit);
        for (MediaItem item : anime) {
            cacheMediaItem(item);
        }
        return anime;
    }
}
