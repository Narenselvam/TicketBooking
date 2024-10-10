package bean;

import service.Customer;


public interface IBookingSystemServiceProvider {
    double calculate_booking_cost(int num_tickets,String eventName);
    void book_tickets(String eventName, int num_tickets, Customer[] arrayOfCustomer);
    void cancel_booking(int booking_id);
    void get_booking_details(int booking_id);
}