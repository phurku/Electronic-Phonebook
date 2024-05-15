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
        
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/file.txt", true))) {
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
            String phoneRegex = "^(\\+\\d{1,3}[- ]?)?\\d{1,4}?[- ]?\\d{3,4}[- ]?\\d{3,4}$";
            Pattern pattern = Pattern.compile(phoneRegex);
            return pattern.matcher(phone).matches();
        }
        private static void deleteContact(String details) {
            String[] parts = details.split(";");
            if (parts.length >= 2) {
                String name = parts[0].trim();
                String birthday = parts[1].trim();
        
                Iterator<Contact> iterator = contacts.iterator();
                boolean contactFound = false;
                while (iterator.hasNext()) {
                    Contact contact = iterator.next();
                    if (contact.getName().equalsIgnoreCase(name) && contact.getBirthday().equals(birthday)) {
                        iterator.remove();
                        System.out.println("Contact deleted: " + name+ birthday);
                        contactFound = true;
        
                        // Write the deleted contact to the deleted.txt file
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/deletedRecords.txt", true))) {
                            writer.write(name);
                            writer.newLine();
                            writer.write(birthday);
                            writer.newLine();
                            if (contact.getEmail() != null) {
                                writer.write(contact.getEmail());
                                writer.newLine();
                            }
                            if (contact.getAddress() != null) {
                                writer.write(contact.getAddress());
                                writer.newLine();
                            }
                            if (contact.getPhone() != null) {
                                writer.write(contact.getPhone());
                                writer.newLine();
                            }
                            writer.write("---");
                            writer.newLine();
                            writer.flush();
                        } catch (IOException e) {
                            System.out.println("Error writing to deleted.txt: " + e.getMessage());
                        }
                    }
                }
        
                if (!contactFound) {
                    System.out.println("Contact not found: " + name + ", " + birthday);
                }
            } else {
                System.out.println("Invalid delete instruction: " + details);
            }
        }
    

        private static void processQueryInstruction(String details) {
            String[] parts = details.split(" ");
            if (parts.length == 2) {
                String key = parts[0].trim().toLowerCase();
                String value = parts[1].trim();
                switch (key) {
                    case "name":
                        queryByName(value);
                        break;
                    case "birthday":
                        queryByBirthday(value);
                        break;
                    case "phone":
                        queryByPhone(value);
                        break;
                    default:
                        System.out.println("Invalid query instruction: " + details);
                }
            } else {
                System.out.println("Invalid query instruction: " + details);
            }
        }
        
        private static void queryByName(String name) {
            boolean found = false;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/query_results.txt", true))) {
                for (Contact contact : contacts) {
                    if (contact.getName().equalsIgnoreCase(name)) {
                        printContactDetails(contact, writer);
                        found = true;
                    }
                }
                if (!found) {
                    writer.write("No contact found with name: " + name);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing query results: " + e.getMessage());
            }
        }
        
        private static void queryByBirthday(String birthday) {
            boolean found = false;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/query_results.txt", true))) {
                for (Contact contact : contacts) {
                    if (contact.getBirthday().equals(birthday)) {
                        printContactDetails(contact, writer);
                        found = true;
                    }
                }
                if (!found) {
                    writer.write("No contact found with birthday: " + birthday);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing query results: " + e.getMessage());
            }
        }
        
        private static void queryByPhone(String phone) {
            boolean found = false;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/query_results.txt", true))) {
                for (Contact contact : contacts) {
                    if (contact.getPhone() != null && contact.getPhone().equals(phone)) {
                        printContactDetails(contact, writer);
                        found = true;
                    }
                }
                if (!found) {
                    writer.write("No contact found with phone number: " + phone);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing query results: " + e.getMessage());
            }
        }
        
        private static void printContactDetails(Contact contact, BufferedWriter writer) throws IOException {
            writer.write("Contact found: " + contact.getName() + ", " + contact.getBirthday() + ", " + contact.getEmail() + ", " + contact.getAddress() + ", " + contact.getPhone());
            writer.newLine();
        }
    }        