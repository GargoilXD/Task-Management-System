package utils;

import models.*;
import java.util.function.Supplier;
/*
public class ConsoleMenu {
    public Scanner scanner;
    ProjectService projectService;
    public ConsoleMenu(ProjectService projectService) {
        this.projectService = projectService;
        this.scanner = new Scanner(System.in);
    }

    public User Login(User[] users) {
        String username;
        String password;
        int tries = 0;
        while (true) {
            if (tries > 5) {
                IO.println("Login Failed. Please try again.");
                return null;
            }
            IO.println("Username: ");
            username = IO.readln();
            IO.println("Enter Password: ");
            password = IO.readln();
            for (User user: users) {
                if (user.name.equals(username) && (user.password.equals(password))) {
                    return user;
                }
            }
            IO.println("Wrong username or password.");
            tries++;
        }
    }
    public int MainMenu(User user) {
        IO.println("Main Menu:");
        IO.println("-----------");
        IO.println("1. Manage Projects");
        IO.println("2. Manage Tasks");
        IO.println("3. View Status Reports");
        IO.println("4. Switch User");
        IO.println("5. Exit");
        IO.println();
        return getChoice(5);
    }
    public int ManageProjects() {
        IO.println("==============================");
        IO.println("        PROJECT CATALOG       ");
        IO.println("==============================");
        IO.println();
        IO.println("Filter Options:");
        IO.println("1. View All Projects (%s)".formatted(projectService.projectCount));
        IO.println("2. Software Projects Only");
        IO.println("3. Hardware Projects Only");
        IO.println("4. Search by Budget Range");
        IO.println("5. Back");
        IO.println();
        return getChoice(5);
    }
    public int viewProjectDetails(Project project, Task[] tasks) {
        IO.println("==============================");
        IO.println("      Project Details: " + project.ID);
        IO.println("==============================");
        IO.println();
        IO.println("Project Name: " + project.Name);
        IO.println("Type: " + (project instanceof SoftwareProject? "Software" : "Hardware"));
        IO.println("Team Size: " + project.TeamSize);
        IO.println("Budget: $" + project.Budget);
        IO.println();
        IO.println("Associated Tasks:");
        IO.println("------------------------------------------");
        IO.println("ID  | TASK NAME        | STATUS   ");
        IO.println("------------------------------------------");
        for (Task task : tasks) {
        IO.println("%s  | %s               | %s".formatted(task.ID, task.Name, task.Status));
        }
        IO.println("------------------------------------------");
        double completionRate = 0;
        for (Task task : tasks) {
            if (task.Status == Task.STATUS.COMPLETED) {
                completionRate += 1;
            }
        }
        completionRate /= tasks.length;
        IO.println("Completion Rate: " + (completionRate * 100) + "%");
        IO.println("Options:");
        IO.println("1. Add New Task");
        IO.println("2. Update Tasks Status");
        IO.println("3. Remove Task");
        IO.println("4. Back to Main Menu");
        return getChoice(4);
    }
    public Task addTask(String defaultProjectID) {
        IO.println("Enter task name:");
        String name = IO.readln();
        IO.println(String.format("Enter assign project ID (leave empty for default: %s):", defaultProjectID));
        String projectID = IO.readln();
        if (projectID.isEmpty()) {
            projectID = defaultProjectID;
        }
        IO.println("Enter initial status (Pending/In Progress/Completed):");
        try {
            Task.STATUS status = switch (IO.readln().toLowerCase().trim()) {
                case "pending" -> Task.STATUS.PENDING;
                case "in progress" -> Task.STATUS.IN_PROGRESS;
                case "completed" -> Task.STATUS.COMPLETED;
                default -> throw new Exception();
            };
            IO.println(String.format("Task \"%s\" added successfully to Project %s", name, projectID));
            return new Task(projectID, "", name, status);
        } catch (Exception ex) {
            IO.println("Expected (Pending/In Progress/Completed)");
        }
        return null;
    }
    public Task updateTask(String defaultProjectID) {
        IO.println(String.format("Enter assign project ID (leave empty for default: %s):", defaultProjectID));
        String projectID = IO.readln();
        if (projectID.isEmpty()) {
            projectID = defaultProjectID;
        }
        IO.println("Enter updated status (Pending/In Progress/Completed):");
        try {
            Task.STATUS status = switch (IO.readln().toLowerCase().trim()) {
                case "pending" -> Task.STATUS.PENDING;
                case "in progress" -> Task.STATUS.IN_PROGRESS;
                case "completed" -> Task.STATUS.COMPLETED;
                default -> throw new Exception();
            };
            IO.println(String.format("successfull"));
            return new Task(projectID, "", "", status);
        } catch (Exception ex) {
            IO.println("Expected (Pending/In Progress/Completed)");
        }
        return null;
    }
    public Task removeTask() {
        IO.println("Enter task ID:");
        String ID = IO.readln();
        IO.println(String.format("Task \"%s\" deleted successfully", ID));
        return new Task(ID, "", "", Task.STATUS.COMPLETED);
    }
    public void statusReportDisplay() {
        IO.println("=======================");
        IO.println(" PROJECT STATUS REPORT ");
        IO.println("=======================");
        IO.println();
        IO.println("-".repeat(20));
        IO.println("PROJECT ID | PROJECT NAME | TASKS | COMPLETED | PROGRESS (%)");
        IO.println("-".repeat(20));
        for (int i = 0; i < 10; i++) {
        IO.println(String.format("%s | %s | %s | %s | %s", 0,0,0,0));
        }
        IO.println("-".repeat(20));
        double average_completion = 0;
        IO.println(String.format("AVERAGE COMPLETION: %.2f", average_completion));
        IO.println("-".repeat(20));
    }
    public void ManageTasks() {

    }
    public void ViewStatus() {

    }
    public void SwitchUser() {

    }
}*/

public abstract class ConsoleMenu {
    String name;
    String title;
    Supplier<String> information;

    public ConsoleMenu(String name, String title, Supplier<String> information) {
        this.name = name;
        this.title = title;
        this.information = information;
    }
    public ConsoleMenu(String name, String title) {
        this.name = name;
        this.title = title;
        this.information = () -> "";
    }
    public void display() {}
}

