// FileHandler.java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static void readPhonebook(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    public static void processInstructions(String instructionsFile, String outputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(instructionsFile));
        FileWriter writer = new FileWriter(outputFile);

        String instruction;
        while ((instruction = reader.readLine()) != null) {
            if (instruction.startsWith("add")) {
                String[] recordDetails = instruction.split(" ");
                Record record = new Record();
                record.setName(recordDetails[1]);
                record.setPhone(recordDetails[2]);
                record.setEmail(recordDetails[3]);
                record.setAddress(recordDetails[4]);
                record.setBirthday(recordDetails[5]);

                writer.write(record.getName() + "\n");
                writer.write(record.getPhone() + "\n");
                writer.write(record.getEmail() + "\n");
                writer.write(record.getAddress() + "\n");
                writer.write(record.getBirthday() + "\n");
            }
        }

        reader.close();
        writer.close();
    }
}