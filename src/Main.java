import models.*;
import services.ProjectService;
import services.TaskService;
import utils.ConsoleMenu;

User[] users = new User[] {
        new AdminUser("Ama", "12345"),
        new RegularUser("Kofi", "12345"),
};
TaskService taskService = new TaskService();
ProjectService projectService = new ProjectService(taskService);
ConsoleMenu consoleMenu = new ConsoleMenu(projectService);

void initialization() {
    projectService.createProject(new SoftwareProject("P001", "Alpha Tracker", "Task tracking app for startups", 5, 15000));
    projectService.addTaskToProject(new Task("P001", "T001", "Design Database", Task.STATUS.COMPLETED));
    projectService.addTaskToProject(new Task("P001", "T002", "Implement API", Task.STATUS.IN_PROGRESS));
    projectService.addTaskToProject(new Task("P001", "T003", "Write Unit Tests", Task.STATUS.PENDING));
    projectService.createProject(new HardwareProject("P002", "IoT Sensor Kit", "Sensor prototype for smart devices", 3, 10000));
    projectService.addTaskToProject(new Task("P002", "T001", "Gather Materials", Task.STATUS.COMPLETED));
    projectService.addTaskToProject(new Task("P002", "T002", "Build prototype", Task.STATUS.IN_PROGRESS));
}

void program() {
    User current_user = new AdminUser("Kobby", "12345");
    /*if (current_user == null) {
        IO.println("Login Failed. Please try again later.");
        System.exit(0);
    }*/
    IO.println("==============================");
    IO.println("JAVA PROJECT MANAGEMENT SYSTEM");
    IO.println("==============================");
    IO.println();
    IO.println("Current User: " + current_user.name + (current_user instanceof AdminUser ? " (Admin) " : ""));
    IO.println();
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

void main() {
    initialization();
    program();
}
