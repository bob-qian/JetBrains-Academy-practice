package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        /*System.out.println("Starting to make a coffee");
        System.out.println("Grinding coffee beans");
        System.out.println("Boiling water");
        System.out.println("Mixing boiled water with crushed coffee beans");
        System.out.println("Pouring coffee into the cup");
        System.out.println("Pouring some milk into the cup");
        System.out.println("Coffee is ready!");*/

        Scanner scnr = new Scanner(System.in);

        System.out.println("Write how many cups of coffee you will need:");
        int cupsCoffee = scnr.nextInt();
        calcIngredients(cupsCoffee);

    }

    public static void calcIngredients(int cupsCoffee) {
        System.out.printf("For %s cups of coffee you will need:\n", cupsCoffee);
        System.out.printf("%d ml of water\n", cupsCoffee * 200);
        System.out.printf("%d ml of milk\n", cupsCoffee * 50);
        System.out.printf("%d g of coffee beans\n", cupsCoffee * 15);
    }
}
