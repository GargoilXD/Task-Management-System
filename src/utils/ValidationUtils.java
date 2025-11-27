package utils;

public class ValidationUtils {
    public ValidationUtils() {}
    public static boolean validateTaskName(String taskName) {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. T001).
        return taskName.matches("^T\\d{3}$");
    }
    public static boolean validateProjectName(String projectName) {
        // Error: Invalid input. Please enter a valid numeric or prefixed ID (e.g. P001).
        return projectName.matches("^P\\d{3}$");
    }
    public static boolean validateTaskStatus(String taskStatus) {
        // Error: Invalid status. Please choose from [Pending, In Progress, Completed].
        return taskStatus.matches("^$");
    }
    public static boolean validateTeamSize(int teamSize) {
        // Error: Team size must be greater than 0.
        return teamSize >= 0;
    }
    // Enter again
    // Project successfully created.
}
