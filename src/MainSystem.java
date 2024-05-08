import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainSystem {
    public static void main(String[] args) {
        try {
            File phonebookFile = new File("src/phonebook.txt");
            File instructionsFile = new File("src/instructions.txt");
            File outputFile = new File("src/newRecords.txt");

            Scanner inputScanner = new Scanner(System.in);
            boolean exit = false;

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
                        addRecord(instructionsFile, outputFile, inputScanner);
                        break;
                    case 2:
                        deleteRecord(phonebookFile, outputFile, inputScanner);
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

    private static void addRecord(File instructionsFile, File outputFile, Scanner inputScanner) throws FileNotFoundException {
        System.out.println("Enter record details (name, phone, email, address, birthday):");
        String name = inputScanner.nextLine();
        String phone = inputScanner.nextLine();
        String email = inputScanner.nextLine();
        String address = inputScanner.nextLine();
        String birthday = inputScanner.nextLine();

        PrintWriter outputWriter = new PrintWriter(outputFile);
        outputWriter.println("Added record " + name + " " + phone + " " + email + " " + address + " " + birthday);
        outputWriter.close();
    }

    private static void deleteRecord(File phonebookFile, File outputFile, Scanner inputScanner) throws FileNotFoundException {
        System.out.println("Enter the name of the record to delete:");
        String nameToDelete = inputScanner.nextLine();

        Scanner phonebookScanner = new Scanner(phonebookFile);
        PrintWriter outputWriter = new PrintWriter(outputFile);

        boolean recordFound = false;

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
                recordFound = true;
                System.out.println("Record deleted: " + name);
            }
        }

        if (!recordFound) {
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