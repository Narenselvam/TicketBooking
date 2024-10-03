package bookingSystem;

public class Venue {

	private String venue_name;
	private String address;
	
	
	public Venue(){
		
	}
	
	
	public Venue(String venue_name , String address) {
		this.venue_name =  venue_name;
		this.address = address;
		
	}
	
	
	public String getVenueName() {
		return venue_name;
	}
	
	public void setVenueName(String venueName) {
		this.venue_name=venueName;
	}

	public String getAddress(){
		return address;
	}

	public void setAddress(String address){
		this.address= address;
	}

	
	 // Method to display venue details
    public void displayVenueDetails() {
        System.out.println("Venue Details:");
        System.out.println("Name: " + venue_name);
        System.out.println("Address: " + address);
    }
}
