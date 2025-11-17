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

        Optional<MediaItem> existing = fileRepo.getById(item.getId());
        if (!existing.isPresent()) {
            fileRepo.save(item);
        }
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
        try {
            switch (mediaSource) {
                case "MAL":
                    return malRepo.getById(id);
                case "TMDB":
                    return tmdbRepo.getById(id);
                case "FILE":
                    return fileRepo.getById(id);
                default:
                    return getById(id);
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
