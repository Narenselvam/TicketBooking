package bookingSystem;

public class Movie extends Event{

    private String genre;
    private String actorName;
    private String actressName;


    public Movie(){
    }

    public Movie (String genre,String actorName,String actressName){
        this.actorName = actorName;
        this.genre = genre;
        this.actressName = actressName;

    }


    public String getGenre() {
        return genre;
    }

    public String getActorName() {
        return actorName;
    }

    public String getActressName() {
        return actressName;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setActressName(String actressName) {
        this.actressName = actressName;
    }


    public void display_movie_details(){
        System.out.println("Movie Details");
        System.out.println("Genre: " + this.genre);
        System.out.println("ActorName: " + this.actorName);
        System.out.println("ActressName:" + this.actressName);

    }

}



