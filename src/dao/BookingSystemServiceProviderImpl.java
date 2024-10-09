package dao;

import entity.*;

import java.util.ArrayList;
import java.util.List;

public class BookingSystemServiceProviderImpl  extends EventServiceProviderImpl implements IBookingSystemServiceProvider{

//    private List<Event> events = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();


    @Override
    public double calculate_booking_cost(int num_tickets) {
        if (currentEvent != null) {
            return currentEvent.getTicketPrice() * num_tickets;
        }
        return 0;
    }

    @Override
    public void book_tickets(String eventName, int num_tickets, Customer[] arrayOfCustomer) {
        Event event = findEventByName(eventName);
        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        if (event.getAvailableSeats() < num_tickets) {
            System.out.println("Not enough seats available.");
            return;
        }

        List<Customer> customers = new ArrayList<>();
        for (Customer customer : arrayOfCustomer) {
            customers.add(customer);
        }

        double totalCost = calculate_booking_cost(num_tickets);
        Booking booking = new Booking(customers, event, num_tickets, totalCost);
        event.setAvailableSeats(event.getAvailableSeats() - num_tickets);

        bookings.add(booking);
        System.out.println("Booking successful. Booking ID: " + booking.getBookingId());
    }

    @Override
    public void cancel_booking(int booking_id) {


        Booking cancelBooking = null;
        for (Booking booking : bookings) {
            if (booking.getBookingId() == booking_id) {
                cancelBooking = booking;
                break;
            }
        }

        if (cancelBooking == null) {
            System.out.println("Booking not found with ID: " + booking_id);
            return;
        }

        Event event = cancelBooking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + cancelBooking.getNumTickets());

        bookings.remove(cancelBooking);
        System.out.println("Booking " + booking_id + " has been cancelled.");
        System.out.println("Updated available seats for event '" + event.getEventName() + "': " + event.getAvailableSeats());

    }

    @Override
    public void get_booking_details(int booking_id) {


        for (Booking booking : bookings) {
            if (booking.getBookingId() == booking_id) {
                System.out.println("Booking ID: " + booking.getBookingId());
                System.out.println("Event: " + booking.getEvent().getEventName());
                System.out.println("Number of tickets: " + booking.getNumTickets());
                System.out.println("Total cost: $" + booking.getTotalCost());
                return;
            }
        }
        System.out.println("Booking not found with ID: " + booking_id);
    }



    public void addEvent(Event event) {
        events.add(event);
    }
}
