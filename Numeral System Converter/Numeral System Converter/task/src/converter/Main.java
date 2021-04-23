package converter;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
   private static HashMap<Integer, String> prefixes;
   static {
       prefixes = new HashMap<>();
       prefixes.put(2, "0b");
       prefixes.put(8, "0");
       prefixes.put(16, "0x");
   }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        long userInput = Long.parseLong(scnr.nextLine());
        int userRadix = Integer.parseInt(scnr.nextLine());

        System.out.println(convertToBaseRadix(userInput, userRadix));
    }

    // Converts a number to base -radix- and then appends a prefix; returns a String
    private static String convertToBaseRadix(Long number, int radix) {
        String output = Long.toString(number, radix);
        return prefixes.get(radix) + output;
    }
}
