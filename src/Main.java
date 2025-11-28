import interfaces.Completable;
import models.*;
import services.ProjectService;
import services.TaskService;
import utils.ConsoleMenu;
import utils.ManualMenu;
import utils.OptionMenu;
import utils.ValidationUtils;

User[] users = new User[] {
        new AdminUser("Ama", "12345"),
        new RegularUser("Kofi", "12345"),
};
User current_user = new AdminUser("Kobby", "12345");
TaskService taskService = new TaskService();
ProjectService projectService = new ProjectService();
ConsoleMenu consoleMenu;

void initialization() {
    projectService.createProject(new SoftwareProject("Alpha Tracker", "Task tracking app for startups", 5, 15000));
    taskService.addTask("P001", "Design Database", Task.STATUS.COMPLETED);
    taskService.addTask("P001", "Implement API", Task.STATUS.IN_PROGRESS);
    taskService.addTask("P001", "Write Unit Tests", Task.STATUS.PENDING);
    projectService.createProject(new HardwareProject("IoT Sensor Kit", "Sensor prototype for smart devices", 3, 1000));
    taskService.addTask("P002", "Gather Materials", Task.STATUS.COMPLETED);
    taskService.addTask("P002", "Build prototype", Task.STATUS.IN_PROGRESS);
}
boolean login() {
    String username;
    String password;
    int tries = 0;
    while (true) {
        if (tries > 5) {
            IO.println("Login Failed. Please try again.");
            return false;
        }
        IO.println("Username: ");
        username = IO.readln();
        IO.println("Enter Password: ");
        password = IO.readln();
        for (User user: users) {
            if (user.name.equals(username) && (user.password.equals(password))) {
                current_user = user;
                return true;
            }
        }
        IO.println("Wrong username or password.");
        tries++;
    }
}
void displayProjectDetailsMenu(Project project, Task[] tasks, Scanner scanner) {
    OptionMenu projectDetailsMenu = new OptionMenu(
            "Project Details",
            """
                    ==============================
                            PROJECT DETAILS      \s
                    ==============================
                    """,
            () -> {
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
                                project instanceof SoftwareProject
                                        ? "Software"
                                        : "Hardware",
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
                builder.append("Completion Rate: ").append(completionRate * 100).append("%\n");
                return builder.toString();
            },
            "Options:",
            new ConsoleMenu[] {
                    displayAddTaskMenu(project.ID),
                    displayUpdateTaskMenu(project.ID),
                    displayDeleteTaskMenu(project.ID),
            },
            "Back",
            "Enter your choice:",
            scanner::nextInt
    );
    projectDetailsMenu.display();
}
ConsoleMenu displayAddTaskMenu(String defaultProjectID) {
    return new ManualMenu(
            "Add New Task",
            """
                    ======================
                        ADD NEW TASK     \s
                    ======================
                    """,
            () -> {
                IO.println("Enter task name:");
                String name = IO.readln();
                String projectID;
                if (defaultProjectID.isEmpty()) {
                    IO.println("Enter assign project ID:");
                    projectID = ValidationUtils.getValidProjectID();
                } else {
                    IO.println(String.format("Enter assign project ID (leave empty for default: %s):", defaultProjectID));
                    projectID = ValidationUtils.getValidProjectID("");
                    if (projectID.isEmpty()) {
                        projectID = defaultProjectID;
                    }
                }
                IO.println("Enter initial status (Pending/In Progress/Completed):");
                Completable.STATUS status = ValidationUtils.getValidTaskStatus();
                if (taskService.addTask(projectID, name, status)) {
                    IO.println(String.format("Task \"%s\" added successfully to Project %s\n", name, projectID));
                } else {
                    IO.println(String.format("Task \"%s\" was not added\n", name));
                }
            }
    );
}
ConsoleMenu displayUpdateTaskMenu(String defaultProjectID) {
    return new ManualMenu(
            "Update Task Status",
            """
                    ======================
                        UPDATE TASK     \s
                    ======================
                    """,
            () -> {
                IO.println("Enter task ID:");
                String taskID = ValidationUtils.getValidTaskID();
                String projectID;
                if (defaultProjectID.isEmpty()) {
                    IO.println("Enter assign project ID:");
                    projectID = ValidationUtils.getValidProjectID();
                } else {
                    IO.println(String.format("Enter assign project ID (leave empty for default: %s):", defaultProjectID));
                    projectID = ValidationUtils.getValidProjectID("");
                    if (projectID.isEmpty()) {
                        projectID = defaultProjectID;
                    }
                }
                IO.println("Enter new status (Pending/In Progress/Completed):");
                Completable.STATUS status = ValidationUtils.getValidTaskStatus();
                if (taskService.updateTask(projectID, taskID, status)) {
                    IO.println(String.format("Task \"%s\" updated successfully", taskID));
                } else {
                    IO.println(String.format("Task \"%s\" update failed", taskID));
                }
            }
    );
}
ConsoleMenu displayDeleteTaskMenu(String defaultProjectID) {
    return new ManualMenu(
            "Remove Task",
            """
                    ======================
                        REMOVE TASK     \s
                    ======================
                    """,
            () -> {
                IO.println("Enter task ID:");
                String taskID = ValidationUtils.getValidTaskID();
                String projectID;
                if (defaultProjectID.isEmpty()) {
                    IO.println("Enter assign project ID:");
                    projectID = ValidationUtils.getValidProjectID();
                } else {
                    IO.println(String.format("Enter assign project ID (leave empty for default: %s):", defaultProjectID));
                    projectID = ValidationUtils.getValidProjectID("");
                    if (projectID.isEmpty()) {
                        projectID = defaultProjectID;
                    }
                }
                if (taskService.removeTask(projectID, taskID)) {
                    IO.println(String.format("Task \"%s\" removed successfully", taskID));
                } else {
                    IO.println(String.format("Task \"%s\" remove failed", taskID));
                }
            }
    );
}
ConsoleMenu getBrowseProjectsMenu(Scanner scanner) {
    Consumer<ProjectService.FILTER> filterMenu = (ProjectService.FILTER filter) -> {
        Project[] projects;
        if (filter == ProjectService.FILTER.BUDGET) {
            IO.println("Enter min budget:");
            int min = (int) ValidationUtils.getValidNumber(scanner, 0);
            IO.println("Enter max budget:");
            int max = (int) ValidationUtils.getValidNumber(scanner, 0);
            projects = projectService.filterProjects(min, max);
        } else {
            projects = projectService.filterProjects(filter);
        }
        IO.println("-".repeat(80));
        IO.println("ID   | PROJECT NAME         | TYPE       | TEAM SIZE | BUDGET");
        IO.println("-".repeat(80));
        for (Project project : projects) {
            IO.println(String.format("%s | %-20s | %-10s | %-9s | %s", project.ID, project.Name, project instanceof SoftwareProject ? "Software" : "Hardware", project.TeamSize, project.Budget));
            IO.println(String.format("     | Description: %s", project.Description));
        }
        IO.println("-".repeat(80));
        IO.println("Enter project ID to view details (0 to return):");
        String response = ValidationUtils.getValidProjectID("0");
        if (!response.equals("0")) {
            Project project = projectService.findProject(response);
            Task[] tasks = taskService.getProjectTasks(response);
            displayProjectDetailsMenu(project, tasks, scanner);
        }
    };
    return new OptionMenu(
            "Browse Projects",
            """
                    ==============================
                            PROJECT CATALOG      \s
                    ==============================
                    """,
            "Filter Options:",
            new ConsoleMenu[] {
                    new ManualMenu("View All Projects", "", () -> filterMenu.accept(ProjectService.FILTER.ALL)),
                    new ManualMenu("Software Projects Only", "", () -> filterMenu.accept(ProjectService.FILTER.SOFTWARE)),
                    new ManualMenu("Hardware Projects Only", "", () -> filterMenu.accept(ProjectService.FILTER.HARDWARE)),
                    new ManualMenu("Search by Budget Range", "", () -> filterMenu.accept(ProjectService.FILTER.BUDGET)),
            },
            "Back",
            "Enter filter choice:",
            scanner::nextInt
    );
}
ConsoleMenu getCreateProjectMenu(Scanner scanner) {
    return new ManualMenu(
            "Create Project",
            """
                    ======================
                        CREATE PROJECT    \s
                    ======================
                    """,
            () -> {
                IO.println("Enter project name:");
                String name = IO.readln();
                IO.println("Enter project type:");
                boolean isSoftware = ValidationUtils.getValidProjectType();
                IO.println("Enter project description:");
                String description = IO.readln();
                IO.println("Enter team size:");
                int teamSize = (int) ValidationUtils.getValidNumber(scanner, 1);
                IO.println("Enter budget:");
                double budget = ValidationUtils.getValidNumber(scanner, 0);
                Project project;
                if (isSoftware) {
                    project = new SoftwareProject(name, description, teamSize, budget);
                } else {
                    project = new HardwareProject(name, description, teamSize, budget);
                }
                if (projectService.createProject(project)) {
                    IO.println(String.format("Project \"%s\" created successfully\n", name));
                } else {
                    IO.println(String.format("Project \"%s\" was not added\n", name));
                }
            }
    );
}
ConsoleMenu getDeleteProjectMenu() {
    return new ManualMenu(
            "Remove Project",
            """
                    ======================
                        REMOVE PROJECT    \s
                    ======================
                    """,
            () -> {
                IO.println("Enter Project ID:");
                String projectID = ValidationUtils.getValidProjectID();
                if (projectService.removeProject(projectID)) {
                    IO.println(String.format("Project \"%s\" removed successfully", projectID));
                } else {
                    IO.println(String.format("Project \"%s\" remove failed", projectID));
                }
            }
    );
}
ConsoleMenu getManageProjectMenu(Scanner scanner) {
    return new OptionMenu(
            "Manage Projects",
            """
                    ==============================
                             MANAGE PROJECTS      \s
                    ==============================
                    """,
            "Options:",
            new ConsoleMenu[]{
                    getCreateProjectMenu(scanner),
                    getDeleteProjectMenu(),
                    getBrowseProjectsMenu(scanner),
            },
            "Back",
            "Enter your choice:",
            scanner::nextInt
    );
}
ConsoleMenu getManageTaskMenu(Scanner scanner) {
    return new OptionMenu(
            "Manage Tasks",
            """
                    ==============================
                             MANAGE TASKS         \s
                    ==============================
                    """,
            "Options:",
            new ConsoleMenu[0],
            "",
            "Enter your choice:",
            scanner::nextInt
    );
}
ConsoleMenu getViewStatusReportMenu() {
    return new ManualMenu(
            "View Status Reports",
            """
                    ==============================
                         PROJECT STATUS REPORT    \s
                    ==============================
                    """,
            () -> {
                Project[] projects = projectService.filterProjects(ProjectService.FILTER.ALL);
                IO.println("-".repeat(80));
                IO.println("PROJECT ID | PROJECT NAME         | TASKS | COMPLETED | PROGRESS (%)");
                IO.println("-".repeat(80));
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
                    IO.println(String.format("%-10s | %-20s | %-5s | %-9s | %s", project.ID, project.Name, tasks.length, completedTasks, progress) + "%");
                }
                IO.println("-".repeat(80));
                IO.println(String.format("AVERAGE COMPLETION: %.2f", average_completion / projects.length) + "%");
                IO.println("-".repeat(80));
                IO.readln("Press enter to continue...\n");
            }
    );
}
ConsoleMenu getSwitchUserMenu() {
    return new ManualMenu(
            "Switch User",
            "",
            () -> {
                if (login()) {
                    IO.println("Login Failed. Please try again later.");
                    System.exit(0);
                }
                IO.println();
            }
    );
}
void program() {
    if (!login()){
        IO.println("Login Failed. Please try again later.");
        System.exit(0);
    }
    Scanner scanner = new Scanner(System.in);
    consoleMenu = new OptionMenu(
            "Main Menu",
            String.format(
                    """
                            ==============================
                            JAVA PROJECT MANAGEMENT SYSTEM
                            ==============================
                            
                            Current User: %s %s""",
                    current_user.name, (current_user instanceof AdminUser ? "(Admin)" : "")
            ),
            "Main Menu\n" + "---------",
            new ConsoleMenu[] {
                    getManageProjectMenu(scanner),
                    getManageTaskMenu(scanner),
                    getViewStatusReportMenu(),
                    getSwitchUserMenu()
            },
            "Exit",
            "Enter your choice:",
            scanner::nextInt
    );
    consoleMenu.display();
    scanner.close();
}

void main() {
    initialization();
    program();
}
