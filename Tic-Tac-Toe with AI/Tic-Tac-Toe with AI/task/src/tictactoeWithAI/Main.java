package tictactoeWithAI;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {
        do {
            String[] userParameters = takeUserCommand();
            if (userParameters[0].equals("exit")) {
                break;
            }
            TTTPlayer[] players = initializePlayers(userParameters);
            playOneGame(players[0], players[1]);
        } while (true);
    }

    // Create two tic-tac-toe players according to the user input, return them in an array
    private static TTTPlayer[] initializePlayers(String[] userParameters) {
        TTTPlayer[] players = new TTTPlayer[2];

        if (userParameters[1].equals("user")) {
            players[0] = new Human("X");
        } else {
            players[0] = new AI("X", userParameters[1]);
        }

        if (userParameters[2].equals("user")) {
            players[1] = new Human("O");
        } else {
            players[1] = new AI("O", userParameters[2]);
        }

        return players;
    }

    // Loops until valid user parameters entered, returned as a string array
    private static String[] takeUserCommand() {
        String[] userParameters;

        do {
            System.out.print("Input command: ");
            userParameters = validateUserCommand();
            if (userParameters == null) {
                System.out.println("Bad parameters!");
            }
        } while (userParameters == null);

        return userParameters;
    }

    // Checks user input string array to make sure all parameters are valid
    private static String[] validateUserCommand() {
        String userInput = scnr.nextLine();
        String[] userParameters = userInput.split(" ");

        // If command is to exit, just return the single element
        if (userParameters[0].equals("exit")) {
            return userParameters;
        }

        // Else do the rest of the validation checking
        if (!(userParameters[0].equals("start"))) {
            return null;
        }

        // Must be 3 parameters total
        if (userParameters.length != 3) {
            return null;
        }

        if (!(userParameters[1].equals("user") || userParameters[1].equals("easy"))) {
            return null;
        }

        if (!(userParameters[2].equals("user") || userParameters[2].equals("easy"))) {
            return null;
        }

        return userParameters;
    }

    private static void playOneGame(TTTPlayer player1, TTTPlayer player2) {
        // Initialize new tic-tac-toe game
        Game tttGame = new Game();
        System.out.println(tttGame);

        do {
            player1.makeMove(tttGame);
            if (!tttGame.calculateGameState().equals("Game not finished")) {
                break;
            }

            player2.makeMove(tttGame);
            if (!tttGame.calculateGameState().equals("Game not finished")) {
                break;
            }
        } while (true);

        // Output game result
        System.out.println(tttGame.calculateGameState());
    }
}
