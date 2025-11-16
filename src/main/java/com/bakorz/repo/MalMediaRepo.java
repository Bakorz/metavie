package com.bakorz.repo;

import com.bakorz.model.*;
import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * MyAnimeList-based implementation of MediaRepo
 * Fetches anime metadata from MyAnimeList API
 */
public class MalMediaRepo implements MediaRepo {
    private static final String MAL_API_BASE = "https://api.myanimelist.net/v2/";
    private String clientId;
    private Gson gson;

    public MalMediaRepo(String clientId) {
        this.clientId = clientId;
        this.gson = new Gson();
    }

    /**
     * Make HTTP GET request to MyAnimeList API
     */
    private String makeApiRequest(String endpoint) throws IOException {
        URI uri = URI.create(MAL_API_BASE + endpoint);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-MAL-CLIENT-ID", clientId);
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new IOException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        conn.disconnect();
        return response.toString();
    }

    /**
     * Parse JSON response to Anime object
     */
    private Anime parseAnimeFromJson(JsonObject animeJson) {
        Anime anime = new Anime();

        // Basic fields
        anime.setId(String.valueOf(animeJson.get("id").getAsInt()));
        anime.setTitle(animeJson.get("title").getAsString());
        anime.setMalId(animeJson.get("id").getAsInt());

        // Description/Synopsis
        if (animeJson.has("synopsis") && !animeJson.get("synopsis").isJsonNull()) {
            anime.setDescription(animeJson.get("synopsis").getAsString());
        }

        // Rating/Score
        if (animeJson.has("mean") && !animeJson.get("mean").isJsonNull()) {
            anime.setRating(animeJson.get("mean").getAsDouble());
            anime.setMalScore(animeJson.get("mean").getAsDouble());
        }

        // Rank and Popularity
        if (animeJson.has("rank") && !animeJson.get("rank").isJsonNull()) {
            anime.setMalRank(animeJson.get("rank").getAsInt());
        }
        if (animeJson.has("popularity") && !animeJson.get("popularity").isJsonNull()) {
            anime.setMalPopularity(animeJson.get("popularity").getAsInt());
        }

        // Images
        if (animeJson.has("main_picture")) {
            JsonObject picture = animeJson.getAsJsonObject("main_picture");
            if (picture.has("medium")) {
                anime.setPosterUrl(picture.get("medium").getAsString());
            }
            if (picture.has("large")) {
                anime.setBackdropUrl(picture.get("large").getAsString());
            }
        }

        // Genres
        if (animeJson.has("genres")) {
            List<String> genres = new ArrayList<>();
            JsonArray genresArray = animeJson.getAsJsonArray("genres");
            for (JsonElement genreElement : genresArray) {
                JsonObject genreObj = genreElement.getAsJsonObject();
                genres.add(genreObj.get("name").getAsString());
            }
            anime.setGenres(genres);
        }

        // Media Type (TV, Movie, OVA, etc.)
        if (animeJson.has("media_type") && !animeJson.get("media_type").isJsonNull()) {
            anime.setAnimeType(animeJson.get("media_type").getAsString().toUpperCase());
        }

        // Status
        if (animeJson.has("status") && !animeJson.get("status").isJsonNull()) {
            anime.setStatus(animeJson.get("status").getAsString());
        }

        // Number of episodes
        if (animeJson.has("num_episodes") && !animeJson.get("num_episodes").isJsonNull()) {
            anime.setEpisodes(animeJson.get("num_episodes").getAsInt());
        }

        // Dates
        if (animeJson.has("start_date") && !animeJson.get("start_date").isJsonNull()) {
            String startDate = animeJson.get("start_date").getAsString();
            anime.setReleaseDate(startDate);
            anime.setAired(startDate);
        }

        // Season and Year
        if (animeJson.has("start_season")) {
            JsonObject season = animeJson.getAsJsonObject("start_season");
            if (season.has("season")) {
                anime.setSeason(season.get("season").getAsString());
            }
            if (season.has("year")) {
                anime.setYear(season.get("year").getAsInt());
            }
        }

        // Source (manga, light novel, original, etc.)
        if (animeJson.has("source") && !animeJson.get("source").isJsonNull()) {
            anime.setSource(animeJson.get("source").getAsString());
        }

        // Studios
        if (animeJson.has("studios")) {
            List<String> studios = new ArrayList<>();
            JsonArray studiosArray = animeJson.getAsJsonArray("studios");
            for (JsonElement studioElement : studiosArray) {
                JsonObject studioObj = studioElement.getAsJsonObject();
                studios.add(studioObj.get("name").getAsString());
            }
            anime.setStudios(studios);
        }

        // Duration (in seconds, convert to minutes)
        if (animeJson.has("average_episode_duration") && !animeJson.get("average_episode_duration").isJsonNull()) {
            int durationSeconds = animeJson.get("average_episode_duration").getAsInt();
            anime.setDuration(durationSeconds / 60); // Convert to minutes
        }

        return anime;
    }

