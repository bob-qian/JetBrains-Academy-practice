package tictactoeWithAI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        // Initialize new tic-tac-toe game
        Game tttGame = new Game();

        // Initialize pre-existing game according to user input
        /*System.out.print("Enter cells: ");
        String userInput = scnr.nextLine();
        tttGame.setGameBoard(userInput);*/

        System.out.println(tttGame);

        do {
            tttGame.makeMove("X");
            if (!tttGame.calculateGameState().equals("Game not finished")) {
                break;
            }

            makeRandomMove(tttGame, "O");
            if (!tttGame.calculateGameState().equals("Game not finished")) {
                break;
            }
        } while (true);

        // Output game result
        System.out.println(tttGame.calculateGameState());

    }

    // Makes a random move for a player
    private static void makeRandomMove(Game game, String player) {
        System.out.println("Making move level \"easy\"");

        boolean added = false;

        do {
            int randomRow = (int) (Math.random() * 3) + 1;
            int randomCol = (int) (Math.random() * 3) + 1;
            added = game.addCoordinates(randomRow, randomCol, player);
        } while (!added);

        System.out.println(game);
    }
}
