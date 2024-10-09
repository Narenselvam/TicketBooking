package dao;

import entity.Event;
import entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventServiceProviderImpl implements IEventServiceProvider {
    public Event currentEvent;
    public List<Event> events = new ArrayList<>();
    @Override
    public Event create_event(String event_name, String date, String time, int total_seats, double ticket_price, String event_type, Venue venue) {
        LocalDate eventDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
        Event event;

        switch (event_type.toLowerCase()) {
            case "movie":
                event = new Movie(event_name, eventDate, eventTime, venue, total_seats, ticket_price, "Not specified", "Not specified", "Not specified");
                break;
            case "sports":
                event = new Sports(event_name, eventDate, eventTime, venue, total_seats, ticket_price, "Not specified", "Not specified");
                break;
            case "concert":
                event = new Concert(event_name, eventDate, eventTime, venue, total_seats, ticket_price, "Not specified", "Not specified");
                break;
            default:
                throw new IllegalArgumentException("Invalid event type");
        }
        events.add(event);
        return event;
    }

    @Override
    public String[] getEventDetails() {
        return new String[0];
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
    public int getAvailableNoOfTickets() {
        if (currentEvent != null) {
            return currentEvent.getAvailableSeats();
        }
        return 0;
    }
}