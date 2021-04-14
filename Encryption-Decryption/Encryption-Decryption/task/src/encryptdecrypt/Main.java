package encryptdecrypt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String mode = "enc"; // Default to encryption
        int key = 0; // Default to key 0
        String data = ""; // Default to an empty string


        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                mode = args[i + 1];
            } else if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i + 1]);
            } else if (args[i].equals("-data")) {
                data = args[i + 1];
            }
        }

        if (mode.equals("dec")) {
            key = -1 * key;
        }

        System.out.println(caesarEncrypt(data, key));
    }

    /**
     * Encrypts message using a caesar shift cypher
     * @param message
     * @param key number of shifts to make for each letter (in either direction)
     * @return encrypted String
     */
    public static String caesarEncrypt(String message, int key) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            encryptedMessage.append(caesarShiftChar(currentChar, key));
        }

        return encryptedMessage.toString();
    }

    // Shifts an individual char letter by a positive/negative amount
    private static char caesarShiftChar(char letter, int amount) {
        int shiftedLetter = 0;

        // For negative shifts
        if (amount < 0) {
            // Use Math.floorMod to make mod of negative number return
            // positive modulus and not negative remainder (Java default)
            shiftedLetter = letter + (Math.floorMod(amount, 143859));
        } else {
            // For positive shifts
            shiftedLetter = letter + (amount % 143859);
        }

        if (shiftedLetter > 143859) {
            shiftedLetter -= 143859;
        }
        return (char)shiftedLetter;
    }


    /**
     * Encrypts message replacing each letter with the letter that is
     * in the corresponding position from the end of the English alphabet
     *
     * Disregards spaces and punctuation
     * @param message
     * @return encrypted String
     */
    public static String pairEncrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);

            // Only encrypt letters, ignore spaces & punctuation
            if (currentChar >= 97 && currentChar <= (97+26)) {
                encryptedMessage.append(findComplement(currentChar));
            } else {
                encryptedMessage.append(currentChar);
            }
        }

        return encryptedMessage.toString();
    }

    // Returns "complement" letter
    // (letter that is in the corresponding position from the end of the English alphabet)
    private static char findComplement(char letter) {
        int index = letterToIndex(letter);
        int complementIndex = 25 - index;
        return indexToLetter(complementIndex);
    }

    // Converts a char letter to an index from 0 to 25
    private static int letterToIndex(char letter) {
        return (letter - 'a');
    }

    // Converts an index from 0 to 25 to a char letter
    private static char indexToLetter(int index) {
        return ((char) (index + 'a'));
    }
}
