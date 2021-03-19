package bullscows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        int userLength = scnr.nextInt();
        scnr.nextLine();
        String secretCode = generateSecretCode(userLength);

        if (secretCode.equals("Error")) {
            System.out.println("Error");
        } else {
            System.out.println("Okay, let's start a game!");

            //System.out.println(secretCode);

            boolean gameFinished = false;
            int turnNumber = 1;

            do {
                System.out.println("Turn " + turnNumber + ":");
                int[] result = findBullsAndCows(secretCode, scnr.nextLine());
                System.out.println(outputBullsAndCows(result));

                if (result[0] == secretCode.length() && result[1] == 0) {
                    System.out.println("Congratulations! You guessed the secret code.");
                    gameFinished = true;
                }

                turnNumber += 1;
            } while (!gameFinished);
        }
    }

    public static String generateSecretCode(int length) {
        if (length > 10) {
            return "Error";
        }

        Random random = new Random();
        int randomDigit;
        HashSet<Integer> digitsAlreadyAdded = new HashSet<>();
        StringBuilder secretCode;


        digitsAlreadyAdded.clear();
        secretCode = new StringBuilder();

        while (secretCode.length() < length) {
            randomDigit = random.nextInt(9);

            if (digitsAlreadyAdded.size() == 0 && randomDigit == 0) {
                continue;
            }

            if (!digitsAlreadyAdded.contains(randomDigit)) {
                secretCode.append(Integer.toString(randomDigit));
                digitsAlreadyAdded.add(randomDigit);
                System.out.println(secretCode);
            }
        }

        return secretCode.toString();
    }

    public static int[] findBullsAndCows(String secretCode, String userGuess) {

        // Init Set containing digits of secret code
        String[] secretCodeSplit = secretCode.split("");
        HashSet<String> secretCodeDigits = new HashSet<>(Arrays.asList(secretCodeSplit));

        int numBulls = 0;
        int numCows = 0;


        for (int i = 0; i < userGuess.length(); i++) {
            if (userGuess.charAt(i) == secretCode.charAt(i)) {
                // If digit is in correct spot, bulls++
                numBulls += 1;
            } else if (secretCodeDigits.contains(Character.toString(userGuess.charAt(i)))) {
                // else if digit is in set of digits of secret code, cows++
                numCows += 1;
            }
        }

        return new int[] {numBulls, numCows};
    }

    public static String outputBullsAndCows(int[] bullsAndCows) {
        StringBuilder output = new StringBuilder("Grade: ");

        String bull = " bulls ";
        String cow = " cows ";

        if (bullsAndCows[0] == 1) {
            bull = " bull ";
        }

        if (bullsAndCows[1] == 1) {
            cow = " cow ";
        }

        if (bullsAndCows[0] > 0 && bullsAndCows[1] > 0) {
            output.append(Integer.toString(bullsAndCows[0]) + bull + "and ");
            output.append(Integer.toString(bullsAndCows[1]) + cow);
        } else if (bullsAndCows[0] > 0) {
            output.append(Integer.toString(bullsAndCows[0]) + bull);
        } else if (bullsAndCows[1] > 0) {
            output.append(Integer.toString(bullsAndCows[1]) + cow);
        } else {
            output.append("None. ");
        }
        return output.toString();
    }
}
