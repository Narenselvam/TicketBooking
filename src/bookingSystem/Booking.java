package bookingSystem;

public class Booking {

    private Event event;
    private int num_tickets;
    private double totalcost;

    public Booking(){

    }

    // Constructor with Event
    public Booking(Event event){
        this.event = event;
        this.num_tickets = 0;
        this.totalcost = 0;

    }


    //Methods
    public double calculate_booking_cost(int num_tickets){
        this.totalcost = num_tickets * event.getTicketPrice();
        return this.totalcost;
    }


    public void book_tickets(int num_tickets){
        this.num_tickets += num_tickets;
        event.book_tickets(num_tickets);
        double v = calculate_booking_cost(num_tickets);
        System.out.println("Tickets Booked !!");
        System.out.println("Total cost: " + v);
    }


    public void cancel_booking(int num_tickets){
        this.num_tickets -= num_tickets;
        event.cancel_booking(num_tickets);
        this.totalcost -= num_tickets * event.getTicketPrice();
        System.out.println("Tickets Cancelled !!");
        System.out.println("Total cost: " + this.totalcost);
    }


    //Getter and Setters
    public  int getAvailableNoOfTickets(){
        return event.getAvailableSeats();
    }

    public void getEventDetails(){
        event.display_event_details();
    }

    public int getNumTickets() {
        return num_tickets;
    }


    public double getTotalCost(){
        return totalcost;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
