package Main;

import Main.models.Projects.*;
import Main.models.Task;
import Main.models.Users.*;
import Main.services.ProjectService;
import Main.services.ReportService;
import Main.services.TaskService;
import Main.services.UserService;
import Main.utilities.ConsoleMenu.*;
import Main.utilities.Validator;

import java.util.Scanner;

public class Main {
    static UserService userService;
    static ProjectService projectService;
    static TaskService taskService;
    static ReportService reportService;
    static void initialize() {
        userService = new UserService(
                new User[] {
                        new AdminUser("Kobby", "12345"),
                        new AdminUser("Ama", "12345"),
                        new RegularUser("Kofi", "12345", new String[] {"T001", "T002", "T003"})
                }
        );
        projectService = new ProjectService(
                new Project[] {
                        new SoftwareProject("Alpha Tracker", "Task tracking app for startups", 5, 15000),
                        new HardwareProject("IoT Sensor Kit", "Sensor prototype for smart devices", 3, 1000),
                        new SoftwareProject("Cloud Backup Tool", "Automated backup solution for SMBs", 4, 12000),
                        new HardwareProject("Smart Thermostat", "Energy-efficient home climate control", 6, 2500),
                        new SoftwareProject("HR Onboarding Portal", "Streamlined employee onboarding system", 6, 18000),
                        new SoftwareProject("EduQuiz Platform", "Interactive quiz app for educators", 4, 9500),
                        new HardwareProject("Solar-Powered Charger", "Portable charger using renewable energy", 5, 1800),
                });
        taskService = new TaskService(
                new Task[] {
                        new Task("P001", "Design Database", Task.STATUS.COMPLETED),
                        new Task("P001", "Implement API", Task.STATUS.IN_PROGRESS),
                        new Task("P001", "Write Unit Tests", Task.STATUS.PENDING),
                        new Task("P002", "Gather Materials", Task.STATUS.COMPLETED),
                        new Task("P002", "Build prototype", Task.STATUS.IN_PROGRESS),
                        new Task("P003", "Define Backup Strategy", Task.STATUS.COMPLETED),
                        new Task("P003", "Develop Sync Engine", Task.STATUS.IN_PROGRESS),
                        new Task("P003", "Create UI Dashboard", Task.STATUS.PENDING),
                        new Task("P004", "Circuit Design", Task.STATUS.COMPLETED),
                        new Task("P004", "Firmware Development", Task.STATUS.IN_PROGRESS),
                        new Task("P004", "Enclosure Prototyping", Task.STATUS.PENDING),
                        new Task("P005", "User Authentication", Task.STATUS.COMPLETED),
                        new Task("P005", "Document Upload Module", Task.STATUS.IN_PROGRESS),
                        new Task("P005", "Integration with Payroll", Task.STATUS.PENDING),
                        new Task("P006", "User Registration Flow", Task.STATUS.COMPLETED),
                        new Task("P006", "Quiz Builder UI", Task.STATUS.IN_PROGRESS),
                        new Task("P006", "Real-time Grading Engine", Task.STATUS.PENDING),
                        new Task("P007", "Solar Panel Sourcing", Task.STATUS.COMPLETED),
                        new Task("P007", "Battery Integration", Task.STATUS.COMPLETED),
                        new Task("P007", "Safety & Overcharge Protection", Task.STATUS.IN_PROGRESS),
                });
        reportService = new ReportService(projectService, taskService);
        Menus.userService = userService;
        Menus.projectService = projectService;
        Menus.taskService = taskService;
        Menus.reportService = reportService;
        Validator.input = new Scanner(System.in);
    }
    public static void main(String[] args) {
        initialize();
        Menus.getLoginMenu().asRoot().display();
        Validator.input.close();
    }
}