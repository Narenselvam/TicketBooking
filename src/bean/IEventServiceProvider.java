package bean;

import service.Event;
import service.Venue;

public interface IEventServiceProvider {
    void get_event_details(String eventName);


   int getAvailableNoOfTickets(String eventName);

    Event create_event(String event_name, String date, String time, int total_seats, double ticket_price, String event_type, Venue venue,String... eventDetails);

    String[] get_Event_Details(String eventNane);
}
