package bookingSystem;

import java.time.LocalDate;
import java.time.LocalTime;

public class Concert extends Event{

    private String artist;
    private String type;

    public Concert(String event_name, LocalDate eventDate, LocalTime eventTime, String venue_name, int total_seats, double ticket_price, String unknown, String s){
    }

    public Concert(String artist,String type){

        this.artist=artist;
        this.type=type;

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
