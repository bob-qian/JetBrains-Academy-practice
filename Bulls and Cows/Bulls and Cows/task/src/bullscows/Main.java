package bullscows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        int userLength = scnr.nextInt();
        scnr.nextLine();

        System.out.println("Input the number of possible symbols in the code:");
        int maxSymbols = scnr.nextInt();
        scnr.nextLine();

        String secretCode = generateSecretCode(userLength, maxSymbols);

        if (secretCode.equals("Error")) {
            System.out.println("Error");
        } else {
            System.out.println("Okay, let's start a game!");

            // System.out.println(secretCode);

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

    public static String generateSecretCode(int length, int maxSymbols) {
        if (length > 36) {
            return "Error";
        }

        Random random = new Random();
        int randomNumber;
        int randomDigit;
        char randomChar;

        HashSet<Integer> digitsAlreadyAdded = new HashSet<>();
        HashSet<Character> symbolsAlreadyAdded = new HashSet<>();
        StringBuilder secretCode;

        digitsAlreadyAdded.clear();
        secretCode = new StringBuilder();

        // Generate next random digit or character
        while ((secretCode.length() < length)) {
            randomNumber = random.nextInt(maxSymbols);

            if (randomNumber < 10) {
                randomDigit = randomNumber;

                // Add digit to secret code if unique
                if (!digitsAlreadyAdded.contains(randomDigit)) {
                    secretCode.append(Integer.toString(randomDigit));
                    digitsAlreadyAdded.add(randomDigit);
                }
            } else {
                randomChar = (char)(randomNumber - 10 + 'a');

                // Add symbol to secret code if unique
                if (!symbolsAlreadyAdded.contains(randomChar)) {
                    secretCode.append(Character.toString(randomChar));
                    symbolsAlreadyAdded.add(randomChar);
                }
            }
        }

        // Output hidden secretCode
        StringBuilder hiddenCode = new StringBuilder("The secret is prepared: ");
        for (int i = 0; i < length; i++) {
            hiddenCode.append("*");
        }
        hiddenCode.append(" (0-9)");
        if (!(maxSymbols < 10)) {
            hiddenCode.deleteCharAt(hiddenCode.length() - 1);
            hiddenCode.append(", a-");
            char lastLetter = (char)((maxSymbols-1) - 10 + 'a');
            hiddenCode.append(lastLetter + ")");
        }
        hiddenCode.append(".");
        System.out.println(hiddenCode);

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
