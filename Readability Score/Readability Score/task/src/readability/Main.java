package readability;

import java.util.ArrayList;
import java.util.Arrays;
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
        String[] words = text.split(" ");

        String endOfSentenceRegex = ".*[.!?]";

        double averageWordsPerSentence = 0;

        int wordsInSentence = 0;
        int totalSentences = 0;

        for (int i = 0; i < words.length; i++) {
            wordsInSentence += 1;
            if (words[i].matches(endOfSentenceRegex) || i == words.length-1) {
                averageWordsPerSentence += wordsInSentence;
                totalSentences += 1;
                wordsInSentence = 0;
            }
        }
        averageWordsPerSentence = averageWordsPerSentence / totalSentences;


        if (averageWordsPerSentence > 10) {
            return "HARD";
        }
        return "EASY";

    }
}
