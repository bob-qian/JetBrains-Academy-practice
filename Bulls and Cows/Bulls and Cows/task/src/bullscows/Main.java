package bullscows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String secretCode = "9305";

        Scanner scnr = new Scanner(System.in);

        String userGuess = scnr.nextLine();

        System.out.println(findBullsAndCows(secretCode, userGuess));




    }

    public static String findBullsAndCows(String secretCode, String userGuess) {

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

        StringBuilder output = new StringBuilder("Grade: ");

        if (numBulls > 0 && numCows > 0) {
            output.append(Integer.toString(numBulls) + " bull(s) and ");
            output.append(Integer.toString(numCows) + " cow(s). ");
        } else if (numBulls > 0) {
            output.append(Integer.toString(numBulls) + " bull(s). ");
        } else if (numCows > 0) {
            output.append(Integer.toString(numCows) + " cow(s). ");
        } else {
            output.append("None. ");
        }
        output.append("The secret code is " + secretCode);

        return output.toString();
    }
}
