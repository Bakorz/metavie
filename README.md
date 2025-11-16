# Metavie

A JavaFX-based media tracking application that allows users to catalog, favorite, and track their anime and movie watching progress.

## Features

- 🎬 **Media Catalog**: Browse and search anime (via MyAnimeList API) and movies (via TMDB API)
- ⭐ **Favorites**: Mark and manage your favorite media
- 📊 **Watch Tracking**: Track your watching progress
- 💾 **Local Storage**: Save your data locally using CSV file storage

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

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn javafx:run
   ```

## Authors

- **Bakorz & Friend(Copilot)** - 