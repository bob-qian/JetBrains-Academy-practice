package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {

        // Initialize new Tic-tac-toe game
        Main tttGame = new Main();

        // Set according to user input
        /*System.out.print("Enter cells: ");
        String userInput = scnr.nextLine();
        tttGame.setGameBoard(userInput);*/

        // Create empty game
        tttGame.setGameBoard("_________");

        System.out.println(tttGame);

        String currentPlayer = "X";

        while (tttGame.analyzeGame().equals("Game not finished")) {
            tttGame.playerTurn(currentPlayer);
            tttGame.analyzeGame();
            if (currentPlayer.equals("X")) {
                currentPlayer = "O";
            } else {
                currentPlayer = "X";
            }
        }
        System.out.println(tttGame.analyzeGame());

    }

    private String[][] gameBoard;

    public Main() {
        gameBoard = new String[3][3];
    }

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

    public void setGameBoard(String input) {
        char[] inputLetters = input.toCharArray();
        for (int i = 0; i < inputLetters.length; i++) {
            if (i < 3) {
                gameBoard[0][i] = Character.toString(inputLetters[i]);
            } else if (i < 6) {
                gameBoard[1][i-3] = Character.toString(inputLetters[i]);
            } else {
                gameBoard[2][i-6] = Character.toString(inputLetters[i]);
            }
        }
    }

    public boolean addCoordinates(int row, int column, String currentPlayer) {
        if (gameBoard[row-1][column-1].equals("_") || gameBoard[row-1][column-1].equals(" ")) {
            // If slot is empty
            gameBoard[row-1][column-1] = currentPlayer;
            System.out.println(this);
            return true;
        }

        // Else slot is occupied
        System.out.println("This cell is occupied! Choose another one!");
        return false;
    }


    public void playerTurn(String currentPlayer) {
        boolean inputSuccessful = false;
        do {
            System.out.print("Enter the coordinates: ");
            String coordinates = scnr.nextLine();
            String[] splitCoordinates = coordinates.split(" ");

            try {
                int x = Integer.parseInt(splitCoordinates[0]);
                int y = Integer.parseInt(splitCoordinates[1]);
                inputSuccessful = this.addCoordinates(x, y, currentPlayer);

                if ((x > 3 || x < 1) || (y > 3 || y < 1)) {
                    throw new IndexOutOfBoundsException();
                }
            } catch (NumberFormatException excpt) {
                System.out.println("You should enter numbers!");
            } catch (IndexOutOfBoundsException excpt) {
                System.out.println("Coordinates should be from 1 to 3!");
            } catch (Exception excpt) {
                System.out.println("Invalid input.");
            }
        } while (!inputSuccessful);
    }


    public String analyzeGame() {
        // Impossible if difference of Xs and Os is 2 or more
        if (Math.abs(this.numX() - this.numO()) >= 2) {
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

    // Helper methods for analyzeGame()
    public int numX() {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j].equals("X")) {
                    total += 1;
                }
            }
        }
        return total;
    }

    public int numO() {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j].equals("O")) {
                    total += 1;
                }
            }
        }
        return total;
    }

    public boolean hasEmptyCells() {
        if ((this.numX() + this.numO()) == 9) {
            return false;
        }
        return true;
    }

    public ArrayList<String> hasThreeInARow() {
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

        // Remove underscores if accidentally caught
        while (winners.contains("_")) {
            winners.remove("_");
        }
        return winners;
    }

}
