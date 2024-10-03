package bookingSystem;
import java.util.Scanner;

public class bookingCalculator {
    
    public static int getTotalCost(int tier, int noTickets) {
        int totalCost = 0;
        int availableTickets = 20;
        availableTickets = availableTickets - noTickets;
        if (availableTickets >= 0) {
            switch (tier) {
                case 1:
                    totalCost = 100 * noTickets;
                    break;
                case 2:
                    totalCost = 200 * noTickets;
                    break;
                case 3:
                    totalCost = 300 * noTickets;
                    break;
            }
        } else {
            System.out.println("No Tickets Available");
        }
        return totalCost;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            System.out.println("SELECT TICKET OPTIONS");
            System.out.println("1 --> Silver = ₹100");
            System.out.println("2 -->   Gold = ₹200");
            System.out.println("3 --> Dimond = ₹300");
            System.out.println("Type 'Exit' to quit");

            input = sc.next();

            if (!input.equalsIgnoreCase("Exit")) {
                try {
                    int tier = Integer.parseInt(input);
                    System.out.println("Enter no. of Tickets ");
                    int noTickets = sc.nextInt();
                    
                    int totalCost = getTotalCost(tier, noTickets);
                    System.out.println("Total cost is " + totalCost);
                    System.out.printf("Number of Tickets: %d\n", noTickets);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number or 'Exit'.");
                }
            }
            
            System.out.println(); // Add a blank line for readability
        } while (!input.equalsIgnoreCase("Exit"));

        System.out.println("Thank you for using our booking system!");
        sc.close();
    }
}



