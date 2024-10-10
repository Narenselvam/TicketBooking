package service;


public abstract class BookingSystem {

    public abstract Event create_event(String event_name, String date, String time, int total_seats,
                                       double ticket_price, String event_type, Venue venue_name, String... eventDetails);

    public abstract int get_available_seats(String eventName);
    public abstract double calculate_booking_cost(String eventName,int num_tickets);
    public abstract void book_tickets(String eventName, int num_tickets);
    public abstract void get_event_details(String eventName);
    public abstract void cancel_tickets(int booking_id, int num_tickets);
}