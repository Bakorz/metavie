package com.bakorz.repo;

import com.bakorz.model.WatchEntry;
import java.util.List;
import java.util.Optional;

public interface WatchRepo {

    boolean addWatchEntry(WatchEntry watchEntry);
    boolean removeWatchEntry(String watchId);
    
    List<WatchEntry> getWatchEntriesByUser(String userId);

    Optional<WatchEntry> getWatchEntryById(String watchId);
    Optional<WatchEntry> getWatchEntryByUserAndMedia(String userId, String mediaId);

    boolean update(WatchEntry watchEntry);

    List<WatchEntry> getAll();
}
