import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainSystem {
    public static void main(String[] args) throws FileNotFoundException {
   

    // Implementing try catch method
        try {
        // Creating an object phoneBookReader of Scanner class to read the content of given file phonebook.txt
            Scanner phoneBookReader = new Scanner(
            new FileReader("src/phonebook.txt"));
            while (phoneBookReader.hasNextLine()) {
                String line = phoneBookReader.nextLine();
                System.out.println(line);
            }

            // Close the Scanner
            phoneBookReader.close();   

            // Creating an object  instructionReader to read the content of instructions.txt file 
            BufferedReader instructionReader = new BufferedReader(new FileReader("src/instructions.txt"));
        
            String instruction;
            FileWriter writer = new FileWriter("src/newRecords.txt");
            while ((instruction = instructionReader.readLine()) != null) {
                if (instruction.startsWith("add")) {
                    // Extract the record details from the instruction
                    String[] recordDetails = instruction.split(" ");
                    for(int i=1;i<recordDetails.length;i++){
                        String name = recordDetails[i];
                        String phone = recordDetails[i];
                        String email=recordDetails[i];
                        String address=recordDetails[i];
                        String birthday=recordDetails[i];
    
    
                        // Write the new record to rewRecords.txt
                        writer.write(name + " " + phone + email+address+birthday+"\n");
                    }
                   
                }
            }

            // Close the FileWriter and Scanner
            writer.close();
            instructionReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while processing the instructions: " + e.getMessage());
        }
    }

    private static void deleteRecord(FileWriter writer, String key, String value) throws IOException {
        // Implement the logic to delete a record from the phonebook
        System.out.println("Deleting record: " + key + " " + value);
    }

    private static void queryRecord(FileWriter writer, String key, String value) throws IOException {
        // Implement the logic to query a record from the phonebook
        System.out.println("Querying record: " + key + " " + value);
    }

        
    }