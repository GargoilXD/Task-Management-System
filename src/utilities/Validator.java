package utilities;

import interfaces.Completable;

import java.util.Scanner;
import java.util.function.Function;

// The functions in this class repeatedly prompts the user until they give a valid response
public class Validator {
    public static Scanner input;
    public static String getValidTaskID() {
        while (true) {
            String response = input.nextLine().trim();
            if (response.matches("^T\\d{3}$")) {
                return response;
            } else {
                System.out.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. T001):");
                System.out.println("Enter again:");
            }
        }
    }
    public static String getValidProjectID() {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001).
        while (true) {
            String response = input.nextLine().trim();
            if (response.matches("^P\\d{3}$")) {
                return response;
            } else {
                System.out.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001)");
                System.out.println("Enter again:");
            }
        }
    }
    public static String getValidProjectID(String orAccept) {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001).
        while (true) {
            String response = input.nextLine().trim();
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
    public static String getValidUserID() {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. U001).
        while (true) {
            String response = input.nextLine().trim();
            if (response.matches("^U\\d{3}$")) {
                return response;
            } else {
                System.out.println("Invalid input. Please enter a valid numeric or prefixed ID (e.g. U001)");
                System.out.println("Enter again:");
            }
        }
    }
    public static Completable.STATUS getValidTaskStatus() {
        // Error: Invalid status. Please choose from [Pending, In Progress, Completed].
        while (true) {
            String response = input.nextLine().trim().toLowerCase();
            if (response.matches("^\\s*(pending|in progress|completed)\\s*$")) {
                switch (response) {
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
    public static boolean getValidProjectType() {
        // Error: Invalid type. Please choose from [Software, Hardware].
        while (true) {
            String response = input.nextLine().trim().toLowerCase();
            if (response.matches("^\\s*(software|hardware)\\s*$")) {
                switch (response) {
                    case "software": return true;
                    case "hardware": return false;
                }
            } else {
                System.out.println("Invalid type. Please choose from [Software, Hardware].");
                System.out.println("Enter again:");
            }
        }
    }
    public static double getValidNumber(int min) {
        while (true) {
            try {
                double response = input.nextDouble();
                input.nextLine();
                if (response >= min) {
                    return response;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Expected number");
                System.out.println("Enter again:");
                input.nextLine();
            }
        }
    }
    public static int getValidInteger(int min, int max) {
        while (true) {
            try {
                int response = input.nextInt();
                input.nextLine();
                if (response >= min && response <= max) {
                    return response;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Expected number");
                System.out.println("Enter again:");
                input.nextLine();
            }
        }
    }
    public static int getValidInteger(int min) {
        while (true) {
            try {
                int response = input.nextInt();
                input.nextLine();
                if (response >= min) {
                    return response;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Expected number");
                System.out.println("Enter again:");
                input.nextLine();
            }
        }
    }
    public static boolean getValidChoice() {
        while (true) {
            String response = input.nextLine().trim().toLowerCase();
            if (response.equals("y")) {
                return true;
            } else if (response.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter a valid choice (Y/N)");
                System.out.println("Enter again:");
            }
        }
    }
    public static String manualValidation(Function<String, Boolean> function, String errorMessage) {
        while (true) {
            String response = input.nextLine().trim();
            if (function.apply(response)) {
                return response;
            } else {
                System.out.println(errorMessage);
                System.out.println("Enter again:");
            }
        }
    }
    public static String getValidEmail() {
        while (true) {
            String response = input.nextLine().trim();
            if (response.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return response;
            } else {
                System.out.println("Invalid email.");
                System.out.println("Enter again:");
            }
        }
    }
}
