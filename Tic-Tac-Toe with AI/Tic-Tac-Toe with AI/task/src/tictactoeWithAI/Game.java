package tictactoeWithAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private static Scanner scnr = new Scanner(System.in);

    private String[][] gameBoard;

    public Game() {
        gameBoard = new String[3][3];
        setGameBoard("_________");
    }

    /**
     * Displays the tic-tac-toe game board
     * @return String output
     */
    @Override
    public String toString() {
        StringBuilder gameBoardDisplay = new StringBuilder();
        gameBoardDisplay.append("---------\n");
        for (int i = 0; i < 3; i++) {
            gameBoardDisplay.append("| " + gameBoard[i][0] + " " + gameBoard[i][1] +
                    " " + gameBoard[i][2] + " |\n");
        }
        gameBoardDisplay.append("---------");
        return gameBoardDisplay.toString();
    }

    /**
     * Initializes the tic-tac-toe game board according to a 9-character input string
     * @param input a 9-character input string where each character represents a cell
     */
    public void setGameBoard(String input) {
        char[] inputLetters = input.toCharArray();
        for (int i = 0; i < inputLetters.length; i++) {
            String cellContent;

            if (inputLetters[i] == '_') {
                cellContent = " ";
            } else {
                cellContent = Character.toString(inputLetters[i]);
            }

            if (i < 3) {
                gameBoard[0][i] = cellContent;
            } else if (i < 6) {
                gameBoard[1][i-3] = cellContent;
            } else {
                gameBoard[2][i-6] = cellContent;
            }
        }
    }

    /**
     * Takes input from player, makes a move; repeats until a valid input is given. Prints game board once successful
     * @param player "X" or "O"
     */
    public void makeMove(String player) {
        boolean inputSuccessful = false;

        do {
            int[] coordinates = getCoordinateInput();

            if (coordinates != null) {
                // Add coordinates
                boolean coordinatesAdded = this
                        .addCoordinates(coordinates[0], coordinates[1], player);
                if (coordinatesAdded) {
                    System.out.println(this);
                    inputSuccessful = true;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }
        } while (!inputSuccessful);
    }

    // Asks user for coordinates, returns an int array of the x and y coordinates
    private static int[] getCoordinateInput() {
        System.out.print("Enter the coordinates: ");
        String coordinates = scnr.nextLine();
        String[] splitCoordinates = coordinates.split(" ");

        int[] intCoordinates = new int[2];

        // Check to make sure they can be converted into numbers
        try {
            intCoordinates[0] = Integer.parseInt(splitCoordinates[0]);
            intCoordinates[1] = Integer.parseInt(splitCoordinates[1]);
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return null;
        }

        // Validate coordinates
        if ((intCoordinates[0] > 3 || intCoordinates[0] < 1)
                || (intCoordinates[1] > 3 || intCoordinates[1] < 1)) {
            System.out.println("Coordinates should be from 1 to 3!");
            return null;
        }

        return intCoordinates;
    }

    // Adds an appropriate tick at the row and column for the currentPlayer (either "X" or "O")
    // Returns success of the operation (true or false)
    protected boolean addCoordinates(int row, int column, String currentPlayer) {
        // If slot is empty
        if (gameBoard[row-1][column-1].equals("_") || gameBoard[row-1][column-1].equals(" ")) {
            gameBoard[row-1][column-1] = currentPlayer;
            return true;
        }

        // Else cell is occupied
        return false;
    }

    /**
     * Calculates the game state of the tic-tac-toe game, returns the game state as a string
     * @return game state as a string message
     */
    public String calculateGameState() {
        // Game state: "Impossible" if difference of Xs and Os is 2 or more
        if (Math.abs(this.countOf("X") - this.countOf("O")) >= 2) {
            return "Impossible";
        }

        ArrayList<String> winner = this.hasThreeInARow();

        // Impossible if 3 Xs and 3 Os
        if (winner.size() > 1) {
            return "Impossible";
        } else if (winner.size() != 0 && winner.get(0).equals("X")) {
            // X wins if 3 Xs in a row
            return "X wins";
        } else if (winner.size() != 0 && winner.get(0).equals("O")) {
            // O wins if 3 Os in a row
            return "O wins";
        }

        if (this.hasEmptyCells()) {
            return "Game not finished";
        }

        return "Draw";
    }

    // Manually checks all possible columns/rows to see if there is a winner
    private ArrayList<String> hasThreeInARow() {
        ArrayList<String> winners = new ArrayList<>();
        String centerPiece = gameBoard[1][1];

        // Middle row check
        if (gameBoard[1][0].equals(centerPiece) && gameBoard[1][2].equals(centerPiece)) {
            winners.add(centerPiece);
            //System.out.println("1");
        }
        // Middle column check
        if (gameBoard[0][1].equals(centerPiece) && gameBoard[2][1].equals(centerPiece)) {
            winners.add(centerPiece);
            //System.out.println("2");
        }
        // Diagonal check
        if (gameBoard[0][0].equals(centerPiece) && gameBoard[2][2].equals(centerPiece)) {
            winners.add(centerPiece);
            //System.out.println("3");
        }
        // Anti-diagonal check
        if (gameBoard[0][2].equals(centerPiece) && gameBoard[2][0].equals(centerPiece)) {
            winners.add(centerPiece);
            //System.out.println("4");
        }

        // Top and bottom row check
        String midRow;
        for (int i = 0; i < 3; i+=2) {
            midRow = gameBoard[i][1];
            if (gameBoard[i][0].equals(midRow) && gameBoard[i][2].equals(midRow)) {
                winners.add(midRow);
                //System.out.println("5");
            }
        }

        // Left and right columns check
        String midColumn;
        for (int i = 0; i < 3; i+=2) {
            midColumn = gameBoard[1][i];
            if (gameBoard[0][i].equals(midColumn) && gameBoard[2][i].equals(midColumn)) {
                winners.add(midColumn);
                //System.out.println("6");
            }
        }

        // Remove spaces if accidentally caught
        while (winners.contains(" ")) {
            winners.remove(" ");
        }
        return winners;
    }

    /**
     * Finds the number of the specified symbol on the game board
     * @param symbol ie "X" or "O"
     * @return int count
     */
    public int countOf(String symbol) {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j].equals(symbol)) {
                    total += 1;
                }
            }
        }
        return total;
    }

    // Checks if the game board still has empty cells
    private boolean hasEmptyCells() {
        if ((this.countOf("X") + this.countOf("O")) == 9) {
            return false;
        }
        return true;
    }

    // Manually checks all possible columns/rows to see if there are 2 in a row
    // If there are, returns a list of all possible [row, col] ways to win
    // Else returns null
    protected ArrayList<Integer[]> findWinningMoves(String symbol) {
        ArrayList<Integer[]> allWinningMoves = new ArrayList<>();

        String centerPiece = gameBoard[1][1];
        if (centerPiece.equals(symbol)) {
            // Middle row check
            if (gameBoard[1][0].equals(centerPiece)) {
                allWinningMoves.add(new Integer[] {1,2});
            } else if (gameBoard[1][2].equals(centerPiece)) {
                allWinningMoves.add(new Integer[] {1,0});
            }

            // Middle column check
            if (gameBoard[0][1].equals(centerPiece)) {
                allWinningMoves.add(new Integer[] {2,1});
            } else if (gameBoard[2][1].equals(centerPiece)) {
                allWinningMoves.add(new Integer[] {0,1});
            }

            // Diagonal check
            if (gameBoard[0][0].equals(centerPiece)) {
                allWinningMoves.add(new Integer[] {2,2});
            } else if (gameBoard[2][2].equals(centerPiece)) {
                allWinningMoves.add(new Integer[] {0,0});
            }

            // Anti-diagonal check
            if (gameBoard[0][2].equals(centerPiece)) {
                allWinningMoves.add(new Integer[] {2,0});
            } else if (gameBoard[2][0].equals(centerPiece)) {
                allWinningMoves.add(new Integer[] {0,2});
            }
        }
        if (centerPiece.equals(" ")) {
            // Middle row check
            if (gameBoard[1][0].equals(symbol) && gameBoard[1][2].equals(symbol)) {
                allWinningMoves.add(new Integer[] {1,1});
            }
            // Middle column check
            if (gameBoard[0][1].equals(symbol) && gameBoard[2][1].equals(symbol)) {
                allWinningMoves.add(new Integer[] {1,1});
            }
            // Diagonal check
            if (gameBoard[0][0].equals(symbol) && gameBoard[2][2].equals(symbol)) {
                allWinningMoves.add(new Integer[] {1,1});
            }
            // Anti-diagonal check
            if (gameBoard[0][2].equals(symbol) && gameBoard[2][0].equals(symbol)) {
                allWinningMoves.add(new Integer[] {1,1});
            }
        }

        // Top and bottom row check
        String midRow;
        for (int i = 0; i < 3; i+=2) {
            midRow = gameBoard[i][1];

            if (midRow.equals(symbol)) {
                if (gameBoard[i][0].equals(midRow)) {
                    allWinningMoves.add(new Integer[] {i,2});
                } else if (gameBoard[i][2].equals(midRow)) {
                    allWinningMoves.add(new Integer[] {i,0});
                }
            }
            if (midRow.equals(" ")) {
                if (gameBoard[i][0].equals(symbol) && gameBoard[i][2].equals(symbol)) {
                    allWinningMoves.add(new Integer[] {i,1});
                }
            }
        }

        // Left and right columns check
        String midColumn;
        for (int i = 0; i < 3; i+=2) {
            midColumn = gameBoard[1][i];

            if (midColumn.equals(symbol)) {
                if (gameBoard[0][i].equals(midColumn)) {
                    allWinningMoves.add(new Integer[] {2,i});
                } else if (gameBoard[2][i].equals(midColumn)) {
                    allWinningMoves.add(new Integer[] {0,i});
                }
            }

            if (midColumn.equals(" ")) {
                if (gameBoard[0][i].equals(symbol) && gameBoard[2][i].equals(symbol)) {
                    allWinningMoves.add(new Integer[] {1,i});
                }
            }
        }

        return allWinningMoves;
    }

    // Returns internal gameBoard as a 1D String array instead of a 2D String array
    // (Needed for minimax algorithm implementation)
    protected String[] toStringArray() {
        String[] output = new String[9];
        for (int i = 0; i < 9; i++) {
            if (i < 3) {
                output[i] = gameBoard[0][i];
            } else if (i < 6) {
                output[i] = gameBoard[1][i-3];
            } else {
                output[i] = gameBoard[2][i-6];
            }
        }

        for (int i = 0; i < 9; i++) {
            if (output[i].equals(" ")) {
                output[i] = String.valueOf(i);
            }
        }
        return output;
    }

}
