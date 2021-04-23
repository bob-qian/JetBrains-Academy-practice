package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        int userInput = Integer.parseInt(scnr.nextLine());
        System.out.println(userInput % 8);
    }
}
