package dao;

import entity.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class BookingSystem {
    public abstract Event create_event(String event_name, String date, String time, int total_seats,
                                       double ticket_price, String event_type, String venue_name, String... eventDetails);

    public abstract int get_available_seats(String eventName);

    public abstract void book_tickets(String eventName, int num_tickets);

    public abstract void cancel_tickets(String eventName, int num_tickets);
}