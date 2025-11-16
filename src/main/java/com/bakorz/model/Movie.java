package com.bakorz.model;

import java.util.List;

/**
 * Represents a Movie media item
 */
public class Movie extends MediaItem {
    private int runtime; // in minutes
    private String director;
    private List<String> cast;
    private String imdbId;
    private String tmdbId;
    private long budget;
    private long revenue;

    public Movie() {
        super();
    }

    public Movie(String id, String title, String description, List<String> genres,
            double rating, String releaseDate, String posterUrl, String backdropUrl,
            int runtime, String director, List<String> cast, String imdbId,
            String tmdbId, long budget, long revenue) {
        super(id, title, description, genres, rating, releaseDate, posterUrl, backdropUrl);
        this.runtime = runtime;
        this.director = director;
        this.cast = cast;
        this.imdbId = imdbId;
        this.tmdbId = tmdbId;
        this.budget = budget;
        this.revenue = revenue;
    }

    @Override
    public String getMediaType() {
        return "MOVIE";
    }

    // Getters and Setters
    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
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

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

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
