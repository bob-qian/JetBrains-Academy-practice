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

        int sourceRadix = Integer.parseInt(scnr.nextLine());

        int sourceNumberBase10;
        if (sourceRadix == 1) {
            // Special case
            String sourceNumberInput = scnr.nextLine();
            sourceNumberBase10 = sourceNumberInput.length();
        } else {
            // Directly convert input into base 10 representation, regardless of original radix/base
            sourceNumberBase10 = Integer.parseInt(scnr.nextLine(), sourceRadix);
        }

        int targetRadix = Integer.parseInt(scnr.nextLine());

        StringBuilder output = new StringBuilder();

        if (targetRadix == 1) {
            // Special case
            for (int i = 0; i < sourceNumberBase10; i++) {
                output.append(1);
            }
        } else {
            output.append(convertToBaseRadix(sourceNumberBase10, targetRadix));
        }
        System.out.println(output.toString());

    }

    // Converts a number to base -radix-; returns a String
    private static String convertToBaseRadix(int number, int radix) {
        String output = Integer.toString(number, radix);
        return output;
    }
}
