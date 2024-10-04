package bookingSystem;

public class Sports extends Event {

    private String sportsName;
    private String teamName;


    public Sports() {
    }

    public Sports(String sportsName, String teamName){
        this.sportsName = sportsName;
        this.teamName = teamName;
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