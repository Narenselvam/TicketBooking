package dao;
import entity.*;

public interface IEventServiceProvider {
    void get_event_details(String eventName);


   int getAvailableNoOfTickets();

    Event create_event(String event_name, String date, String time, int total_seats, double ticket_price, String event_type, Venue venue);

    String[] getEventDetails();
}
