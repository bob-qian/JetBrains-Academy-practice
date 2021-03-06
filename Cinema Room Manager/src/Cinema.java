import java.util.ArrayList;
import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // Cinema initialization
        System.out.println("Enter the number of rows:");
        int inputRows = scnr.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int inputSeats = scnr.nextInt();
        System.out.print("\n");

        Cinema cinema = new Cinema(inputRows, inputSeats);

        // Program menu
        int ticketRow;
        int ticketSeat;
        int inputMenu;
        boolean ticketPurchase;
        do {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            inputMenu = scnr.nextInt();
            System.out.print("\n");

            if (inputMenu == 1) {
                cinema.printDiagram();
                System.out.print("\n");
            } else if (inputMenu == 2) {
                do {
                    System.out.println("Enter a row number:");
                    ticketRow = scnr.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    ticketSeat = scnr.nextInt();
                    System.out.print("\n");

                    ticketPurchase = cinema.addGuest(ticketRow,ticketSeat);
                    System.out.print("\n");
                } while (!ticketPurchase);
            } else if (inputMenu == 3) {
                System.out.println("Number of purchased tickets: " + cinema.getTicketsSold());
                double percentage = (100*(double)cinema.getTicketsSold()/((double)inputRows*inputSeats));
                System.out.printf("Percentage: %.2f%%\n", percentage);
                System.out.println("Current income: $" + cinema.getCurrentIncome());
                System.out.println("Total income: $" + cinema.calcTotalIncome());
                System.out.print("\n");
            }
        } while (inputMenu != 0);
    }

    private final int myRows;
    private final int mySeatsPerRow;
    private ArrayList<ArrayList<String>> diagram;
    private int ticketsSold;
    private int currentIncome;

    public int getCurrentIncome() {
        return currentIncome;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public Cinema(int cinemaRows, int cinemaSeatsPerRow) {
        myRows = cinemaRows;
        mySeatsPerRow = cinemaSeatsPerRow;
        diagram = new ArrayList<ArrayList<String>>();
        ticketsSold = 0;
        currentIncome = 0;

        // Populate all rows with seats
        for (int i = 0; i < myRows; i++) {
            diagram.add(new ArrayList<>());
            for (int j = 0; j < mySeatsPerRow; j++) {
                diagram.get(i).add("S");
            }
        }
    }

    public void printDiagram() {
        System.out.println("Cinema: ");

        // Display seat numbers
        System.out.print(" ");
        for (int i = 0; i < mySeatsPerRow; i++) {
            System.out.print(" " + (i+1));
        }
        System.out.print("\n");

        for (int i = 0; i < myRows; i++) {
            // Display row numbers
            System.out.print((i+1));

            for (int j = 0; j < mySeatsPerRow; j++) {
                System.out.print(" ");
                System.out.print(diagram.get(i).get(j));
            }
            System.out.print("\n");
        }
    }

    public boolean addGuest(int guestRowNumber, int guestSeatNumber) {
        if (guestRowNumber > this.myRows || guestSeatNumber > this.mySeatsPerRow) {
            System.out.println("Wrong input!");
            return false;
        }

        if (diagram.get(guestRowNumber - 1).get(guestSeatNumber - 1).equals("B")) {
            System.out.println("That ticket has already been purchased!");
            return false;
        }

        diagram.get(guestRowNumber-1).set(guestSeatNumber-1, "B");
        ticketsSold += 1;
        currentIncome += calcTicketPrice(guestRowNumber);
        System.out.println("Ticket price: $" + this.calcTicketPrice(guestRowNumber));
        return true;
    }

    public int calcTotalIncome() {
        int totalIncome = 0;

        if (myRows * mySeatsPerRow <= 60) {
            totalIncome = myRows * mySeatsPerRow * 10;
        } else {
            totalIncome = (myRows / 2) * mySeatsPerRow * 10 + (myRows - (myRows / 2)) * mySeatsPerRow * 8;
        }
        return(totalIncome);
    }

    public int calcTicketPrice(int rowNumber) {
        if (myRows * mySeatsPerRow <= 60) {
            return 10;
        }
        if (rowNumber > (myRows / 2)) {
            // back half
            return 8;
        }
        return 10;
    }
}
