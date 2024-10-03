package bookingSystem;

public class Customer {

    private String customer_name;
    private String email;
    private int phone_number;


    public Customer(){

    }

    public Customer(String customer_name,String email, int phone_number){

        this.customer_name = customer_name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public void display_customer_details(){
        System.out.println("Customer Details:");
        System.out.println("Name: " + customer_name);
        System.out.println("Email: " + email);
        System.out.println("PhoneNumber: " + phone_number);
    }
}


