package com.bakorz.model;

import java.util.List;

public class TVShow extends MediaItem {
    private int numberOfSeasons;
    private int numberOfEpisodes;
    private List<String> creators;
    private String status;
    private String firstAirDate;
    private String lastAirDate;
    private List<String> networks;
    private int episodeRuntime;
    private String imdbId;
    private String tmdbId;

    public TVShow() {
        super();
    }

    public TVShow(String id, String title, String description, List<String> genres,
            double rating, String releaseDate, String posterUrl, String backdropUrl,
            int numberOfSeasons, int numberOfEpisodes, List<String> creators,
            String status, String firstAirDate, String lastAirDate,
            List<String> networks, int episodeRuntime, String imdbId, String tmdbId) {
        super(id, title, description, genres, rating, releaseDate, posterUrl, backdropUrl);
        this.numberOfSeasons = numberOfSeasons;
        this.numberOfEpisodes = numberOfEpisodes;
        this.creators = creators;
        this.status = status;
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.networks = networks;
        this.episodeRuntime = episodeRuntime;
        this.imdbId = imdbId;
        this.tmdbId = tmdbId;
    }

    @Override
    public String getMediaType() {
        return "TV_SHOW";
    }

    // Getters and Setters
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public List<String> getCreators() {
        return creators;
    }

    public void setCreators(List<String> creators) {
        this.creators = creators;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public List<String> getNetworks() {
        return networks;
    }

    public void setNetworks(List<String> networks) {
        this.networks = networks;
    }

    public int getEpisodeRuntime() {
        return episodeRuntime;
    }

    public void setEpisodeRuntime(int episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(String tmdbId) {
        this.tmdbId = tmdbId;
    }

    @Override
    public String toString() {
        return "TVShow{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", numberOfSeasons=" + numberOfSeasons +
                ", numberOfEpisodes=" + numberOfEpisodes +
                ", status='" + status + '\'' +
                ", rating=" + rating +
                '}';
    }
}
