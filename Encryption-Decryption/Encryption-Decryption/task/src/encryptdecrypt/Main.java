package encryptdecrypt;

public class Main {
    public static void main(String[] args) {
        String message = "we found a treasure!";

        System.out.println(encrypt(message));

    }

    /**
     * Encrypts message replacing each letter with the letter that is
     * in the corresponding position from the end of the English alphabet
     *
     * Disregards spaces
     * @param message
     * @return encrypted String
     */
    public static String encrypt(String message) {
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
