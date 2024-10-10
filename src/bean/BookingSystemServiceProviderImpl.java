package bean;

import exception.*;
import service.Booking;
import service.Customer;
import service.Event;

import java.util.*;

public class BookingSystemServiceProviderImpl extends EventServiceProviderImpl implements IBookingSystemServiceProvider {

    private Map<String, Event> events = new HashMap<>();
    private Map<Integer, Booking> bookings = new HashMap<>();


    @Override
    public double calculate_booking_cost(int num_tickets, String eventName) {
        Event event = findEventByName(eventName);
        if (event != null) {
            return event.getTicketPrice() * num_tickets;
        }
        return 0;
    }

    @Override
    public void book_tickets(String eventName, int num_tickets, Customer[] arrayOfCustomer) throws EventNotFoundException {
        Event event = findEventByName(eventName);
        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        if (event.getAvailableSeats() < num_tickets) {
            System.out.println("Not enough seats available.");
            return;
        }

        Map<String, Customer> customerMap = new HashMap<>();
        for (Customer customer : arrayOfCustomer) {
            customerMap.put(customer.getEmail(), customer);
        }

        double totalCost = calculate_booking_cost(num_tickets, eventName);
        Booking booking = new Booking(customerMap, event, num_tickets, totalCost);
        event.setAvailableSeats(event.getAvailableSeats() - num_tickets);

        bookings.put(booking.getBookingId(), booking);
        System.out.println("Booking successful. Booking ID: " + booking.getBookingId());
    }

    @Override
    public void cancel_booking(int booking_id) throws InvalidBookingIDException {


        Booking cancelBooking = bookings.get(booking_id);


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
    public void get_booking_details(int booking_id) throws InvalidBookingIDException {
        Booking booking = bookings.get(booking_id);

        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("Event: " + booking.getEvent().getEventName());
        System.out.println("Number of tickets: " + booking.getNumTickets());
        System.out.println("Total cost: ₹" + booking.getTotalCost());
        System.out.println("Booking not found with ID: " + booking_id);
    }


    public void addEvent(Event event) {
        String event_name=event.getEventName();
        events.put(event_name,event);
    }
}
