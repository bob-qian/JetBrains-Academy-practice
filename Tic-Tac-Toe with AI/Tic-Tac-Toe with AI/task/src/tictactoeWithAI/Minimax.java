package tictactoeWithAI;

import java.util.ArrayList;
import java.util.Arrays;

public class Minimax {
    private String humanSymbol;
    private String aiSymbol;
    String[] gameBoard;

    private class Move {
        String index;
        int score;

        public Move() {
            index = "";
            score = 0;
        }

        public Move(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Move: " +
                    "index='" + index + '\'' +
                    ", score=" + score +
                    '}';
        }
    }

    public Minimax(Game game, AI aiPlayer) {
        aiSymbol = aiPlayer.getSymbol();
        if (aiSymbol.equals("X")) {
            humanSymbol = "O";
        } else {
            humanSymbol = "X";
        }
        gameBoard = game.toStringArray();
    }

    // Returns String index of the next best move according to the minimax algorithm
    protected static String findMinimaxMove(Game game, AI aiPlayer) {
        Minimax minimax = new Minimax(game, aiPlayer);
        Move bestMove = minimax.runMinimax(minimax.gameBoard, minimax.aiSymbol);
        return bestMove.index;
    }

    // The main minimax function
    private Move runMinimax(String[] newBoard, String player) {
        int[] availableSpots = findEmptyIndexes(newBoard);

        // Check for terminal states and return a value accordingly
        if (isWinning(newBoard, humanSymbol)) {
            return new Move(-10);
        } else if (isWinning(newBoard, aiSymbol)) {
            return new Move(10);
        } else if (availableSpots.length == 0) {
            return new Move(0);
        }

        // An array to collect all the objects
        ArrayList<Move> moves = new ArrayList<>();

        // Loop through available spots
        for (int i = 0; i < availableSpots.length; i++) {
            // Create an objet for each and store the index of that spot
            Move move = new Move();
            move.index = newBoard[availableSpots[i]];

            // Set the empty spot to the current player
            newBoard[availableSpots[i]] = player;

            // Collect the score resulted from calling minimax on the opponent of the current player
            if (player.equals(aiSymbol)) {
                Move result = runMinimax(newBoard, humanSymbol);
                move.score = result.score;
            } else {
                Move result = runMinimax(newBoard, aiSymbol);
                move.score = result.score;
            }

            // Reset the spot to empty
            newBoard[availableSpots[i]] = move.index;

            // Add the object to the array
            moves.add(move);
        }

        // If turn is AI, choose the move with the highest score
        int bestMoveLocation = 0;
        if (player.equals(aiSymbol)) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore) {
                    bestScore = moves.get(i).score;
                    bestMoveLocation = i;
                }
            }
        } else {
            // Else choose the move with the lowest score
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score < bestScore) {
                    bestScore = moves.get(i).score;
                    bestMoveLocation = i;
                }
            }
        }

        // Return the chose Move object from the moves array
        return moves.get(bestMoveLocation);
    }

    // Returns list of the indexes of empty spots on the board
    private static int[] findEmptyIndexes(String[] origBoard) {
        ArrayList<String> emptyIndexes = new ArrayList<>();
        for (int i = 0; i < origBoard.length; i++) {
            if (!(origBoard[i].equals("O") || origBoard[i].equals("X"))) {
                emptyIndexes.add(String.valueOf(i));
            }
        }
        int[] output = new int[emptyIndexes.size()];

        for (int i = 0; i < emptyIndexes.size(); i++) {
            output[i] = Integer.parseInt(emptyIndexes.get(i));
        }

        return output;
    }

    // Returns true if the board contains a winning combination using the board indexes
    private static boolean isWinning(String[] board, String player) {
        if ((board[0].equals(player) && board[1].equals(player) && board[2].equals(player)) ||
                (board[3].equals(player) && board[4].equals(player) && board[5].equals(player)) ||
                (board[6].equals(player) && board[7].equals(player) && board[8].equals(player)) ||
                (board[0].equals(player) && board[3].equals(player) && board[6].equals(player)) ||
                (board[1].equals(player) && board[4].equals(player) && board[7].equals(player)) ||
                (board[2].equals(player) && board[5].equals(player) && board[8].equals(player)) ||
                (board[0].equals(player) && board[4].equals(player) && board[8].equals(player)) ||
                (board[2].equals(player) && board[4].equals(player) && board[6].equals(player))) {
            return true;
        } else {
            return false;
        }
    }
}
