package utils;

import java.util.Scanner;
import interfaces.Completable;

// The functions in this class repeatedly prompts the user until they give a valid response
public class ValidationUtils {
    public static String getValidTaskID(Scanner scanner) {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. T001).
        while (true) {
            String response = scanner.nextLine().trim();
            if (response.matches("^T\\d{3}$")) {
                return response;
            } else {
                System.out.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. T001):");
                System.out.println("Enter again:");
            }
        }
    }
    public static String getValidProjectID(Scanner scanner) {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001).
        while (true) {
            String response = scanner.nextLine().trim();
            if (response.matches("^P\\d{3}$")) {
                return response;
            } else {
                System.out.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001)");
                System.out.println("Enter again:");
            }
        }
    }
    public static String getValidProjectID(Scanner scanner, String orAccept) {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001).
        while (true) {
            String response = scanner.nextLine().trim();
            if (response.matches("^P\\d{3}$")) {
                return response;
            } else if (response.equals(orAccept)) {
                return response;
            } else {
                System.out.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001)");
                System.out.println("Enter again:");
            }
        }
    }
    public static String getValidUserID(Scanner scanner) {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. U001).
        while (true) {
            String response = scanner.nextLine().trim();
            if (response.matches("^U\\d{3}$")) {
                return response;
            } else {
                System.out.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. U001)");
                System.out.println("Enter again:");
            }
        }
    }
    public static Completable.STATUS getValidTaskStatus(Scanner scanner) {
        // Error: Invalid status. Please choose from [Pending, In Progress, Completed].
        while (true) {
            String response = scanner.nextLine().trim();
            if (response.matches("^\\s*(Pending|In Progress|Completed)\\s*$")) {
                switch (response.toLowerCase()) {
                    case "pending": return Completable.STATUS.PENDING;
                    case "in progress": return Completable.STATUS.IN_PROGRESS;
                    case "completed": return Completable.STATUS.COMPLETED;
                }
            } else {
                System.out.println("Invalid status. Please choose from [Pending, In Progress, Completed].");
                System.out.println("Enter again:");
            }
        }
    }
    public static boolean getValidProjectType(Scanner scanner) {
        // Error: Invalid type. Please choose from [Software, Hardware].
        while (true) {
            String response = scanner.nextLine().trim();
            if (response.matches("^\\s*(Software|Hardware)\\s*$")) {
                switch (response.toLowerCase()) {
                    case "software": return true;
                    case "hardware": return false;
                }
            } else {
                System.out.println("Invalid type. Please choose from [Software, Hardware].");
                System.out.println("Enter again:");
            }
        }
    }
    public static double getValidNumber(Scanner scanner, int min) {
        while (true) {
            try {
                double response = scanner.nextDouble();
                scanner.nextLine();
                if (response >= min) {
                    return response;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Expected number");
                System.out.println("Enter again:");
                scanner.nextLine();
            }

        }
    }
}
