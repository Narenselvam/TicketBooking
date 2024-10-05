package bookingSystem;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

	private String event_name;
	private LocalDate event_date;
	private LocalTime event_time; 
	private String	venue_name;
	private int total_seats;
	private int available_seats;
	private double ticket_price;
	private event_Type eventType;
	
	
	enum event_Type{
		movie,sports, Concert
    }
	
	
	public Event(){
		
	}
	
	public Event(String event_name,LocalDate event_date,LocalTime event_time,
			String	venue_name,int total_seats,int available_seats,double ticket_price,event_Type eventType){
		
		
		this.event_name = event_name;
        this.event_date = event_date;
        this.event_time = event_time;
        this.venue_name = venue_name;
        this.total_seats = total_seats;
        this.available_seats = available_seats; // Initially, all seats are available
        this.ticket_price = ticket_price;
        this.eventType = eventType;

		
	}
	
	

    // Getters and setters
    public String getEventName() {
        return event_name;
    }

    public void setEventName(String eventName) {
        this.event_name = eventName;
    }

    public LocalDate getEventDate() {
        return event_date;
    }

    public void setEventDate(LocalDate eventDate) {
        this.event_date = eventDate;
    }

    public LocalTime getEventTime() {
        return event_time;
    }
	
	
    public void setEventTime(LocalTime eventTime) {
        this.event_time = eventTime;
    }

    public String getVenueName() {
        return venue_name;
    }

    public void setVenueName(String venueName) {
        this.venue_name = venueName;
    }

    public int getTotalSeats() {
        return total_seats;
    }

    public void setTotalSeats(int totalSeats) {
        this.total_seats = totalSeats;
    }

    public int getAvailableSeats() {
    return available_seats;
    }
    
    
    public void setAvailableSeats(int availableSeats) {
        this.available_seats = availableSeats;
    }
    
    
    public double getTicketPrice() {
    	return ticket_price;
    }
    
    public void setTicketPrice(double ticketPrice) {
        this.ticket_price = ticketPrice;
    }
    
    
    
    public event_Type getEventType() {
        return eventType;
    }

    public void setEventType(event_Type eventType) {
        this.eventType = eventType;
    }
    
    //Methods
    
    public double calculate_total_revenue(){
    	int soldTickets = total_seats - available_seats;
		double totalRevenue = soldTickets * ticket_price ;
		return totalRevenue;
    	
    }
    
    public int getBookedNoOfTickets() {
    	return total_seats - available_seats;
    }
    
    public boolean book_tickets(int num_tickets) {
    	if (num_tickets<total_seats){
        available_seats-=num_tickets;
    	return true;}
        return false;
    }
    
    public boolean cancel_booking(int num_tickets) {
    	if (num_tickets <= (total_seats - available_seats)) {
    		available_seats += num_tickets;
    		return true;
    	}
    	return false;
    	
    }
    
    public void display_event_details() {
    	System.out.println("Event Details");
    	System.out.println("Name: " + this.event_name);
    	System.out.println("Date: " + this.event_date);
    	System.out.println("Time: " + this.event_time);
    	System.out.println("Venue " + this.venue_name);
    	System.out.println("Event Type: " + this.eventType);
    	System.out.println("AvailableSeats: " + this.available_seats);
    	System.out.println("Ticket Price: ₹" + this.ticket_price);
    }
    
    
    
    
}
