package readability;

import java.util.Scanner;

public class Main {
    private static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {
        String input = scnr.nextLine();
        System.out.println(textDifficulty(input));
    }

    /**
     * Determines a text difficulty ("EASY" or "HARD") based on symbol count
     * @param text
     * @return
     */
    public static String textDifficulty(String text) {
        if (text.length() > 100) {
            return "HARD";
        }
        return "EASY";
    }
}
