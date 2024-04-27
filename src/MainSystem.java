import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class MainSystem {
    public static void main(String[] args) throws FileNotFoundException {
    System.out.println("hey this is main");

    PhoneBook phoneBook = new PhoneBook();
    List<String> queries = new ArrayList<>();

    try {
    BufferedReader phoneBookReader = new BufferedReader(
    new FileReader("src/phonebook.txt"));
    String infoLine;
    while ((infoLine = phoneBookReader.readLine()) != null) {
    System.out.println(infoLine);
    }

    BufferedReader instructionReader = new BufferedReader(
    new FileReader("src/instructions.txt"));
    String instructionLine;
    while ((instructionLine = instructionReader.readLine()) != null) {
    System.out.println(instructionLine);

    }
    BufferedWriter outputWriter = new BufferedWriter(new
    FileWriter("src/JavaProject/output.txt"));
    BufferedWriter reportWriter = new BufferedWriter(
    new FileWriter("src/JavaProject/query_report.txt", true));
    } catch (Exception e) {
    System.out.println("File do not exist");
    }
    

    }
    }
    