    @Override
    public List<MediaItem> searchByTitle(String title) {
        try {
            String response = makeApiRequest("anime?q=" + URLEncoder.encode(title, "UTF-8") +
                    "&limit=10&fields=id,title,main_picture,alternative_titles,start_date,end_date," +
                    "synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,genres," +
                    "media_type,status,num_episodes,start_season,source,studios");

            JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
            JsonArray dataArray = jsonResponse.getAsJsonArray("data");

            List<MediaItem> results = new ArrayList<>();
            for (JsonElement element : dataArray) {
                JsonObject nodeObj = element.getAsJsonObject().getAsJsonObject("node");
                results.add(parseAnimeFromJson(nodeObj));
            }

            return results;
        } catch (IOException e) {
            System.err.println("Error searching anime: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<MediaItem> getById(String id) {
        return getAnimeById(id).map(anime -> (MediaItem) anime);
    }

    @Override
    public Optional<Movie> getMovieById(String id) {
        // MAL doesn't have traditional movies in the same way, return empty
        return Optional.empty();
    }

    @Override
    public Optional<TVShow> getTVShowById(String id) {
        // MAL doesn't have TV shows, return empty
        return Optional.empty();
    }

    @Override
    public Optional<Anime> getAnimeById(String id) {
        try {
            String response = makeApiRequest("anime/" + id +
                    "?fields=id,title,main_picture,alternative_titles,start_date,end_date," +
                    "synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,genres," +
                    "media_type,status,num_episodes,start_season,source,studios,average_episode_duration");

            JsonObject animeJson = gson.fromJson(response, JsonObject.class);
            return Optional.of(parseAnimeFromJson(animeJson));
        } catch (IOException e) {
            System.err.println("Error getting anime by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<MediaItem> getByGenre(String genre) {
        try {
            // Note: MAL API v2 doesn't support direct genre search
            // This is a workaround using regular search
            String response = makeApiRequest("anime?q=" + URLEncoder.encode(genre, "UTF-8") + "&limit=20");

            JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
            JsonArray dataArray = jsonResponse.getAsJsonArray("data");

            List<MediaItem> results = new ArrayList<>();
            for (JsonElement element : dataArray) {
                JsonObject nodeObj = element.getAsJsonObject().getAsJsonObject("node");
                results.add(parseAnimeFromJson(nodeObj));
            }

            return results;
        } catch (IOException e) {
            System.err.println("Error searching by genre: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<MediaItem> getTopRated(int limit) {
        return getTopRated(limit, 0);
    }

    /**
     * Get top-rated anime with pagination support
     */
    public List<MediaItem> getTopRated(int limit, int offset) {
        try {
            String response = makeApiRequest("anime/ranking?ranking_type=all&limit=" + limit + "&offset=" + offset +
                    "&fields=id,title,main_picture,synopsis,mean,rank,popularity,genres," +
                    "media_type,status,num_episodes,start_season,source,studios");

            JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
            JsonArray dataArray = jsonResponse.getAsJsonArray("data");

            List<MediaItem> results = new ArrayList<>();
            for (JsonElement element : dataArray) {
                JsonObject nodeObj = element.getAsJsonObject().getAsJsonObject("node");
                results.add(parseAnimeFromJson(nodeObj));
            }

            return results;
        } catch (IOException e) {
            System.err.println("Error getting top rated anime: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<MediaItem> getAll() {
        throw new UnsupportedOperationException("Cannot fetch all items from MAL API");
    }

    @Override
    public boolean save(MediaItem mediaItem) {
        throw new UnsupportedOperationException("Cannot save to MAL API");
    }

    @Override
    public List<Movie> getLatestMovies(int limit) {
        return new ArrayList<>(); // MAL doesn't handle movies
    }

    @Override
    public List<TVShow> getLatestTVShows(int limit) {
        return new ArrayList<>(); // MAL doesn't handle TV shows
    }

    /**
     * Get latest/seasonal anime
     */
    public List<Anime> getLatestAnime(int limit) {
        try {
            // Get current season anime (airing now)
            String response = makeApiRequest("anime/ranking?ranking_type=airing&limit=" + limit +
                    "&fields=id,title,main_picture,synopsis,mean,rank,popularity,genres," +
                    "media_type,status,num_episodes,start_season,source,studios");

            JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
            JsonArray dataArray = jsonResponse.getAsJsonArray("data");

            List<Anime> results = new ArrayList<>();
            for (JsonElement element : dataArray) {
                JsonObject nodeObj = element.getAsJsonObject().getAsJsonObject("node");
                Anime anime = parseAnimeFromJson(nodeObj);
                results.add(anime);
            }

            return results;
        } catch (IOException e) {
            System.err.println("Error getting latest anime: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean update(MediaItem mediaItem) {
        throw new UnsupportedOperationException("Cannot update MAL API");
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Cannot delete from MAL API");
    }
}
