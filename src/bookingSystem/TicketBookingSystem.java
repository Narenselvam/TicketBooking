package bookingSystem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class TicketBookingSystem {

    private List<Event> events;

    public TicketBookingSystem(){
        this.events = new ArrayList<>();
    }
    public Event create_event(String event_name,String date,String time, int total_seats,
                              double ticket_price,String event_type,String venue_name,String... eventDetails){

        LocalDate eventDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
        Event event;

        switch (event_type.toLowerCase()){
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
                event = new Sports(event_name, eventDate, eventTime, venue_name, total_seats, ticket_price,eventDetails[0], eventDetails[1]);
                break;
            case "concert":
                if (eventDetails.length < 2) {
                    throw new IllegalArgumentException("Insufficient details for sports event");
                }
                event = new Concert(event_name, eventDate, eventTime, venue_name, total_seats, ticket_price,eventDetails[0], eventDetails[1]);
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
    public void display_event_details(String eventName) {
        Event event = findEventByName(eventName);
        if (event != null) {
            event.display_event_details();
        } else {
            System.out.println("Event not found.");
        }
    }


    public void book_tickets(String eventName, int num_tickets){
        Event event = findEventByName(eventName);
        if (event != null){
       Booking book = new Booking(event);
       book.book_tickets(num_tickets);
    }else {
        System.out.println("sorry Event sold out !!,no tickets Available");
    }}


    public void cancel_tickets(String eventName, int num_tickets){
        Event event = findEventByName(eventName);
        if (event != null){
            Booking book = new Booking(event);
            book.cancel_booking(num_tickets);
        }
        else{
            System.out.println("Sorry Cannot Cancel Tickets");
        }

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
        System.out.println("1. Display Event");
        System.out.println("2. Book Tickets");
        System.out.println("3. Cancel Tickets");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1: {
                System.out.println("Enter event Name : ");
                String eventName = sc.nextLine();
//                sc.nextLine();
                bookingSystem.display_event_details(eventName);
                break;
            }
            case 2:{
                System.out.print("Enter event name: ");
                String eventName = sc.nextLine();
                System.out.print("Enter number of tickets: ");
                int numTicket = sc.nextInt();

                bookingSystem.book_tickets(eventName,numTicket);
                break;
            }
            case 3:{
                System.out.print("Enter event name: ");
                String eventName = sc.nextLine();
                System.out.print("Enter number of tickets: ");
                int numTicket = sc.nextInt();

                bookingSystem.cancel_tickets(eventName,numTicket);
                break;
            }

            case 4: {
                System.out.println("Thank you for using the ticket booking system.");
                System.exit(0);
        }
        }
    }}

}
