import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainSystem {
    public static void main(String[] args) {
        try {
            // Read from phonebook.txt
            File phonebookFile = new File("src/phonebook.txt");
            Scanner phonebookScanner = new Scanner(phonebookFile);
            while (phonebookScanner.hasNextLine()) {
                String line = phonebookScanner.nextLine();
                System.out.println(line);
            }
            phonebookScanner.close();

            // Read from instructions.txt and write to newRecords.txt
            File instructionsFile = new File("src/instructions.txt");
            File outputFile = new File("src/newRecords.txt");
            Scanner instructionsScanner = new Scanner(instructionsFile);
            PrintWriter outputWriter = new PrintWriter(outputFile);

            while (instructionsScanner.hasNextLine()) {
                String instruction = instructionsScanner.nextLine();
                if (instruction.startsWith("add")) {
                    String[] recordDetails = instruction.split(" ");
                    Record record = new Record();
                    record.setName(recordDetails[1]);
                    record.setPhone(recordDetails[2]);
                    record.setEmail(recordDetails[3]);
                    record.setAddress(recordDetails[4]);
                    record.setBirthday(recordDetails[5]);

                    outputWriter.println(record.getName());
                    outputWriter.println(record.getPhone());
                    outputWriter.println(record.getEmail());
                    outputWriter.println(record.getAddress());
                    outputWriter.println(record.getBirthday());
                }
            }

            instructionsScanner.close();
            outputWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}