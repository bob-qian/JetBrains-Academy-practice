package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {
    private static Scanner scnr = new Scanner(System.in);

    // Create an HttpClient object
    private static HttpClient myClient = HttpClient.newBuilder().build();

    private static String authServerPath = "https://accounts.spotify.com";
    private static String APIServerPath = "https://api.spotify.com";

    private static String redirectURI = "http://localhost:8080";
    private static String authLink = authServerPath + "/authorize?client_id=6fd95ac46e9841c5acd0b107f5ebfbeb&response_type=code&redirect_uri=" + redirectURI;

    private static String authCode;
    private static String accessToken;
    private static String refreshToken;

    // Saves loaded categories and their spotify Ids for playlist retrieval
    private static Map<String, String> categoryMap = new HashMap<>();

    public static void main(String[] args) {
        // Change from default server path if user specifies
        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-access")) {
                    authServerPath = args[i + 1];
                } else if (args[i].equals("-resource")) {
                    APIServerPath = args[i + 1];
                }
            }
        }
        preAuthorizationProcess();

        if (authCode != null && accessToken != null) {
            /*System.out.println("---SUCCESS---");*/
            postAuthorizationProcess();
        }
    }

    // Handles the program before user authorizes Spotify access
    private static void preAuthorizationProcess() {
        while (true) {
            String userInput = scnr.nextLine();
            if (userInput.equals("auth")) {
                try {
                    authCode = Auth.getAuthCode(authLink);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                if (authCode.length() > 1) {
                    System.out.println("code received");
                }

                System.out.println("Making http request for access_token...");
                String responseToken = "";

                try {
                    responseToken = Auth.getAccessToken(authServerPath, redirectURI);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                if (responseToken.length() > 1) {
                    System.out.println("Success!");
                    setTokens(responseToken);
                }
                return;
            } else if (userInput.equals("exit")) {
                return;
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }

    private static void setTokens(String accessResponse) {
        // Use GSON library to parse JSON formatted response and set appropriate tokens
        JsonObject jo = JsonParser.parseString(accessResponse).getAsJsonObject();
        accessToken = jo.get("access_token").getAsString();
        refreshToken = jo.get("refresh_token").getAsString();
    }

    // Handles the program functionality after Spotify API access is granted
    private static void postAuthorizationProcess() {
        while (true) {
            String userInput = scnr.nextLine();

            if (userInput.equals("exit")) {
                /*System.out.println("---GOODBYE!---");*/
                break;
            } else if (userInput.equals("new")) {
                try {
                    displayNewReleases();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.equals("featured")) {
                try {
                    displayFeaturedPlaylists();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.equals("categories")) {
                try {
                    displayCategories();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInput.substring(0, "playlists".length()).equals("playlists")) {
                // Get the category that the user wants to see the playlists of
                String userCategory = userInput.substring("playlists".length()+1);

                // Load categories if not loaded yet
                if (categoryMap.size() == 0) {
                    try {
                        displayCategories();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (categoryMap.get(userCategory) == null) {
                    System.out.println("Unknown category name.");
                } else {
                    try {
                        Map<String, String> playlistsAndLinks = getPlaylistsFromCategory(userCategory);

                        for (String playlistName : playlistsAndLinks.keySet()) {
                            // Print playlist name
                            System.out.println(playlistName);

                            // Print playlist link
                            System.out.println(playlistsAndLinks.get(playlistName) + "\n");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    // Displays a list of new albums with artists and links on Spotify
    private static void displayNewReleases() throws IOException, InterruptedException {
        // Create a GET request for new releases using the user access token
        HttpRequest getSpotifyNew = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(APIServerPath + "/v1/browse/new-releases"))
                .GET()
                .build();

        // Send the HTTP GET request
        HttpResponse<String> response = myClient.send(getSpotifyNew, HttpResponse.BodyHandlers.ofString());

        String newReleasesResponse = "";

        // If successful, get new releases in JSON format
        if (response.statusCode() == 200) {
            newReleasesResponse = response.body();
        } else {
            newReleasesResponse = "error in retrieving new releases";
        }

        // Parse JSON data
        JsonObject newReleasesJSON = JsonParser.parseString(newReleasesResponse).getAsJsonObject();

        // Go one layer in, to "albums"
        JsonObject albumsObj = newReleasesJSON.getAsJsonObject("albums");

        // Go one more layer in, to "items" (this contains the first 20 songs of the new releases)
        JsonArray itemsObj = albumsObj.getAsJsonArray("items");

        // For each new release song:
        for (JsonElement song : itemsObj) {
            // Convert JsonElement to JsonObject to work with
            JsonObject songJSON = song.getAsJsonObject();

            // Print the name of the song
            System.out.println(songJSON.get("name").getAsString());

            // Get the artist name(s)
            List<String> artists = new ArrayList<>();

            JsonArray artistsJSON = songJSON.get("artists").getAsJsonArray();
            for (JsonElement artist : artistsJSON) {
                // Again convert artist to JsonObject
                JsonObject artistJSON = artist.getAsJsonObject();

                // Add artist name to array
                artists.add(artistJSON.get("name").getAsString());
            }

            // Print artists
            System.out.println(artists);

            // Print the song link
            System.out.println(songJSON.get("external_urls").getAsJsonObject().get("spotify").getAsString() + "\n");
        }
    }

    // Displays a list of Spotify-featured playlists with links fetched from API
    private static void displayFeaturedPlaylists() throws IOException, InterruptedException {
        // Create a GET request for featured playlists using the user access token
        HttpRequest getSpotifyFeatured = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(APIServerPath + "/v1/browse/featured-playlists"))
                .GET()
                .build();

        // Send the HTTP GET request
        HttpResponse<String> response = myClient.send(getSpotifyFeatured, HttpResponse.BodyHandlers.ofString());

        String featuredResponse = "";

        // If successful, get featured playlists in JSON formatted string
        if (response.statusCode() == 200) {
            featuredResponse = response.body();
        } else {
            featuredResponse = "error in retrieving featured playlists";
        }

        // Get list of featured playlists as JsonElements in a JsonArray (> playlists > items > [])
        JsonArray featuredJSON = JsonParser.parseString(featuredResponse).getAsJsonObject()
                .get("playlists").getAsJsonObject()
                .get("items").getAsJsonArray();

        for (JsonElement featuredPlaylist : featuredJSON) {
            JsonObject featuredPlaylistJSON = featuredPlaylist.getAsJsonObject();

            // Print playlist name (> name)
            System.out.println(featuredPlaylistJSON.get("name").getAsString());

            // ... and link (> external_urls > spotify)
            System.out.println(featuredPlaylistJSON.get("external_urls").getAsJsonObject()
                    .get("spotify").getAsString() + "\n");
        }
    }

    // Displays a list of all available categories on Spotify
    private static void displayCategories() throws IOException, InterruptedException{
        // Create a GET request for categories using the user access token
        HttpRequest getSpotifyCategories = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(APIServerPath + "/v1/browse/categories"))
                .GET()
                .build();

        // Send the HTTP GET request
        HttpResponse<String> response = myClient.send(getSpotifyCategories, HttpResponse.BodyHandlers.ofString());

        String categoriesResponse = "";

        // If successful, get new releases in JSON format
        if (response.statusCode() == 200) {
            categoriesResponse = response.body();
        } else {
            categoriesResponse = "error in retrieving categories";
        }

        // Get list of categories as JsonElements in a JsonArray (> categories > items > [])
        JsonArray categoriesJSON = JsonParser.parseString(categoriesResponse).getAsJsonObject()
                .get("categories").getAsJsonObject()
                .get("items").getAsJsonArray();

        // Get list of categories and save each with its corresponding category id to static categoryMap
        for (JsonElement category : categoriesJSON) {
            JsonObject categoryJSON = category.getAsJsonObject();

            // Category name
            String categoryName = categoryJSON.get("name").getAsString();

            // Category id
            String categoryId = categoryJSON.get("id").getAsString();

            categoryMap.putIfAbsent(categoryName, categoryId);
        }

        // Print all loaded categories
        for (String category : categoryMap.keySet()) {
            System.out.println(category);
        }
    }

    // Returns a HashMap of playlists [and their URLs] associated with a given Spotify category
    private static Map<String, String> getPlaylistsFromCategory(String category) throws IOException, InterruptedException {
        LinkedHashMap<String, String> playlistsAndLinks = new LinkedHashMap<>();

        String categoryId = categoryMap.get(category);
        String categoryAPILink = APIServerPath + "/v1/browse/categories/" +
                categoryId + "/playlists";


        // Create a GET request for playlists from the specified category using the user access token
        HttpRequest getCategoryPlaylists = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(categoryAPILink))
                .GET()
                .build();

        // Send the HTTP GET request
        HttpResponse<String> response = myClient.send(getCategoryPlaylists, HttpResponse.BodyHandlers.ofString());

        String categoryPlaylistsResponse = "";

        // If successful, get new releases in JSON format
        if (response.statusCode() == 200) {
            categoryPlaylistsResponse = response.body();
        } else {
            System.out.println("error in retrieving playlists for " + category);
            return null;
        }

        System.out.println(categoryPlaylistsResponse);

        // Get list of playlists as JsonElements in a JsonArray (> playlists > items > [])
        JsonArray categoryPlaylistsJSON = JsonParser.parseString(categoryPlaylistsResponse).getAsJsonObject()
                .get("playlists").getAsJsonObject()
                .get("items").getAsJsonArray();

        // Log each playlist and its link into the HashMap
        for (JsonElement playlist : categoryPlaylistsJSON) {
            JsonObject playlistJSON = playlist.getAsJsonObject();

            // (> name)
            String playlistName = playlistJSON.get("name").getAsString();
            // (> external_urls > spotify)
            String playlistLink = playlistJSON.get("external_urls").getAsJsonObject()
                    .get("spotify").getAsString();

            playlistsAndLinks.putIfAbsent(playlistName, playlistLink);
        }

        return playlistsAndLinks;
    }
}
