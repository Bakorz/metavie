package com.bakorz.model;

import java.util.List;

/**
 * Represents a TV Show media item with specific television-related attributes.
 * Extends MediaItem and includes information from TMDB API.
 * Contains details like seasons, episodes, creators, and airing status.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class TVShow extends MediaItem {
    /** Total number of seasons */
    private int numberOfSeasons;

    /** Total number of episodes across all seasons */
    private int numberOfEpisodes;

    /** List of show creators */
    private List<String> creators;

    /** Current airing status (Returning Series, Ended, etc.) */
    private String status;

    /** Date of first episode aired */
    private String firstAirDate;

    /** Date of last episode aired */
    private String lastAirDate;

    /** List of broadcasting networks */
    private List<String> networks;

    /** Average runtime per episode in minutes */
    private int episodeRuntime;

    /** IMDb identifier */
    private String imdbId;

    /** TMDB identifier */
    private String tmdbId;

    /**
     * Default constructor for TVShow.
     */
    public TVShow() {
        super();
    }

    /**
     * Parameterized constructor for TVShow.
     * 
     * @param id               Unique identifier
     * @param title            Title of the TV show
     * @param description      Synopsis of the TV show
     * @param genres           List of genres
     * @param rating           Rating score
     * @param releaseDate      Release date
     * @param posterUrl        URL to poster image
     * @param backdropUrl      URL to backdrop image
     * @param numberOfSeasons  Number of seasons
     * @param numberOfEpisodes Total number of episodes
     * @param creators         List of creators
     * @param status           Airing status
     * @param firstAirDate     First air date
     * @param lastAirDate      Last air date
     * @param networks         List of networks
     * @param episodeRuntime   Average episode runtime
     * @param imdbId           IMDb ID
     * @param tmdbId           TMDB ID
     */
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

    /**
     * Returns the media type identifier for TV show.
     * 
     * @return "TV_SHOW" string constant
     */
    @Override
    public String getMediaType() {
        return "TV_SHOW";
    }

    // Getters and Setters with documentation

    /**
     * Gets the number of seasons.
     * 
     * @return Total season count
     */
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     * Sets the number of seasons.
     * 
     * @param numberOfSeasons Season count to set
     */
    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    /**
     * Gets the total number of episodes.
     * 
     * @return Total episode count
     */
    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    /**
     * Sets the total number of episodes.
     * 
     * @param numberOfEpisodes Episode count to set
     */
    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    /**
     * Gets the list of creators.
     * 
     * @return List of creator names
     */
    public List<String> getCreators() {
        return creators;
    }

    /**
     * Sets the list of creators.
     * 
     * @param creators List of creator names to set
     */
    public void setCreators(List<String> creators) {
        this.creators = creators;
    }

    /**
     * Gets the airing status.
     * 
     * @return Status (Returning Series, Ended, etc.)
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the airing status.
     * 
     * @param status Airing status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the first air date.
     * 
     * @return First air date string
     */
    public String getFirstAirDate() {
        return firstAirDate;
    }

    /**
     * Sets the first air date.
     * 
     * @param firstAirDate First air date to set
     */
    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    /**
     * Gets the last air date.
     * 
     * @return Last air date string
     */
    public String getLastAirDate() {
        return lastAirDate;
    }

    /**
     * Sets the last air date.
     * 
     * @param lastAirDate Last air date to set
     */
    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    /**
     * Gets the list of broadcasting networks.
     * 
     * @return List of network names
     */
    public List<String> getNetworks() {
        return networks;
    }

    /**
     * Sets the list of broadcasting networks.
     * 
     * @param networks List of network names to set
     */
    public void setNetworks(List<String> networks) {
        this.networks = networks;
    }

    /**
     * Gets the average episode runtime.
     * 
     * @return Runtime in minutes
     */
    public int getEpisodeRuntime() {
        return episodeRuntime;
    }

    /**
     * Sets the average episode runtime.
     * 
     * @param episodeRuntime Runtime in minutes to set
     */
    public void setEpisodeRuntime(int episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    /**
     * Gets the IMDb identifier.
     * 
     * @return IMDb ID
     */
    public String getImdbId() {
        return imdbId;
    }

    /**
     * Sets the IMDb identifier.
     * 
     * @param imdbId IMDb ID to set
     */
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    /**
     * Gets the TMDB identifier.
     * 
     * @return TMDB ID
     */
    public String getTmdbId() {
        return tmdbId;
    }

    /**
     * Sets the TMDB identifier.
     * 
     * @param tmdbId TMDB ID to set
     */
    public void setTmdbId(String tmdbId) {
        this.tmdbId = tmdbId;
    }

    /**
     * Returns a string representation of the TVShow.
     * 
     * @return String containing key TV show information
     */
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
