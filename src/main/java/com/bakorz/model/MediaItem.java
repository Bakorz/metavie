package com.bakorz.model;

import java.util.List;

/**
 * Abstract base class representing a media item in the Metavie application.
 * This class serves as the parent for all specific media types (Anime, Movie,
 * TVShow).
 * Contains common attributes and methods shared across all media types.
 * 
 * @author Bakorz
 * @version 1.0
 */
public abstract class MediaItem {
    /** Unique identifier for the media item */
    protected String id;

    /** Title of the media item */
    protected String title;

    /** Description or synopsis of the media item */
    protected String description;

    /** List of genres associated with the media item */
    protected List<String> genres;

    /** Rating score of the media item (0-10) */
    protected double rating;

    /** Release date of the media item */
    protected String releaseDate;

    /** URL to the poster image */
    protected String posterUrl;

    /** URL to the backdrop/banner image */
    protected String backdropUrl;

    /**
     * Default constructor for MediaItem.
     */
    public MediaItem() {
    }

    /**
     * Parameterized constructor for MediaItem.
     * 
     * @param id          Unique identifier
     * @param title       Title of the media
     * @param description Description or synopsis
     * @param genres      List of genres
     * @param rating      Rating score
     * @param releaseDate Release date
     * @param posterUrl   URL to poster image
     * @param backdropUrl URL to backdrop image
     */
    public MediaItem(String id, String title, String description, List<String> genres,
            double rating, String releaseDate, String posterUrl, String backdropUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.backdropUrl = backdropUrl;
    }

    /**
     * Abstract method to get the type of media.
     * Must be implemented by subclasses to return their specific type.
     * 
     * @return String representing the media type (e.g., "ANIME", "MOVIE",
     *         "TV_SHOW")
     */
    public abstract String getMediaType();

    /**
     * Gets the unique identifier of the media item.
     * 
     * @return The media item ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the media item.
     * 
     * @param id The media item ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of the media item.
     * 
     * @return The media title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the media item.
     * 
     * @param title The media title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the media item.
     * 
     * @return The media description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the media item.
     * 
     * @param description The media description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list of genres for the media item.
     * 
     * @return List of genre names
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Sets the list of genres for the media item.
     * 
     * @param genres List of genre names to set
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * Gets the rating score of the media item.
     * 
     * @return Rating score (0-10)
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the rating score of the media item.
     * 
     * @param rating Rating score to set (0-10)
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Gets the release date of the media item.
     * 
     * @return Release date as string
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the media item.
     * 
     * @param releaseDate Release date to set
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the URL of the poster image.
     * 
     * @return Poster image URL
     */
    public String getPosterUrl() {
        return posterUrl;
    }

    /**
     * Sets the URL of the poster image.
     * 
     * @param posterUrl Poster image URL to set
     */
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    /**
     * Gets the URL of the backdrop image.
     * 
     * @return Backdrop image URL
     */
    public String getBackdropUrl() {
        return backdropUrl;
    }

    /**
     * Sets the URL of the backdrop image.
     * 
     * @param backdropUrl Backdrop image URL to set
     */
    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    /**
     * Returns a string representation of the MediaItem.
     * 
     * @return String containing key media item information
     */
    @Override
    public String toString() {
        return "MediaItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
