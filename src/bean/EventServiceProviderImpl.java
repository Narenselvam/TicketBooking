package bean;

import service.*;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventServiceProviderImpl implements IEventServiceProvider {
    public Event currentEvent;
    public List<Event> events = new ArrayList<>();

    public Connection getConnection() throws SQLException {
        return DBUtil.getDBConn();
    }

    @Override
    public Event create_event(String event_name, String date, String time, int total_seats, double ticket_price, String event_type, Venue venue, String... eventDetails) throws SQLException {

        try {
            Connection conn = getConnection(); // Assuming you have a method to get the DB connection

            // Step 1: Insert the Venue and retrieve its generated venue_id
            String insertVenueSQL = "INSERT INTO venue (venue_name, address) VALUES (?, ?)";
            PreparedStatement venueStmt = conn.prepareStatement(insertVenueSQL, Statement.RETURN_GENERATED_KEYS);
            venueStmt.setString(1, venue.getVenue_name());
            venueStmt.setString(2, venue.getAddress());
            venueStmt.executeUpdate();

            // Retrieve generated venue_id
            ResultSet venueKeys = venueStmt.getGeneratedKeys();
            int venueId = 0;
            if (venueKeys.next()) {
                venueId = venueKeys.getInt(1); // Get the generated venue_id
            } else {
                throw new SQLException("Failed to retrieve venue ID.");
            }


            String eventSql = "INSERT INTO Event (event_name, event_date, event_time, total_seats, available_seats, ticket_price, event_type, venue_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement prepare = conn.prepareStatement(eventSql, Statement.RETURN_GENERATED_KEYS);
            {
                prepare.setString(1, event_name);
                prepare.setDate(2, Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ISO_DATE)));
                prepare.setTime(3, Time.valueOf(LocalTime.parse(time, DateTimeFormatter.ISO_TIME)));
                prepare.setInt(4, total_seats);
                prepare.setInt(5, total_seats);
                prepare.setDouble(6, ticket_price);
                prepare.setString(7, event_type);
                prepare.setInt(8, venueId);
                prepare.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
                event = new Concert(event_name, eventDate, eventTime, venue, total_seats, ticket_price, eventDetails[0], eventDetails[1]);
                break;
            default:
                throw new IllegalArgumentException("Invalid event type");
        }
        events.add(event);
        return event;
    }

    @Override
    public String[] get_Event_Details(String eventName) {
        String[] eventDetails = null;

        try {
            Connection conn = DBUtil.getDBConn();
            String sql = "SELECT e.*, v.venue_name FROM Event e JOIN Venue v ON e.venue_id = v.venue_id WHERE e.event_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, eventName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                eventDetails = new String[]{
                        "Name: " + rs.getString("event_name"),
                        "Date: " + rs.getDate("event_date"),
                        "Time: " + rs.getTime("event_time"),
                        "Event Type: " + rs.getString("event_type"),
                        "Available Seats: " + rs.getInt("available_seats"),
                        "Ticket Price: ₹" + rs.getDouble("ticket_price"),
                        "Venue Name: " + rs.getString("venue_name")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
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
    public int getAvailableNoOfTickets(String eventName) {
        int availableSeats = 0;

        try {
            Connection conn = DBUtil.getDBConn();
            String sql = "SELECT available_seats FROM Event WHERE event_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, eventName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                availableSeats = rs.getInt("available_seats");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableSeats;
    }



    private static final Comparator<Event> EVENT_COMPARATOR = new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            // by event name
            int nameComparison = e1.getEventName().compareToIgnoreCase(e2.getEventName());
            if (nameComparison != 0) {
                return nameComparison;
            }
            return e1.getVenue().getVenue_name().compareToIgnoreCase(e2.getVenue().getVenue_name());
        }
    };

    public List<Event> getSortedEvents() {
        List<Event> sortedEvents = new ArrayList<>(events);
        sortedEvents.sort(EVENT_COMPARATOR);
        return sortedEvents;
    }


}