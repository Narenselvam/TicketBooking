package service;

import java.time.LocalDate;
import java.time.LocalTime;

public  class Sports extends Event {

    private String sportsName;
    private String teamName;


    public Sports(){
    }

    public Sports(String event_name, LocalDate eventDate, LocalTime eventTime, Venue venue_name, int total_seats, double ticket_price, String sportsName, String teamName) {
        super( event_name,  eventDate,  eventTime, venue_name,  total_seats,  ticket_price, Event.event_Type.valueOf("sports"));

        this.sportsName = sportsName; // Name of the Game
        this.teamName = teamName; // (India vs Pakistan)
    }



    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void display_sport_details(){
        System.out.println("Sport Details :");
        System.out.println("sportsName : " + sportsName);
        System.out.println("teamName : " + teamName);
    }
}
