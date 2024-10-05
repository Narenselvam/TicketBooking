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
    public Event create_event(String event_name,String date,String time,String venue_name,
                              int total_seats,String event_type,double ticket_price){

        LocalDate eventDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
        Event event = null;

        switch (event_type){
            case "movie":
                event = new Movie(event_name, eventDate, eventTime, venue_name, total_seats, ticket_price, "Unknown", "Unknown", "Unknown");
               break;
            case "sports":
                event = new Sports(event_name, eventDate, eventTime, venue_name, total_seats, ticket_price,"unknown","unknown");
                break;
            case "concert":
                event = new Concert(event_name, eventDate, eventTime, venue_name, total_seats, ticket_price,"unknown","unknown");
                break;
        }

        events.add(event);
        return event;
    }


    public void  display_event_details(String eventName) {
        events.get(events.indexOf(eventName)).display_event_details();
    }

    public double book_tickets(String eventName, int num_tickets){
        Event event = events.get(events.indexOf(eventName));
        if (event.getAvailableSeats() >= num_tickets){
            event.book_tickets(num_tickets);
            return event.getTicketPrice() * num_tickets;
        }
        else{
            System.out.println("sorry Event sold out !!,no tickets Available");
            return 0.0;
        }
    }


    public void cancel_tickets(String eventName, int num_tickets){
        Event event = events.get(events.indexOf(eventName));
      event.cancel_booking(num_tickets);
    }


    public static void main(String[] args) {

        TicketBookingSystem bookingSystem = new TicketBookingSystem();


        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- Ticket Booking System ---");
        System.out.println("1. Display Event");
        System.out.println("2. Book Tickets");
        System.out.println("3. Cancel Tickets");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
//        sc.nextLine();

        switch (choice) {

            case 1: {
                System.out.println("Enter event Name : ");
                String eventName = sc.nextLine();
                bookingSystem.display_event_details(eventName);
            }
            case 2:{
                System.out.print("Enter event name: ");
                String eventName = sc.nextLine();
                System.out.print("Enter number of tickets: ");
                int numTicket = sc.nextInt();

                bookingSystem.book_tickets(eventName,numTicket);
            }
            case 3:{
                System.out.print("Enter event name: ");
                String eventName = sc.nextLine();
                System.out.print("Enter number of tickets: ");
                int numTicket = sc.nextInt();

                bookingSystem.cancel_tickets(eventName,numTicket);

            }

            case 4: {
                System.out.println("Thank you for using the ticket booking system.");
                System.exit(0);
        }
        }
    }

}
