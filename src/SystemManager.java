import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class SystemManager {
    private List<Contact> contacts; // Declaring a private list to store contacts

    public SystemManager() {
        contacts = new ArrayList<>(); // Initializing the contacts list as an ArrayList
    }

    public void addContact(String details) throws IOException {
        String[] parts = details.split(";"); // Splitting the details string by semicolon
        if (parts.length >= 2) { // Checking if there are at least two parts (name and birthday)
            String name = parts[0].trim(); // Extracting the name and trimming whitespace from the srting value
            String birthday = parts[1].trim(); // Extracting the birthday and trimming whitespace
            // Initializing variables as null
            String address = null; 
            String email = null; 
            String phone = null; 

            for (int i = 2; i < parts.length; i++) { // Iterating over the remaining parts
                String detail = parts[i].trim(); // Trimming whitespace from the current part
                if (isValidEmailAddress(detail)) { // Checking if the part is a valid email address
                    email = detail; // Assigning the part to the email variable
                } else if (isValidPhoneNumber(detail)) { // Checking if the part is a valid phone number
                    phone = detail; // Assigning the part to the phone variable
                } else {
                    address = detail; // Assuming the part is an address
                }
            }

            Contact contact = new Contact(name, birthday, email, address, phone); // Creating a new Contact object with the extracted details
            contacts.add(contact); // Adding the new Contact object to the contacts list
            System.out.println("Contact added: " + name); // Printing a success message with the name of the added contact
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/phonebook.txt", true))) { // Opening the phonebook.txt file in append mode
                writer.write(name); // Writing the name to the file
                writer.newLine(); // Adding a new line
                writer.write(birthday); // Writing the birthday to the file
                writer.newLine(); // Adding a new line
                if (email != null) { // Checking if the email is not null
                    writer.write(email); // Writing the email to the file
                    writer.newLine(); // Adding a new line
                }
                if (address != null) { // Checking if the address is not null
                    writer.write(address); // Writing the address to the file
                    writer.newLine(); // Adding a new line
                }
                if (phone != null) { // Checking if the phone number is not null
                    writer.write(phone); // Writing the phone number to the file
                    writer.newLine(); // Adding a new line
                }
                writer.write("---"); // Writing a separator
                writer.newLine(); // Adding a new line
                writer.flush(); // Flushing the writer to ensure all data is written to the file
            } catch (IOException e) { // Catching any IOException that may occur
                System.out.println("Error writing to file: " + e.getMessage()); // Printing an error message with the exception details
            }
        }
    }

    public void deleteContact(String details) {
        String[] parts = details.split(";"); // Split the details string by semicolon
        if (parts.length >= 2) { // Check if there are at least two parts (name and birthday)
            String name = parts[0].trim(); // Extract the name and trim whitespace
            String birthday = parts[1].trim(); // Extract the birthday and trim whitespace
    
            Iterator<Contact> iterator = contacts.iterator(); // Get an iterator for the contacts list
            boolean contactFound = false; // Flag to track if the contact is found
            while (iterator.hasNext()) { // Iterate over the contacts
                Contact contact = iterator.next(); // Get the next contact
                if (contact.getName().equalsIgnoreCase(name) && contact.getBirthday().equals(birthday)) { // Check if the contact matches the given name and birthday
                    iterator.remove(); // Remove the contact from the list
                    System.out.println("Contact deleted: " + name); // Print a success message
                    contactFound = true; // Set the flag to true
    
                    // Remove the deleted contact from the phonebook.txt file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/phonebook.txt", false))) { // Open the phonebook.txt file in write mode
                        for (Contact c : contacts) { // Iterate over the remaining contacts
                            writer.write(c.getName()); // Write the name
                            writer.newLine(); // Add a new line
                            writer.write(c.getBirthday()); // Write the birthday
                            writer.newLine(); // Add a new line
                            if (c.getEmail() != null) { // Check if the email is not null
                                writer.write(c.getEmail()); // Write the email
                                writer.newLine(); // Add a new line
                            }
                            if (c.getAddress() != null) { // Check if the address is not null
                                writer.write(c.getAddress()); // Write the address
                                writer.newLine(); // Add a new line
                            }
                            if (c.getPhone() != null) { // Check if the phone is not null
                                writer.write(c.getPhone()); // Write the phone
                                writer.newLine(); // Add a new line
                            }
                            writer.write("---"); // Write a separator
                            writer.newLine(); // Add a new line
                        }
                        writer.flush(); // Flush the writer to ensure all data is written
                    } catch (IOException e) { // Catch any IOException
                        System.out.println("Error writing to phonebook.txt: " + e.getMessage()); // Print an error message with the exception details
                    }
                }
            }
    
            if (!contactFound) { // If the contact was not found
                System.out.println("Contact not found: " + name + ", " + birthday); // Print a message indicating the contact was not found
            }
        } else {
            System.out.println("Invalid delete instruction: " + details); // If the details string is invalid, print an error message
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
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; // Regular expression pattern for email validation
        Pattern pattern = Pattern.compile(emailRegex); // Compile the regular expression pattern
        return pattern.matcher(email).matches(); // Check if the email matches the pattern and return the result
    }
    private boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{1,4}?[- ]?\\d{3,4}[- ]?\\d{3,4}$"; // Regular expression pattern for phone number validation
        Pattern pattern = Pattern.compile(phoneRegex); // Compile the regular expression pattern
        return pattern.matcher(phone).matches(); // Check if the phone number matches the pattern and return the result
    }
    
    private void printContactDetails(Contact contact) {
        System.out.println("Contact found: " + contact.getName() + ", " + contact.getBirthday() + ", " + contact.getEmail() + ", " + contact.getAddress() + ", " + contact.getPhone());
    }
    
}
