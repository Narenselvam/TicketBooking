package bean;

import exception.*;
import service.Customer;

import java.sql.SQLException;


public interface IBookingSystemServiceProvider {
    double calculate_booking_cost(int num_tickets,String eventName) throws EventNotFoundException;
    void book_tickets(String eventName, int num_tickets, Customer[] arrayOfCustomer) throws EventNotFoundException, SQLException;
    void cancel_booking(int booking_id) throws InvalidBookingIDException;
    void get_booking_details(int booking_id)throws InvalidBookingIDException, SQLException;
}