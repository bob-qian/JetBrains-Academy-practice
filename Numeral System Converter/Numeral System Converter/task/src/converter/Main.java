package converter;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        try {
            // Get 3 input values
            //System.out.print("Source radix: ");
            int sourceRadix = Integer.parseInt(scnr.nextLine());
            if (sourceRadix > 36 || sourceRadix < 1) {
                throw new Exception("Error: Source radix must be between 1-36!");
            }

            //System.out.print("Source number: ");
            String sourceNumber = scnr.nextLine();

            //System.out.print("Target radix: ");
            int targetRadix = Integer.parseInt(scnr.nextLine());
            if (targetRadix > 36 || targetRadix < 1) {
                throw new Exception("Error: Target radix must be between 1-36!");
            }

            double decimalSourceNumber = convertNumberStringToDecimal(sourceNumber, sourceRadix);

            System.out.println(convertDecimalNumberToRadixString(decimalSourceNumber, targetRadix));
        } catch (NumberFormatException e) {
            System.out.println("Error: not a number.");
        } catch (NoSuchElementException e) {
            System.out.println("Error: source number not a number.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Converts any number IN STRING FORM in base 1-36 to its decimal (base 10) representation
     * @param origNumber
     * @param origRadix the original base of the number
     * @return a double base 10 representation of the original number
     */
    public static double convertNumberStringToDecimal(String origNumber, int origRadix) {
        if (origNumber.contains(".")) {
            return convertDoubleStringToDecimal(origNumber, origRadix);
        } else {
            return convertIntegerStringToDecimal(origNumber, origRadix);
        }
    }

    // Converts any double IN STRING FORM in base 1-36 to its decimal (base 10) representation
    private static double convertDoubleStringToDecimal(String origNumber, int origRadix) {
        // Special case: base 1 (cannot have fractional parts)
        if (origRadix == 1) {
            return convertIntegerStringToDecimal(origNumber, origRadix);
        }

        // Split number into 2 parts
        String[] number = origNumber.split("\\.");

        // Find decimal representations of each part
        double decimalIntegerPart = (double) convertIntegerStringToDecimal(number[0], origRadix);
        double decimalFractionalPart = convertFractionStringToDecimal(number[1], origRadix);

        // Sum the parts
        return decimalIntegerPart + decimalFractionalPart;
    }

    // Converts any integer IN STRING FORM in base 1-36 to its decimal (base 10) representation
    private static int convertIntegerStringToDecimal(String origNumber, int origRadix) {
        // Special case: base 1
        if (origRadix == 1) {
            return(origNumber.length());
        }

        int decimalValue = 0;

        int currentPower = origNumber.length() - 1;
        for (int i = 0; i < origNumber.length(); i++) {
            char currentChar = origNumber.charAt(i);
            int currentDigitsValue = convertLetterToNumber(currentChar);
            decimalValue += currentDigitsValue * Math.pow(origRadix, currentPower);
            currentPower -= 1;
        }

        return decimalValue;
    }

    // Converts any fraction IN STRING FORM in base 1-36 to its decimal (base 10) representation
    private static double convertFractionStringToDecimal(String fraction, int origRadix) {
        double decimalValue = 0;

        // Formula provided by JetBrains Academy
        for (int i = 0; i < fraction.length(); i++) {
            char currentChar = fraction.charAt(i);
            int currentDigitsValue = convertLetterToNumber(currentChar);
            decimalValue += (currentDigitsValue) / Math.pow(origRadix, i+1);
        }
        return decimalValue;
    }


    // Converts a letter to a decimal representation between 10-35
    private static int convertLetterToNumber(char letter) {
        // If char letter is a digit between 0 and 9
        if (Character.getNumericValue(letter) <= 9) {
            return Character.getNumericValue(letter);
        }

        return (int) (letter - 87);
    }

    // Converts a number between 10-35 to a String letter representation
    private static String convertNumberToLetter(int number) {
        // If number is between 0 and 9 (has no letter representation)
        if (number <= 9) {
            return Integer.toString(number);
        }

        return Character.toString((char) (number + 87));
    }


    /**
     * Converts any decimal number to base -newRadix- representation IN STRING FORM
     * @param origNumber
     * @param newRadix
     * @return String representation of original decimal number in base -newRadix-
     */
    public static String convertDecimalNumberToRadixString(double origNumber, int newRadix) {
        if ((int) origNumber != origNumber) {
            return convertDecimalDoubleToRadixString(origNumber, newRadix);
        } else {
            return convertDecimalIntegerToRadixString((int) origNumber, newRadix);
        }
    }

    // Converts any decimal double to base -newRadix- representation IN STRING FORM
    private static String convertDecimalDoubleToRadixString(double origNumber, int newRadix) {
        // Special case: base 1 (cannot have fractional parts)
        if (newRadix == 1) {
            return convertDecimalIntegerToRadixString((int)origNumber, newRadix);
        }

        // Split number into 2 parts
        int integerPart = (int) Math.floor(origNumber);
        double fractionalPart = origNumber - integerPart;

        // Find -newRadix- representations of each part
        String newIntegerPart = convertDecimalIntegerToRadixString(integerPart, newRadix);
        String newFractionalPart = convertDecimalFractionToRadixString(fractionalPart, newRadix);

        return newIntegerPart + newFractionalPart;
    }

    // Converts any decimal integer to base -newRadix- representation IN STRING FORM
    private static String convertDecimalIntegerToRadixString(int origNumber, int newRadix) {
        StringBuilder newNumber = new StringBuilder();
        // Special case: base 1
        if (newRadix == 1) {
            for (int i = 0; i < origNumber; i++) {
                newNumber.append(1);
            }
            return newNumber.toString();
        }

        return Integer.toString(origNumber, newRadix);
    }

    // Converts any decimal fraction to base -newRadix- representation IN STRING FORM
    private static String convertDecimalFractionToRadixString(double fraction, int newRadix) {
        StringBuilder newNumber = new StringBuilder(".");

        // Array containing the new digits
        ArrayList<String> newNumberDigits = new ArrayList<>();

        // Output only 5 digits (letters) in the fractional part
        double multiplyWithBase = fraction;
        for (int i = 0; i < 5; i++) {
            double fractionTimesBase = multiplyWithBase * newRadix;
            int integerPart = (int) Math.floor(fractionTimesBase);
            newNumberDigits.add(convertNumberToLetter(integerPart));

            double fractionalPart = fractionTimesBase - integerPart;
            multiplyWithBase = fractionalPart;
        }

        // Iterate through the list of digits and append them
        for (int i = 0; i < newNumberDigits.size(); i++) {
            newNumber.append(newNumberDigits.get(i));
        }

        return newNumber.toString();
    }
}
