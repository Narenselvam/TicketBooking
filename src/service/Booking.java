package service;

import service.Customer;
import service.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Booking {

    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private int bookingId;
    private List<Customer> customers= new ArrayList<>();
    private Event event;
    private int num_tickets = customers.size();
    private double total_cost;
    private LocalDateTime booking_date;

    public Booking() {
        this.bookingId = idCounter.incrementAndGet(); // Automatically increment booking ID
        this.booking_date = LocalDateTime.now(); // Set the booking date to the current timestamp
    }

    public Booking(List<Customer> customers, Event event, int num_tickets, double total_cost) {
        this.bookingId = idCounter.incrementAndGet();
        this.customers = customers;
        this.event = event;
        this.num_tickets = num_tickets;
        this.total_cost = total_cost;
        this.booking_date = LocalDateTime.now();
    }


    public int getBookingId() {
        return bookingId;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getNumTickets() {
        return num_tickets;
    }

    public void setNumTickets(int num_tickets) {
        this.num_tickets = num_tickets;
    }

    public double getTotalCost() {
        return total_cost;
    }

    public void setTotalCost(double total_cost) {
        this.total_cost = total_cost;
    }

    public LocalDateTime getBookingDate() {
        return booking_date;
    }

    public void setBookingDate(LocalDateTime booking_date) {
        this.booking_date = booking_date;
    }


    public void display_booking_details() {
        System.out.println("Booking Details : ");
        System.out.println("Booking ID: " + this.bookingId);
        System.out.println("Booking Date: " + this.booking_date);
        System.out.println("Event: " + this.event.getEventName() + " (" + this.event.getEventType() + ")");
        System.out.println("Number of Tickets: " + this.num_tickets);
        System.out.println("Total Cost: ₹" + this.total_cost);

        System.out.println("Customers: ");
        for (Customer customer : this.customers) {
            System.out.println("  - " + customer.getCustomer_name());
        }
    }

}
