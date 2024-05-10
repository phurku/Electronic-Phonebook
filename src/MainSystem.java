import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// import java.nio.file.Files;
// import java.nio.file.StandardCopyOption;

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
                        deleteRecord(inputScanner, phonebookFile);
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
// Here is the method to add new data to the existing file(phonebook.txt)
private static void addRecord(File phonebookFile, Scanner inputScanner) throws FileNotFoundException {
    System.out.println("Enter record details (name, birthday (yyyy-mm-dd), phone, address):");
    String name = inputScanner.nextLine();
    String birthday = inputScanner.nextLine();
    String phone = inputScanner.nextLine();
    String address = inputScanner.nextLine();

    List<String> lines = new ArrayList<>();
    boolean recordFound = false;

    try (Scanner fileScanner = new Scanner(phonebookFile)) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");

            if (fields.length == 5) {
                String existingName = fields[0];
                String existingBirthday = fields[4];

                if (existingName.equalsIgnoreCase(name) && existingBirthday.equals(birthday)) {
                    String updatedRecord = String.join(",", name, phone, fields[2], address, birthday);
                    lines.add(updatedRecord);
                    recordFound = true;
                    System.out.println("Record updated successfully.");
                } else {
                    lines.add(line);
                }
            }
        }

        if (!recordFound) {
            String newRecord = String.join(",", name, phone, "", address, birthday);
            lines.add(newRecord);
            System.out.println("New record added successfully.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred while reading the file: " + e.getMessage());
    }

    try (PrintWriter writer = new PrintWriter(new FileWriter(phonebookFile, false))) {
        for (String line : lines) {
            writer.println(line);
        }
    } catch (IOException e) {
        System.out.println("An error occurred while writing to the file: " + e.getMessage());
    }
}
// Here is the method to delete  data to the existing file(phonebook.txt) using name and birthday
private static void deleteRecord(Scanner inputScanner, File phonebookFile) throws IOException {
    System.out.println("Enter the name of the record to delete:");
    String nameToDelete = inputScanner.nextLine();

    System.out.println("Enter the birthday of the record to delete (yyyy-mm-dd):");
    String birthdayToDelete = inputScanner.nextLine();

    List<String> lines = new ArrayList<>();
    boolean recordFound = false;

    try (Scanner fileScanner = new Scanner(phonebookFile)) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] fields = line.split(",");

            if (fields.length == 5) {
                String name = fields[0];
                String birthday = fields[4];

                if (!name.equalsIgnoreCase(nameToDelete) || !birthday.equals(birthdayToDelete)) {
                    lines.add(line);
                } else {
                    recordFound = true;
                    System.out.println("Record deleted: " + line);
                }
            }
        }

        if (!recordFound) {
            System.out.println("Record not found.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred while deleting the record: " + e.getMessage());
    }

    if (recordFound) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(phonebookFile, false))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
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