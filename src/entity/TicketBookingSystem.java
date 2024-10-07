package entity;
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
        System.out.println("1. Create Event");
        System.out.println("2. Display Event");
        System.out.println("3. Book Tickets");
        System.out.println("4. Cancel Tickets");
        System.out.println("5. Exit");
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
                bookingSystem.display_event_details(eventName);
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
                System.out.print("Enter event name: ");
                String eventName = sc.nextLine();
                System.out.print("Enter number of tickets: ");
                int numTicket = sc.nextInt();

                bookingSystem.cancel_tickets(eventName,numTicket);
                break;
            }

            case 5: {
                System.out.println("Thank you for using the ticket booking system.");
                System.exit(0);
        }
        }
    }}

}
