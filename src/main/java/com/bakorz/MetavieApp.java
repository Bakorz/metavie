package com.bakorz;

import com.bakorz.repo.*;
import com.bakorz.service.*;
import com.bakorz.ui.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for Metavie media tracking application.
 * Extends JavaFX Application to provide GUI functionality.
 * Initializes all services, repositories, and launches the main window.
 * Connects to MyAnimeList and TMDB APIs for media data.
 * 
 * @author Bakorz
 * @version 1.0
 */
public class MetavieApp extends Application {

    /** Service for managing media catalog operations */
    private CatalogService catalogService;

    /** Service for managing user favorites */
    private FavoriteService favoriteService;

    /** Service for managing watch list */
    private TrackingService trackingService;

    /** Current user ID (hardcoded for demo purposes) */
    private static final String CURRENT_USER_ID = "user001";

    /**
     * Initializes all services and repositories before the application starts.
     * Sets up MAL and TMDB API connections with client credentials.
     * Creates service instances with proper dependency injection.
     */
    @Override
    public void init() {
        FileMediaRepo fileMediaRepo = new FileMediaRepo();
        FileFavoriteRepo fileFavoriteRepo = new FileFavoriteRepo();
        FileWatchRepo fileWatchRepo = new FileWatchRepo();

        String malClientId = "your_mal_client_id_here";
        String tmdbApiKey = "your_tmdb_api_key_here";

        MalMediaRepo malMediaRepo = new MalMediaRepo(malClientId);
        TmdbMediaRepo tmdbMediaRepo = new TmdbMediaRepo(tmdbApiKey);

        catalogService = new CatalogService(malMediaRepo, tmdbMediaRepo, fileMediaRepo);
        favoriteService = new FavoriteService(fileFavoriteRepo);
        trackingService = new TrackingService(fileWatchRepo);

        System.out.println("Metavie services initialized successfully!");
    }

    /**
     * Starts the JavaFX application and displays the main window.
     * Loads the FXML layout, injects services into the controller,
     * and configures the primary stage.
     * 
     * @param primaryStage The primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();

            MainViewController controller = loader.getController();
            controller.setServices(catalogService, favoriteService, trackingService, CURRENT_USER_ID);

            Scene scene = new Scene(root, 1400, 800);

            primaryStage.setTitle("Metavie");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(1400);
            primaryStage.setMinHeight(800);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML: " + e.getMessage());
        }
    }

    /**
     * Called when the application is about to stop.
     * Performs cleanup operations before shutdown.
     */
    @Override
    public void stop() {
        System.out.println("Metavie application closing...");
    }

    /**
     * Main entry point for the application.
     * Launches the JavaFX application.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
