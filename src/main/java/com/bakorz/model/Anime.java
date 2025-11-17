package com.bakorz.model;

import java.util.List;

public class Anime extends MediaItem {
    private String animeType; 
    private int episodes;
    private String status;
    private String aired; 
    private String source; 
    private List<String> studios;
    private int malId;
    private double malScore;
    private int malRank;
    private int malPopularity;
    private String season;
    private int year;
    private int duration;

    public Anime() {
        super();
    }

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

    @Override
    public String getMediaType() {
        return "ANIME";
    }

    public String getAnimeType() {
        return animeType;
    }

    public void setAnimeType(String animeType) {
        this.animeType = animeType;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAired() {
        return aired;
    }

    public void setAired(String aired) {
        this.aired = aired;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getStudios() {
        return studios;
    }

    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    public int getMalId() {
        return malId;
    }

    public void setMalId(int malId) {
        this.malId = malId;
    }

    public double getMalScore() {
        return malScore;
    }

    public void setMalScore(double malScore) {
        this.malScore = malScore;
    }

    public int getMalRank() {
        return malRank;
    }

    public void setMalRank(int malRank) {
        this.malRank = malRank;
    }

    public int getMalPopularity() {
        return malPopularity;
    }

    public void setMalPopularity(int malPopularity) {
        this.malPopularity = malPopularity;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

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
