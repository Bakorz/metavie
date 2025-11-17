package com.bakorz.model;

import java.util.List;

/**
 * Represents an Anime media item in the Metavie application.
 * This class extends MediaItem and adds anime-specific properties such as
 * episode count, airing status, studios, and MyAnimeList (MAL) statistics.
 */
public class Anime extends MediaItem {
    /** Type of anime (e.g., TV, Movie, OVA, Special) */
    private String animeType; 
    
    /** Number of episodes in the anime series */
    private int episodes;
    
    /** Current airing status (e.g., "finished_airing", "currently_airing") */
    private String status;
    
    /** Airing date or date range */
    private String aired; 
    
    /** Source material (e.g., Manga, Light Novel, Original) */
    private String source; 
    
    /** List of animation studios that produced the anime */
    private List<String> studios;
    
    /** MyAnimeList ID for this anime */
    private int malId;
    
    /** MyAnimeList score/rating */
    private double malScore;
    
    /** MyAnimeList ranking position */
    private int malRank;
    
    /** MyAnimeList popularity ranking */
    private int malPopularity;
    
    /** Season when the anime aired (e.g., "Spring", "Fall") */
    private String season;
    
    /** Year when the anime was released */
    private int year;
    
    /** Duration of each episode in minutes */
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
     * @param id Unique identifier
     * @param title Title of the anime
     * @param description Synopsis or description
     * @param genres List of genres
     * @param rating Rating score
     * @param releaseDate Release date
     * @param posterUrl URL to poster image
     * @param backdropUrl URL to backdrop image
     * @param animeType Type of anime (TV, Movie, etc.)
     * @param episodes Number of episodes
     * @param status Airing status
     * @param aired Airing date/period
     * @param source Source material
     * @param studios List of production studios
     * @param malId MyAnimeList ID
     * @param malScore MyAnimeList score
     * @param malRank MyAnimeList rank
     * @param malPopularity MyAnimeList popularity
     * @param season Airing season
     * @param year Release year
     * @param duration Episode duration in minutes
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
     * Returns the media type for anime.
     * 
     * @return "ANIME" string constant
     */
    /**
     * Returns the media type for anime.
     * 
     * @return "ANIME" string constant
     */
    @Override
    public String getMediaType() {
        return "ANIME";
    }

    // Getters and Setters with documentation

    /** @return The type of anime (TV, Movie, OVA, etc.) */
    public String getAnimeType() {
        return animeType;
    }

    /** @param animeType The anime type to set */
    public void setAnimeType(String animeType) {
        this.animeType = animeType;
    }

    /** @return Number of episodes */
    public int getEpisodes() {
        return episodes;
    }

    /** @param episodes Number of episodes to set */
    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    /** @return Current airing status */
    public String getStatus() {
        return status;
    }

    /** @param status Airing status to set */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return Airing date or period */
    public String getAired() {
        return aired;
    }

    /** @param aired Airing date to set */
    public void setAired(String aired) {
        this.aired = aired;
    }

    /** @return Source material of the anime */
    public String getSource() {
        return source;
    }

    /** @param source Source material to set */
    public void setSource(String source) {
        this.source = source;
    }

    /** @return List of production studios */
    public List<String> getStudios() {
        return studios;
    }

    /** @param studios List of studios to set */
    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    /** @return MyAnimeList ID */
    public int getMalId() {
        return malId;
    }

    /** @param malId MAL ID to set */
    public void setMalId(int malId) {
        this.malId = malId;
    }

    /** @return MyAnimeList score */
    public double getMalScore() {
        return malScore;
    }

    /** @param malScore MAL score to set */
    public void setMalScore(double malScore) {
        this.malScore = malScore;
    }

    /** @return MyAnimeList ranking */
    public int getMalRank() {
        return malRank;
    }

    /** @param malRank MAL rank to set */
    public void setMalRank(int malRank) {
        this.malRank = malRank;
    }

    /** @return MyAnimeList popularity ranking */
    public int getMalPopularity() {
        return malPopularity;
    }

    /** @param malPopularity MAL popularity to set */
    public void setMalPopularity(int malPopularity) {
        this.malPopularity = malPopularity;
    }

    /** @return Airing season */
    public String getSeason() {
        return season;
    }

    /** @param season Airing season to set */
    public void setSeason(String season) {
        this.season = season;
    }

    /** @return Release year */
    public int getYear() {
        return year;
    }

    /** @param year Release year to set */
    public void setYear(int year) {
        this.year = year;
    }

    /** @return Episode duration in minutes */
    public int getDuration() {
        return duration;
    }

    /** @param duration Episode duration to set */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Returns a string representation of the Anime object.
     * 
     * @return String with anime details
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
