package tictactoeWithAI;

import java.util.ArrayList;
import java.util.Arrays;

public class AI implements TTTPlayer {
    String symbol; // X or O
    String difficulty; // easy, medium, hard

    public AI(String symbol, String difficulty) {
        this.symbol = symbol;
        this.difficulty = difficulty;
    }

    /**
     * Makes an AI move
     * @param game a tic-tac-toe game
     */
    public void makeMove(Game game) {
        System.out.println("Making move level \"" + this.difficulty + "\"");
        if (this.difficulty.equals("easy")) {
            makeMoveEasy(game);
        } else if (this.difficulty.equals("medium")) {
            makeMoveMedium(game);
        } else {
            makeMoveHard(game);
        }
        System.out.println(game);
    }

    // Makes an "easy" move (randomly generated move)
    private void makeMoveEasy(Game game) {
        boolean added = false;

        do {
            int randomRow = (int) (Math.random() * 3) + 1;
            int randomCol = (int) (Math.random() * 3) + 1;
            added = game.addCoordinates(randomRow, randomCol, this.symbol);
        } while (!added);
    }

    // Makes a "medium" move according to logic
    private void makeMoveMedium(Game game) {
        // If it already has 2 in a row and can win with one further move, it does so
        ArrayList<Integer[]> winningMoves = game.findWinningMoves(this.symbol);
        if (winningMoves != null) {
            for (Integer[] coordinates : winningMoves) {
                boolean added = game.addCoordinates(coordinates[0] + 1,
                        coordinates[1] + 1, this.symbol);

                if (added) {
                    return;
                }
            }
        }

        // Else if its opponent can win with one move, it plays the move necessary to block
        String opponentSymbol;
        if (this.symbol.equals("X")) {
            opponentSymbol = "O";
        } else {
            opponentSymbol = "X";
        }

        ArrayList<Integer[]> opponentWinningMoves = game.findWinningMoves(opponentSymbol);
        if (opponentWinningMoves != null) {
            for (Integer[] coordinates : opponentWinningMoves) {
                boolean added = game.addCoordinates(coordinates[0] + 1,
                        coordinates[1] + 1, this.symbol);

                if (added) {
                    return;
                }
            }
        }

        // Otherwise, it makes a random move
        makeMoveEasy(game);
    }

    // Makes a "hard" move according to minimax algorithm
    private void makeMoveHard(Game game) {
        int moveIndex = Integer.parseInt(Minimax.findMinimaxMove(game, this));
        int row = (moveIndex / 3) + 1;
        int col = (moveIndex % 3) + 1;
        game.addCoordinates(row, col, this.symbol);
    }

    public String getSymbol() {
        return symbol;
    }
}
