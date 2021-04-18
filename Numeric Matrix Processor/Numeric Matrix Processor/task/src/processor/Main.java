package processor;

import java.util.Scanner;

public class Main {
    private static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {
        int[][] matrix1 = createMatrixFromInput();
        int[][] matrix2 = createMatrixFromInput();

        int[][] sum = addMatrix(matrix1, matrix2);

        if (sum == null) {
            System.out.println("ERROR");
        } else {
            System.out.println(displayMatrix(sum));
        }
    }

    public static int[][] addMatrix(int[][] matrix1, int[][] matrix2) {
        int[][] outputMatrix = new int[matrix1.length][matrix1[0].length];

        if (matrix1.length != matrix2.length) {
            return null;
        }

        if (matrix1[0].length != matrix2[0].length) {
            return null;
        }

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                outputMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return outputMatrix;
    }

    // Creates a 2D array matrix from user input
    private static int[][] createMatrixFromInput() {
        /*System.out.println("Enter matrix size:");*/
        String[] dimensions = scnr.nextLine().split(" ");

        int numRows = Integer.parseInt(dimensions[0]);
        int numCols = Integer.parseInt(dimensions[1]);


        // Create a new 2D array of dimensions specified
        int[][] matrix = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String[] row = scnr.nextLine().split(" ");
            for (int j = 0; j < row.length; j++) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }
        return matrix;
    }

    // Creates a string representation of a 2D array matrix
    private static String displayMatrix(int[][] matrix) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                output.append(matrix[i][j]);
                output.append(" ");
            }
            output.append("\n");
        }

        return output.toString();
    }
}
