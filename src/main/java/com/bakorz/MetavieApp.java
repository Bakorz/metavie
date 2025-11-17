package com.bakorz;

import com.bakorz.repo.*;
import com.bakorz.service.*;
import com.bakorz.ui.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MetavieApp extends Application {

    private CatalogService catalogService;
    private FavoriteService favoriteService;
    private TrackingService trackingService;

    private static final String CURRENT_USER_ID = "user001";

    @Override
    public void init() {
        FileMediaRepo fileMediaRepo = new FileMediaRepo();
        FileFavoriteRepo fileFavoriteRepo = new FileFavoriteRepo();
        FileWatchRepo fileWatchRepo = new FileWatchRepo();

        String malClientId = "57b341f948bbb18ed62b6300db7df135";
        String tmdbApiKey = "f1fa8dc0755eae97610653fe943dcce4";

        MalMediaRepo malMediaRepo = new MalMediaRepo(malClientId);
        TmdbMediaRepo tmdbMediaRepo = new TmdbMediaRepo(tmdbApiKey);

        catalogService = new CatalogService(malMediaRepo, tmdbMediaRepo, fileMediaRepo);
        favoriteService = new FavoriteService(fileFavoriteRepo);
        trackingService = new TrackingService(fileWatchRepo);

        System.out.println("Metavie services initialized successfully!");
    }

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

    @Override
    public void stop() {
        System.out.println("Metavie application closing...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
