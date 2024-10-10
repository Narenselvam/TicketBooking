package bookingSystem;

import bean.BookingSystemServiceProviderImpl;
import service.Customer;
import service.Event;
import service.Venue;

import java.util.Arrays;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        BookingSystemServiceProviderImpl bookingSystem = new BookingSystemServiceProviderImpl();
        Scanner scanner = new Scanner(System.in);
        Venue Mvenue = new Venue("Cinema Hall", "NYC");
        bookingSystem.create_event("Movie Night", "2024-06-10", "19:30", 100, 15.00,
                "movie", Mvenue, "Action", "Thriller", "John Doe");
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
                    getEventDetails(bookingSystem, scanner);
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
        System.out.println("Enter venue Address");
        String venueAddress = scanner.nextLine();

        String[] eventDetails;
        switch (eventType.toLowerCase()) {
            case "movie":
                System.out.print("Enter Genre: ");
                String movieGenre = scanner.nextLine().trim();
                System.out.print("Enter Actor: ");
                String actor = scanner.nextLine().trim();
                System.out.print("Enter Actress: ");
                String actress = scanner.nextLine().trim();
                eventDetails = new String[]{movieGenre, actor, actress};
                break;
            case "sports":
                System.out.print("Enter Sport: ");
                String sport = scanner.nextLine().trim();
                System.out.print("Enter Team: ");
                String team = scanner.nextLine().trim();
                eventDetails = new String[]{sport, team};
                break;
            case "concert":
                System.out.print("Enter Concert Type: ");
                String concertType = scanner.nextLine().trim();
                System.out.print("Enter Artist Name: ");
                String artist = scanner.nextLine().trim();
                eventDetails = new String[]{concertType, artist};
                break;
            default:
                throw new IllegalArgumentException("Invalid event type");
        }


        Venue venue = new Venue(venueName, venueAddress);
        Event event = bookingSystem.create_event(eventName, date, time, totalSeats, ticketPrice, eventType, venue, eventDetails);
        bookingSystem.addEvent(event);
        System.out.println("Event created successfully.");
    }

    private static void getEventDetails(BookingSystemServiceProviderImpl bookingSystem, Scanner scanner) {
        System.out.println("Enter Event Name: ");
        String eventName = scanner.nextLine();
        String[] res = bookingSystem.get_Event_Details(eventName);
        for (String str : res) {
            System.out.println(str);
        }
    }


    private static void getAvailableSeats(BookingSystemServiceProviderImpl bookingSystem, Scanner scanner) {
        System.out.print("Enter Event Name: ");
        String eventName = scanner.nextLine();
        System.out.println("Available Seats: " + bookingSystem.getAvailableNoOfTickets(eventName));
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

    private static void cancelBooking(BookingSystemServiceProviderImpl bookingSystem, Scanner scanner) {
        System.out.println("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        bookingSystem.cancel_booking(bookingId);


    }

    private static void getBookingDetails(BookingSystemServiceProviderImpl bookingSystem, Scanner scanner) {
        System.out.println("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        bookingSystem.get_booking_details(bookingId);

    }

}
