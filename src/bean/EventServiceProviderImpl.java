package bean;

import service.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventServiceProviderImpl implements IEventServiceProvider {
    public Event currentEvent;
    public List<Event> events = new ArrayList<>();
    @Override
    public Event create_event(String event_name, String date, String time, int total_seats, double ticket_price, String event_type, Venue venue,String... eventDetails) {
        LocalDate eventDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
        Event event;

        switch (event_type.toLowerCase()) {
            case "movie":
                if (eventDetails.length < 3) {
                    throw new IllegalArgumentException("Movie requires 3 additional details: genre, Actor, and actress");
                }
                event = new Movie(event_name, eventDate, eventTime, venue, total_seats, ticket_price, eventDetails[0], eventDetails[1], eventDetails[2]);
                break;
            case "sports":
                if (eventDetails.length < 2) {
                    throw new IllegalArgumentException("Insufficient details for sports event");
                }
                event = new Sports(event_name, eventDate, eventTime, venue, total_seats, ticket_price, eventDetails[0], eventDetails[1]);
                break;
            case "concert":
                if (eventDetails.length < 2) {
                    throw new IllegalArgumentException("Insufficient details for concert event");
                }
                event = new Concert(event_name, eventDate, eventTime, venue, total_seats, ticket_price,  eventDetails[0], eventDetails[1]);
                break;
            default:
                throw new IllegalArgumentException("Invalid event type");
        }
        events.add(event);
        return event;
    }

    @Override
    public String[] get_Event_Details(String eventName) {
        Event event = findEventByName(eventName);
        Venue venue = event.getVenue();
        if (events == null || events.isEmpty()) {
            return new String[0];
        }
        String[] eventDetails = new String[]{
                "Name: " + event.getEventName(),
                "Date: " + event.getEventDate(),
                "Time: " + event.getEventTime(),
                "Event Type: " + event.getEventType(),
                "Available Seats: " + event.getAvailableSeats(),
                "Ticket Price: ₹" + event.getTicketPrice(),
                "Venue Name: " + venue.getVenue_name()
        };
        return eventDetails;

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
    public void get_event_details(String eventName) {

        Event event = findEventByName(eventName);
        event.display_event_details();
    }


    @Override
    public int getAvailableNoOfTickets(String eventName) {
        Event event = findEventByName(eventName);
        if (event != null) {
            return event.getAvailableSeats();
        }
        return 0;
    }
}