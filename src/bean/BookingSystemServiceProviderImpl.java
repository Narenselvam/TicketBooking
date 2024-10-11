package bean;

import exception.*;
import service.Booking;
import service.Customer;
import service.Event;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookingSystemServiceProviderImpl extends EventServiceProviderImpl implements IBookingSystemServiceProvider {

    private Map<String, Event> events = new HashMap<>();
    private Map<Integer, Booking> bookings = new HashMap<>();


    @Override
    public double calculate_booking_cost(int num_tickets, String eventName) throws EventNotFoundException {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT ticket_price FROM event WHERE event_name = ?")) {

            pstmt.setString(1, eventName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double ticketPrice = rs.getDouble("ticket_price");
                    return ticketPrice * num_tickets;
                }
                throw new EventNotFoundException("Event not found: " + eventName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating booking cost", e);
        }
    }


    public int getEventId(String eventName) throws SQLException {
        ResultSet rs = null;

        try {
            Connection conn = getConnection();
            String sql = "SELECT event_id FROM event WHERE event_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, eventName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("event_id");
            } else {
                return -1; // Indicate that the event was not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void book_tickets(String eventName, int num_tickets, Customer[] arrayOfCustomer) throws EventNotFoundException, SQLException {
        int eventId = 0;
        try {
            eventId = getEventId(eventName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String bookingSql = "INSERT INTO booking (event_id, num_tickets, total_cost, booking_date) VALUES (?, ?, ?, CURDATE())";
            PreparedStatement pstmt = conn.prepareStatement(bookingSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, eventId);
            pstmt.setInt(2, num_tickets);
            pstmt.setDouble(3, calculate_booking_cost(num_tickets, eventName));
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int bookingId;
            if (rs.next()) {
                bookingId = rs.getInt(1);
            } else {
                throw new SQLException("Creating booking failed, no ID obtained.");
            }

            // Insert customers
            String customerSql = "INSERT INTO customer (customer_name, email, phone_number, booking_id) VALUES (?, ?, ?, ?)";
            PreparedStatement prepare = conn.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS);
            for (Customer customer : arrayOfCustomer) {
                prepare.setString(1, customer.getCustomer_name());
                prepare.setString(2, customer.getEmail());
                prepare.setInt(3, customer.getPhone_number());
                prepare.setInt(4, bookingId);
                prepare.addBatch();
            }
            prepare.executeBatch();

            ResultSet result = prepare.getGeneratedKeys();
            int customerId = -1;
            if (result.next()) {
                customerId = result.getInt(1);
            } else {
                throw new SQLException("Creating customer failed, no ID obtained.");
            }

            String updateEventSql = "UPDATE event SET available_seats = available_seats - ? WHERE event_id = ?";
            prepare = conn.prepareStatement(updateEventSql);
            prepare.setInt(1, num_tickets);
            prepare.setInt(2, eventId);
            prepare.executeUpdate();

            System.out.println("Booking successful. Booking ID: " + bookingId);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException("Failed to book tickets: " + e.getMessage(), e);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }


    @Override
    public void cancel_booking(int booking_id) throws InvalidBookingIDException {


        Booking cancelBooking = bookings.get(booking_id);

        try {

            Connection conn = getConnection();
            // Get booking details
            String getBookingSql = "SELECT event_id, num_tickets FROM booking WHERE booking_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(getBookingSql);
            pstmt.setInt(1, booking_id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new InvalidBookingIDException("Invalid Booking ID: " + booking_id);
            }
            int eventId = rs.getInt("event_id");
            int numTickets = rs.getInt("num_tickets");

            // Update available seats
            String updateEventSql = "UPDATE event SET available_seats = available_seats + ? WHERE event_id = ?";
            pstmt = conn.prepareStatement(updateEventSql);
            pstmt.setInt(1, numTickets);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();

            // Delete customers associated with the booking
            String deleteCustomersSql = "DELETE FROM customer WHERE booking_id = ?";
            pstmt = conn.prepareStatement(deleteCustomersSql);
            pstmt.setInt(1, booking_id);
            pstmt.executeUpdate();

            // Delete booking
            String deleteBookingSql = "DELETE FROM booking WHERE booking_id = ?";
            pstmt = conn.prepareStatement(deleteBookingSql);
            pstmt.setInt(1, booking_id);
            pstmt.executeUpdate();

            conn.commit();
            System.out.println("Booking " + booking_id + " has been cancelled.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Cancellation failed: " + e.getMessage());
        }

        if (cancelBooking == null) {
            throw new InvalidBookingIDException("Invalid Booking ID: " + booking_id);
        }

        Event event = cancelBooking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + cancelBooking.getNumTickets());

        bookings.remove(cancelBooking);
        System.out.println("Booking " + booking_id + " has been cancelled.");
        System.out.println("Updated available seats for event '" + event.getEventName() + "': " + event.getAvailableSeats());

    }


    @Override
    public void get_booking_details(int booking_id) throws InvalidBookingIDException, SQLException {
        try (Connection conn = getConnection()) {
            String sql = "SELECT b.booking_id, e.event_name, b.num_tickets, b.total_cost, " +
                    "c.customer_name, c.email, c.phone_number " +
                    "FROM booking b " +
                    "JOIN event e ON b.event_id = e.event_id " +
                    "JOIN customer c ON b.customer_id = c.customer_id " +
                    "WHERE b.booking_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, booking_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id"));
                System.out.println("Event: " + rs.getString("event_name"));
                System.out.println("Number of tickets: " + rs.getInt("num_tickets"));
                System.out.println("Total cost: ₹" + rs.getDouble("total_cost"));
                System.out.println("Customer Names: " + rs.getString("customer_name"));
                System.out.println("Customer Emails: " + rs.getString("email"));
                System.out.println("Customer Phones: " + rs.getString("phone_number"));
            } else {
                throw new InvalidBookingIDException("Booking not found with ID: " + booking_id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve booking details: " + e.getMessage());
        }
    }


    public void addEvent(Event event) {
        String event_name = event.getEventName();
        String etype = String.valueOf(event.getEventType());

        String getVenueName = event.getVenue().getVenue_name();


        try {
            Connection conn = getConnection();
            String venuIdsql = "SELECT venue_id from venue WHERE venue_name = ?";
            PreparedStatement pt = conn.prepareStatement(venuIdsql);
            pt.setString(1, getVenueName);
            ResultSet rs = pt.executeQuery();

            if (!rs.next()) {
                throw new RuntimeException("Venue not found: " + getVenueName);
            }

            int venueId = rs.getInt("venue_id");

            String sql = "INSERT INTO event (event_name, event_date, event_time, venue_id, total_seats, available_seats, ticket_price, event_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, event.getEventName());
            pstmt.setDate(2, Date.valueOf(event.getEventDate()));
            pstmt.setTime(3, Time.valueOf(event.getEventTime()));
            pstmt.setInt(4, venueId);
            pstmt.setInt(5, event.getTotalSeats());
            pstmt.setInt(6, event.getAvailableSeats());
            pstmt.setDouble(7, event.getTicketPrice());
            pstmt.setString(8, etype);
            pstmt.executeUpdate();

            System.out.println("Event added successfully: " + event.getEventName());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add event: " + e.getMessage());
        }

        events.put(event_name, event);
    }


}
