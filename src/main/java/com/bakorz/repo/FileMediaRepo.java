package com.bakorz.repo;

import com.bakorz.model.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * File-based implementation of MediaRepo interface.
 * Stores media data in a CSV file (data/media.csv) for caching purposes.
 * Uses in-memory caching for fast retrieval.
 * Supports all media types (Anime, Movie, TVShow) with composite key storage.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class FileMediaRepo implements MediaRepo {
    /** Path to the media cache CSV file */
    private static final String MEDIA_FILE = "data/media.csv";

    /** CSV delimiter character */
    private static final String DELIMITER = ",";

    /** In-memory cache of media items mapped by ID (with composite keys) */
    private Map<String, MediaItem> mediaCache;

    /**
     * Constructor that initializes the repository and loads existing cached data.
     */
    public FileMediaRepo() {
        this.mediaCache = new HashMap<>();
        loadFromFile();
    }

    /**
     * Loads media data from CSV file into memory cache.
     * Skips header line and handles missing files gracefully.
     */
    private void loadFromFile() {
        File file = new File(MEDIA_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }

                MediaItem item = parseMediaItem(line);
                if (item != null) {
                    mediaCache.put(item.getId(), item);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading media file: " + e.getMessage());
        }
    }

    /**
     * Parses a CSV line into appropriate MediaItem subclass (Anime, Movie, or
     * TVShow).
     * Uses media type field to determine which subclass to instantiate.
     * 
     * @param line CSV line to parse
     * @return MediaItem object (Anime, Movie, or TVShow) or null if parsing fails
     */
    private MediaItem parseMediaItem(String line) {
        try {
            List<String> fields = parseCSVLine(line);
            if (fields.size() < 10) {
                return null;
            }

            String id = fields.get(0);
            String type = fields.get(1);
            String title = fields.get(2);
            String description = fields.get(3);
            List<String> genres = fields.get(4).isEmpty() ? new ArrayList<>()
                    : Arrays.asList(fields.get(4).split(";"));
            double rating = fields.get(5).isEmpty() ? 0.0 : Double.parseDouble(fields.get(5));
            String releaseDate = fields.get(6);
            String posterUrl = fields.get(7);
            String backdropUrl = fields.get(8);
            String additionalData = fields.get(9);

            MediaItem item;
            switch (type.toLowerCase()) {
                case "movie":
                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setDescription(description);
                    movie.setGenres(genres);
                    movie.setRating(rating);
                    movie.setReleaseDate(releaseDate);
                    movie.setPosterUrl(posterUrl);
                    movie.setBackdropUrl(backdropUrl);
                    parseMovieAdditionalData(movie, additionalData);
                    item = movie;
                    break;
                case "tvshow":
                case "tv_show":
                    TVShow tvShow = new TVShow();
                    tvShow.setId(id);
                    tvShow.setTitle(title);
                    tvShow.setDescription(description);
                    tvShow.setGenres(genres);
                    tvShow.setRating(rating);
                    tvShow.setReleaseDate(releaseDate);
                    tvShow.setPosterUrl(posterUrl);
                    tvShow.setBackdropUrl(backdropUrl);
                    parseTVShowAdditionalData(tvShow, additionalData);
                    item = tvShow;
                    break;
                case "anime":
                    Anime anime = new Anime();
                    anime.setId(id);
                    anime.setTitle(title);
                    anime.setDescription(description);
                    anime.setGenres(genres);
                    anime.setRating(rating);
                    anime.setReleaseDate(releaseDate);
                    anime.setPosterUrl(posterUrl);
                    anime.setBackdropUrl(backdropUrl);
                    parseAnimeAdditionalData(anime, additionalData);
                    item = anime;
                    break;
                default:
                    return null;
            }

            return item;
        } catch (Exception e) {
            System.err.println("Error parsing media item: " + e.getMessage());
            return null;
        }
    }

    private List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result;
    }

    private void parseMovieAdditionalData(Movie movie, String data) {
        if (data.isEmpty())
            return;
        try {
            String[] parts = data.split("\\|");
            if (parts.length >= 1 && !parts[0].isEmpty()) {
                movie.setRuntime(Integer.parseInt(parts[0]));
            }
            if (parts.length >= 2)
                movie.setDirector(parts[1]);
        } catch (Exception e) {
            // Ignore parsing errors for additional data
        }
    }

    private void parseTVShowAdditionalData(TVShow tvShow, String data) {
        if (data.isEmpty())
            return;
        try {
            String[] parts = data.split("\\|");
            if (parts.length >= 1 && !parts[0].isEmpty()) {
                tvShow.setNumberOfSeasons(Integer.parseInt(parts[0]));
            }
            if (parts.length >= 2 && !parts[1].isEmpty()) {
                tvShow.setNumberOfEpisodes(Integer.parseInt(parts[1]));
            }
            if (parts.length >= 3)
                tvShow.setStatus(parts[2]);
        } catch (Exception e) {
            // Ignore parsing errors for additional data
        }
    }

    private void parseAnimeAdditionalData(Anime anime, String data) {
        if (data.isEmpty())
            return;
        try {
            String[] parts = data.split("\\|");
            if (parts.length >= 1 && !parts[0].isEmpty()) {
                anime.setEpisodes(Integer.parseInt(parts[0]));
            }
            if (parts.length >= 2 && !parts[1].isEmpty()) {
                anime.setStudios(Arrays.asList(parts[1].split(";")));
            }
            if (parts.length >= 3)
                anime.setStatus(parts[2]);
        } catch (Exception e) {
            // Ignore parsing errors for additional data
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEDIA_FILE))) {
            // Write header
            bw.write("id,type,title,description,genres,rating,releaseDate,posterUrl,backdropUrl,additionalData");
            bw.newLine();

            // Write each media item
            for (MediaItem item : mediaCache.values()) {
                bw.write(formatMediaItem(item));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving media file: " + e.getMessage());
        }
    }

    private String formatMediaItem(MediaItem item) {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getId()).append(DELIMITER);
        sb.append(item.getMediaType()).append(DELIMITER);
        sb.append(escapeCSV(item.getTitle())).append(DELIMITER);
        sb.append(escapeCSV(item.getDescription())).append(DELIMITER);
        sb.append(escapeCSV(String.join(";", item.getGenres() != null ? item.getGenres() : new ArrayList<>())))
                .append(DELIMITER);
        sb.append(item.getRating()).append(DELIMITER);
        sb.append(item.getReleaseDate()).append(DELIMITER);
        sb.append(escapeCSV(item.getPosterUrl())).append(DELIMITER);
        sb.append(escapeCSV(item.getBackdropUrl())).append(DELIMITER);

        sb.append(escapeCSV(formatAdditionalData(item)));

        return sb.toString();
    }

    private String formatAdditionalData(MediaItem item) {
        StringBuilder sb = new StringBuilder();
        if (item instanceof Movie) {
            Movie movie = (Movie) item;
            sb.append(movie.getRuntime()).append("|");
            sb.append(movie.getDirector() != null ? movie.getDirector() : "").append("|");
        } else if (item instanceof TVShow) {
            TVShow tvShow = (TVShow) item;
            sb.append(tvShow.getNumberOfSeasons()).append("|");
            sb.append(tvShow.getNumberOfEpisodes()).append("|");
            sb.append(tvShow.getStatus() != null ? tvShow.getStatus() : "").append("|");
        } else if (item instanceof Anime) {
            Anime anime = (Anime) item;
            sb.append(anime.getEpisodes()).append("|");
            sb.append(anime.getStudios() != null ? String.join(";", anime.getStudios()) : "").append("|");
            sb.append(anime.getStatus() != null ? anime.getStatus() : "").append("|");
        }
        return sb.toString();
    }

    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    @Override
    public List<MediaItem> searchByTitle(String title) {
        return mediaCache.values().stream()
                .filter(item -> item.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MediaItem> getById(String id) {
        return Optional.ofNullable(mediaCache.get(id));
    }

    @Override
    public Optional<Movie> getMovieById(String id) {
        MediaItem item = mediaCache.get(id);
        if (item instanceof Movie) {
            return Optional.of((Movie) item);
        }
        return Optional.empty();
    }

    @Override
    public Optional<TVShow> getTVShowById(String id) {
        MediaItem item = mediaCache.get(id);
        if (item instanceof TVShow) {
            return Optional.of((TVShow) item);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Anime> getAnimeById(String id) {
        MediaItem item = mediaCache.get(id);
        if (item instanceof Anime) {
            return Optional.of((Anime) item);
        }
        return Optional.empty();
    }

    @Override
    public List<MediaItem> getByGenre(String genre) {
        return mediaCache.values().stream()
                .filter(item -> item.getGenres() != null &&
                        item.getGenres().stream().anyMatch(g -> g.equalsIgnoreCase(genre)))
                .collect(Collectors.toList());
    }

    @Override
    public List<MediaItem> getTopRated(int limit) {
        return mediaCache.values().stream()
                .sorted((a, b) -> Double.compare(b.getRating(), a.getRating()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> getLatestMovies(int limit) {
        return mediaCache.values().stream()
                .filter(item -> item instanceof Movie)
                .map(item -> (Movie) item)
                .filter(movie -> movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty())
                .sorted((a, b) -> b.getReleaseDate().compareTo(a.getReleaseDate()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<TVShow> getLatestTVShows(int limit) {
        return mediaCache.values().stream()
                .filter(item -> item instanceof TVShow)
                .map(item -> (TVShow) item)
                .filter(tv -> tv.getReleaseDate() != null && !tv.getReleaseDate().isEmpty())
                .sorted((a, b) -> b.getReleaseDate().compareTo(a.getReleaseDate()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<MediaItem> getAll() {
        return new ArrayList<>(mediaCache.values());
    }

    @Override
    public boolean save(MediaItem mediaItem) {
        if (mediaItem == null || mediaItem.getId() == null) {
            return false;
        }

        mediaCache.put(mediaItem.getId(), mediaItem);
        saveToFile();
        return true;
    }

    @Override
    public boolean update(MediaItem mediaItem) {
        if (mediaItem == null || !mediaCache.containsKey(mediaItem.getId())) {
            return false;
        }

        mediaCache.put(mediaItem.getId(), mediaItem);
        saveToFile();
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (mediaCache.remove(id) != null) {
            saveToFile();
            return true;
        }
        return false;
    }
}
