package dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketBookingSystem extends BookingSystem {
    private List<Event> events = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    @Override
    public Event create_event(String event_name, String date, String time, int total_seats,
                              double ticket_price, String event_type, String venue_name, String... eventDetails) {
        LocalDate eventDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
        Event event;

        switch (event_type.toLowerCase()) {
            case "movie":
                if (eventDetails.length < 3) {
                    throw new IllegalArgumentException("Movie requires 3 additional details: genre, Actor, and actress");
                }
                event = new Movie(event_name, eventDate, eventTime, venue_name, total_seats, ticket_price, eventDetails[0], eventDetails[1], eventDetails[2]);
                break;
            case "sports":
                if (eventDetails.length < 2) {
                    throw new IllegalArgumentException("Insufficient details for sports event");
                }
                event = new Sports(event_name, eventDate, eventTime, venue_name, total_seats, ticket_price, eventDetails[0], eventDetails[1]);
                break;
            case "concert":
                if (eventDetails.length < 2) {
                    throw new IllegalArgumentException("Insufficient details for concert event");
                }
                event = new Concert(event_name, eventDate, eventTime, venue_name, total_seats, ticket_price, eventDetails[0], eventDetails[1]);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + event_type.toLowerCase());
        }
        events.add(event);
        return event;
    }

    public Event findEventByName(String eventName) {
        for (Event event : events) {
            if (event.getEventName().equalsIgnoreCase(eventName)) {
                return event;
            }
        }
        return null;
    }

    @Override
    public int get_available_seats(String eventName) {

        Event event = findEventByName(eventName);
        if (event != null) {
            return event.getAvailableSeats();
        } else {
            System.out.println("Event not found.");
        }
        return 0;
    }

    @Override
    public double calculate_booking_cost(String eventName, int num_tickets) {
        Event event =findEventByName(eventName);

        return event.getTicketPrice() * num_tickets;
    }

    @Override
    public void book_tickets(String eventName, int num_tickets) {
        Event event =findEventByName(eventName);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        if (event.getAvailableSeats() < num_tickets) {
            System.out.println("Not enough seats available.");
            return;
        }


        List<Customer> customers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < num_tickets; i++) {
            System.out.println("Enter details for customer " + (i + 1) + ":");
            System.out.print("Name: ");
            String customerName = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Phone number: ");
            int phoneNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            customers.add(new Customer(customerName, email, phoneNumber));
        }
        double totalCost = calculate_booking_cost(eventName, num_tickets);
        Booking booking = new Booking(customers, event, num_tickets, totalCost);
        event.setAvailableSeats(event.getAvailableSeats() - num_tickets);

        bookings.add(booking);
        System.out.println("Booking successful. Booking ID: " + booking.getBookingId());



    }

    @Override
    public void get_event_details(String eventName) {

        Event event = findEventByName(eventName);
        event.display_event_details();
    }

    @Override
    public void cancel_tickets(int booking_id, int num_tickets) {
        Booking cancelbooking =null;
        for (Booking booked : bookings){
            if (booked.getBookingId() == booking_id){
                cancelbooking=booked;
                break;
            }
        }

        if (cancelbooking == null) {
            System.out.println("Booking not found with ID: " + booking_id);
            return;
        }

        Event event = cancelbooking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + num_tickets);

        if (cancelbooking.getNumTickets() == num_tickets) {
            // Cancel the entire booking
            bookings.remove(cancelbooking);
            System.out.println("Booking " + booking_id + " has been fully cancelled.");
        } else {
            // Partially cancel the booking
            cancelbooking.setNumTickets(cancelbooking.getNumTickets() - num_tickets);
            double refundAmount = event.getTicketPrice() * num_tickets;
            cancelbooking.setTotalCost(cancelbooking.getTotalCost() - refundAmount);
            System.out.println(num_tickets + " tickets cancelled from booking " + booking_id + ". Refund amount: $" + refundAmount);
        }

        System.out.println("Cancellation successful. Updated available seats for event '" + event.getEventName() + "': " + event.getAvailableSeats());
    }



    public static void main(String[] args) {

         TicketBookingSystem bookingSystem = new TicketBookingSystem();
        bookingSystem.create_event("Movie Night","2024-06-10","19:30",100,15.00,
                "movie","Cinema Hall","Action","Thriller","John Doe");
        bookingSystem.create_event("Rock Concert","2024-09-20","19:00",200,120,
                "concert","Main Stage Arena","coldplay","pop");
        bookingSystem.create_event("Boxing Match","2024-09-27","21:00",250,200,
                "sports","Auditorium","Alex Pereira","brazil vs US");

        while (true){
            Scanner sc = new Scanner(System.in);

            System.out.println("\n--- Ticket Booking System ---");
            System.out.println("1. Create Event");
            System.out.println("2. Get Available Seats");
            System.out.println("3. Book Tickets");
            System.out.println("4. Cancel Tickets");
            System.out.println("5. Show Event Details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:{
                    System.out.println("\n--- Create Event ---");
                    System.out.println("Enter Event Name :");
                    String eventName= sc.nextLine();

                    System.out.print("Enter Date: ");
                    String dateInput = sc.nextLine();

                    System.out.print("Enter Time: ");
                    String timeInput = sc.nextLine();

                    System.out.print("Enter Total Seats: ");
                    int totalSeats = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Ticket Price: ");
                    double ticketPrice = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter Event Type: ");
                    String eventType = sc.nextLine();

                    System.out.print("Enter Venue Name: ");
                    String venueName = sc.nextLine();

                    String eventDetails="";
                    switch (eventType.toLowerCase()) {

                        case "movie":
                            System.out.print("Enter Movie Genre: ");
                            String movieGenre = sc.nextLine();
                            System.out.print("Enter Actor Name: ");
                            String actorName = sc.nextLine();
                            System.out.print("Enter Actress Name: ");
                            String actressName = sc.nextLine();
                            eventDetails += ", Genre: " + movieGenre + ", Actor: " + actorName + ", Actress: " + actressName;
                            break;
                        case "sport":
                            System.out.print("Enter Sport Name: ");
                            String sportName = sc.nextLine();
                            System.out.print("Enter Team Name: ");
                            String teamName = sc.nextLine();
                            eventDetails += ", Sport: " + sportName + ", Team: " + teamName;
                            break;
                        case "concert":
                            System.out.print("Enter Artist Name: ");
                            String artistName = sc.nextLine();
                            System.out.print("Enter Event Type: ");
                            String concertType = sc.nextLine();
                            eventDetails += ", Artist: " + artistName + ", Type: " + concertType;
                            break;
                        default:
                            eventDetails += ", Additional Details: " + eventDetails;
                    }


                    bookingSystem.create_event(eventName, dateInput, timeInput,
                            totalSeats, ticketPrice, eventType, venueName,eventDetails);

                }
                case 2: {

                    System.out.println("Enter event Name : ");
                    String eventName = sc.nextLine();
//                sc.nextLine();
                    System.out.println("Available Tickets for the Event" +bookingSystem.get_available_seats(eventName));
                    break;
                }
                case 3:{
                    System.out.print("Enter event name: ");
                    String eventName = sc.nextLine();
                    System.out.print("Enter number of tickets: ");
                    int numTicket = sc.nextInt();

                    bookingSystem.book_tickets(eventName,numTicket);
                    break;
                }
                case 4:{
                    System.out.print("Enter Booking ID : ");
                    int bookingId = sc.nextInt();
                    System.out.print("Enter number of tickets: ");
                    int numTicket = sc.nextInt();

                    bookingSystem.cancel_tickets(bookingId,numTicket);
                    break;
                }
                case 5:{
                    System.out.println("Enter Event Name :");
                    String eventName = sc.nextLine();
                    bookingSystem.get_event_details(eventName);
                }
                case 6: {
                    System.out.println("Thank you for using the ticket booking system.");
                    System.exit(0);
                }
            }
        }}
}