import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class SystemManager {
    private List<Contact> contacts; // Declaring a private list to store contacts

    public SystemManager() {
        contacts = new ArrayList<>(); // Initializing the contacts list as an ArrayList
    }

    public void addContact(String details) throws IOException {
        String[] parts = details.split(";");
        if (parts.length >= 2) {
            String name = parts[0].trim();
            String birthday = parts[1].trim();
    
            String address = null;
            String email = null;
            String phone = null;
    
            for (int i = 2; i < parts.length; i++) {
                String detail = parts[i].trim();
                if (isValidEmailAddress(detail)) {
                    email = detail;
                } else if (isValidPhoneNumber(detail)) {
                    phone = detail;
                } else {
                    if (address == null) {
                        address = detail;
                    } else {
                        address += " " + detail; // Append to the existing address
                    }
                }
            }
    
            Contact contact = new Contact(name, birthday, email, address, phone);
            contacts.add(contact);
            System.out.println("Contact added: " + name);
    
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/phonebook.txt", true))) {
                writer.write(name);
                writer.newLine();
                writer.write(birthday);
                writer.newLine();
                if (email != null) {
                    writer.write(email);
                    writer.newLine();
                }
                if (address != null) {
                    writer.write(address);
                    writer.newLine();
                }
                if (phone != null) {
                    writer.write(phone);
                    writer.newLine();
                }
                writer.write("---");
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }
    
    
    public void deleteContact(String details) {
        String[] parts = details.split(";");
        // System.out.println(details);
        String name = parts[0].trim();
        String birthday = parts.length > 1 ? parts[1].trim() : null;
        // System.out.println(name);
        if (name!= null) {
            // Delete by name only
            Iterator<Contact> iterator = contacts.iterator();
            // System.out.println(name);

            while (iterator.hasNext()) {
                // System.out.println(name);

                Contact contact = iterator.next();
                System.out.println(contact.getName());
                // System.out.println(name);

                if (contact.getName()!=null) {
                    // System.out.println(contact.getName());
                    // System.out.println(iterator);

                    iterator.remove();
                    System.out.println("Contact deleted: " + contact.getName());
                    
                    updatePhonebookFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/deletedRecord.txt", true))) {
                        writer.write(name);
                        writer.newLine();
                        writer.write(birthday);
                        writer.newLine();
                        
                        writer.write("---");
                        writer.newLine();
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("Error writing to file: " + e.getMessage());
                    }
                    return; // Exit the method after deleting the contact
                }
            }
            System.out.println("Contact not found: " + name);
        } else {
            // Delete by name and birthday
            Iterator<Contact> iterator = contacts.iterator();
            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if (contact.getName().equalsIgnoreCase(name) && contact.getBirthday().equals(birthday)) {
                    iterator.remove();
                    System.out.println("Contact deleted: " + contact.getName() + ", " + contact.getBirthday());
                    updatePhonebookFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/deletedRecord.txt", true))) {
                        writer.write(name);
                        writer.newLine();
                        writer.write(birthday);
                        writer.newLine();
                        
                        writer.write("---");
                        writer.newLine();
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("Error writing to file: " + e.getMessage());
                    }
                    return; // Exit the method after deleting the contact
                }
            }
            System.out.println("Contact not found: " + name + ", " + birthday);
        }
     
    }

 private void updatePhonebookFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/phonebook.txt", false))) {
            for (Contact c : contacts) {
                writer.write(c.getName());
                writer.newLine();
                writer.write(c.getBirthday());
                writer.newLine();
                if (c.getEmail() != null) {
                    writer.write(c.getEmail());
                    writer.newLine();
                }
                if (c.getAddress() != null) {
                    writer.write(c.getAddress());
                    writer.newLine();
                }
                if (c.getPhone() != null) {
                    writer.write(c.getPhone());
                    writer.newLine();
                }
                writer.write("---");
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing to phonebook.txt: " + e.getMessage());
        }
    }
    
    
   public void processQueryInstruction(String details) {
        String[] parts = details.split(" "); // Split the details string by space
        if (parts.length == 2) { // Check if there are exactly two parts (key and value)
            String key = parts[0].trim().toLowerCase(); // Extract the key and convert it to lowercase
            String value = parts[1].trim(); // Extract the value and trim whitespace
            switch (key) { // Switch based on the key
                case "name":
                    queryByName(value); // Call the queryByName method with the value
                    break;
                case "birthday":
                    queryByBirthday(value); // Call the queryByBirthday method with the value
                    break;
                case "phone":
                    queryByPhone(value); // Call the queryByPhone method with the value
                    break;
                default:
                    System.out.println("Invalid query instruction: " + details); // Print an error message for an invalid key
            }
        } else {
            System.out.println("Invalid query instruction: " + details); // Print an error message for an invalid number of parts
        }
    }
    private void queryByName(String name) {
        boolean found = false; // Flag to track if a contact is found
        for (Contact contact : contacts) { // Iterate over the contacts list
            if (contact.getName().equalsIgnoreCase(name)) { // Check if the contact's name matches the given name (case-insensitive)
                printContactDetails(contact); // Print the contact details
                found = true; // Set the found flag to true
            }
        }
        if (!found) { // If no contact was found
            System.out.println("No contact found with name: " + name); // Print a message indicating no contact was found with the given name
        }
    }
    private void queryByBirthday(String birthday) {
        boolean found = false; // Flag to track if a contact is found
        for (Contact contact : contacts) { // Iterate over the contacts list
            if (contact.getBirthday().equals(birthday)) { // Check if the contact's birthday matches the given birthday
                printContactDetails(contact); // Print the contact details
                found = true; // Set the found flag to true
            }
        }
        if (!found) { // If no contact was found
            System.out.println("No contact found with birthday: " + birthday); // Print a message indicating no contact was found with the given birthday
        }
    }
    private void queryByPhone(String phone) {
        boolean found = false; // Flag to track if a contact is found
        for (Contact contact : contacts) { // Iterate over the contacts list
            if (contact.getPhone() != null && contact.getPhone().equals(phone)) { // Check if the contact's phone number matches the given phone number
                printContactDetails(contact); // Print the contact details
                found = true; // Set the found flag to true
            }
        }
        if (!found) { // If no contact was found
            System.out.println("No contact found with phone number: " + phone); // Print a message indicating no contact was found with the given phone number
        }
    }
    
    public void saveContacts(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) { // Open the file for writing
            for (Contact contact : contacts) { // Iterate over the contacts list
                writer.write(contact.getName()); // Write the contact's name
                writer.newLine(); // Write a new line
                writer.write(contact.getBirthday()); // Write the contact's birthday
                writer.newLine(); // Write a new line
                if (contact.getEmail() != null) { // Check if the contact has an email
                    writer.write(contact.getEmail()); // Write the contact's email
                    writer.newLine(); // Write a new line
                }
                if (contact.getAddress() != null) { // Check if the contact has an address
                    writer.write(contact.getAddress()); // Write the contact's address
                    writer.newLine(); // Write a new line
                }
                if (contact.getPhone() != null) { // Check if the contact has a phone number
                    writer.write(contact.getPhone()); // Write the contact's phone number
                    writer.newLine(); // Write a new line
                }
                writer.write("---"); // Write a separator
                writer.newLine(); // Write a new line
            }
            System.out.println("Contacts saved successfully."); // Print a success message
        } catch (IOException e) { // Catch any IOException that may occur
            System.out.println("Error saving contacts: " + e.getMessage()); // Print an error message with the exception details
        }
    }
    
    public void loadContacts(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { // Open the file for reading
            String line;
            String name = null;
            String birthday = null;
            String email = null;
            String address = null;
            String phone = null;
    
            while ((line = reader.readLine()) != null) { // Read each line of the file
                if (line.equals("---")) { // Check if the line is a separator
                    if (name != null && birthday != null) { // Check if name and birthday are not null
                        Contact contact = new Contact(name, birthday, email, address, phone); // Create a new Contact object
                        contacts.add(contact); // Add the contact to the contacts list
                    }
                    name = null; // Reset the name
                    birthday = null; // Reset the birthday
                    email = null; // Reset the email
                    address = null; // Reset the address
                    phone = null; // Reset the phone number
                } else {
                    if (name == null) { // If name is null, assign the line to name
                        name = line;
                    } else if (birthday == null) { // If birthday is null, assign the line to birthday
                        birthday = line;
                    } else if (email == null) { // If email is null, assign the line to email
                        email = line;
                    } else if (address == null) { // If address is null, assign the line to address
                        address = line;
                    } else if (phone == null) { // If phone number is null, assign the line to phone number
                        phone = line;
                    }
                }
            }
    
            if (name != null && birthday != null) { // Check if name and birthday are not null
                Contact contact = new Contact(name, birthday, email, address, phone); // Create a new Contact object
                contacts.add(contact); // Add the contact to the contacts list
            }
    
            System.out.println("Contacts loaded successfully."); // Print a success message
        } catch (IOException e) { // Catch any IOException that may occur
            System.out.println("Error loading contacts: " + e.getMessage()); // Print an error message with the exception details
        }
    }
    
   
    private boolean isValidEmailAddress(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{1,4}?[- ]?\\d{3,4}[- ]?\\d{3,4}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }

    private void printContactDetails(Contact contact) {
        System.out.println("Contact found: " + contact.getName() + ", " + contact.getBirthday() + ", " + contact.getEmail() + ", " + contact.getAddress() + ", " + contact.getPhone());
    }
}
