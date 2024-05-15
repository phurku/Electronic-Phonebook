import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class MainSystem {
    private static List<Contact> contacts = new ArrayList<>();

        private static Scanner scanner = new Scanner(System.in);
    
        public static void main(String[] args) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/instructions.txt"));
                String instruction;
                while ((instruction = reader.readLine()) != null) {
                    processInstruction(instruction);
                }
                reader.close();
            } catch (IOException e) {
                System.err.println("Error reading instructions file: " + e.getMessage());
            }
        }
        private static void processInstruction(String instruction) throws IOException {
            String[] parts = instruction.split(" ");
            if (parts.length > 0) {
                String command = parts[0].trim().toLowerCase();
                switch (command) {
                    case "add":
                        if (parts.length > 1) {
                            String details = String.join(" ", parts).substring(4);
                            if (details.split(";").length >= 2) {
                                addContact(details);
                            } else {
                                System.out.println("Invalid add instruction: " + instruction);
                            }
                        } else {
                            System.out.println("Invalid add instruction: " + instruction);
                        }
                        break;
                    case "delete":
                        if (parts.length > 1) {
                            String details = String.join(" ", parts).substring(7);
                            deleteContact(details);
                        } else {
                            System.out.println("Invalid delete instruction: " + instruction);
                        }
                        break;
                    case "query":
                        if (parts.length > 1) {
                            String details = String.join(" ", parts).substring(6);
                            processQueryInstruction(details);
                        } else {
                            System.out.println("Invalid query instruction: " + instruction);
                        }
                        break;
                    case "save":
                        // saveContacts();
                        break;
                    default:
                        System.out.println("Invalid instruction: " + instruction);
                }
            } else {
                System.out.println("Invalid instruction: " + instruction);
            }
        }
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
        
      private static void clearFile() throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/file.txt", false))) {
                // Clear the file by not writing anything
            } catch (IOException e) {
                System.out.println("Error clearing file: " + e.getMessage());
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
    
        private static void deleteContact(String details) {
            // Implementation for deleting a contact
            System.out.println("Invalid delete instruction: " + details);
        }
    
        private static void processQueryInstruction(String details) {
            // Implementation for processing query instructions
            System.out.println("Invalid query instruction: " + details);
        }
        
    // ... (deleteContact, queryContact, printContactDetails, and saveContacts methods remain the same)

    

    private static void printContactDetails(Contact contact) {
        System.out.println("Contact found: " + contact.getName() + ", " + contact.getBirthday() + ", " + contact.getEmail() + ", " + contact.getAddress() + ", " + contact.getPhone());
    }}

//     private static void saveContacts() {
//         try {
//             BufferedWriter writer = new BufferedWriter(new FileWriter("src/contacts.txt", true));
//             for (Contact contact : contacts) {
//                 writer.write(contact.getName() + ":" + contact.getBirthday().format(DateTimeFormatter.ISO_LOCAL_DATE) + ":" + contact.getEmail() + ":" + contact.getAddress() + ":" + contact.getPhone());
//                 writer.newLine(); // Move to the next line
//             }
//             writer.close();
//             System.out.println("Contacts saved successfully.");
//         } catch (IOException e) {
//             System.err.println("Error saving contacts: " + e.getMessage());
//         }
//     }
// }