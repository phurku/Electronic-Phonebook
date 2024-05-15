
class Contact {
    private String name;
    private String birthday;
   private String email;
    private String address;
    private String phone;

    public Contact(String name, String birthday, String email, String address, String phone) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}