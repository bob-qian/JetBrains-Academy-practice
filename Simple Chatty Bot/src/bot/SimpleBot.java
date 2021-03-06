package bot;

import java.util.Scanner;

public class SimpleBot {
    final static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {

        greet("Robert", "2021");
        nameQuery();
        guessAge();
        countUp();
        testTheUser();
        System.out.println("Congratulations, have a nice day!");
    }

    public static void greet(String botName, String birthYear) {
        System.out.printf("Hello! My name is %s.\n", botName);
        System.out.printf("I was created in %s.\n", birthYear);
    }

    public static void nameQuery() {
        System.out.println("Please, remind me your name.");
        String userName = scnr.nextLine();
        System.out.printf("What a great name you have, %s!\n", userName);
    }

    public static void guessAge() {
        System.out.println("Let me guess your age.");
        System.out.println("Enter remainders of dividing your age by 3, 5, and 7.");
        int remainder3 = scnr.nextInt();
        int remainder5 = scnr.nextInt();
        int remainder7 = scnr.nextInt();

        int age = (remainder3 * 70 + remainder5 * 21 + remainder7 * 15) % 105;
        System.out.printf("Your age is %d; that's a good time to start programming!\n", age);
    }

    public static void countUp() {
        System.out.println("Now I will prove to you that I can count to any number you want.");
        int countToMe = scnr.nextInt();

        for (int i = 0; i < countToMe + 1; i++) {
            System.out.printf("%d!\n", i);
        }
    }

    public static void testTheUser() {
        System.out.println("Let's test your programming knowlege.");
        System.out.println("Why do we use methods?");
        System.out.println("1. To repeat a statement multiple times.");
        System.out.println("2. To decompose a program into several small subroutines.");
        System.out.println("3. To determine the execution time of a program.");
        System.out.println("4. To interrupt the execution of a program.");

        int answer = scnr.nextInt();
        while (answer != 2) {
            System.out.println("Please, try again.");
            answer = scnr.nextInt();
        }
    }
}
