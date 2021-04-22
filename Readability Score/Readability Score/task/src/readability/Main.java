package readability;

import java.io.File;
import java.util.*;

public class Main {
    private static int[] mapARItoAgeGroup;
    static {
        mapARItoAgeGroup = new int[14];

        // Each ARI score is mapped to its corresponding age group at index [score-1]
        // For the age groups, only the UPPER BOUND of the age range is listed
        mapARItoAgeGroup[0] = 6;
        mapARItoAgeGroup[1] = 7;
        mapARItoAgeGroup[2] = 9;
        mapARItoAgeGroup[3] = 10;
        mapARItoAgeGroup[4] = 11;
        mapARItoAgeGroup[5] = 12;
        mapARItoAgeGroup[6] = 13;
        mapARItoAgeGroup[7] = 14;
        mapARItoAgeGroup[8] = 16;
        mapARItoAgeGroup[9] = 16;
        mapARItoAgeGroup[10] = 17;
        mapARItoAgeGroup[11] = 18;
        mapARItoAgeGroup[12] = 24;
        mapARItoAgeGroup[13] = 25;
    }

    private static HashSet<Character> vowels;
    static {
        vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        vowels.add('y');
    }

    static int totalCharacters = 0;
    static int totalWords = 0;
    static int totalSentences = 0;
    static int totalSyllables = 0;
    static int totalPolysyllables = 0;

    public static void main(String[] args) {
        // Get file path as argument from command line
        String file = args[0];
        File inputFile = new File(file);

        ArrayList<String> allWords = fileToWords(inputFile);

        textStatistics(allWords);
        scoreCalculateAction();
    }

    // Converts a file to an ArrayList containing all of the words in the file
    private static ArrayList<String> fileToWords(File inputFile) {
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
        return allWords;
    }

    // Calculates global statistics for the input text (# words, # sentences, # characters, etc.)
    private static void textStatistics(ArrayList<String> wordsOfText) {
        totalWords = wordsOfText.size();

        String endOfSentenceRegex = ".*[.!?]";
        // Find total characters, total sentences, total syllables
        for (int i = 0; i < wordsOfText.size(); i++) {
            String currentWord = wordsOfText.get(i);
            if (currentWord.matches(endOfSentenceRegex) || i == wordsOfText.size()-1) {
                totalSentences += 1;
            }
            totalCharacters += currentWord.length();

            int numSyllables = countSyllables(currentWord);
            totalSyllables += numSyllables;
            if (numSyllables > 2) {
                totalPolysyllables += 1;
            }
        }

        System.out.println("Words: " + totalWords);
        System.out.println("Sentences: " + totalSentences);
        System.out.println("Characters: " + totalCharacters);
        System.out.println("Syllables: " + totalSyllables);
        System.out.println("Polysyllables: " + totalPolysyllables);
    }

    // Handles the input and decision making for which scores to calculate and output
    private static void scoreCalculateAction() {
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scnr = new Scanner(System.in);
        String userWantsToCalculate = scnr.nextLine();
        System.out.println();

        ArrayList<Integer> ageGroups = new ArrayList<>();

        if (userWantsToCalculate.equals("all")) {
            selectFormula("ARI", ageGroups);
            selectFormula("FK", ageGroups);
            selectFormula("SMOG", ageGroups);
            selectFormula("CL", ageGroups);
        } else {
            selectFormula(userWantsToCalculate, ageGroups);
        }

        double averageAgeGroup = 0;
        for (Integer age : ageGroups) {
            averageAgeGroup += age;
        }
        averageAgeGroup /= ageGroups.size();
        System.out.println("\nThis text should be understood in average by " + averageAgeGroup +
                "-year-olds.");
    }

