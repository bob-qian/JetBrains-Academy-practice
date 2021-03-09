package machine;

// 3/8/2021

import java.util.Scanner;

public class CoffeeMachine {
    private static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {


        // Initialize a new Coffee Machine
        CoffeeMachine coffee = new CoffeeMachine(400, 540, 120, 9, 550);

        String action;

        do {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            action = scnr.nextLine();
            switch (action) {
                case "buy":
                    System.out.println(coffee.buy());
                    break;
                case "fill":
                    coffee.fillMachine();
                    break;
                case "take":
                    coffee.takeMachine();
                    break;
                case "remaining":
                    System.out.println();
                    System.out.println(coffee);
                    break;
            }
        } while (!action.equals("exit"));

    }

    private int waterAmt;
    private int milkAmt;
    private int beansAmt;
    private int cupsAmt;
    private int moneyAmt;

    public CoffeeMachine(int waterAmt, int milkAmt, int beansAmt, int cupsAmt, int moneyAmt) {
        this.waterAmt = waterAmt;
        this.milkAmt = milkAmt;
        this.beansAmt = beansAmt;
        this.cupsAmt = cupsAmt;
        this.moneyAmt = moneyAmt;
    }

    public void addWaterAmt(int waterAmt) {
        this.waterAmt += waterAmt;
    }

    public void addMilkAmt(int milkAmt) {
        this.milkAmt += milkAmt;
    }

    public void addBeansAmt(int beansAmt) {
        this.beansAmt += beansAmt;
    }

    public void addCupsAmt(int cupsAmt) {
        this.cupsAmt += cupsAmt;
    }

    public void addMoneyAmt(int moneyAmt) {
        this.moneyAmt += moneyAmt;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder("The coffee machine has:\n");
        display.append(this.waterAmt).append(" of water\n");
        display.append(this.milkAmt).append(" of milk\n");
        display.append(this.beansAmt).append(" of coffee beans\n");
        display.append(this.cupsAmt).append(" of disposable cups\n$");
        display.append(this.moneyAmt).append(" of money\n");
        return display.toString();
    }

    public void fillMachine() {
        System.out.println();

        // NOTE: nextInt() does not read the newline character so we use nextLine() and convert to int
        try {
            System.out.println("Write how many ml of water do you want to add:");
            String input = scnr.nextLine();
            this.addWaterAmt(Integer.parseInt(input));

            System.out.println("Write how many ml of milk do you want to add:");
            input = scnr.nextLine();
            this.addMilkAmt(Integer.parseInt(input));

            System.out.println("Write how many grams of coffee beans do you want to add:");
            input = scnr.nextLine();
            this.addBeansAmt(Integer.parseInt(input));

            System.out.println("Write how many disposable cups of coffee do you want to add:");
            input = scnr.nextLine();
            this.addCupsAmt(Integer.parseInt(input));
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("Not a number!\n");
            return;
        }
    }

    public void takeMachine() {
        System.out.printf("\nI gave you $%d\n", this.moneyAmt);
        this.moneyAmt = 0;
        System.out.println();
    }

    public String buy() {
        System.out.println();
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, " +
                "3 - cappuccino, back - to main menu:");
        String coffeeType = scnr.nextLine();

        int waterNeeded = 0;
        int milkNeeded = 0;
        int beansNeeded = 0;
        int cost = 0;

        switch (coffeeType) {
            case "1":
                // Espresso
                waterNeeded = 250;
                milkNeeded = 0;
                beansNeeded = 16;
                cost = 4;
                break;
            case "2":
                // Latte
                waterNeeded = 350;
                milkNeeded = 75;
                beansNeeded = 20;
                cost = 7;
                break;
            case "3":
                // Cappuccino
                waterNeeded = 200;
                milkNeeded = 100;
                beansNeeded = 12;
                cost = 6;
                break;
            case "back":
                return "";
            default:
                System.out.println("Invalid order");
        }

        if (this.waterAmt < waterNeeded) {
            return "Sorry, not enough water!\n";
        }
        if (this.milkAmt < milkNeeded) {
            return "Sorry, not enough milk!n";
        }
        if (this.beansAmt < beansNeeded) {
            return "Sorry, not enough beans!\n";
        }

        this.waterAmt -= waterNeeded;
        this.milkAmt -= milkNeeded;
        this.beansAmt -= beansNeeded;
        this.cupsAmt -= 1;
        this.moneyAmt += cost;
        return "I have enough resources, making you a coffee!\n";

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
