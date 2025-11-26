import models.*;
import services.ProjectService;
import services.TaskService;
import utils.ConsoleMenu;

User[] users = new User[] {
        new AdminUser("Ama", "12345"),
        new RegularUser("Kofi", "12345"),
};
ProjectService projectService = new ProjectService();
TaskService taskService = new TaskService();
ConsoleMenu consoleMenu = new ConsoleMenu(users);

void initialization() {
    projectService.createProject(new SoftwareProject("P001", "P", "", 2, 200));
}

void program() {
    User current_user = new AdminUser("Kobby", "12345");
    if (current_user == null) {
        IO.println("Login Failed. Please try again later.");
        System.exit(0);
    }
    while (true) {
        switch (consoleMenu.MainMenu(current_user)) {
            case 1:
                while (true) {
                    boolean back = false;
                    switch (consoleMenu.ManageProjects(projectService.projectCount)) {
                        case 1:
                            IO.println(projectService.printProjects(ProjectService.FILTER.ALL));
                            break;
                        case 2:
                            IO.println(projectService.printProjects(ProjectService.FILTER.SOFTWARE));
                            break;
                        case 3:
                            IO.println(projectService.printProjects(ProjectService.FILTER.HARDWARE));
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
                            Task[] tasks = projectService.filterTasksByProjectID();
                            switch (consoleMenu.viewProjectDetails(project, tasks)) {
                                case 1:
                                    Task newTask = consoleMenu.addTask();

                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                            }
                            IO.readln();
                        }
                    }
                }
                break;
            case 2: consoleMenu.ManageTasks(); break;
            case 3: consoleMenu.ViewStatus(); break;
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
