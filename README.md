# Metavie

A JavaFX-based media tracking application that allows users to catalog, favorite, and track their anime and movie watching progress.

## Features

- 🎬 **Media Catalog**: Browse and search anime (via MyAnimeList API) and movies (via TMDB API)
- ⭐ **Favorites**: Mark and manage your favorite media
- 📊 **Watch Tracking**: Track your watching progress and status
- 💾 **Local Storage**: Save your data locally using JSON file storage

## Technologies Used

- **Java 21+**
- **JavaFX** - UI framework
- **Maven** - Build and dependency management
- **MyAnimeList API** - Anime data source
- **TMDB API** - Movie data source
- **JSON** - Local data persistence

## Project Structure

```
metavie/
├── src/
│   └── main/
│       ├── java/com/bakorz/
│       │   ├── model/          # Domain models (Media, Favorite, WatchEntry)
│       │   ├── repo/           # Data repositories (API & File)
│       │   ├── service/        # Business logic layer
│       │   ├── ui/             # JavaFX controllers
│       │   └── MetavieApp.java # Application entry point
│       └── resources/
│           └── fxml/           # FXML layout files
├── pom.xml
└── README.md
```

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MyAnimeList API Client ID
- TMDB API Key

## Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd metavie
   ```

2. **Get API Keys**
   - **MyAnimeList**: Register at [MyAnimeList API](https://myanimelist.net/apiconfig) to get a Client ID
   - **TMDB**: Register at [TMDB](https://www.themoviedb.org/settings/api) to get an API key

3. **Configure API Keys**
   
   Open `src/main/java/com/bakorz/MetavieApp.java` and replace the placeholder values:
   ```java
   String malClientId = "your_mal_client_id_here";
   String tmdbApiKey = "your_tmdb_api_key_here";
   ```
   
   **⚠️ Security Note**: For production, use environment variables or a configuration file (not tracked in git).

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn javafx:run
   ```

## Usage

- **Browse Media**: Search for anime or movies using the catalog
- **Add to Favorites**: Mark media you love
- **Track Progress**: Update your watching status and episode/movie count
- **View History**: Check your watched media and ratings

## Data Storage

User data is stored locally in JSON files:
- `media.json` - Cached media information
- `favorites.json` - User's favorite media
- `watch_history.json` - Watching progress and history

## Architecture

The application follows a layered architecture:

- **UI Layer**: JavaFX controllers handle user interactions
- **Service Layer**: Business logic for catalog, favorites, and tracking
- **Repository Layer**: Data access abstraction (API clients and file storage)
- **Model Layer**: Domain entities

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is for educational purposes as part of an Object-Oriented Programming course.

## Authors

- **Gasb** - Initial work

## Acknowledgments

- [MyAnimeList](https://myanimelist.net/) for anime data
- [The Movie Database (TMDB)](https://www.themoviedb.org/) for movie data
- JavaFX community for UI components and examples