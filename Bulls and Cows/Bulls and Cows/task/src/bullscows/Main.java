package bullscows;

import java.util.Arrays;
import java.util.HashSet;
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
        boolean codeGenerated = false;
        long pseudoRandomNumber;
        HashSet<Character> digitsAlreadyAdded = new HashSet<>();
        StringBuilder secretCode;

        do {
            pseudoRandomNumber = System.nanoTime();
            //System.out.println(pseudoRandomNumber);

            // convert to a string
            String randomNumber = Long.toString(pseudoRandomNumber);

            if (randomNumber.length() < length || length > 10) {
                return "Error";
            }

            digitsAlreadyAdded.clear();

            secretCode = new StringBuilder();

            int i = randomNumber.length()-1;
            while (secretCode.length() < length && i >= 0) {
                char currentDigit = randomNumber.charAt(i);

                if (digitsAlreadyAdded.size() == 0 && currentDigit == '0') {
                    i -= 1;
                    continue;
                }

                if (!digitsAlreadyAdded.contains(currentDigit)) {
                    secretCode.append(currentDigit);
                    digitsAlreadyAdded.add(currentDigit);
                }
                i -= 1;
            }

            if (secretCode.length() == length) {
                codeGenerated = true;
            }
        } while (!codeGenerated);

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
