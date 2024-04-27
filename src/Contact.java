
import java.util.*;

// Abstract class for a contact
abstract class Contact {
    protected String name;

    public Contact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Abstract method for displaying contact details
    public abstract String display();
}

// Concrete class representing a person's contact details
class PersonContact extends Contact {
    private String birthday;
    private String phone;
    private String address;

    public PersonContact(String name, String birthday, String phone, String address) {
        super(name);
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
    }

    // Display person contact details
    @Override
    public String display() {
        return "Name: " + name + ", Birthday: " + birthday + ", Phone: " + phone + ", Address: " + address;
    }
}

// Concrete class representing a business contact
class BusinessContact extends Contact {
    private String email;
    private String address;

    public BusinessContact(String name, String email, String address) {
        super(name);
        this.email = email;
        this.address = address;
    }

    // Display business contact details
    @Override
    public String display() {
        return "Name: " + name + ", Email: " + email + ", Address: " + address;
    }
}

// Class representing the phone book
class PhoneBook {
    private List<Contact> contacts;

    public PhoneBook() {
        contacts = new ArrayList<>();
    }

    // Add a contact to the phone book
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    // Delete a contact from the phone book by name
    // public void deleteContact(String name) {
    //     contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
    // }

    // Query contacts by name
    public List<Contact> queryByName(String name) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                result.add(contact);
            }
        }
        return result;
    }

    // Query contacts by type
    public List<Contact> queryByType(Class<?> type) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (type.isInstance(contact)) {
                result.add(contact);
            }
        }
        return result;
    }
}
    // Class representing the ECB system
    
