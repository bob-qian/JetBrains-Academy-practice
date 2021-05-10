package advisor;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner scnr = new Scanner(System.in);

    private static String serverPath = "https://accounts.spotify.com";
    private static String redirectURI = "http://localhost:8080";
    private static String authLink = serverPath + "/authorize?client_id=6fd95ac46e9841c5acd0b107f5ebfbeb&response_type=code&redirect_uri=" + redirectURI;

    private static String authCode;
    private static String accessToken;

    public static void main(String[] args) {
        // Change from default server path if user specifies
        if (args.length != 0) {
            if (args[0].equals("-access")) {
            }
            System.out.println(serverPath);
        }
        preAuthorizationProcess();

        if (authCode != null && accessToken != null) {
            System.out.println("---SUCCESS---");
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

                System.out.println("making http request for access_token...");

                try {
                    accessToken = Auth.getAccessToken(serverPath, redirectURI);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                if (accessToken.length() > 1) {
                    System.out.println("response:");
                    System.out.println(accessToken);
                }
                return;
            } else if (userInput.equals("exit")) {
                return;
            } else {
                System.out.println("Please, provide access for application.");
            }
        }
    }

    // Handles the program functionality after Spotify API access is granted
    private static void postAuthorizationProcess() {
        while (true) {
            String userInput = scnr.nextLine();

            if (userInput.equals("exit")) {
                System.out.println("---GOODBYE!---");
                break;
            }

            switch (userInput) {
                case "new":
                    displayNewReleases();
                    break;
                case "featured":
                    displayFeaturedPlaylists();
                    break;
                case "categories":
                    displayCategories();
                    break;
                case "playlists Mood":
                    System.out.println("---MOOD PLAYLISTS---");
                    System.out.println("Walk Like A Badass");
                    System.out.println("Rage Beats");
                    System.out.println("Arab Mood Booster");
                    System.out.println("Sunday Stroll");
                    break;
            }
        }
    }

    // Displays a list of new albums with artists and links on Spotify
    private static void displayNewReleases() {
        System.out.println("---NEW RELEASES---");
        System.out.println("Mountains [Sia, Diplo, Labrinth]");
        System.out.println("Runaway [Lil Peep]");
        System.out.println("The Greatest Show [Panic! At The Disco]");
        System.out.println("All Out Life [Slipknot]");
    }

    // Displays a list of Spotify-featured playlists with links fetched from API
    private static void displayFeaturedPlaylists() {
        System.out.println("---FEATURED---");
        System.out.println("Mellow Morning");
        System.out.println("Wake Up and Smell the Coffee");
        System.out.println("Monday Motivation");
        System.out.println("Songs to Sing in the Shower");
    }

    // Displays a list of all available categories on Spotify
    private static void displayCategories() {
        System.out.println("---CATEGORIES---");
        System.out.println("Top Lists");
        System.out.println("Pop");
        System.out.println("Mood");
        System.out.println("Latin");
    }
}
