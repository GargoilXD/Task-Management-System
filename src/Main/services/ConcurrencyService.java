package Main.services;

import Main.Main;
import Main.utilities.FileUtilities;
import Main.utilities.exceptions.FileLoadException;
import Main.utilities.exceptions.FileSaveException;

public class ConcurrencyService {
    public static synchronized void initializeServices() {
        System.out.println("Loading data from files...");
        try {
            Thread thread1 = new Thread(() -> Main.projectService = new ProjectService(FileUtilities.loadProjects()));
            Thread thread2 = new Thread(() -> Main.taskService = new TaskService(FileUtilities.loadTasks()));
            Thread thread3 = new Thread(() -> Main.userService = new UserService(FileUtilities.loadUsers()));
            thread1.start();
            thread2.start();
            thread3.start();

            thread1.join();
            System.out.printf("%s projects loaded successfully from files %n", Main.projectService.projects.size());
        } catch (FileLoadException e) {
            System.err.println("Error loading data from files!");
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Error loading data from files!");
            System.exit(1);
        }
        Main.reportService = new ReportService(Main.projectService, Main.taskService);
    }
    public static synchronized void uninitializeServices() {
        System.out.println("Saving data to files...");
        try {
            Thread thread1 = new Thread(() -> FileUtilities.saveProjects(Main.projectService.getProjects()));
            Thread thread2 = new Thread(() -> FileUtilities.saveTasks(Main.taskService.getTasks()));
            Thread thread3 = new Thread(() -> FileUtilities.saveUsers(Main.userService.getUsers()));
            thread1.start();
            thread2.start();
            thread3.start();

            thread1.join();
            System.out.printf("%s projects saved successfully%n", Main.projectService.projects.size());
        } catch (FileSaveException e) {
            System.err.println("Error saving projects!");
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error saving projects!");
        }
    }
}
