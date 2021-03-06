package bot;

import java.util.Scanner;

public class SimpleBot {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Hello! My name is Robert.");
        System.out.println("I was created in 2021.");
        System.out.println("Please, remind me your name.");

        String userName = scnr.nextLine();

        System.out.printf("What a great name you have, %s!\n", userName);
    }
}
