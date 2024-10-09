import java.util.Scanner;
import dao.*;
import entity.*;

public class TicketBookingSystem {
    public static void main(String[] args) {
        BookingSystemServiceProviderImpl bookingSystem = new BookingSystemServiceProviderImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Ticket Booking System ---");
            System.out.println("1. Create Event");
            System.out.println("2. Get Available Seats");
            System.out.println("3. Book Tickets");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Get Event Details");
            System.out.println("6. Get Booking Details");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createEvent(bookingSystem, scanner);
                    break;
                case 2:
                    getAvailableSeats(bookingSystem, scanner);
                    break;
                case 3:
                    bookTickets(bookingSystem, scanner);
                    break;
                case 4:
                    cancelBooking(bookingSystem, scanner);
                    break;
                case 5:
                    getEventDetails(bookingSystem);
                    break;
                case 6:
                    getBookingDetails(bookingSystem, scanner);
                    break;
                case 7:
                    System.out.println("Thank you for using the ticket booking system.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createEvent(BookingSystemServiceProviderImpl bookingSystem, Scanner scanner) {
        System.out.print("Enter Event Name: ");
        String eventName = scanner.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Time (HH:MM): ");
        String time = scanner.nextLine();
        System.out.print("Enter Total Seats: ");
        int totalSeats = scanner.nextInt();
        System.out.print("Enter Ticket Price: ");
        double ticketPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Event Type (movie/sports/concert): ");
        String eventType = scanner.nextLine();
        System.out.print("Enter Venue Name: ");
        String venueName = scanner.nextLine();

        Venue venue = new Venue();
        venue.setVenue_name(venueName);
        Event event = bookingSystem.create_event(eventName, date, time, totalSeats, ticketPrice, eventType, venue);
        bookingSystem.addEvent(event);
        System.out.println("Event created successfully.");
    }

    private static void getAvailableSeats(BookingSystemServiceProviderImpl bookingSystem, Scanner scanner) {
        System.out.print("Enter Event Name: ");
        String eventName = scanner.nextLine();
        System.out.println("Available Seats: " + bookingSystem.getAvailableNoOfTickets());
    }

    private static void bookTickets(BookingSystemServiceProviderImpl bookingSystem, Scanner scanner) {
        System.out.print("Enter Event Name: ");
        String eventName = scanner.nextLine();
        System.out.print("Enter number of tickets: ");
        int numTickets = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Customer[] customers = new Customer[numTickets];
        for (int i = 0; i < numTickets; i++) {
            System.out.println("Enter details for customer " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Phone number: ");
            int phoneNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            customers[i] = new Customer(name, email, phoneNumber);
        }

        bookingSystem.book_tickets(eventName, numTickets, customers);
    }

    private static void cancelBooking(BookingSystemServiceProviderImpl bookingSystem, Scanner scanner){
    }
}