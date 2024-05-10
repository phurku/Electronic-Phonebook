import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainSystem {
    // The function main is declared as public and static so that it can be accessed for anywhere in the program and it doesnot require any instance of the class to be created
    public static void main(String[] args) throws IOException {
        try {
            // File objects are created
            File phonebookFile = new File("src/phonebook.txt");
            File instructionsFile = new File("src/instructions.txt");
            File outputFile = new File("src/newDataRecords.txt");
// inputScanner object of class Scanner is create to read the user input
            Scanner inputScanner = new Scanner(System.in);
            // boolean variable exit is initialized to false to control the loop
            boolean exit = false;
// This is the main loop of the program which will run until the user chooses to exit
            while (!exit) {
                System.out.println("Choose an option:");
                System.out.println("1. Add record");
                System.out.println("2. Delete record");
                System.out.println("3. Query records");
                System.out.println("4. Exit");

                int choice = inputScanner.nextInt();
                inputScanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addRecord(phonebookFile,inputScanner);
                        break;
                    case 2:
                        deleteRecord(inputScanner, phonebookFile,outputFile);
                        break;
                    case 3:
                        queryRecords(phonebookFile);
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            inputScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
// Here is the method to add new data to the existing file
    private static void addRecord(File phonebookFile, Scanner inputScanner) throws FileNotFoundException {
        System.out.println("Enter record details (name, phone, email, address, birthday):");
    //   These below lines of code will prompt the user to enter the record of details
        String name = inputScanner.nextLine();
        String phone = inputScanner.nextLine();
        String email = inputScanner.nextLine();
        String address = inputScanner.nextLine();
        String birthday = inputScanner.nextLine();
    //    An object outputFile of class PrintWriter is created and is initialized with a FileWriter object and the argument inside FileWriter is made true so that it will open the file in append mode.
        try (PrintWriter outputWriter = new PrintWriter(new FileWriter(phonebookFile, true))) {

            // If the above condition is satisfis then the below statement is executedelse it will execute the statement inside catch block
            outputWriter.println(name + "," + phone + "," + email + "," + address + "," + birthday);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
        System.out.println("Record added successfully.");
    }

    private static void deleteRecord( Scanner inputScanner, File phonebookFile, File outputFile)  throws FileNotFoundException {

        // File deletedRecordFile = new File("src/deletedRecordFile.txt");
        System.out.println("Enter the name of the record you want to delete:");
        String nameToDelete = inputScanner.nextLine();
        Scanner phonebookScanner = new Scanner(phonebookFile);
        PrintWriter outputWriter = new PrintWriter(outputFile);       

        boolean notRecordFound = true;
        // System.out.println(notRecordFound);
        while (phonebookScanner.hasNextLine()) {
            String name = phonebookScanner.nextLine();
            String phone = phonebookScanner.nextLine();
            String email = phonebookScanner.nextLine();
            String address = phonebookScanner.nextLine();
            String birthday = phonebookScanner.nextLine();

            if (!name.equals(nameToDelete)) {
                outputWriter.println(name);
                outputWriter.println(phone);
                outputWriter.println(email);
                outputWriter.println(address);
                outputWriter.println(birthday);
            } else {
                notRecordFound = false;
                System.out.println("Record deleted: " + name);
            }
        }

        if (notRecordFound) {
            System.out.println("Record not found.");
        }

        phonebookScanner.close();
        outputWriter.close();
    }

    private static void queryRecords(File phonebookFile) throws FileNotFoundException {
        Scanner phonebookScanner = new Scanner(phonebookFile);
        while (phonebookScanner.hasNextLine()) {
            String line = phonebookScanner.nextLine();
            System.out.println(line);
        }
        phonebookScanner.close();
    }
}