package Main;

import Main.models.Projects.*;
import Main.models.Users.*;
import Main.services.ProjectService;
import Main.services.ReportService;
import Main.services.TaskService;
import Main.services.UserService;
import Main.utilities.ConsoleMenu.*;
import Main.utilities.FileUtilities;
import Main.utilities.Validator;
import Main.utilities.exceptions.FileLoadException;

import java.util.Scanner;

public class Main {
    public static UserService userService;
    public static ProjectService projectService;
    public static TaskService taskService;
    public static ReportService reportService;
    static synchronized void initialize() {
        try {
            Thread thread1 = new Thread(() -> projectService = new ProjectService(FileUtilities.loadProjects()));
            Thread thread2 = new Thread(() -> taskService = new TaskService(FileUtilities.loadTasks()));
            Thread thread3 = new Thread(() -> userService = new UserService(FileUtilities.loadUsers()));
            thread1.start();
            thread2.start();
            thread3.start();
        } catch (FileLoadException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        reportService = new ReportService(projectService, taskService);
        Validator.input = new Scanner(System.in);
    }
    public static void main(String[] args) {
        initialize();
        Menus.getLoginMenu().asRoot().display();
        Validator.input.close();
        FileUtilities.saveProjects(projectService.getProjects());
        FileUtilities.saveTasks(taskService.getTasks());
        FileUtilities.saveUsers(userService.getUsers());
    }
}