import interfaces.Completable;
import models.*;
import services.ProjectService;
import services.TaskService;
import services.UserService;
import utils.ConsoleMenu;
import utils.ManualMenu;
import utils.OptionMenu;
import utils.ValidationUtils;

import java.util.Scanner;

public class Main {
    // Services
    static UserService userService = new UserService();
    static TaskService taskService = new TaskService();
    static ProjectService projectService = new ProjectService();
    public static void main(String[] args) {
        initialization();
        Scanner scanner = new Scanner(System.in);
        // Display the LoginMenu
        getLoginMenu(scanner, true).display();
        // Scanner is closed to free memory. Not sure if that was necessary.
        scanner.close();
    }

    // Initialization by creating sample values
    static void initialization() {
        userService.addUser(new AdminUser("Ama", "12345"));
        RegularUser user = new RegularUser("Kofi", "12345");
        user.assign("T001");
        user.assign("T002");
        user.assign("T003");
        userService.addUser(user);
        projectService.createProject(new SoftwareProject("Alpha Tracker", "Task tracking app for startups", 5, 15000));
        taskService.addTask("P001", "Design Database", Task.STATUS.COMPLETED);
        taskService.addTask("P001", "Implement API", Task.STATUS.IN_PROGRESS);
        taskService.addTask("P001", "Write Unit Tests", Task.STATUS.PENDING);

        projectService.createProject(new HardwareProject("IoT Sensor Kit", "Sensor prototype for smart devices", 3, 1000));
        taskService.addTask("P002", "Gather Materials", Task.STATUS.COMPLETED);
        taskService.addTask("P002", "Build prototype", Task.STATUS.IN_PROGRESS);

        projectService.createProject(new SoftwareProject("Cloud Backup Tool", "Automated backup solution for SMBs", 4, 12000));
        taskService.addTask("P003", "Define Backup Strategy", Task.STATUS.COMPLETED);
        taskService.addTask("P003", "Develop Sync Engine", Task.STATUS.IN_PROGRESS);
        taskService.addTask("P003", "Create UI Dashboard", Task.STATUS.PENDING);

        projectService.createProject(new HardwareProject("Smart Thermostat", "Energy-efficient home climate control", 6, 2500));
        taskService.addTask("P004", "Circuit Design", Task.STATUS.COMPLETED);
        taskService.addTask("P004", "Firmware Development", Task.STATUS.IN_PROGRESS);
        taskService.addTask("P004", "Enclosure Prototyping", Task.STATUS.PENDING);

        projectService.createProject(new SoftwareProject("HR Onboarding Portal", "Streamlined employee onboarding system", 6, 18000));
        taskService.addTask("P005", "User Authentication", Task.STATUS.COMPLETED);
        taskService.addTask("P005", "Document Upload Module", Task.STATUS.IN_PROGRESS);
        taskService.addTask("P005", "Integration with Payroll", Task.STATUS.PENDING);

        projectService.createProject(new SoftwareProject("EduQuiz Platform", "Interactive quiz app for educators", 4, 9500));
        taskService.addTask("P006", "User Registration Flow", Task.STATUS.COMPLETED);
        taskService.addTask("P006", "Quiz Builder UI", Task.STATUS.IN_PROGRESS);
        taskService.addTask("P006", "Real-time Grading Engine", Task.STATUS.PENDING);

        projectService.createProject(new HardwareProject("Solar-Powered Charger", "Portable charger using renewable energy", 5, 1800));
        taskService.addTask("P007", "Solar Panel Sourcing", Task.STATUS.COMPLETED);
        taskService.addTask("P007", "Battery Integration", Task.STATUS.IN_PROGRESS);
        taskService.addTask("P007", "Safety & Overcharge Protection", Task.STATUS.PENDING);
    }
    // Display Project Details. Moved to function because it's used multiple times.
    static String displayProjectDetails(Project project, Task[] tasks) {
        StringBuilder builder = new StringBuilder();
        builder.append(
                String.format(
                        """
                                Project Name: %s
                                Type: %s
                                Team Size: %s
                                Budget: %s
                                """,
                        project.Name,
                        project instanceof SoftwareProject? "Software" : "Hardware",
                        project.TeamSize,
                        project.Budget
                )
        );
        builder.append("\n");
        builder.append("Associated Tasks: \n");
        builder.append("-".repeat(80));
        builder.append("\n");
        builder.append("ID   | TASK NAME            | STATUS\n");
        builder.append("-".repeat(80));
        builder.append("\n");
        for (Task task : tasks) {
            builder.append(String.format("%s | %-20s | %s\n", task.ID, task.name, task.status));
        }
        builder.append("-".repeat(80));
        builder.append("\n");
        double completionRate = 0;
        for (Task task : tasks) {
            if (task.status == Task.STATUS.COMPLETED) {
                completionRate += 1;
            }
        }
        completionRate /= tasks.length;
        builder.append(String.format("Completion Rate: %.2f", completionRate * 100)).append("%\n");
        return builder.toString();
    }
    // Now here are the Menu functions that display different menus.
    // It is designed to be modular
    static ConsoleMenu getLoginMenu(Scanner scanner, boolean main) {
        return new ManualMenu(
                "Login",
                """
                        ==============================
                                WELCOME TO JPMS       \s
                        ==============================""",
                () -> {
                    String username;
                    String password;
                    int tries = 5;
                    while (true) {
                        if (tries == 0) {
                            System.out.println("Login Failed. Please try again later.");
                            if (main) {
                                System.exit(0);
                            } else {
                                return;
                            }
                        }
                        System.out.println("Username: ");
                        username = scanner.nextLine();
                        System.out.println("Enter Password: ");
                        password = scanner.nextLine();
                        if (userService.validateCredentials(username, password)) {
                            getMainMenu(scanner).display();
                            System.exit(0);
                        }
                        tries--;
                        System.out.printf("Wrong username or password. %s Tries left %n", tries);
                    }
                }
        );
    }
    // Main Menu
    static ConsoleMenu getMainMenu(Scanner scanner) {
        return new OptionMenu(
                "Main Menu",
                """
                        =====================================
                        JAVA PROJECT MANAGEMENT SYSTEM (JPMS)
                        =====================================
                        """,
                String.format("""
                            Current User: %s %s
                            
                            Main Menu
                            ---------""", userService.current_user.name, (userService.current_user instanceof AdminUser ? "(Admin)" : "")),
                // Role based Access
                // Admins can: ManageProjects, ManageTasks, ViewStatusReports, ManageUsers and SwitchUsers
                // RegularUsers can: ManageProjects, ManageTasks, ViewStatusReports and SwitchUsers
                (userService.current_user instanceof AdminUser)?
                        new ConsoleMenu[] {
                            getManageProjectMenu(scanner),
                            getManageTaskMenu(scanner),
                            getViewStatusReportMenu(scanner),
                            getManageUsersMenu(scanner),
                            getSwitchUserMenu(scanner),
                        }:
                        new ConsoleMenu[] {
                                getManageProjectMenu(scanner),
                                getManageTaskMenu(scanner),
                                getViewStatusReportMenu(scanner),
                                getSwitchUserMenu(scanner),
                        }
                ,
                "Exit",
                "Enter your choice:",
                scanner
        );
    }
    static ConsoleMenu getManageUsersMenu(Scanner scanner) {
        return new OptionMenu(
                "Manage Users",
                """
                        ==============================
                                 MANAGE USERS         \s
                        ==============================
                        
                        """,
                "Options:",
                new ConsoleMenu[] {
                        getCreateUserMenu(scanner),
                        getAssignUserTasksMenu(scanner)
                },
                "Back",
                "Enter your choice",
                scanner
        );
    }
    static ConsoleMenu getCreateUserMenu(Scanner scanner) {
        return new ManualMenu(
                "Create User",
                """
                        =================
                           CREATE USER   \s
                        =================
                        """,
                () -> {
                    System.out.println("Username: ");
                    String username = scanner.nextLine();
                    System.out.println("Password: ");
                    String password = scanner.nextLine();
                    System.out.println("Email: ");
                    String email = scanner.nextLine();
                    String admin;
                    do {
                        System.out.println("Is Admin? Y/N: ");
                        admin = scanner.nextLine().toLowerCase();
                        if (admin.equals("y") || admin.equals("n")) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please try again.");
                        }
                    } while (true);
                    userService.addUser(admin.equals("y")? new AdminUser(username, password, email) : new RegularUser(username, password, email));
                }
        );
    }
    static ConsoleMenu getAssignUserTasksMenu(Scanner scanner) {
        return new ManualMenu(
                "Assign User Tasks",
                """
                        =======================
                           ASSIGN USER TASKS   \s
                        =======================
                        """,
                () -> {
                    System.out.println("Enter User ID: ");
                    String userID = ValidationUtils.getValidUserID(scanner);
                    User user = userService.findUser(userID);
                    Task[] tasks;
                    switch (user) {
                        case null -> {
                            System.out.println("User not found.");
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            return;
                        }
                        case AdminUser ignored -> {
                            System.out.println("Cannot assign an admin user.");
                            System.out.println("Press enter to continue...");
                            scanner.nextLine();
                            return;
                        }
                        case RegularUser ignored -> tasks = taskService.getTasks();
                        default -> {
                            // Should be unreachable here.
                            return;
                        }
                    }
                    System.out.println("Associated Tasks:");
                    System.out.println("-".repeat(80));
                    System.out.println("ID   | TASK NAME                      | STATUS          | ASSIGNED");
                    System.out.println("-".repeat(80));
                    // Find Tasks for user
                    for (Task task : tasks) {
                        if (task.status != Completable.STATUS.COMPLETED) {
                            boolean userTask = false;
                            for (String taskID: ((RegularUser) user).assignedTasks) {
                                if (taskID != null && taskID.equals(task.ID)) {
                                    userTask = true;
                                    break;
                                }
                            }
                            System.out.printf("%s | %-30s | %-15s | %s%n", task.ID, task.name, task.status, userTask? "o" : "x");
                        }
                    }
                    System.out.println("-".repeat(80));
                    System.out.println("Enter the Task ID of the Task you want to assign:");
                    String assignedTask = ValidationUtils.getValidTaskID(scanner);
                    if (taskService.findTaskByID(assignedTask) != null && taskService.findTaskByID(assignedTask).status != Completable.STATUS.COMPLETED) {
                        ((RegularUser) user).assign(assignedTask);
                        System.out.println("Task assigned successfully.");
                    } else {
                        System.out.println("No Such Pending or Running Task found.");
                    }
                }
        );
    }
    static ConsoleMenu getProjectDetailsMenu(Project project, Task[] tasks, Scanner scanner) {
        return new OptionMenu(
                "Project Details",
                """
                        ==============================
                                PROJECT DETAILS      \s
                        ==============================
                        
                        """ + displayProjectDetails(project, tasks),
                "Options:",
                // Role based access
                (userService.current_user instanceof AdminUser)
                        ? new ConsoleMenu[] {
                                getAddTaskMenu(project.ID, scanner),
                                getUpdateTaskMenu(project.ID, scanner),
                                getDeleteTaskMenu(project.ID, scanner),
                        }
                        : new ConsoleMenu[] {
                                getUpdateTaskMenu(project.ID, scanner),
                        },
                "Back",
                "Enter your choice:",
                scanner
        );
    }
    static ConsoleMenu getAddTaskMenu(String defaultProjectID, Scanner scanner) {
        return new ManualMenu(
                "Add New Task",
                """
                        ======================
                            ADD NEW TASK     \s
                        ======================
                        """,
                () -> {
                    System.out.println("Enter task name:");
                    String name = scanner.nextLine();
                    String projectID;
                    if (defaultProjectID.isEmpty()) {
                        System.out.println("Enter assign project ID:");
                        projectID = ValidationUtils.getValidProjectID(scanner);
                    } else {
                        System.out.printf("Enter assign project ID (leave empty for default: %s):%n", defaultProjectID);
                        projectID = ValidationUtils.getValidProjectID(scanner, "");
                        if (projectID.isEmpty()) {
                            projectID = defaultProjectID;
                        }
                    }
                    System.out.println("Enter initial status (Pending/In Progress/Completed):");
                    Completable.STATUS status = ValidationUtils.getValidTaskStatus(scanner);
                    if (taskService.addTask(projectID, name, status)) {
                        System.out.printf("Task \"%s\" added successfully to Project %s\n%n", name, projectID);
                    } else {
                        System.out.printf("Task \"%s\" was not added\n%n", name);
                    }
                }
        );
    }
    static ConsoleMenu getUpdateTaskMenu(String defaultProjectID, Scanner scanner) {
        return new ManualMenu(
                "Update Task Status",
                """
                        ======================
                            UPDATE TASK     \s
                        ======================
                        """,
                () -> {
                    System.out.println("Enter task ID:");
                    String taskID = ValidationUtils.getValidTaskID(scanner);
                    String projectID;
                    if (defaultProjectID.isEmpty()) {
                        System.out.println("Enter assign project ID:");
                        projectID = ValidationUtils.getValidProjectID(scanner);
                    } else {
                        System.out.printf("Enter assign project ID (leave empty for default: %s):%n", defaultProjectID);
                        projectID = ValidationUtils.getValidProjectID(scanner, "");
                        if (projectID.isEmpty()) {
                            projectID = defaultProjectID;
                        }
                    }
                    System.out.println("Enter new status (Pending/In Progress/Completed):");
                    Completable.STATUS status = ValidationUtils.getValidTaskStatus(scanner);
                    if (taskService.updateTask(projectID, taskID, status)) {
                        System.out.printf("Task \"%s\" updated successfully%n", taskID);
                    } else {
                        System.out.printf("Task \"%s\" update failed%n", taskID);
                    }
                }
        );
    }
    static ConsoleMenu getDeleteTaskMenu(String defaultProjectID, Scanner scanner) {
        return new ManualMenu(
                "Remove Task",
                """
                        ======================
                            REMOVE TASK     \s
                        ======================
                        """,
                () -> {
                    System.out.println("Enter task ID:");
                    String taskID = ValidationUtils.getValidTaskID(scanner);
                    String projectID;
                    if (defaultProjectID.isEmpty()) {
                        System.out.println("Enter assign project ID:");
                        projectID = ValidationUtils.getValidProjectID(scanner);
                    } else {
                        System.out.printf("Enter assign project ID (leave empty for default: %s):%n", defaultProjectID);
                        projectID = ValidationUtils.getValidProjectID(scanner, "");
                        if (projectID.isEmpty()) {
                            projectID = defaultProjectID;
                        }
                    }
                    if (taskService.removeTask(projectID, taskID)) {
                        System.out.printf("Task \"%s\" removed successfully%n", taskID);
                    } else {
                        System.out.printf("Task \"%s\" remove failed%n", taskID);
                    }
                }
        );
    }
    // Note: doesn't return a menu. It's specifically for the getBrowseProjectsMenu ManualMenus
    static void projectFilterProcess(ProjectService.FILTER filter, Scanner scanner) {
        Project[] projects;
        // If the filter is for BUDGET then we ask for the range
        if (filter == ProjectService.FILTER.BUDGET) {
            System.out.println("Enter min budget:");
            int min = (int) ValidationUtils.getValidNumber(scanner, 0);
            System.out.println("Enter max budget:");
            int max = (int) ValidationUtils.getValidNumber(scanner, 0);
            projects = projectService.filterProjects(min, max);
        } else {
            projects = projectService.filterProjects(filter);
        }
        System.out.println("-".repeat(80));
        System.out.println("ID   | PROJECT NAME                             | TYPE       | TEAM SIZE | BUDGET");
        System.out.println("-".repeat(80));
        for (Project project : projects) {
            System.out.printf("%s | %-40s | %-10s | %-9s | %s%n", project.ID, project.Name, project instanceof SoftwareProject ? "Software" : "Hardware", project.TeamSize, project.Budget);
            System.out.printf("     | Description: %s%n", project.Description);
        }
        System.out.println("-".repeat(80));
        System.out.println("Enter project ID to view details (0 to return):");
        String response = ValidationUtils.getValidProjectID(scanner, "0");
        if (!response.equals("0")) {
            Project project = projectService.findProject(response);
            Task[] tasks = taskService.getProjectTasks(response);
            getProjectDetailsMenu(project, tasks, scanner).display();
        }
    }
    static ConsoleMenu getBrowseProjectsMenu(Scanner scanner) {
        return new OptionMenu(
                "Browse Projects",
                """
                        ==============================
                                PROJECT CATALOG      \s
                        ==============================
                        """,
                "Filter Options:",
                new ConsoleMenu[] {
                        new ManualMenu("View All Projects", "", () -> projectFilterProcess(ProjectService.FILTER.ALL, scanner)),
                        new ManualMenu("Software Projects Only", "", () -> projectFilterProcess(ProjectService.FILTER.SOFTWARE, scanner)),
                        new ManualMenu("Hardware Projects Only", "", () -> projectFilterProcess(ProjectService.FILTER.HARDWARE, scanner)),
                        new ManualMenu("Search by Budget Range", "", () -> projectFilterProcess(ProjectService.FILTER.BUDGET, scanner)),
                },
                "Back",
                "Enter filter choice:",
                scanner
        );
    }
    static ConsoleMenu getCreateProjectMenu(Scanner scanner) {
        return new ManualMenu(
                "Create Project",
                """
                        ======================
                            CREATE PROJECT    \s
                        ======================
                        """,
                () -> {
                    System.out.println("Enter project name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter project type:");
                    boolean isSoftware = ValidationUtils.getValidProjectType(scanner);
                    System.out.println("Enter project description:");
                    String description = scanner.nextLine();
                    System.out.println("Enter team size:");
                    int teamSize = (int) ValidationUtils.getValidNumber(scanner, 1);
                    System.out.println("Enter budget:");
                    double budget = ValidationUtils.getValidNumber(scanner, 0);
                    Project project;
                    if (isSoftware) {
                        project = new SoftwareProject(name, description, teamSize, budget);
                    } else {
                        project = new HardwareProject(name, description, teamSize, budget);
                    }
                    if (projectService.createProject(project)) {
                        System.out.printf("Project \"%s\" created successfully\n%n", name);
                    } else {
                        System.out.printf("Project \"%s\" was not added\n%n", name);
                    }
                }
        );
    }
    static ConsoleMenu getDeleteProjectMenu(Scanner scanner) {
        return new ManualMenu(
                "Remove Project",
                """
                        ======================
                            REMOVE PROJECT    \s
                        ======================
                        """,
                () -> {
                    System.out.println("Enter Project ID:");
                    String projectID = ValidationUtils.getValidProjectID(scanner);
                    if (projectService.removeProject(projectID)) {
                        System.out.printf("Project \"%s\" removed successfully%n", projectID);
                    } else {
                        System.out.printf("Project \"%s\" remove failed%n", projectID);
                    }
                }
        );
    }
    static ConsoleMenu getManageProjectMenu(Scanner scanner) {
        return new OptionMenu(
                "Manage Projects",
                """
                        ==============================
                                 MANAGE PROJECTS      \s
                        ==============================
                        """,
                "Options:",
                (userService.current_user instanceof AdminUser)? new ConsoleMenu[]{
                        getCreateProjectMenu(scanner),
                        getDeleteProjectMenu(scanner),
                        getBrowseProjectsMenu(scanner),
                }:new ConsoleMenu[]{
                        getBrowseProjectsMenu(scanner),
                }
                ,
                "Back",
                "Enter your choice:",
                scanner
        );
    }
    static ConsoleMenu getManageTaskMenu(Scanner scanner) {
        return new OptionMenu(
                "Manage Tasks",
                """
                        ==============================
                                 MANAGE TASKS         \s
                        ==============================
                        """,
                "Options:",
                // Role based access
                (userService.current_user instanceof AdminUser)?
                        new ConsoleMenu[]{
                        getViewTasksForProjectMenu(scanner),
                        getAddTaskMenu("", scanner),
                        getUpdateTaskMenu("", scanner),
                        getDeleteTaskMenu("", scanner),
                }:
                        new ConsoleMenu[]{
                        getViewTasksForProjectMenu(scanner),
                        getUpdateTaskMenu("", scanner),
                },
                "Back",
                "Enter your choice:",
                scanner
        );
    }
    static ConsoleMenu getViewTasksForProjectMenu(Scanner scanner) {
        return new ManualMenu(
                "View Tasks For Project",
                """
                        ==============================
                                PROJECT CATALOG      \s
                        ==============================
                        """,
                () -> {
                    System.out.println("Enter project ID to view details (0 to return):");
                    String response = ValidationUtils.getValidProjectID(scanner, "0");
                    if (!response.equals("0")) {
                        Project project = projectService.findProject(response);
                        Task[] tasks = taskService.getProjectTasks(response);
                        System.out.println(displayProjectDetails(project, tasks));
                    }
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                }
        );
    }
    static ConsoleMenu getViewStatusReportMenu(Scanner scanner) {
        return new ManualMenu(
                "View Status Reports",
                """
                        ==============================
                             PROJECT STATUS REPORT    \s
                        ==============================
                        """,
                () -> {
                    Project[] projects = projectService.filterProjects(ProjectService.FILTER.ALL);
                    System.out.println("-".repeat(80));
                    System.out.println("PROJECT ID | PROJECT NAME                   | TASKS | COMPLETED | PROGRESS (%)");
                    System.out.println("-".repeat(80));
                    double average_completion = 0;
                    for (Project project : projects) {
                        Task[] tasks = taskService.getProjectTasks(project.ID);
                        int completedTasks = 0;
                        for (Task task : tasks) {
                            if (task.status == Task.STATUS.COMPLETED) {
                                completedTasks++;
                            }
                        }
                        double progress = (completedTasks / (double) tasks.length) * 100;
                        average_completion += progress;
                        System.out.println(String.format("%-10s | %-30s | %-5s | %-9s | %.2f", project.ID, project.Name, tasks.length, completedTasks, progress) + "%");
                    }
                    System.out.println("-".repeat(80));
                    System.out.println(String.format("AVERAGE COMPLETION: %.2f", average_completion / projects.length) + "%");
                    System.out.println("-".repeat(80));
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                }
        );
    }
    static ConsoleMenu getSwitchUserMenu(Scanner scanner) {
        // Login menu reuse
        ConsoleMenu menu = getLoginMenu(scanner, false);
        menu.name = "Switch User";
        menu.title = """
                        ==============================
                                  SWITCH USER         \s
                        ==============================""";
        return menu;
    }
}