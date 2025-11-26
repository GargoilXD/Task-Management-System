package utils;

import models.*;

import java.util.Scanner;

public class ConsoleMenu {
    public Scanner scanner;
    public ConsoleMenu(User[] users) {
        this.scanner = new Scanner(System.in);
    }
    int getChoice(int range) {
        while (true) {
            IO.println("Enter your choice:");
            int choice = scanner.nextInt();
            if (choice <= 0 || choice > range) {
                IO.println("Invalid choice.");
            } else {
                return choice;
            }
        }
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
        IO.println("==============================");
        IO.println("JAVA PROJECT MANAGEMENT SYSTEM");
        IO.println("==============================");
        IO.println();
        IO.println("Current User: " + user.name + (user instanceof AdminUser ? " (Admin) " : ""));
        IO.println();
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
    public int ManageProjects(int number_of_projects) {
        IO.println("==============================");
        IO.println("        PROJECT CATALOG       ");
        IO.println("==============================");
        IO.println();
        IO.println("Filter Options:");
        IO.println("1. View All Projects (%s)".formatted(number_of_projects));
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
            if (task.Status == Status.COMPLETED) {
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
    public Task addTask() {
        IO.println("Enter task name:");
        String name = IO.readln();
        IO.println("Enter assign project ID:");
        String projectID = IO.readln();
        IO.println("Enter initial status (Pending/In Progress/Completed):");
        String rawStatus = IO.readln();
        Task.STATUS status;
        switch (rawStatus.toLowerCase()) {
            case "completed":
                status = Task.STATUS.COMPLETED;
                break;
            case "in progress":
                status = Task.STATUS.IN_PROGRESS;
                break;
            case "pending":
                status = Task.STATUS.PENDING;
                break;
        }
        return new Task();
    }
    public void ManageTasks() {

    }
    public void ViewStatus() {

    }
    public void SwitchUser() {

    }
}
