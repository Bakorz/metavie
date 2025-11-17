package com.bakorz.repo;

import com.bakorz.model.*;
import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * TMDB (The Movie Database) API implementation of MediaRepo interface.
 * Connects to TMDB API to fetch movie and TV show data.
 * Requires an API key for authentication.
 * Provides search, retrieval, and ranking operations for movies and TV shows.
 * Converts JSON responses to Movie and TVShow model objects.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class TmdbMediaRepo implements MediaRepo {
    /** Base URL for TMDB API */
    private static final String TMDB_API_BASE = "https://api.themoviedb.org/3";

    /** Base URL for TMDB images */
    private static final String IMAGE_BASE = "https://image.tmdb.org/t/p/";

    /** TMDB API key for authentication */
    private String apiKey;

    /** JSON parser/serializer */
    private Gson gson;

    /**
     * Constructor that initializes the repository with API credentials.
     * 
     * @param apiKey TMDB API key
     */
    public TmdbMediaRepo(String apiKey) {
        this.apiKey = apiKey;
        this.gson = new Gson();
    }

    /**
     * Makes an HTTP GET request to the TMDB API.
     * Appends API key to the request URL for authentication.
     * 
     * @param endpoint API endpoint path (appended to base URL)
     * @return JSON response as string
     * @throws IOException if request fails
     */
    private String makeApiRequest(String endpoint) throws IOException {
        String separator = endpoint.contains("?") ? "&" : "?";
        String urlString = TMDB_API_BASE + endpoint + separator + "api_key=" + apiKey;

        URI uri = URI.create(urlString);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
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

    private Movie parseMovieFromJson(JsonObject movieJson) {
        Movie movie = new Movie();

        movie.setId(String.valueOf(movieJson.get("id").getAsInt()));
        movie.setTitle(movieJson.get("title").getAsString());
        movie.setTmdbId(String.valueOf(movieJson.get("id").getAsInt()));

        if (movieJson.has("overview") && !movieJson.get("overview").isJsonNull()) {
            movie.setDescription(movieJson.get("overview").getAsString());
        }

        if (movieJson.has("vote_average") && !movieJson.get("vote_average").isJsonNull()) {
            movie.setRating(movieJson.get("vote_average").getAsDouble());
        }

        if (movieJson.has("release_date") && !movieJson.get("release_date").isJsonNull()) {
            String dateStr = movieJson.get("release_date").getAsString();
            if (!dateStr.isEmpty()) {
                movie.setReleaseDate(dateStr);
            }
        }

        if (movieJson.has("poster_path") && !movieJson.get("poster_path").isJsonNull()) {
            movie.setPosterUrl(IMAGE_BASE + "w500" + movieJson.get("poster_path").getAsString());
        }
        if (movieJson.has("backdrop_path") && !movieJson.get("backdrop_path").isJsonNull()) {
            movie.setBackdropUrl(IMAGE_BASE + "original" + movieJson.get("backdrop_path").getAsString());
        }

        if (movieJson.has("genres")) {
            List<String> genres = new ArrayList<>();
            JsonArray genresArray = movieJson.getAsJsonArray("genres");
            for (JsonElement genreElement : genresArray) {
                JsonObject genreObj = genreElement.getAsJsonObject();
                genres.add(genreObj.get("name").getAsString());
            }
            movie.setGenres(genres);
        } else if (movieJson.has("genre_ids")) {
            List<String> genres = new ArrayList<>();
            JsonArray genreIds = movieJson.getAsJsonArray("genre_ids");
            for (JsonElement genreId : genreIds) {
                genres.add("Genre " + genreId.getAsInt());
            }
            movie.setGenres(genres);
        }

        if (movieJson.has("runtime") && !movieJson.get("runtime").isJsonNull()) {
            movie.setRuntime(movieJson.get("runtime").getAsInt());
        }

        if (movieJson.has("budget") && !movieJson.get("budget").isJsonNull()) {
            movie.setBudget(movieJson.get("budget").getAsLong());
        }
        if (movieJson.has("revenue") && !movieJson.get("revenue").isJsonNull()) {
            movie.setRevenue(movieJson.get("revenue").getAsLong());
        }

        if (movieJson.has("imdb_id") && !movieJson.get("imdb_id").isJsonNull()) {
            movie.setImdbId(movieJson.get("imdb_id").getAsString());
        }

        return movie;
    }

    private TVShow parseTVShowFromJson(JsonObject tvJson) {
        TVShow tvShow = new TVShow();

        tvShow.setId(String.valueOf(tvJson.get("id").getAsInt()));
        tvShow.setTitle(tvJson.get("name").getAsString());
        tvShow.setTmdbId(String.valueOf(tvJson.get("id").getAsInt()));

        if (tvJson.has("overview") && !tvJson.get("overview").isJsonNull()) {
            tvShow.setDescription(tvJson.get("overview").getAsString());
        }

        if (tvJson.has("vote_average") && !tvJson.get("vote_average").isJsonNull()) {
            tvShow.setRating(tvJson.get("vote_average").getAsDouble());
        }

        if (tvJson.has("first_air_date") && !tvJson.get("first_air_date").isJsonNull()) {
            String dateStr = tvJson.get("first_air_date").getAsString();
            if (!dateStr.isEmpty()) {
                tvShow.setReleaseDate(dateStr);
                tvShow.setFirstAirDate(dateStr);
            }
        }

        if (tvJson.has("last_air_date") && !tvJson.get("last_air_date").isJsonNull()) {
            String dateStr = tvJson.get("last_air_date").getAsString();
            if (!dateStr.isEmpty()) {
                tvShow.setLastAirDate(dateStr);
            }
        }

        if (tvJson.has("poster_path") && !tvJson.get("poster_path").isJsonNull()) {
            tvShow.setPosterUrl(IMAGE_BASE + "w500" + tvJson.get("poster_path").getAsString());
        }
        if (tvJson.has("backdrop_path") && !tvJson.get("backdrop_path").isJsonNull()) {
            tvShow.setBackdropUrl(IMAGE_BASE + "original" + tvJson.get("backdrop_path").getAsString());
        }

        if (tvJson.has("genres")) {
            List<String> genres = new ArrayList<>();
            JsonArray genresArray = tvJson.getAsJsonArray("genres");
            for (JsonElement genreElement : genresArray) {
                JsonObject genreObj = genreElement.getAsJsonObject();
                genres.add(genreObj.get("name").getAsString());
            }
            tvShow.setGenres(genres);
        } else if (tvJson.has("genre_ids")) {
            List<String> genres = new ArrayList<>();
            JsonArray genreIds = tvJson.getAsJsonArray("genre_ids");
            for (JsonElement genreId : genreIds) {
                genres.add("Genre " + genreId.getAsInt());
            }
            tvShow.setGenres(genres);
        }

        if (tvJson.has("number_of_seasons") && !tvJson.get("number_of_seasons").isJsonNull()) {
            tvShow.setNumberOfSeasons(tvJson.get("number_of_seasons").getAsInt());
        }
        if (tvJson.has("number_of_episodes") && !tvJson.get("number_of_episodes").isJsonNull()) {
            tvShow.setNumberOfEpisodes(tvJson.get("number_of_episodes").getAsInt());
        }

        if (tvJson.has("status") && !tvJson.get("status").isJsonNull()) {
            tvShow.setStatus(tvJson.get("status").getAsString());
        }

        if (tvJson.has("episode_run_time")) {
            JsonArray runtimes = tvJson.getAsJsonArray("episode_run_time");
            if (runtimes.size() > 0 && !runtimes.get(0).isJsonNull()) {
                tvShow.setEpisodeRuntime(runtimes.get(0).getAsInt());
            }
        }

        if (tvJson.has("networks")) {
            List<String> networks = new ArrayList<>();
            JsonArray networksArray = tvJson.getAsJsonArray("networks");
            for (JsonElement networkElement : networksArray) {
                JsonObject networkObj = networkElement.getAsJsonObject();
                networks.add(networkObj.get("name").getAsString());
            }
            tvShow.setNetworks(networks);
        }

        if (tvJson.has("created_by")) {
            List<String> creators = new ArrayList<>();
            JsonArray creatorsArray = tvJson.getAsJsonArray("created_by");
            for (JsonElement creatorElement : creatorsArray) {
                JsonObject creatorObj = creatorElement.getAsJsonObject();
                creators.add(creatorObj.get("name").getAsString());
            }
            tvShow.setCreators(creators);
        }

        return tvShow;
    }

    @Override
    public List<MediaItem> searchByTitle(String title) {
        try {
            List<MediaItem> results = new ArrayList<>();

            String movieResponse = makeApiRequest("/search/movie?query=" + URLEncoder.encode(title, "UTF-8"));
            JsonObject movieJson = gson.fromJson(movieResponse, JsonObject.class);
            if (movieJson.has("results")) {
                JsonArray movieResults = movieJson.getAsJsonArray("results");
                for (JsonElement element : movieResults) {
                    results.add(parseMovieFromJson(element.getAsJsonObject()));
                }
            }

            String tvResponse = makeApiRequest("/search/tv?query=" + URLEncoder.encode(title, "UTF-8"));
            JsonObject tvJson = gson.fromJson(tvResponse, JsonObject.class);
            if (tvJson.has("results")) {
                JsonArray tvResults = tvJson.getAsJsonArray("results");
                for (JsonElement element : tvResults) {
                    results.add(parseTVShowFromJson(element.getAsJsonObject()));
                }
            }

            return results;
        } catch (IOException e) {
            System.err.println("Error searching by title: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<MediaItem> getById(String id) {
        Optional<Movie> movie = getMovieById(id);
        if (movie.isPresent()) {
            return movie.map(m -> (MediaItem) m);
        }

        Optional<TVShow> tvShow = getTVShowById(id);
        return tvShow.map(tv -> (MediaItem) tv);
    }

    @Override
    public Optional<Movie> getMovieById(String id) {
        try {
            String response = makeApiRequest("/movie/" + id);
            JsonObject movieJson = gson.fromJson(response, JsonObject.class);
            return Optional.of(parseMovieFromJson(movieJson));
        } catch (IOException e) {
            System.err.println("Error getting movie by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<TVShow> getTVShowById(String id) {
        try {
            String response = makeApiRequest("/tv/" + id);
            JsonObject tvJson = gson.fromJson(response, JsonObject.class);
            return Optional.of(parseTVShowFromJson(tvJson));
        } catch (IOException e) {
            System.err.println("Error getting TV show by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Anime> getAnimeById(String id) {
        return Optional.empty();
    }

    @Override
    public List<MediaItem> getByGenre(String genre) {
        try {
            List<MediaItem> results = new ArrayList<>();

            String movieResponse = makeApiRequest("/discover/movie?with_genres=" + genre);
            JsonObject movieJson = gson.fromJson(movieResponse, JsonObject.class);
            if (movieJson.has("results")) {
                JsonArray movieResults = movieJson.getAsJsonArray("results");
                for (JsonElement element : movieResults) {
                    results.add(parseMovieFromJson(element.getAsJsonObject()));
                }
            }

            return results;
        } catch (IOException e) {
            System.err.println("Error getting by genre: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<MediaItem> getTopRated(int limit) {
        return getTopRated(limit, 1);
    }

    public List<MediaItem> getTopRated(int limit, int page) {
        try {
            List<MediaItem> results = new ArrayList<>();

            int itemsPerPage = 20;
            int moviesNeeded = limit / 2;
            int tvShowsNeeded = limit / 2;

            int moviesFetched = 0;
            int moviePage = page;
            while (moviesFetched < moviesNeeded) {
                String movieResponse = makeApiRequest("/movie/top_rated?page=" + moviePage);
                JsonObject movieJson = gson.fromJson(movieResponse, JsonObject.class);
                if (movieJson.has("results")) {
                    JsonArray movieResults = movieJson.getAsJsonArray("results");
                    for (JsonElement element : movieResults) {
                        if (moviesFetched >= moviesNeeded)
                            break;
                        results.add(parseMovieFromJson(element.getAsJsonObject()));
                        moviesFetched++;
                    }
                    if (movieResults.size() < itemsPerPage)
                        break;
                }
                moviePage++;
            }

            int tvShowsFetched = 0;
            int tvPage = page;
            while (tvShowsFetched < tvShowsNeeded) {
                String tvResponse = makeApiRequest("/tv/top_rated?page=" + tvPage);
                JsonObject tvJson = gson.fromJson(tvResponse, JsonObject.class);
                if (tvJson.has("results")) {
                    JsonArray tvResults = tvJson.getAsJsonArray("results");
                    for (JsonElement element : tvResults) {
                        if (tvShowsFetched >= tvShowsNeeded)
                            break;
                        results.add(parseTVShowFromJson(element.getAsJsonObject()));
                        tvShowsFetched++;
                    }
                    if (tvResults.size() < itemsPerPage)
                        break;
                }
                tvPage++;
            }

            return results;
        } catch (IOException e) {
            System.err.println("Error getting top rated: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Movie> getLatestMovies(int limit) {
        return getLatestMovies(limit, 1);
    }

    public List<Movie> getLatestMovies(int limit, int page) {
        try {
            String response = makeApiRequest("/movie/now_playing?language=en-US&page=" + page);
            JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);

            if (!jsonResponse.has("results")) {
                System.err.println("TMDB API response missing 'results' field");
                return new ArrayList<>();
            }

            JsonArray moviesArray = jsonResponse.getAsJsonArray("results");

            List<Movie> movies = new ArrayList<>();
            int count = 0;

            for (JsonElement movieElement : moviesArray) {
                if (count >= limit)
                    break;

                JsonObject movieJson = movieElement.getAsJsonObject();
                Movie movie = parseMovieFromJson(movieJson);
                if (movie.getTitle() != null && !movie.getTitle().isEmpty()) {
                    movies.add(movie);
                    count++;
                }
            }

            return movies;
        } catch (IOException e) {
            System.err.println("Error getting latest movies: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<TVShow> getLatestTVShows(int limit) {
        return getLatestTVShows(limit, 1);
    }

    public List<TVShow> getLatestTVShows(int limit, int page) {
        try {
            String response = makeApiRequest("/tv/on_the_air?language=en-US&page=" + page);
            JsonObject jsonResponse = gson.fromJson(response, JsonObject.class);
            JsonArray tvArray = jsonResponse.getAsJsonArray("results");

            List<TVShow> tvShows = new ArrayList<>();
            int count = 0;

            for (JsonElement tvElement : tvArray) {
                if (count >= limit)
                    break;

                JsonObject tvJson = tvElement.getAsJsonObject();
                TVShow tvShow = parseTVShowFromJson(tvJson);
                tvShows.add(tvShow);
                count++;
            }

            return tvShows;
        } catch (IOException e) {
            System.err.println("Error getting latest TV shows: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<MediaItem> getAll() {
        throw new UnsupportedOperationException("Cannot fetch all items from TMDB API");
    }

    @Override
    public boolean save(MediaItem mediaItem) {
        throw new UnsupportedOperationException("Cannot save to TMDB API");
    }

    @Override
    public boolean update(MediaItem mediaItem) {
        throw new UnsupportedOperationException("Cannot update TMDB API");
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException("Cannot delete from TMDB API");
    }
}