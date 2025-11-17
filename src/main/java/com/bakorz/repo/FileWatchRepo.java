package com.bakorz.repo;

import com.bakorz.model.WatchEntry;
import java.io.*;
import java.util.*;

public class FileWatchRepo implements WatchRepo {
    private static final String WATCH_FILE = "data/watch.csv";
    private static final String DELIMITER = ",";

    private Map<String, WatchEntry> watchCache;

    public FileWatchRepo() {
        this.watchCache = new HashMap<>();
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(WATCH_FILE);
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

                WatchEntry entry = parseWatchEntry(line);
                if (entry != null) {
                    watchCache.put(entry.getWatchId(), entry);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading watch file: " + e.getMessage());
        }
    }

    private WatchEntry parseWatchEntry(String line) {
        String[] parts = line.split(DELIMITER, -1);
        if (parts.length < 4) {
            return null;
        }

        try {
            String watchId = parts[0];
            String userId = parts[1];
            String mediaId = parts[2];
            String mediaSource = parts[3].isEmpty() ? "FILE" : parts[3];

            WatchEntry entry = new WatchEntry(watchId, userId, mediaId);
            entry.setMediaSource(mediaSource);
            return entry;
        } catch (Exception e) {
            System.err.println("Error parsing watch entry line: " + e.getMessage());
            return null;
        }
    }

    private void saveToFile() {
        File file = new File(WATCH_FILE);
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // Write header
            bw.write("watchId,userId,mediaId,mediaSource");
            bw.newLine();

            // Write all entries
            for (WatchEntry entry : watchCache.values()) {
                String line = entryToCSV(entry);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving watch file: " + e.getMessage());
        }
    }

    private String entryToCSV(WatchEntry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(entry.getWatchId()).append(DELIMITER);
        sb.append(entry.getUserId()).append(DELIMITER);
        sb.append(entry.getMediaId()).append(DELIMITER);
        sb.append(entry.getMediaSource() != null ? entry.getMediaSource() : "FILE");
        return sb.toString();
    }

    @Override
    public boolean addWatchEntry(WatchEntry entry) {
        if (entry == null || watchCache.containsKey(entry.getWatchId())) {
            return false;
        }
        watchCache.put(entry.getWatchId(), entry);
        saveToFile();
        return true;
    }

    @Override
    public boolean removeWatchEntry(String watchId) {
        if (watchCache.remove(watchId) != null) {
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public List<WatchEntry> getWatchEntriesByUser(String userId) {
        List<WatchEntry> result = new ArrayList<>();
        for (WatchEntry entry : watchCache.values()) {
            if (entry.getUserId().equals(userId)) {
                result.add(entry);
            }
        }
        return result;
    }

    @Override
    public Optional<WatchEntry> getWatchEntryById(String watchId) {
        return Optional.ofNullable(watchCache.get(watchId));
    }

    @Override
    public Optional<WatchEntry> getWatchEntryByUserAndMedia(String userId, String mediaId) {
        for (WatchEntry entry : watchCache.values()) {
            if (entry.getUserId().equals(userId) && entry.getMediaId().equals(mediaId)) {
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean update(WatchEntry watchEntry) {
        if (watchEntry == null || !watchCache.containsKey(watchEntry.getWatchId())) {
            return false;
        }

        watchCache.put(watchEntry.getWatchId(), watchEntry);
        saveToFile();
        return true;
    }

    @Override
    public List<WatchEntry> getAll() {
        return new ArrayList<>(watchCache.values());
    }
}
