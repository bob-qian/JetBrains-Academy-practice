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

        System.out.println("Write how many ml of water the coffee machine has:");
        int inputWater = scnr.nextInt();

        System.out.println("Write how many ml of milk the coffee machine has:");
        int inputMilk = scnr.nextInt();

        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        int inputBeans = scnr.nextInt();

        // Initialize a new Coffee Machine
        CoffeeMachine coffee = new CoffeeMachine(inputWater, inputMilk, inputBeans);

        System.out.println("Write how many cups of coffee you will need:");
        int cupsCoffee = scnr.nextInt();

        System.out.println(coffee.makeCups(cupsCoffee));


    }

    private int waterAmt;
    private int milkAmt;
    private int beansAmt;

    public CoffeeMachine(int waterAmt, int milkAmt, int beansAmt) {
        this.waterAmt = waterAmt;
        this.milkAmt = milkAmt;
        this.beansAmt = beansAmt;
    }

    public String makeCups(int cupsCoffee) {
        int waterCanProduce = this.waterAmt / 200; // How many cups of coffee could be produced
        int milkCanProduce = this.milkAmt / 50; // How many cups of coffee could be produced
        int beansCanProduce = this.beansAmt / 15; // How many cups of coffee could be produced

        int cupsMade = Math.min(Math.min(waterCanProduce, milkCanProduce), beansCanProduce);

        if (cupsMade == cupsCoffee) {
            return "Yes, I can make that amount of coffee";
        } else if (cupsMade > cupsCoffee) {
            return "Yes, I can make that amount of coffee (and even " +
                    (cupsMade - cupsCoffee) + " more than that)";
        }
        return "No, I can make only " + (cupsMade) + " cup(s) of coffee";
    }
}
