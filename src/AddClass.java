import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddClass {
        private static List<Contact> contacts = new ArrayList<>();

  private static void addContact(String details) throws IOException {
            String[] parts = details.split(";");
            String name = null;
            String birthday = null;
            String email = null;
            String address = null;
            String phone = null;
        
            for (String part : parts) {
                String[] keyValue = part.trim().split(" ", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().toLowerCase();
                    String value = keyValue[1].trim();
                    switch (key) {
                        case "name":
                            name = value;
                            break;
                        case "birthday":
                            birthday = value;
                            break;
                        case "email":
                            if (isValidEmailAddress(value)) {
                                email = value;
                            } else {
                                System.out.println("Invalid email address: " + value);
                            }
                            break;
                        case "address":
                            address = value;
                            break;
                        case "phone":
                            if (isValidPhoneNumber(value)) {
                                phone = value;
                            } else {
                                System.out.println("Invalid phone number: " + value);
                            }
                            break;
                        default:
                            System.out.println("Invalid key: " + key);
                    }
                } else {
                    System.out.println("Invalid format: " + part);
                }
            }
        
            if (name != null && birthday != null) {
                Contact contact = new Contact(name, birthday, email, address, phone);
                contacts.add(contact);
                System.out.println("Contact added: " + name+birthday+email+address+phone);
        
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
            } else {
                System.out.println("Invalid add instruction: " + details);
            }
        }
       private static boolean isValidEmailAddress(String email) {
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            Pattern pattern = Pattern.compile(emailRegex);
            return pattern.matcher(email).matches();
        }
    
        private static boolean isValidPhoneNumber(String phone) {
            String phoneRegex = "^\\d{8}$"; // Assuming a 10-digit phone number format
            Pattern pattern = Pattern.compile(phoneRegex);
            return pattern.matcher(phone).matches();
        }
    
}
