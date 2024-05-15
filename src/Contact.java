public class Contact {
// Declare a private instance variable to store the name,birthday,email,address and phone
    private String name; 
    private String birthday; 
    private String email; 
    private String address; 
    private String phone; 

    public Contact(String name, String birthday, String email, String address, String phone) {
// Initialize the  instance variable
        this.name = name; 
        this.birthday = birthday; 
        this.email = email; 
        this.address = address; 
        this.phone = phone; 
    }
// Getter and Setter method to retrieve tand ser the variables
    public String getName() {
        return name; 
    }

    public void setName(String name) {
        this.name = name; 
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday; 
    }

    public String getEmail() {
        return email; 
    }

    public void setEmail(String email) {
        this.email = email; 
    }

    public String getAddress() {
        return address; 
    }

    public void setAddress(String address) {
        this.address = address; 
    }

    public String getPhone() {
        return phone; 
    }

    public void setPhone(String phone) {
        this.phone = phone; 
    }
}
