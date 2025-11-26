package utils;

public class ValidationUtils {
    public ValidationUtils() {}
    public static boolean validateTaskName(String taskName) {
        return taskName.matches("^T\\d{3}$");
    }
    public static boolean validateProjectName(String projectName) {
        return projectName.matches("^P\\d{3}$");
    }
}
