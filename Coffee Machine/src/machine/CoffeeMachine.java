package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private static Scanner scnr = new Scanner(System.in);

    public static void main(String[] args) {


        // Initialize a new Coffee Machine
        CoffeeMachine coffee = new CoffeeMachine(400, 540, 120, 9, 550);
        System.out.println(coffee);

        System.out.println("Write action (buy, fill, take)");
        String action = scnr.nextLine();

        switch (action) {
            case "buy":
                coffee.buy();
                break;
            case "fill":
                coffee.fillMachine();
                break;
            case "take":
                coffee.takeMachine();
                break;
            default:
                System.out.println("Undefined input");
        }


        //System.out.println(coffee.makeCups(cupsCoffee));


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
        display.append(this.waterAmt);
        display.append(" of water\n");
        display.append(this.milkAmt);
        display.append(" of milk\n");
        display.append(this.beansAmt);
        display.append(" of coffee beans\n");
        display.append(this.cupsAmt);
        display.append(" of disposable cups\n");
        display.append(this.moneyAmt);
        display.append(" of money\n");
        return display.toString();
    }

    public void fillMachine() {
        System.out.println("Write how many ml of water do you want to add:");
        int inputWater = scnr.nextInt();
        this.addWaterAmt(inputWater);

        System.out.println("Write how many ml of milk do you want to add:");
        int inputMilk = scnr.nextInt();
        this.addMilkAmt(inputMilk);

        System.out.println("Write how many grams of coffee beans do you want to add:");
        int inputBeans = scnr.nextInt();
        this.addBeansAmt(inputBeans);

        System.out.println("Write how many disposable cups of coffee do you want to add:");
        int inputCups = scnr.nextInt();
        this.addCupsAmt(inputCups);

        System.out.println();
        System.out.println(this);
    }

    public void takeMachine() {
        System.out.printf("I gave you $%d\n", this.moneyAmt);
        this.moneyAmt = 0;

        System.out.println();
        System.out.println(this);
    }

    public void buy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        int coffeeType = scnr.nextInt();

        int waterNeeded = 0;
        int milkNeeded = 0;
        int beansNeeded = 0;
        int cost = 0;

        switch (coffeeType) {
            case 1:
                // Espresso
                waterNeeded = 250;
                milkNeeded = 0;
                beansNeeded = 16;
                cost = 4;
                break;
            case 2:
                // Latte
                waterNeeded = 350;
                milkNeeded = 75;
                beansNeeded = 20;
                cost = 7;
                break;
            case 3:
                // Cappuccino
                waterNeeded = 200;
                milkNeeded = 100;
                beansNeeded = 12;
                cost = 6;
                break;
            default:
                System.out.println("Invalid order");
        }

        this.waterAmt -= waterNeeded;
        this.milkAmt -= milkNeeded;
        this.beansAmt -= beansNeeded;
        this.cupsAmt -= 1;
        this.moneyAmt += cost;

        System.out.println();
        System.out.println(this);
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