    private static void selectFormula(String name, ArrayList<Integer> ageGroups) {
        String testName = "";
        double testScore = 0;

        switch (name) {
            case "ARI":
                // Find ARI score
                testName = "Automated Readability Index";
                testScore = calculateARIScore(totalCharacters, totalWords, totalSentences);
                break;

            case "FK":
                // Find Flesh-Kincaid score
                testName = "Flesch-Kincaid readability tests";
                testScore = calculateFKScore(totalWords, totalSentences, totalSyllables);
                break;

            case "SMOG":
                // Find SMOG score
                testName = "Simple Measure of Gobbledygook";
                testScore = calculateSMOGscore(totalSentences, totalPolysyllables);
                break;

            case "CL":
                // Find CL score
                testScore = calculateCLscore(totalCharacters, totalWords, totalSentences);
                testName = "Coleman-Liau index";
                break;
        }

        // Replace in order for JetBrains tests to properly detect certain test names
        testName.replace("-", "–");

        int ageGroup = testToAgeGroup(testScore);
        System.out.println(testName + ": " + testScore + " (about " + ageGroup + "-year-olds).");

        // Update ageGroups array
        ageGroups.add(ageGroup);
    }

    // Returns number of syllables in a word using the number of vowels
    private static int countSyllables(String word) {
        int numSyllables = 0;
        int numVowels = countVowels(word);

        if (numVowels == 0) {
            numSyllables = 1;
        } else {
            numSyllables = numVowels;
        }
        return numSyllables;
    }

    // Returns the number of vowels in a word according to JetBrain Academy's 4 rules
    // IMPORTANT: does not actually count the literal number of vowels
    // 4 Rules: https://hyperskill.org/projects/39/stages/208/implement#comment
    private static int countVowels(String word) {
        int numVowels = 0;
        word = word.toLowerCase();

        // Remove last character in word if it is punctuation
        String punctuationRegex = ".*[.!?,:]";
        if (word.matches(punctuationRegex)) {
            word = word.substring(0, word.length() - 1);
        }

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (vowels.contains(currentChar)) {
                if (i == 0) {
                    numVowels += 1;
                } else if (i == word.length() - 1 && currentChar == 'e') {
                    // Do not count as a vowel if the last word is 'e'
                } else {
                    // Don't count double vowels (letter before must not be a vowel)
                    if (!vowels.contains(word.charAt(i - 1))) {
                        numVowels += 1;
                    }
                }
            }
        }
        return numVowels;
    }

    // Calculates ARI score as a double rounded down to 2 decimal places
    private static double calculateARIScore(int totalCharacters, int totalWords, int totalSentences) {
        // Automated readability index formula
        double ARIscore = 4.71 * ((double)totalCharacters / totalWords) +
                0.5 * ((double)totalWords / totalSentences) - 21.43;

        return roundDown(ARIscore);
    }

    // Takes test score and returns its age group as an int
    private static int testToAgeGroup(double score) {
        int scoreRounded = (int) Math.floor(score);
        int ageGroup = mapARItoAgeGroup[scoreRounded - 1];
        return ageGroup;
    }

    // Calculates FK score
    private static double calculateFKScore(int totalWords, int totalSentences, int totalSyllables) {
        // Flesh-Kincaid readability test formula
        double FKscore = 0.39 * ((double)totalWords / totalSentences) +
                11.8 * ((double)totalSyllables / totalWords) - 15.59;

        return roundDown(FKscore);
    }

    // Calculates SMOG score
    private static double calculateSMOGscore(int totalSentences, int totalPolysyllables) {
        // Simple Measure of Gobbledygook index formula
        double SMOGscore = 1.043 * Math.sqrt(totalPolysyllables * (30.0 / totalSentences)) + 3.1291;

        return roundDown(SMOGscore);
    }

    // Calculates CL score
    private static double calculateCLscore(int totalCharacters, int totalWords, int totalSentences) {
        // Coleman-Liau index formula
        double CLscore = 0.0588 * (100.0 * totalCharacters / totalWords) - 0.296 * (100.0 * totalSentences / totalWords) - 15.8;

        return roundDown(CLscore);
    }

    // Rounds a double down to 2 decimal places
    private static double roundDown(double number) {
        return (double) Math.floor(number*100) / 100;
    }
}
