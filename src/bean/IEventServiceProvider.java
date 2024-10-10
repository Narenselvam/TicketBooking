package bean;

import service.Event;
import service.Venue;

import java.sql.SQLException;

public interface IEventServiceProvider {

   int getAvailableNoOfTickets(String eventName);

    Event create_event(String event_name, String date, String time, int total_seats, double ticket_price, String event_type, Venue venue,String... eventDetails) throws SQLException;

    String[] get_Event_Details(String eventNane);
}
