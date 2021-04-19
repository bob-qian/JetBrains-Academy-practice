package processor;

import java.util.Scanner;

public class Main {
    private static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {
        boolean continueProgram = menuAction();
        while (continueProgram) {
            continueProgram = menuAction();
        }
    }

    // Displays menu and asks for user input
    private static boolean menuAction() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("0. Exit");

        System.out.print("Your choice: ");
        String selectedOperation = scnr.nextLine();

        switch (selectedOperation) {
            case "1":
                addMatrixAction();
                break;
            case "2":
                multiplyMatrixByConstantAction();
                break;
            case "3":
                multiplyMatricesAction();
                break;
            case "0":
                return false;
        }
        return true;
    }

    // Asks for user input and adds matrices
    private static void addMatrixAction() {
        System.out.print("Enter size of first matrix: ");
        String[] dimensions1 = dimensionsFromInput();
        System.out.println("Enter first matrix:");
        double[][] matrix1 = createMatrixFromInput(dimensions1);

        System.out.print("Enter size of second matrix: ");
        String[] dimensions2 = dimensionsFromInput();
        System.out.println("Enter second matrix:");
        double[][] matrix2 = createMatrixFromInput(dimensions2);

        double[][] result = addMatrix(matrix1, matrix2);
        if (result == null) {
            System.out.println("The operation cannot be performed.");
        } else {
            System.out.println("The result is:");
            System.out.println(displayMatrix(result));
        }
    }

    // Asks for user input and multiplies matrix by a constant
    private static void multiplyMatrixByConstantAction() {
        System.out.print("Enter size of matrix: ");
        String[] dimensions = dimensionsFromInput();
        System.out.println("Enter matrix:");
        double[][] matrix = createMatrixFromInput(dimensions);

        System.out.print("Enter constant: ");
        double constant = createConstantFromInput();

        System.out.println("The result is:");
        System.out.println(displayMatrix(multiplyMatrix(matrix, constant)));
    }

    // Asks for user input and multiplies 2 matrices
    private static void multiplyMatricesAction() {
        System.out.print("Enter size of first matrix: ");
        String[] dimensions1 = dimensionsFromInput();
        System.out.println("Enter first matrix:");
        double[][] matrix1 = createMatrixFromInput(dimensions1);

        System.out.print("Enter size of second matrix: ");
        String[] dimensions2 = dimensionsFromInput();
        System.out.println("Enter second matrix:");
        double[][] matrix2 = createMatrixFromInput(dimensions2);

        double[][] result = multiplyMatrix(matrix1, matrix2);
        if (result == null) {
            System.out.println("The operation cannot be performed.");
        } else {
            System.out.println("The result is:");
            System.out.println(displayMatrix(result));
        }
    }

    /**
     * Sums two m x n matrices
     * @param matrix1 first matrix
     * @param matrix2 second matrix
     * @return m x n sum
     */
    public static double[][] addMatrix(double[][] matrix1, double[][] matrix2) {
        double[][] outputMatrix = new double[matrix1.length][matrix1[0].length];

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

    /**
     * Multiply a matrix by another matrix (overloaded method)
     * @param matrix
     * @param matrix2
     * @return
     */
    public static double[][] multiplyMatrix(double[][] matrix, double[][] matrix2) {
        if (matrix[0].length != matrix2.length) {
            return null;
        }

        double[][] outputMatrix = new double[matrix.length][matrix2[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                double innerProduct = 0;

                // For i = 0, j = 0:
                // matrix[0][0] * matrix2[0][0] + matrix[0][1] * matrix2[1][0] + matrix[0][2] * matrix[2][0]

                // This loop computes the inner product of row i of matrix with col j of matrix2
                for (int k = 0; k < matrix[0].length; k++) {
                    innerProduct += matrix[i][k] * matrix2[k][j];
                }
                outputMatrix[i][j] = innerProduct;
            }
        }

        return outputMatrix;
    }

    /**
     * Scale an m x n matrix by a constant (overloaded method)
     * @param matrix
     * @param constant
     * @return
     */
    public static double[][] multiplyMatrix(double[][] matrix, double constant) {
        double[][] outputMatrix = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                outputMatrix[i][j] = matrix[i][j] * constant;
            }
        }

        return outputMatrix;
    }

    // Creates a double constant from user input
    private static double createConstantFromInput() {
        String constant = scnr.nextLine();
        return Double.parseDouble(constant);
    }

    // Creates a 2D array matrix using matrix dimensions (in the form of a String array)
    private static double[][] createMatrixFromInput(String[] dimensions) {
        int numRows = Integer.parseInt(dimensions[0]);
        int numCols = Integer.parseInt(dimensions[1]);

        // Create a new 2D array of dimensions specified
        double[][] matrix = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String[] row = scnr.nextLine().split(" ");
            for (int j = 0; j < row.length; j++) {
                matrix[i][j] = Double.parseDouble(row[j]);
            }
        }
        return matrix;
    }

    // Gets matrix dimensions from user input (in the form of a String array)
    private static String[] dimensionsFromInput() {
        String[] dimensions = scnr.nextLine().split(" ");
        return dimensions;
    }

    // Creates a string representation of a 2D array matrix
    private static String displayMatrix(double[][] matrix) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double currentElement = matrix[i][j];
                // Leave off trailing ".0" if double is a whole number
                if (currentElement % 1 == 0) {
                    output.append((int) currentElement);
                } else {
                    output.append(currentElement);
                }

                output.append(" ");
            }
            output.append("\n");
        }

        return output.toString();
    }
}
