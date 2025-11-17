package com.bakorz.model;

import java.util.List;

/**
 * Represents a Movie media item with specific movie-related attributes.
 * Extends MediaItem and includes information from TMDB API.
 * Contains details like runtime, director, budget, and revenue.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class Movie extends MediaItem {
    /** Runtime of the movie in minutes */
    private int runtime;

    /** Director of the movie */
    private String director;

    /** IMDb identifier */
    private String imdbId;

    /** TMDB identifier */
    private String tmdbId;

    /** Production budget in dollars */
    private long budget;

    /** Box office revenue in dollars */
    private long revenue;

    /**
     * Default constructor for Movie.
     */
    public Movie() {
        super();
    }

    /**
     * Parameterized constructor for Movie.
     * 
     * @param id          Unique identifier
     * @param title       Title of the movie
     * @param description Synopsis of the movie
     * @param genres      List of genres
     * @param rating      Rating score
     * @param releaseDate Release date
     * @param posterUrl   URL to poster image
     * @param backdropUrl URL to backdrop image
     * @param runtime     Runtime in minutes
     * @param director    Director name
     * @param imdbId      IMDb ID
     * @param tmdbId      TMDB ID
     * @param budget      Production budget
     * @param revenue     Box office revenue
     */
    public Movie(String id, String title, String description, List<String> genres,
            double rating, String releaseDate, String posterUrl, String backdropUrl,
            int runtime, String director, String imdbId,
            String tmdbId, long budget, long revenue) {
        super(id, title, description, genres, rating, releaseDate, posterUrl, backdropUrl);
        this.runtime = runtime;
        this.director = director;
        this.imdbId = imdbId;
        this.tmdbId = tmdbId;
        this.budget = budget;
        this.revenue = revenue;
    }

    /**
     * Returns the media type identifier for movie.
     * 
     * @return "MOVIE" string constant
     */
    @Override
    public String getMediaType() {
        return "MOVIE";
    }

    // Getters and Setters with documentation

    /**
     * Gets the runtime of the movie.
     * 
     * @return Runtime in minutes
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Sets the runtime of the movie.
     * 
     * @param runtime Runtime in minutes to set
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Gets the director of the movie.
     * 
     * @return Director name
     */
    public String getDirector() {
        return director;
    }

    /**
     * Sets the director of the movie.
     * 
     * @param director Director name to set
     */
    public void setDirector(String director) {
        this.director = director;
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
     * Gets the production budget.
     * 
     * @return Budget in dollars
     */
    public long getBudget() {
        return budget;
    }

    /**
     * Sets the production budget.
     * 
     * @param budget Budget in dollars to set
     */
    public void setBudget(long budget) {
        this.budget = budget;
    }

    /**
     * Gets the box office revenue.
     * 
     * @return Revenue in dollars
     */
    public long getRevenue() {
        return revenue;
    }

    /**
     * Sets the box office revenue.
     * 
     * @param revenue Revenue in dollars to set
     */
    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    /**
     * Returns a string representation of the Movie.
     * 
     * @return String containing key movie information
     */
    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", runtime=" + runtime +
                ", rating=" + rating +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
