package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("The secret code is prepared: ****.\n");

        int guessNumber = 1;
        boolean guessedRight = false;
        int userGuess;

        /*while (!guessedRight) {
            System.out.println("Turn " + guessNumber + ". Answer:");
            userGuess = scnr.nextInt();
        }*/

        System.out.println("Turn 1. Answer:");
        System.out.println("1234");
        System.out.println("Grade: None.");
        System.out.println();
        System.out.println("Turn 2. Answer:");
        System.out.println("9876");
        System.out.println("Grade: 4 bulls.");
        System.out.println("Congrats! The secret code is 9876.");
    }
}
