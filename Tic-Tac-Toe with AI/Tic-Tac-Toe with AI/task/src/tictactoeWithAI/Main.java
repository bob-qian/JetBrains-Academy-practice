package tictactoeWithAI;

import tictactoe.Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        // Initialize new tic-tac-toe game
        Game tttGame = new Game();

        // Initialize pre-existing game according to user input
        System.out.print("Enter cells: ");
        String userInput = scnr.nextLine();
        tttGame.setGameBoard(userInput);

        System.out.println(tttGame);

        String currentPlayer;

        // If there are an equal number of Xs and Os, the player is X
        if (tttGame.countOfX() == tttGame.countOfO()) {
            currentPlayer = "X";
        } else {
            // If there is an extra X, the move should be made with O
            currentPlayer = "O";
        }

        tttGame.makeMove(currentPlayer);
        System.out.println(tttGame.calculateGameState());

        /*while (tttGame.calculateGameState().equals("Game not finished")) {
            tttGame.makeMove(currentPlayer);
            tttGame.calculateGameState();
            if (currentPlayer.equals("X")) {
                currentPlayer = "O";
            } else {
                currentPlayer = "X";
            }
        }
        System.out.println(tttGame.calculateGameState());*/
    }
}
