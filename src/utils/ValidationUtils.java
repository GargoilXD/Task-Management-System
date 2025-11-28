package utils;

import java.util.Scanner;

import interfaces.Completable;

public class ValidationUtils {
    public static String getValidTaskID() {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. T001).
        while (true) {
            String response = IO.readln().trim();
            if (response.matches("^T\\d{3}$")) {
                return response;
            } else {
                IO.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. T001):");
                IO.println("Enter again:");
            }
        }
    }
    public static String getValidProjectID() {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001).
        while (true) {
            String response = IO.readln().trim();
            if (response.matches("^P\\d{3}$")) {
                return response;
            } else {
                IO.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001)");
                IO.println("Enter again:");
            }
        }
    }
    public static String getValidProjectID(String orAccept) {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001).
        while (true) {
            String response = IO.readln().trim();
            if (response.matches("^P\\d{3}$")) {
                return response;
            } else if (response.equals(orAccept)) {
                return response;
            } else {
                IO.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001)");
                IO.println("Enter again:");
            }
        }
    }
    public static Completable.STATUS getValidTaskStatus() {
        // Error: Invalid status. Please choose from [Pending, In Progress, Completed].
        while (true) {
            String response = IO.readln().trim();
            if (response.matches("^\\s*(Pending|In Progress|Completed)\\s*$")) {
                switch (response.toLowerCase()) {
                    case "pending": return Completable.STATUS.PENDING;
                    case "in progress": return Completable.STATUS.IN_PROGRESS;
                    case "completed": return Completable.STATUS.COMPLETED;
                }
            } else {
                IO.println("Invalid status. Please choose from [Pending, In Progress, Completed].");
                IO.println("Enter again:");
            }
        }
    }
    public static boolean getValidProjectType() {
        // Error: Invalid type. Please choose from [Software, Hardware].
        while (true) {
            String response = IO.readln().trim();
            if (response.matches("^\\s*(Software|Hardware)\\s*$")) {
                switch (response.toLowerCase()) {
                    case "software": return true;
                    case "hardware": return false;
                }
            } else {
                IO.println("Invalid type. Please choose from [Software, Hardware].");
                IO.println("Enter again:");
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
                IO.println("Expected number");
                IO.println("Enter again:");
                scanner.nextLine();
            }

        }
    }
}
