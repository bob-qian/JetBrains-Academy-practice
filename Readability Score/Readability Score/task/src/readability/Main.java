package readability;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static String[] mapARItoAgeGroup;
    static {
        mapARItoAgeGroup = new String[14];

        // Each ARI score is mapped to its corresponding age group at index [score-1]
        mapARItoAgeGroup[0] = "5-6";
        mapARItoAgeGroup[1] = "6-7";
        mapARItoAgeGroup[2] = "7-9";
        mapARItoAgeGroup[3] = "9-10";
        mapARItoAgeGroup[4] = "10-11";
        mapARItoAgeGroup[5] = "11-12";
        mapARItoAgeGroup[6] = "12-13";
        mapARItoAgeGroup[7] = "13-14";
        mapARItoAgeGroup[8] = "14-15";
        mapARItoAgeGroup[9] = "15-16";
        mapARItoAgeGroup[10] = "16-17";
        mapARItoAgeGroup[11] = "17-18";
        mapARItoAgeGroup[12] = "18-24";
        mapARItoAgeGroup[13] = "24+";
    }

    public static void main(String[] args) {
        String file = args[0];
        File inputFile = new File(file);

        ArrayList<String> text = new ArrayList<>();

        try (Scanner scnr = new Scanner(inputFile)) {
            while (scnr.hasNext()) {
                text.add(scnr.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error: no file \""+ inputFile + "\" found.");
        }

        ArrayList<String> allWords = new ArrayList<>();

        for (String line : text) {
            String[] words = line.split(" ");

            allWords.addAll(Arrays.asList(words));
        }

        textDifficulty(allWords);
    }

    private static void textDifficulty(ArrayList<String> wordsOfText) {
        String endOfSentenceRegex = ".*[.!?]";

        int totalWords = wordsOfText.size();
        int totalSentences = 0;
        int totalCharacters = 0;
        for (int i = 0; i < wordsOfText.size(); i++) {
            if (wordsOfText.get(i).matches(endOfSentenceRegex) ||
                    i == wordsOfText.size()-1) {
                totalSentences += 1;
            }
            totalCharacters += wordsOfText.get(i).length();
        }

        // Automated readability index formula
        double ARIscore = 4.71 * ((double)totalCharacters / totalWords) +
                0.5 * ((double)totalWords / totalSentences) - 21.43;

        // Use math floor since unfortunately, JetBrains expects to round down always
        ARIscore = (double) Math.floor(ARIscore*100) / 100;

        System.out.println("Words: " + totalWords);
        System.out.println("Sentences: " + totalSentences);
        System.out.println("Characters: " + totalCharacters);
        System.out.println("The score is: " + ARIscore);

        ARItoAgeGroup(ARIscore);
    }

    // Takes ARI score and outputs its age group
    private static void ARItoAgeGroup(double ARIscore) {
        int ARIRounded = (int) Math.ceil(ARIscore);
        String ageGroup = mapARItoAgeGroup[ARIRounded - 1];
        System.out.println("This text should be understood by " + ageGroup + "-year-olds.");
    }
}
