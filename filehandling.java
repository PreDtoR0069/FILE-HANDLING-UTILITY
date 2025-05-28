
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class filehandling {

    // We'll use this file to store our data
    private static final String FILE_PATH = "sample.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        // Simple text-based menu loop
        while (keepRunning) {
            System.out.println("\n=== FILE HANDLING MENU ===");
            System.out.println("1. Write to file");
            System.out.println("2. Read file");
            System.out.println("3. Modify file");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    writeToFile(scanner);
                    break;
                case 2:
                    readFromFile();
                    break;
                case 3:
                    modifyFile(scanner);
                    break;
                case 4:
                    keepRunning = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    // Appends text to the file
    private static void writeToFile(Scanner scanner) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            System.out.println("Type something to write to the file (type 'exit' to stop):");

            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                writer.write(input);
                writer.newLine();
            }

            System.out.println("Done writing to file.");
        } catch (IOException e) {
            System.out.println("Something went wrong while writing: " + e.getMessage());
        }
    }

    // Reads and prints the file line by line
    private static void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            System.out.println("\n--- File Content ---");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("--- End of File ---");
        } catch (IOException e) {
            System.out.println("Couldn't read the file: " + e.getMessage());
        }
    }

    // Allows user to modify a specific line in the file
    private static void modifyFile(Scanner scanner) {
        List<String> lines = new ArrayList<>();

        // First, read all lines into memory
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lines.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Show user the current lines
        if (lines.isEmpty()) {
            System.out.println("The file is empty. Nothing to modify.");
            return;
        }

        System.out.println("\nCurrent file content:");
        for (int i = 0; i < lines.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, lines.get(i));
        }

        // Ask which line to change
        System.out.print("Enter the line number to modify: ");
        int lineToEdit = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (lineToEdit < 1 || lineToEdit > lines.size()) {
            System.out.println("That's not a valid line number.");
            return;
        }

        System.out.print("Enter the new content for line " + lineToEdit + ": ");
        String newContent = scanner.nextLine();

        lines.set(lineToEdit - 1, newContent); // Replace the line

        // Write the modified list back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

