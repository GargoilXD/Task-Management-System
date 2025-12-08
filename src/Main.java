import interfaces.Completable;
import models.Projects.*;
import models.StatusReport;
import models.Task;
import models.Users.*;
import services.ProjectService;
import services.ReportService;
import services.TaskService;
import services.UserService;
import utilities.ConsoleMenu.*;
import utilities.Validator;
import utilities.exceptions.EntityAlreadyExists;
import utilities.exceptions.EntityDoesNotExist;

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
        Validator.input = new Scanner(System.in);
    }
    public static void main(String[] args) {
        initialize();
        getLoginMenu().asRoot().display();
        Validator.input.close();
    }
    static ConsoleMenu getLoginMenu() {
        return new DynamicMenu(
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
                            System.err.println("Login Failed. Please try again later.");
                            System.exit(0);
                        }
                        System.out.println("Username: ");
                        username = Validator.input.nextLine();
                        System.out.println("Enter Password: ");
                        password = Validator.input.nextLine();
                        if (userService.validateCredentials(username, password)) {
                            ConsoleMenu mainMenu = getMainMenu();
                            mainMenu.display();
                            if (mainMenu.goToRoot) {
                                tries = 5;
                            } else {
                                System.exit(0);
                            }
                        } else {
                            tries--;
                            System.err.printf("Wrong username or password. %s Tries left %n", tries);
                        }
                    }
                }
        );
    }
    static ConsoleMenu getMainMenu() {
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
                ---------""", userService.currentUser.Name, (userService.currentUser instanceof AdminUser ? "(Admin)" : "")),
                // Role based Access
                // Admins can: ManageProjects, ManageTasks, ViewStatusReports, ManageUsers and SwitchUsers
                // RegularUsers can: ManageProjects, ManageTasks, ViewStatusReports and SwitchUsers
                (userService.currentUser instanceof AdminUser)?
                        new ConsoleMenu[] {
                                getManageProjectMenu(),
                                getManageTaskMenu(),
                                getViewStatusReportMenu(),
                                getManageUsersMenu(),
                                getSwitchUserMenu()
                        }:
                        new ConsoleMenu[] {
                                getManageProjectMenu(),
                                getManageTaskMenu(),
                                getViewStatusReportMenu(),
                                getSwitchUserMenu()
                        },
                "Exit",
                "Enter your choice:"
        );
    }
    static String displayProjectDetails(Project project, Task[] tasks) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(
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
        ));
        builder.append("\n");
        builder.append("Associated Tasks: \n");
        builder.append("-".repeat(80));
        builder.append("\n");
        builder.append("ID   | TASK NAME            | STATUS\n");
        builder.append("-".repeat(80));
        builder.append("\n");
        for (Task task : tasks) {
            builder.append(String.format("%s | %-20s | %s\n", task.ID, task.Name, task.Status));
        }
        builder.append("-".repeat(80));
        builder.append("\n");
        double completionRate = 0;
        for (Task task : tasks) {
            if (task.Status == Task.STATUS.COMPLETED) {
                completionRate += 1;
            }
        }
        completionRate /= tasks.length > 0 ? tasks.length : 1;
        builder.append(String.format("Completion Rate: %.2f", completionRate * 100)).append("%\n");
        return builder.toString();
    }
    static ConsoleMenu getManageUsersMenu() {
        return new OptionMenu(
                "Manage Users",
                """
                    ==============================
                             MANAGE USERS         \s
                    ==============================
                    
                    """,
                "Options:",
                new ConsoleMenu[] {
                        getCreateUserMenu(),
                        getAssignUserTasksMenu()
                },
                "Back",
                "Enter your choice"
        );
    }
    static ConsoleMenu getCreateUserMenu() {
        return new DynamicMenu(
                "Create User",
                """
                    =================
                       CREATE USER   \s
                    =================
                    """,
                () -> {
                    System.out.println("Username: ");
                    String username = Validator.manualValidation((input) -> userService.findUserByName(input) == null, "Username already taken");
                    System.out.println("Password: ");
                    String password = Validator.input.nextLine();
                    System.out.println("Email: ");
                    String email = Validator.getValidEmail();
                    System.out.println("Is Admin? Y/N: ");
                    boolean isAdmin = Validator.getValidChoice();
                    try {
                        userService.addUser(isAdmin? new AdminUser(username, password, email) : new RegularUser(username, password, email));
                        System.out.println("User created!");
                    } catch (EntityAlreadyExists e) {
                        System.out.println(e.getMessage());
                    }
                }
        );
    }
    static ConsoleMenu getAssignUserTasksMenu() {
        return new DynamicMenu(
                "Assign User Tasks",
                """
                    =======================
                       ASSIGN USER TASKS   \s
                    =======================
                    """,
                () -> {
                    System.out.println("Users:");
                    System.out.println("-".repeat(80));
                    System.out.println("ID   | USERNAME       | EMAIL                          | TASKS ASSIGNED ");
                    System.out.println("-".repeat(80));
                    for (User user: userService.getUsers()) {
                        System.out.printf("%s | %-14s | %-30s | %s%n", user.ID, user.Name, user.Email, (user instanceof RegularUser)? ((RegularUser)(user)).assignedTasks.size : "ADMIN");
                    }
                    System.out.println("-".repeat(80));
                    System.out.println();
                    System.out.println("Enter User ID: ");
                    String userID = Validator.getValidUserID();
                    RegularUser user;
                    switch (userService.findUserByID(userID)) {
                        case null -> {
                            System.err.println("User not found.");
                            System.out.println("Press enter to continue...");
                            Validator.input.nextLine();
                            return;
                        }
                        case AdminUser ignored -> {
                            System.err.println("Cannot assign an admin user.");
                            System.out.println("Press enter to continue...");
                            Validator.input.nextLine();
                            return;
                        }
                        case RegularUser regularUser -> user = regularUser;
                        default -> {
                            return;
                        }
                    }
                    Task[] tasks = taskService.getTasks();
                    System.out.println("Associated Tasks:");
                    System.out.println("-".repeat(80));
                    System.out.println("ID   | TASK NAME                      | STATUS          | ASSIGNED");
                    System.out.println("-".repeat(80));
                    // Find Tasks for user
                    for (Task task : tasks) {
                        if (task.Status != Completable.STATUS.COMPLETED) {
                            boolean userTask = false;
                            for (String taskID: user.getAssignedTasks()) {
                                if (taskID != null && taskID.equals(task.ID)) {
                                    userTask = true;
                                    break;
                                }
                            }
                            System.out.printf("%s | %-30s | %-15s | %s%n", task.ID, task.Name, task.Status, userTask? "o" : "x");
                        }
                    }
                    System.out.println("-".repeat(80));
                    System.out.println("Enter the Task ID of the Task you want to assign or unassign:");
                    String assignedTask = Validator.getValidTaskID();
                    if (taskService.findTaskByID(assignedTask) != null && taskService.findTaskByID(assignedTask).Status != Completable.STATUS.COMPLETED) {
                        System.out.println("Enter y to assign and n to unassign:");
                        boolean assign = Validator.getValidChoice();
                        if (assign) {
                            if (user.isAssignedTask(assignedTask)) {
                                System.err.println("Cannot assign an assigned task.");
                            } else {
                                user.assign(assignedTask);
                                System.out.println("Assigned Task: " + assignedTask + " to User: " + user.ID);
                            }
                        } else {
                            if (!user.isAssignedTask(assignedTask)) {
                                System.err.println("Cannot unassign an unassigned task.");
                            } else {
                                user.unassign(assignedTask);
                                System.out.println("Unassigned Task: " + assignedTask + " from User: " + user.ID);
                            }
                        }
                    } else {
                        System.err.println("No Such Pending or Running Task found.");
                    }
                }
        );
    }
    static ConsoleMenu getProjectDetailsMenu(Project project, Task[] tasks) {
        return new OptionMenu(
                "Project Details",
                """
                    ==============================
                            PROJECT DETAILS      \s
                    ==============================
                    
                    """ + displayProjectDetails(project, tasks),
                "Options:",
                // Role based access
                new ConsoleMenu[] {
                        getAddTaskMenu(project.ID),
                        getUpdateTaskMenu(),
                        getDeleteTaskMenu()
                },
                "Back",
                "Enter your choice:"
        );
    }
    static ConsoleMenu getAddTaskMenu(String defaultProjectID) {
        return new DynamicMenu(
                "Add New Task",
                """
                    ======================
                        ADD NEW TASK     \s
                    ======================
                    """,
                () -> {
                    System.out.println("Enter task name:");
                    String name = Validator.manualValidation((input) -> taskService.findTaskByName(input) == null, "Task name is already taken");
                    String projectID;
                    if (defaultProjectID.isEmpty()) {
                        System.out.println("Enter assign project ID:");
                        projectID = Validator.getValidProjectID();
                    } else {
                        System.out.printf("Enter assign project ID (leave empty for default: %s):%n", defaultProjectID);
                        projectID = Validator.getValidProjectID("");
                        if (projectID.isEmpty()) {
                            projectID = defaultProjectID;
                        }
                    }
                    System.out.println("Enter initial status (Pending/In Progress/Completed):");
                    Completable.STATUS status = Validator.getValidTaskStatus();
                    try {
                        taskService.createTask(projectID, name, status);
                        System.out.println("Task Created!");
                    } catch (EntityAlreadyExists e) {
                        System.err.println(e.getMessage());
                    }
                }
        );
    }
    static ConsoleMenu getUpdateTaskMenu() {
        return new DynamicMenu(
                "Update Task Status",
                """
                    ======================
                        UPDATE TASK     \s
                    ======================
                    """,
                () -> {
                    System.out.println("Enter task ID:");
                    String taskID = Validator.getValidTaskID();
                    System.out.println("Enter new status (Pending/In Progress/Completed):");
                    Completable.STATUS status = Validator.getValidTaskStatus();
                    try {
                        taskService.updateTask(taskID, status);
                        System.out.println("Task updated successfully.");
                    } catch (EntityAlreadyExists e) {
                        System.err.println(e.getMessage());
                    }
                }
        );
    }
    static ConsoleMenu getDeleteTaskMenu() {
        return new DynamicMenu(
                "Remove Task",
                """
                    ======================
                        REMOVE TASK     \s
                    ======================
                    """,
                () -> {
                    System.out.println("Enter task ID:");
                    String taskID = Validator.getValidTaskID();
                    try {
                        taskService.removeTask(taskID);
                        System.out.println("Task removed successfully.");
                    } catch (EntityDoesNotExist e) {
                        System.err.println(e.getMessage());
                    }
                }
        );
    }
    // Note: doesn't return a menu. It's specifically for the getBrowseProjectsMenu DynamicMenus
    static void projectFilterProcess(ProjectService.FILTER filter) {
        Project[] projects;
        // If the filter is for BUDGET then we ask for the range
        if (filter instanceof ProjectService.FILTER.BUDGET) {
            int min;
            int max;
            while (true) {
                System.out.println("Enter min budget:");
                min = (int) Validator.getValidNumber(0);
                System.out.println("Enter max budget:");
                max = (int) Validator.getValidNumber(0);
                if (min > max) {
                    System.err.println("Minimum budget is greater than maximum budget.");
                    System.out.println("Try again:");
                } else {
                    break;
                }
            }
            // Update the range for the FatEnum
            filter = new ProjectService.FILTER.BUDGET(min, max);
        }
        projects = projectService.filterProjects(filter);
        System.out.println("-".repeat(80));
        System.out.println("ID   | PROJECT NAME                             | TYPE       | TEAM SIZE | BUDGET");
        System.out.println("-".repeat(80));
        for (Project project : projects) {
            System.out.printf("%s | %-40s | %-10s | %-9s | %s%n", project.ID, project.Name, project instanceof SoftwareProject ? "Software" : "Hardware", project.TeamSize, project.Budget);
            System.out.printf("     | Description: %s%n", project.Description);
        }
        System.out.println("-".repeat(80));
        System.out.println("Enter project ID to view details (0 to return):");
        String response = Validator.getValidProjectID("0");
        if (!response.equals("0")) {
            Project project = projectService.findProjectByID(response);
            if (project == null) {
                System.err.println("Project " + response + " not found.");
                return;
            }
            Task[] tasks = taskService.getProjectTasks(response);
            getProjectDetailsMenu(project, tasks).display();
        }
    }
    static ConsoleMenu getBrowseProjectsMenu() {
        return new OptionMenu(
                "Browse Projects",
                """
                    ==============================
                            PROJECT CATALOG      \s
                    ==============================
                    """,
                "Filter Options:",
                new ConsoleMenu[] {
                        new DynamicMenu("View All Projects", "", () -> projectFilterProcess(new ProjectService.FILTER.ALL())),
                        new DynamicMenu("Software Projects Only", "", () -> projectFilterProcess(new ProjectService.FILTER.SOFTWARE())),
                        new DynamicMenu("Hardware Projects Only", "", () -> projectFilterProcess(new ProjectService.FILTER.HARDWARE())),
                        new DynamicMenu("Search by Budget Range", "", () -> projectFilterProcess(new ProjectService.FILTER.BUDGET())),
                },
                "Back",
                "Enter filter choice:"
        );
    }
    static ConsoleMenu getCreateProjectMenu() {
        return new DynamicMenu(
                "Create Project",
                """
                        ======================
                            CREATE PROJECT    \s
                        ======================
                        """,
                () -> {
                    System.out.println("Enter project name:");
                    String name = Validator.manualValidation((input) -> projectService.findProjectByName(input) == null, "Project name is already taken");
                    System.out.println("Enter project type:");
                    boolean isSoftware = Validator.getValidProjectType();
                    System.out.println("Enter project description:");
                    String description = Validator.input.nextLine();
                    System.out.println("Enter team size:");
                    int teamSize = Validator.getValidInteger(1);
                    System.out.println("Enter budget:");
                    double budget = Validator.getValidNumber(0);
                    try {
                        projectService.createProject(name, description, teamSize, budget, isSoftware);
                        System.out.println("Project created successfully.");
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
        );
    }
    static ConsoleMenu getDeleteProjectMenu() {
        return new DynamicMenu(
                "Remove Project",
                """
                        ======================
                            REMOVE PROJECT    \s
                        ======================
                        """,
                () -> {
                    System.out.println("Enter Project ID:");
                    String projectID = Validator.manualValidation((input) -> projectService.findProjectByID(input) != null, "Project does not exist");
                    try {
                        projectService.removeProject(projectID);
                        System.out.println("Project " + projectID + " has been removed.");
                    } catch (EntityDoesNotExist e) {
                        System.err.println(e.getMessage());
                    }
                }
        );
    }
    static ConsoleMenu getManageProjectMenu() {
        return new OptionMenu(
                "Manage Projects",
                """
                        ==============================
                                 MANAGE PROJECTS      \s
                        ==============================
                        """,
                "Options:",
                (userService.currentUser instanceof AdminUser)
                        ? new ConsoleMenu[] {
                        getCreateProjectMenu(),
                        getDeleteProjectMenu(),
                        getBrowseProjectsMenu()
                }
                        : new ConsoleMenu[] {
                        getBrowseProjectsMenu()
                },
                "Back",
                "Enter your choice:"
        );
    }
    static ConsoleMenu getManageTaskMenu() {
        return new OptionMenu(
                "Manage Tasks",
                """
                        ==============================
                                 MANAGE TASKS         \s
                        ==============================
                        """,
                "Options:",
                new ConsoleMenu[] {
                        getViewTasksForProjectMenu(),
                        getAddTaskMenu(""),
                        getUpdateTaskMenu(),
                        getDeleteTaskMenu(),
                },
                "Back",
                "Enter your choice:"
        );
    }
    static ConsoleMenu getViewTasksForProjectMenu() {
        return new DynamicMenu(
                "View Tasks For Project",
                """
                    ==============================
                            PROJECT CATALOG      \s
                    ==============================
                    """,
                () -> {
                    System.out.println("Enter project ID to view details (0 to return):");
                    String response = Validator.getValidProjectID("0");
                    if (!response.equals("0")) {
                        Project project = projectService.findProjectByID(response);
                        if (project == null) {
                            System.err.println("Project " + response + " does not exist.");
                            return;
                        }
                        Task[] tasks = taskService.getProjectTasks(response);
                        System.out.println(displayProjectDetails(project, tasks));
                    }
                    System.out.println("Press enter to continue...");
                    Validator.input.nextLine();
                }
        );
    }
    static ConsoleMenu getViewStatusReportMenu() {
        return new DynamicMenu(
                "View Status Reports",
                """
                    ==============================
                         PROJECT STATUS REPORT    \s
                    ==============================
                    """,
                () -> {
                    reportService.updateReports();
                    System.out.println("-".repeat(80));
                    System.out.println("PROJECT ID | PROJECT NAME                   | TASKS | COMPLETED | PROGRESS (%)");
                    System.out.println("-".repeat(80));
                    for (StatusReport report : reportService.reports.toArray()) {
                        System.out.println(String.format("%-10s | %-30s | %-5s | %-9s | %.2f", report.ProjectID, report.ProjectName, report.Tasks, report.CompletedTasks, report.Progress) + "%");
                    }
                    System.out.println("-".repeat(80));
                    System.out.printf("AVERAGE COMPLETION: %.2f%s%n", reportService.AverageCompletion, "%");
                    System.out.println("-".repeat(80));
                    System.out.println("Press enter to continue...");
                    Validator.input.nextLine();
                }
        );
    }
    static ConsoleMenu getSwitchUserMenu() {
        ConsoleMenu switchUserMenu = new DynamicMenu("Switch User", "", () -> {});
        switchUserMenu.goToRoot = true;
        return switchUserMenu;
    }
}