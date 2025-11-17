package com.bakorz.ui;

import com.bakorz.model.*;
import com.bakorz.service.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class MainViewController {
    private static final String BG_COLOR = "#141414";
    private static final String CARD_BG = "#2a2a2a";
    private static final String RED = "#E50914";

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private ScrollPane contentScrollPane;
    @FXML
    private VBox contentContainer;

    @FXML
    private HBox continueWatchingContainer;
    @FXML
    private HBox favoritesContainer;
    @FXML
    private HBox latestMoviesContainer;
    @FXML
    private HBox topRatedMoviesContainer;
    @FXML
    private HBox latestTVShowsContainer;
    @FXML
    private HBox topRatedTVShowsContainer;
    @FXML
    private HBox topRatedAnimeContainer;
    @FXML
    private HBox airingNowAnimeContainer;

    private CatalogService catalogService;
    private FavoriteService favoriteService;
    private TrackingService trackingService;
    private String userId;

    private final Map<String, Integer> pageCounters = new HashMap<>();

    @FXML
    public void initialize() {
        System.out.println("MainViewController FXML initialized!");
        pageCounters.put("Top Rated Movies", 1);
        pageCounters.put("Latest Movies", 1);
        pageCounters.put("Top Rated TV Shows", 1);
        pageCounters.put("Latest TV Shows", 1);
        pageCounters.put("Top Rated Anime", 0);
        pageCounters.put("Latest Anime", 1);
    }

    public void setServices(
            CatalogService catalogService,
            FavoriteService favoriteService,
            TrackingService trackingService,
            String userId) {
        this.catalogService = catalogService;
        this.favoriteService = favoriteService;
        this.trackingService = trackingService;
        this.userId = userId;

        loadAllSections();
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().trim();
        if (!query.isEmpty()) {
            performSearch(query);
        }
    }

    private void performSearch(String query) {
        new Thread(() -> {
            try {
                List<MediaItem> results = catalogService.searchAll(query);
                Platform.runLater(() -> showSearchResults(query, results));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> showError("Search failed: " + e.getMessage()));
            }
        }).start();
    }

    private void showSearchResults(String query, List<MediaItem> results) {
        Stage searchStage = new Stage();
        searchStage.setTitle("Search Results: " + query);

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: " + BG_COLOR);

        Label title = new Label("Found " + results.size() + " results for \"" + query + "\"");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        FlowPane flow = new FlowPane(15, 15);
        flow.setPrefWrapLength(1200);
        flow.setAlignment(Pos.TOP_LEFT);

        for (MediaItem item : results) {
            flow.getChildren().add(createMediaCard(item, true));
        }

        ScrollPane scrollPane = new ScrollPane(flow);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + BG_COLOR + "; -fx-background-color: " + BG_COLOR);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.getChildren().addAll(title, scrollPane);

        Scene scene = new Scene(root, 1200, 700);
        searchStage.setScene(scene);
        searchStage.setMinWidth(1200);
        searchStage.setMinHeight(700);
        searchStage.setWidth(1200);
        searchStage.setHeight(700);
        searchStage.centerOnScreen();
        searchStage.show();
    }

    private void loadAllSections() {
        new Thread(() -> {
            loadContinueWatching();
            loadFavorites();
            loadTopRatedMovies();
            loadLatestMovies();
            loadTopRatedTVShows();
            loadLatestTVShows();
            loadTopAnime();
            loadAiringNowAnime();
        }).start();
    }

    private void loadContinueWatching() {
        List<WatchEntry> watching = trackingService.getCurrentlyWatching(userId);
        List<MediaItem> items = watching.stream()
                .map(entry -> catalogService.getById(entry.getMediaId(), entry.getMediaSource()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Platform.runLater(() -> updateSection(continueWatchingContainer, items, true));
    }

    private void loadFavorites() {
        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        List<MediaItem> items = favorites.stream()
                .map(fav -> catalogService.getById(fav.getMediaId(), fav.getMediaSource(), fav.getMediaType())
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Platform.runLater(() -> updateSection(favoritesContainer, items, true));
    }

    private void loadTopRatedMovies() {
        List<MediaItem> items = catalogService.getTopRatedMoviesAndTV(20, 1).stream()
                .filter(item -> item instanceof Movie)
                .limit(10)
                .collect(Collectors.toList());
        Platform.runLater(() -> updateSection(topRatedMoviesContainer, items, false));
    }

    private void loadLatestMovies() {
        List<Movie> movies = catalogService.getLatestMovies(10, 1);
        Platform.runLater(() -> updateSection(latestMoviesContainer, new ArrayList<>(movies), false));
    }

    private void loadTopRatedTVShows() {
        List<MediaItem> items = catalogService.getTopRatedMoviesAndTV(20, 1).stream()
                .filter(item -> item instanceof TVShow)
                .limit(10)
                .collect(Collectors.toList());
        Platform.runLater(() -> updateSection(topRatedTVShowsContainer, items, false));
    }

    private void loadLatestTVShows() {
        List<TVShow> shows = catalogService.getLatestTVShows(10, 1);
        Platform.runLater(() -> updateSection(latestTVShowsContainer, new ArrayList<>(shows), false));
    }

    private void loadTopAnime() {
        List<Anime> anime = catalogService.getTopRatedAnime(10, 0);
        Platform.runLater(() -> updateSection(topRatedAnimeContainer, new ArrayList<>(anime), false));
    }

    private void loadAiringNowAnime() {
        List<Anime> anime = catalogService.getLatestAnime(10);
        Platform.runLater(() -> updateSection(airingNowAnimeContainer, new ArrayList<>(anime), false));
    }

    @FXML
    private void handleTopMovies() {
        loadMore("Top Rated Movies", topRatedMoviesContainer);
    }

    @FXML
    private void handleLatestMovies() {
        loadMore("Latest Movies", latestMoviesContainer);
    }

    @FXML
    private void handleTopTVShows() {
        loadMore("Top Rated TV Shows", topRatedTVShowsContainer);
    }

    @FXML
    private void handleLatestTVShows() {
        loadMore("Latest TV Shows", latestTVShowsContainer);
    }

    @FXML
    private void handleTopAnime() {
        loadMore("Top Rated Anime", topRatedAnimeContainer);
    }

    @FXML
    private void handleAiringNowAnime() {
        loadMore("Latest Anime", airingNowAnimeContainer);
    }

    private void loadMore(String sectionTitle, HBox container) {
        new Thread(() -> {
            try {
                int page = pageCounters.get(sectionTitle);
                page++;
                pageCounters.put(sectionTitle, page);
                List<MediaItem> newItems = new ArrayList<>();

                switch (sectionTitle) {
                    case "Top Rated Movies":
                        newItems = catalogService.getTopRatedMoviesAndTV(20, page).stream()
                                .filter(item -> item instanceof Movie)
                                .limit(10)
                                .collect(Collectors.toList());
                        break;
                    case "Latest Movies":
                        newItems = new ArrayList<>(catalogService.getLatestMovies(10, page));
                        break;
                    case "Top Rated TV Shows":
                        newItems = catalogService.getTopRatedMoviesAndTV(20, page).stream()
                                .filter(item -> item instanceof TVShow)
                                .limit(10)
                                .collect(Collectors.toList());
                        break;
                    case "Latest TV Shows":
                        newItems = new ArrayList<>(catalogService.getLatestTVShows(10, page));
                        break;
                    case "Top Rated Anime":
                        int offset = page * 10;
                        List<Anime> anime = catalogService.getTopRatedAnime(10, offset);
                        newItems = new ArrayList<>(anime);
                        break;
                    case "Latest Anime":
                        List<Anime> allAnime = catalogService.getLatestAnime(10 * page);
                        newItems = new ArrayList<>(allAnime.stream().skip((page - 1) * 10L).limit(10)
                                .collect(Collectors.toList()));
                        break;
                }

                List<MediaItem> finalNewItems = newItems;
                Platform.runLater(() -> {
                    for (MediaItem item : finalNewItems) {
                        container.getChildren().add(createMediaCard(item, false));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateSection(HBox container, List<MediaItem> items, boolean allowRemove) {
        container.getChildren().clear();
        for (MediaItem item : items) {
            container.getChildren().add(createMediaCard(item, allowRemove));
        }
    }

    private VBox createMediaCard(MediaItem media, boolean allowRemove) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 8px; -fx-padding: 10px;");
        card.setPrefWidth(240);
        card.setMaxWidth(240);
        card.setPrefHeight(380);
        card.setAlignment(Pos.TOP_CENTER);

        ImageView poster = new ImageView();
        poster.setFitWidth(220);
        poster.setFitHeight(300);
        poster.setPreserveRatio(false);

        if (media.getPosterUrl() != null && !media.getPosterUrl().isEmpty()) {
            Image image = new Image(media.getPosterUrl(), true);
            poster.setImage(image);
        } else {
            poster.setStyle("-fx-background-color: #333;");
        }

        Label titleLabel = new Label(media.getTitle());
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(220);
        titleLabel.setAlignment(Pos.CENTER);

        Label ratingLabel = new Label("★ " + String.format("%.1f", media.getRating()));
        ratingLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 11px;");

        card.setOnMouseClicked(e -> showDetailWindow(media));
        card.setStyle(card.getStyle() + "-fx-cursor: hand;");

        card.getChildren().addAll(poster, titleLabel, ratingLabel);

        return card;
    }

    private void showDetailWindow(MediaItem media) {
        Stage detailStage = new Stage();
        detailStage.setTitle(media.getTitle());

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG_COLOR);

        ImageView backdrop = new ImageView();
        backdrop.setFitWidth(800);
        backdrop.setFitHeight(300);
        backdrop.setPreserveRatio(false);

        if (media.getBackdropUrl() != null && !media.getBackdropUrl().isEmpty()) {
            Image image = new Image(media.getBackdropUrl(), true);
            backdrop.setImage(image);
        } else if (media.getPosterUrl() != null && !media.getPosterUrl().isEmpty()) {
            Image image = new Image(media.getPosterUrl(), true);
            backdrop.setImage(image);
        }

        root.setTop(backdrop);

        VBox infoBox = new VBox(15);
        infoBox.setPadding(new Insets(20));
        infoBox.setStyle("-fx-background-color: " + BG_COLOR);

        Label title = new Label(media.getTitle());
        title.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        title.setWrapText(true);

        HBox metaBox = new HBox(20);
        Label rating = new Label("★ " + String.format("%.1f", media.getRating()));
        rating.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label type = new Label(media.getMediaType());
        type.setStyle("-fx-text-fill: #888; -fx-font-size: 14px;");

        Label releaseDate = new Label(media.getReleaseDate() != null ? media.getReleaseDate() : "Unknown");
        releaseDate.setStyle("-fx-text-fill: #888; -fx-font-size: 14px;");

        metaBox.getChildren().addAll(rating, type, releaseDate);

        HBox genresBox = new HBox(10);
        if (media.getGenres() != null && !media.getGenres().isEmpty()) {
            for (String genre : media.getGenres()) {
                Label genreLabel = new Label(genre);
                genreLabel.setStyle(
                        "-fx-background-color: " + RED
                                + "; -fx-text-fill: white; -fx-padding: 5px 10px; -fx-background-radius: 15px; -fx-font-size: 12px;");
                genresBox.getChildren().add(genreLabel);
            }
        }

        Label synopsisTitle = new Label("Synopsis:");
        synopsisTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label synopsis = new Label(
                media.getDescription() != null ? media.getDescription() : "No description available.");
        synopsis.setStyle("-fx-text-fill: #ccc; -fx-font-size: 14px;");
        synopsis.setWrapText(true);
        synopsis.setMaxWidth(760);

        HBox actionBox = new HBox(15);
        actionBox.setPadding(new Insets(20, 0, 0, 0));

        String source = determineMediaSource(media);
        boolean isFavorite = favoriteService.isFavorited(userId, media.getId());
        boolean isWatching = trackingService.isWatching(userId, media.getId());

        Button favoriteBtn = new Button(isFavorite ? "❤ Remove from Favorites" : "♡ Add to Favorites");
        favoriteBtn.setStyle("-fx-background-color: " + RED
                + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        favoriteBtn.setOnAction(e -> {
            if (isFavorite) {
                favoriteService.removeFavorite(userId, media.getId());
                favoriteBtn.setText("♡ Add to Favorites");
                loadFavorites();
            } else {
                // Determine media type
                String mediaType = null;
                if (media instanceof Anime) {
                    mediaType = "ANIME";
                } else if (media instanceof Movie) {
                    mediaType = "MOVIE";
                } else if (media instanceof TVShow) {
                    mediaType = "TV_SHOW";
                }
                boolean success = favoriteService.addFavorite(userId, media.getId(), source, mediaType);
                if (success) {
                    favoriteBtn.setText("❤ Remove from Favorites");
                    loadFavorites();
                }
            }
        });

        Button watchingBtn = new Button(isWatching ? "✓ Remove from Watching" : "+ Add to Watching");
        watchingBtn.setStyle(
                "-fx-background-color: #2a2a2a; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-color: "
                        + RED + "; -fx-border-width: 2px;");
        watchingBtn.setOnAction(e -> {
            if (isWatching) {
                trackingService.removeFromWatchList(userId, media.getId());
                watchingBtn.setText("+ Add to Watching");
                loadContinueWatching();
            } else {
                boolean success = trackingService.addToWatchList(userId, media.getId(), source, "WATCHING");
                if (success) {
                    watchingBtn.setText("✓ Remove from Watching");
                    loadContinueWatching();
                }
            }
        });

        actionBox.getChildren().addAll(favoriteBtn, watchingBtn);

        infoBox.getChildren().addAll(title, metaBox, genresBox, synopsisTitle, synopsis, actionBox);

        ScrollPane scrollPane = new ScrollPane(infoBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + BG_COLOR + "; -fx-background-color: " + BG_COLOR);

        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 800, 600);
        detailStage.setScene(scene);
        detailStage.setMinWidth(800);
        detailStage.setMinHeight(600);
        detailStage.setWidth(800);
        detailStage.setHeight(600);
        detailStage.centerOnScreen();
        detailStage.show();
    }

    private String determineMediaSource(MediaItem media) {
        if (media instanceof Anime) {
            return "MAL";
        } else if (media instanceof Movie || media instanceof TVShow) {
            return "TMDB";
        }
        return "FILE";
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
