package advisor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

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

    // Displays a list of Spotify-fetaured playlists with links fetched from API
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
