package bean;

import exception.*;
import service.Customer;


public interface IBookingSystemServiceProvider {
    double calculate_booking_cost(int num_tickets,String eventName);
    void book_tickets(String eventName, int num_tickets, Customer[] arrayOfCustomer) throws EventNotFoundException;
    void cancel_booking(int booking_id) throws InvalidBookingIDException;
    void get_booking_details(int booking_id)throws InvalidBookingIDException;
}