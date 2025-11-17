package com.bakorz.model;

import java.util.List;

/**
 * Represents an Anime media item with specific anime-related attributes.
 * Extends MediaItem and includes information from MyAnimeList API.
 * Contains details like episodes, studios, MAL rankings, and airing status.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class Anime extends MediaItem {
    /** Type of anime (TV, Movie, OVA, etc.) */
    private String animeType;

    /** Total number of episodes */
    private int episodes;

    /** Current airing status (finished_airing, currently_airing, not_yet_aired) */
    private String status;

    /** Airing date information */
    private String aired;

    /** Source material (Manga, Light Novel, Original, etc.) */
    private String source;

    /** List of animation studios */
    private List<String> studios;

    /** MyAnimeList ID */
    private int malId;

    /** MyAnimeList score (0-10) */
    private double malScore;

    /** MyAnimeList ranking position */
    private int malRank;

    /** MyAnimeList popularity ranking */
    private int malPopularity;

    /** Season of release (Spring, Summer, Fall, Winter) */
    private String season;

    /** Year of release */
    private int year;

    /** Duration per episode in minutes */
    private int duration;

    /**
     * Default constructor for Anime.
     */
    public Anime() {
        super();
    }

    /**
     * Parameterized constructor for Anime.
     * 
     * @param id            Unique identifier
     * @param title         Title of the anime
     * @param description   Synopsis of the anime
     * @param genres        List of genres
     * @param rating        Rating score
     * @param releaseDate   Release date
     * @param posterUrl     URL to poster image
     * @param backdropUrl   URL to backdrop image
     * @param animeType     Type of anime (TV, Movie, OVA, etc.)
     * @param episodes      Number of episodes
     * @param status        Airing status
     * @param aired         Airing date information
     * @param source        Source material
     * @param studios       List of animation studios
     * @param malId         MyAnimeList ID
     * @param malScore      MyAnimeList score
     * @param malRank       MyAnimeList rank
     * @param malPopularity MyAnimeList popularity rank
     * @param season        Release season
     * @param year          Release year
     * @param duration      Episode duration in minutes
     */
    public Anime(String id, String title, String description, List<String> genres,
            double rating, String releaseDate, String posterUrl, String backdropUrl,
            String animeType, int episodes, String status, String aired,
            String source, List<String> studios, int malId, double malScore,
            int malRank, int malPopularity,
            String season, int year, int duration) {
        super(id, title, description, genres, rating, releaseDate, posterUrl, backdropUrl);
        this.animeType = animeType;
        this.episodes = episodes;
        this.status = status;
        this.aired = aired;
        this.source = source;
        this.studios = studios;
        this.malId = malId;
        this.malScore = malScore;
        this.malRank = malRank;
        this.malPopularity = malPopularity;
        this.season = season;
        this.year = year;
        this.duration = duration;
    }

    /**
     * Returns the media type identifier for anime.
     * 
     * @return "ANIME" string constant
     */
    @Override
    public String getMediaType() {
        return "ANIME";
    }

    // Getters and Setters with documentation

    /**
     * Gets the type of anime.
     * 
     * @return Anime type (TV, Movie, OVA, etc.)
     */
    public String getAnimeType() {
        return animeType;
    }

    /**
     * Sets the type of anime.
     * 
     * @param animeType Anime type to set
     */
    public void setAnimeType(String animeType) {
        this.animeType = animeType;
    }

    /**
     * Gets the number of episodes.
     * 
     * @return Total episode count
     */
    public int getEpisodes() {
        return episodes;
    }

    /**
     * Sets the number of episodes.
     * 
     * @param episodes Episode count to set
     */
    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    /**
     * Gets the airing status.
     * 
     * @return Status (finished_airing, currently_airing, not_yet_aired)
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
     * Gets the airing date information.
     * 
     * @return Airing date string
     */
    public String getAired() {
        return aired;
    }

    /**
     * Sets the airing date information.
     * 
     * @param aired Airing date to set
     */
    public void setAired(String aired) {
        this.aired = aired;
    }

    /**
     * Gets the source material type.
     * 
     * @return Source (Manga, Light Novel, Original, etc.)
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source material type.
     * 
     * @param source Source material to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Gets the list of animation studios.
     * 
     * @return List of studio names
     */
    public List<String> getStudios() {
        return studios;
    }

    /**
     * Sets the list of animation studios.
     * 
     * @param studios List of studio names to set
     */
    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    /**
     * Gets the MyAnimeList ID.
     * 
     * @return MAL ID
     */
    public int getMalId() {
        return malId;
    }

    /**
     * Sets the MyAnimeList ID.
     * 
     * @param malId MAL ID to set
     */
    public void setMalId(int malId) {
        this.malId = malId;
    }

    /**
     * Gets the MyAnimeList score.
     * 
     * @return MAL score (0-10)
     */
    public double getMalScore() {
        return malScore;
    }

    /**
     * Sets the MyAnimeList score.
     * 
     * @param malScore MAL score to set
     */
    public void setMalScore(double malScore) {
        this.malScore = malScore;
    }

    /**
     * Gets the MyAnimeList ranking position.
     * 
     * @return MAL rank
     */
    public int getMalRank() {
        return malRank;
    }

    /**
     * Sets the MyAnimeList ranking position.
     * 
     * @param malRank MAL rank to set
     */
    public void setMalRank(int malRank) {
        this.malRank = malRank;
    }

    /**
     * Gets the MyAnimeList popularity ranking.
     * 
     * @return MAL popularity rank
     */
    public int getMalPopularity() {
        return malPopularity;
    }

    /**
     * Sets the MyAnimeList popularity ranking.
     * 
     * @param malPopularity MAL popularity rank to set
     */
    public void setMalPopularity(int malPopularity) {
        this.malPopularity = malPopularity;
    }

    /**
     * Gets the release season.
     * 
     * @return Season (Spring, Summer, Fall, Winter)
     */
    public String getSeason() {
        return season;
    }

    /**
     * Sets the release season.
     * 
     * @param season Season to set
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * Gets the release year.
     * 
     * @return Year of release
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the release year.
     * 
     * @param year Year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the episode duration.
     * 
     * @return Duration per episode in minutes
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the episode duration.
     * 
     * @param duration Duration in minutes to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Returns a string representation of the Anime.
     * 
     * @return String containing key anime information
     */
    @Override
    public String toString() {
        return "Anime{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", animeType='" + animeType + '\'' +
                ", episodes=" + episodes +
                ", status='" + status + '\'' +
                ", malScore=" + malScore +
                ", rating=" + rating +
                '}';
    }
}
