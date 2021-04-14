package encryptdecrypt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        String message = scnr.nextLine();
        int key = Integer.parseInt(scnr.nextLine());
        System.out.println(caesarEncrypt(message, key));
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

            // Only encrypt letters, ignore spaces & punctuation
            if (currentChar >= 97 && currentChar <= (97+26)) {
                encryptedMessage.append(caesarShiftChar(currentChar, key));
            } else {
                encryptedMessage.append(currentChar);
            }
        }

        return encryptedMessage.toString();
    }

    // Shifts an individual char letter by a positive/negative amount
    private static char caesarShiftChar(char letter, int amount) {
        // For negative shifts
        if (amount < 0) {
            // Use Math.floorMod to make mod of negative number return
            // positive modulus and not negative remainder (Java default)
            int shiftedLetter = letter + (Math.floorMod(amount, 26));
            if (shiftedLetter > (int) ('z')) {
                shiftedLetter -= 26;
            }
            return ((char)shiftedLetter);
        }

        // For positive shifts
        int shiftedLetter = letter + (amount % 26);
        if (shiftedLetter > (int) ('z')) {
            shiftedLetter -= 26;
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
