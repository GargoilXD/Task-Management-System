import interfaces.Completable;
import models.*;
import services.ProjectService;
import services.TaskService;
import utils.ConsoleMenu;
import utils.ManualMenu;
import utils.OptionMenu;

User[] users = new User[] {
        new AdminUser("Ama", "12345"),
        new RegularUser("Kofi", "12345"),
};

TaskService taskService = new TaskService();
ProjectService projectService = new ProjectService();
ConsoleMenu consoleMenu;

void initialization() {
    projectService.createProject(new SoftwareProject("P001", "Alpha Tracker", "Task tracking app for startups", 5, 15000));
    taskService.addTask("P001", "Design Database", Task.STATUS.COMPLETED);
    taskService.addTask("P001", "Implement API", Task.STATUS.IN_PROGRESS);
    taskService.addTask("P001", "Write Unit Tests", Task.STATUS.PENDING);
    projectService.createProject(new HardwareProject("P002", "IoT Sensor Kit", "Sensor prototype for smart devices", 3, 10000));
    taskService.addTask("P002", "Gather Materials", Task.STATUS.COMPLETED);
    taskService.addTask("P002", "Build prototype", Task.STATUS.IN_PROGRESS);
}

void program() {
    User current_user = new AdminUser("Kobby", "12345"); 
    /*if (current_user == null) {
        IO.println("Login Failed. Please try again later.");
        System.exit(0);
    }*/
    /*IO.println("==============================");
    IO.println("JAVA PROJECT MANAGEMENT SYSTEM");
    IO.println("==============================");
    IO.println();
    IO.println("Current User: " + current_user.name + (current_user instanceof AdminUser ? " (Admin) " : ""));
    IO.println();*/
    Scanner scanner = new Scanner(System.in);
    Consumer<ProjectService.FILTER> reused = (ProjectService.FILTER filter) -> {
        Project[] projects;
        if (filter == ProjectService.FILTER.BUDGET) {
            IO.println("Enter min budget:");
            int min = scanner.nextInt();
            IO.println("Enter max budget:");
            int max = scanner.nextInt();
            projects = projectService.filterProjects(min, max);
        } else {
            projects = projectService.filterProjects(filter);
        }
        IO.println("-".repeat(80));
        IO.println("ID   | PROJECT NAME       | TYPE     | TEAM SIZE | BUDGET");
        IO.println("-".repeat(80));
        for (Project project : projects) {
            IO.println(String.format("%s   | %s | %s | %s | %s", project.ID, project.Name, project instanceof SoftwareProject? "Software" : "Hardware", project.TeamSize, project.Budget));
            IO.println(String.format("     | Description: %s", project.Description));
        }
        IO.println("-".repeat(80));
        IO.println("Enter project ID to view details (0 to return):");
        String response = IO.readln().trim();
        if (response.equals("0")) {
            return;
        } else {
            Project project = projectService.findProject(response);
            Task[] tasks = taskService.getProjectTasks(response);
            OptionMenu projectDetailsMenu = new OptionMenu(
                    "Project Details",
                    "==============================\n" +
                            "        PROJECT Details       \n" +
                            "==============================\n",
                    () -> {
                        StringBuilder builder = new StringBuilder();
                        builder.append(String.format(
                                "Project Name: %s\n" +
                                        "Type: %s\n" +
                                        "Team Size: %s\n" +
                                        "Budget: %s\n",
                                project.Name, project instanceof SoftwareProject? "Software" : "Hardware", project.TeamSize, project.Budget
                        ));
                        builder.append("Associated Tasks: \n");
                        builder.append("-".repeat(80));
                        builder.append("\n");
                        builder.append("ID      | TASK NAME           | STATUS\n");
                        builder.append("-".repeat(80));
                        builder.append("\n");
                        for (Task task : tasks) {
                            builder.append(String.format("%s | %s | %s\n", task.ID, task.name, task.status));
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
                        IO.println("Completion Rate: " + (completionRate * 100) + "%");
                        return builder.toString();
                    },
                    "Options:",
                    new ConsoleMenu[] {
                            new ManualMenu("Add New Task",
                                    "======================\n" +
                                            "    ADD NEW TASK      \n" +
                                            "======================\n",
                                    () -> {
                                        IO.println("Enter task name:");
                                        String name = IO.readln();
                                        IO.println(String.format("Enter assign project ID (leave empty for default: %s):", project.ID));
                                        String projectID = IO.readln();
                                        if (projectID.isEmpty()) {
                                            projectID = project.ID;
                                        }
                                        IO.println("Enter initial status (Pending/In Progress/Completed):");
                                        Completable.STATUS status = switch (IO.readln().toLowerCase().trim()) {
                                            case "pending" -> Task.STATUS.PENDING;
                                            case "in progress" -> Task.STATUS.IN_PROGRESS;
                                            case "completed" -> Task.STATUS.COMPLETED;
                                            default -> Task.STATUS.PENDING; // Handle error
                                        };
                                        IO.println(String.format("Task \"%s\" added successfully to Project %s", name, projectID));
                                        taskService.addTask(projectID, name, status);
                                    }
                            ),
                            new ManualMenu("Update Task Status",
                                    "======================\n" +
                                            "    UPDATE TASK      \n" +
                                            "======================\n",
                                    () -> {
                                        IO.println("Enter task ID:");
                                        String taskID = IO.readln();
                                        IO.println("Enter new status (Pending/In Progress/Completed):");
                                        Completable.STATUS status = switch (IO.readln().toLowerCase().trim()) {
                                            case "pending" -> Task.STATUS.PENDING;
                                            case "in progress" -> Task.STATUS.IN_PROGRESS;
                                            case "completed" -> Task.STATUS.COMPLETED;
                                            default -> Task.STATUS.PENDING; // Handle error
                                        };
                                        IO.println(String.format("Task \"%s\" added successfully to Project %s", taskID, project.ID));
                                        taskService.updateTask(project.ID, taskID, status);
                                    }
                            ),
                            new ManualMenu("Remove Task",
                                    "======================\n" +
                                            "    REMOVE TASK      \n" +
                                            "======================\n",
                                    () -> {
                                        IO.println("Enter task ID:");
                                        String taskID = IO.readln();
                                        taskService.removeTask(project.ID, taskID);
                                    }
                            )
                    },
                    "Back",
                    "Enter your choice:",
                    scanner::nextInt
            );
            projectDetailsMenu.display();
        }

    };
    consoleMenu = new OptionMenu(
        "Main Menu",
        String.format(
            "==============================\n" +
            "JAVA PROJECT MANAGEMENT SYSTEM\n" +
            "==============================\n" +
            "\n" +
            "Current User: %s %s",
            current_user.name,
            (current_user instanceof AdminUser ? "(Admin)" : "")
        ),
        "Main Menu\n" +
                "---------",
        new ConsoleMenu[] {
                new OptionMenu(
                        "Manage Projects",
                        "==============================\n" +
                            "        PROJECT CATALOG       \n" +
                            "==============================\n",
                        "Filter Options:",
                        new ConsoleMenu[] {
                                new ManualMenu("View All Projects", "", () -> reused.accept(ProjectService.FILTER.ALL)),
                                new ManualMenu("Software Projects Only", "", () -> reused.accept(ProjectService.FILTER.SOFTWARE)),
                                new ManualMenu("Hardware Projects Only", "", () -> reused.accept(ProjectService.FILTER.HARDWARE)),
                                new ManualMenu("Search by Budget Range", "", () -> reused.accept(ProjectService.FILTER.BUDGET)),
                        },
                        "Back",
                        "Enter filter choice:",
                        scanner::nextInt

                ),
                new OptionMenu("Manage Tasks", "", "", new ConsoleMenu[0], "", "Enter your choice:", scanner::nextInt),
                new ManualMenu(
                        "View Status Reports",
                        "==============================\n" +
                             "     PROJECT STATUS REPORT     \n" +
                             "==============================\n",
                            () -> {
                                Project[] projects = projectService.filterProjects(ProjectService.FILTER.ALL);
                                IO.println("-".repeat(80));
                                IO.println("PROJECT ID | PROJECT NAME | TASKS | COMPLETED | PROGRESS (%)");
                                IO.println("-".repeat(80));
                                for (Project project : projects) {
                                    Task[] tasks = taskService.getProjectTasks(project.ID);
                                    int completedTasks = 0;
                                    for (Task task : tasks) {
                                        if (task.status == Task.STATUS.COMPLETED) {
                                            completedTasks++;
                                        }
                                    }
                                    double progress = completedTasks / (double) tasks.length;
                                    IO.println(String.format("%s | %s | %s | %s | %s", project.ID, project.Name, tasks.length, completedTasks, progress) + "%");
                                }
                                IO.println("-".repeat(80));
                                double average_completion = 0;
                                IO.println(String.format("AVERAGE COMPLETION: %.2f", average_completion));
                                IO.println("-".repeat(80));
                            }
                ),
                new OptionMenu("Switch User", "", "", new ConsoleMenu[0], "", "Enter your choice:", scanner::nextInt),
        },
        "Exit",
            "Enter your choice:",
            scanner::nextInt

    );
    consoleMenu.display();
    /*
    while (true) {
        switch (consoleMenu.MainMenu(current_user)) {
            case 1:
                while (true) {
                    boolean back = false;
                    switch (consoleMenu.ManageProjects()) {
                        case 1:
                            projectService.printProjects(ProjectService.FILTER.ALL);
                            break;
                        case 2:
                            projectService.printProjects(ProjectService.FILTER.SOFTWARE);
                            break;
                        case 3:
                            projectService.printProjects(ProjectService.FILTER.HARDWARE);
                            break;
                        case 4:
                            IO.println("Enter the min range:");
                            int min = consoleMenu.scanner.nextInt();
                            IO.println("Enter the max range:");
                            int max = consoleMenu.scanner.nextInt();
                            IO.println(projectService.printProjectsByBudget(min, max));
                            break;
                        case 5:
                            back = true;
                            break;
                    }
                    if (back) {
                        break;
                    } else {
                        IO.println("Enter project ID to view details (or 0 to return to main menu):");
                        String projectID = IO.readln();
                        if (projectID.equals("0")) {
                            break;
                        }
                        Project project = projectService.findProject(projectID);
                        if (project == null) {
                            IO.println("Project Not Found.");
                        } else {
                            Task[] tasks = projectService.filterTasksByProjectID(projectID);
                            switch (consoleMenu.viewProjectDetails(project, tasks)) {
                                case 1:
                                    Task newTask = consoleMenu.addTask(projectID);
                                    projectService.addTaskToProject(newTask);
                                    break;
                                case 2:
                                    Task updatedTask = consoleMenu.updateTask(projectID);
                                    projectService.updateTaskStatus(updatedTask);
                                    break;
                                case 3:
                                    Task deleteTask = consoleMenu.removeTask();
                                    //projectService.
                                    break;
                                case 4:
                                    back =  true;
                                    break;
                            }
                            IO.readln();
                        }
                    }
                }
                break;
            case 2: consoleMenu.ManageTasks(); break;
            case 3: consoleMenu.statusReportDisplay(); break;
            case 4:
                current_user = consoleMenu.Login(users);
                if (current_user == null) {
                    IO.println("Login Failed. Please try again later.");
                    System.exit(0);
                }
                break;
            case 5: System.exit(0); break;

        }
    }

}
     */
}


void main() {
    initialization();
    program();
}
