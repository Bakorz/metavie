package com.bakorz.repo;

import com.bakorz.model.Favorite;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileFavoriteRepo implements FavoriteRepo {
    private static final String FAVORITES_FILE = "data/favorite.csv";
    private static final String DELIMITER = ",";

    private Map<String, Favorite> favoriteCache;

    public FileFavoriteRepo() {
        this.favoriteCache = new HashMap<>();
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FAVORITES_FILE);
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

                Favorite favorite = parseFavorite(line);
                if (favorite != null) {
                    favoriteCache.put(favorite.getFavoriteId(), favorite);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading favorites file: " + e.getMessage());
        }
    }

    private Favorite parseFavorite(String line) {
        String[] parts = line.split(DELIMITER, -1);
        if (parts.length < 4) {
            return null;
        }

        try {
            String favoriteId = parts[0];
            String userId = parts[1];
            String mediaId = parts[2];
            String mediaSource = parts[3].isEmpty() ? "FILE" : parts[3];

            return new Favorite(favoriteId, userId, mediaId, mediaSource);
        } catch (Exception e) {
            System.err.println("Error parsing favorite line: " + e.getMessage());
            return null;
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FAVORITES_FILE))) {
            bw.write("favoriteId,userId,mediaId,mediaSource");
            bw.newLine();

            for (Favorite favorite : favoriteCache.values()) {
                bw.write(formatFavorite(favorite));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving favorites file: " + e.getMessage());
        }
    }

    private String formatFavorite(Favorite favorite) {
        StringBuilder sb = new StringBuilder();
        sb.append(favorite.getFavoriteId()).append(DELIMITER);
        sb.append(favorite.getUserId()).append(DELIMITER);
        sb.append(favorite.getMediaId()).append(DELIMITER);
        sb.append(favorite.getMediaSource() != null ? favorite.getMediaSource() : "FILE");
        return sb.toString();
    }

    @Override
    public boolean addFavorite(Favorite favorite) {
        if (favorite == null || favorite.getFavoriteId() == null) {
            return false;
        }

        favoriteCache.put(favorite.getFavoriteId(), favorite);
        saveToFile();
        return true;
    }

    @Override
    public boolean removeFavorite(String favoriteId) {
        if (favoriteCache.remove(favoriteId) != null) {
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFavoriteByUserAndMedia(String userId, String mediaId) {
        Optional<Favorite> favorite = favoriteCache.values().stream()
                .filter(f -> f.getUserId().equals(userId) && f.getMediaId().equals(mediaId))
                .findFirst();

        if (favorite.isPresent()) {
            return removeFavorite(favorite.get().getFavoriteId());
        }
        return false;
    }

    @Override
    public List<Favorite> getFavoritesByUser(String userId) {
        return favoriteCache.values().stream()
                .filter(f -> f.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Favorite> getFavoriteById(String favoriteId) {
        return Optional.ofNullable(favoriteCache.get(favoriteId));
    }

    @Override
    public boolean isFavorited(String userId, String mediaId) {
        return favoriteCache.values().stream()
                .anyMatch(f -> f.getUserId().equals(userId) && f.getMediaId().equals(mediaId));
    }

    @Override
    public List<Favorite> getAll() {
        return new ArrayList<>(favoriteCache.values());
    }

    @Override
    public boolean update(Favorite favorite) {
        if (favorite == null || !favoriteCache.containsKey(favorite.getFavoriteId())) {
            return false;
        }

        favoriteCache.put(favorite.getFavoriteId(), favorite);
        saveToFile();
        return true;
    }
}
