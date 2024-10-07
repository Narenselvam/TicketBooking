package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Concert extends Event{

    private String artist;
    private String type;


    public Concert(){
    }

    public Concert(String event_name, LocalDate eventDate, LocalTime eventTime, String venue_name, int total_seats, double ticket_price, String artist,String type){
        super( event_name,  eventDate,  eventTime, venue_name,  total_seats,  ticket_price, event_Type.valueOf("concert"));
        this.artist=artist;
       this.type=type; //(Theatrical, Classical, Rock, Recital)
    }




    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void display_concert_details(){
        System.out.println("Concert Details :");
        System.out.println("Concert Type : " + this.type);
        System.out.println("Artist Name : " + this.artist);
    }
}
