package bookingSystem;
import java.util.InputMismatchException;
import java.util.Scanner;

public class bookingCalculator {

    private static int getTotalCost(int tier, int noTickets) {
        int ticketPrice;
        if (tier == 1) {
            ticketPrice = 100;
        } else if (tier == 2) {
            ticketPrice = 200;
        } else if (tier == 3) {
            ticketPrice = 300;
        } else {
            throw new IllegalArgumentException("Invalid tier");
        }
        return ticketPrice * noTickets;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("SELECT TICKET OPTIONS");
            System.out.println("1 --> Silver = ₹100");
            System.out.println("2 -->   Gold = ₹200");
            System.out.println("3 --> Dimond = ₹300");
            System.out.println("4 --> Exit");

            try {
                int choice = sc.nextInt();
                if (choice == 4) {
                    break;
                }
                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
                    continue;
                }

                System.out.println("Enter no. of Tickets ");
                int noTickets = sc.nextInt();

                int totalCost = getTotalCost(choice, noTickets);
                System.out.println("Total cost is " + totalCost);
                System.out.printf("Number of Tickets: %d\n", noTickets);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Clear the invalid input
            }

        }
        System.out.println("Thank you for using our booking system!");
        sc.close();
    }

}


