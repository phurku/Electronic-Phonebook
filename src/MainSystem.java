import java.io.*;

public class MainSystem {
    private static SystemManager contact; // Declaring a private static variable 'contact' of type SystemManager

    public static void main(String[] args) {
        
        contact = new SystemManager(); // Creating a new instance of SystemManager and assigning it to 'contact'

        try {
            // Load contacts from the phone book file
            contact.loadContacts("src/phonebook.txt"); // Calling the loadContacts method to load contacts from the file

            // Read instructions from the instructions file
            BufferedReader reader = new BufferedReader(new FileReader("src/instructions.txt")); // Creating a BufferedReader to read from the instructions file
            String instruction;
            while ((instruction = reader.readLine()) != null) { // Reading each line of the file
                processInstruction(instruction); // Calling the processInstruction method with the current instruction
            }
            reader.close(); // Closing the BufferedReader

        } catch (IOException e) {
            System.err.println("Error reading/writing files: " + e.getMessage()); // Printing an error message if an IOException occurs
        }
    }

    private static void processInstruction(String instruction) throws IOException {
        String[] parts = instruction.split(" "); // Splitting the instruction into parts based on spaces
        if (parts.length > 0) { // Checking if there are any parts
            String command = parts[0].trim().toLowerCase(); // Getting the first part (command) and converting it to lowercase
            switch (command) { // Switching based on the command
                case "add":
                    if (parts.length > 1) { // Checking if there are more than one part
                        String details = String.join(" ", parts).substring(4); // Getting the details by joining the parts after the first 4 characters
                        if (details.split(";").length >= 2) { // Checking if the details contain at least one semicolon
                            contact.addContact(details); // Calling the addContact method with the details
                        } else {
                            System.out.println("Invalid add instruction: " + instruction); // Printing an error message if the add instruction is invalid
                        }
                    } else {
                        System.out.println("Invalid add instruction: " + instruction); // Printing an error message if the add instruction is invalid
                    }
                    break;
                case "delete":
                    if (parts.length > 1) { // Checking if there are more than one part
                        String details = String.join(" ", parts).substring(7); // Getting the details by joining the parts after the first 6 characters
                        // System.out.print(details);
                        contact.deleteContact(details); // Calling the deleteContact method with the details
                    } else {
                        System.out.println("Invalid delete instruction: " + instruction); // Printing an error message if the delete instruction is invalid
                    }
                    break;
                case "query":
                    if (parts.length > 1) { // Checking if there are more than one part
                        String details = String.join(" ", parts).substring(6); // Getting the details by joining the parts after the first 6 characters
                        // contact.processQueryInstruction(details); // Calling the processQueryInstruction method with the details
                    } else {
                        System.out.println("Invalid query instruction: " + instruction); // Printing an error message if the query instruction is invalid
                    }
                    break;
                case "save":
                    // No action needed, as contacts are saved at the end
                    break;
                default:
                    System.out.println("Invalid instruction: " + instruction); // Printing an error message if the instruction is invalid
            }
        } else {
            System.out.println("Invalid instruction: " + instruction); // Printing an error message if the instruction is invalid
        }
    }
}
