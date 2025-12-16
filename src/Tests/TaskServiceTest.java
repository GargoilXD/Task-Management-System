package Tests;

import Main.interfaces.Completable;
import Main.models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Main.services.TaskService;

class TaskServiceTest {
    static TaskService taskService;

    @BeforeAll
    static void start() {
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
    }
    @Test
    void getTasks() {
        Assertions.assertEquals(20, taskService.getTasks().length);
    }

    @Test
    void getProjectTasks() {
        Assertions.assertEquals(3, taskService.getProjectTasks("P001").length);
    }

    @Test
    void findTaskByID() {
        Assertions.assertNotNull(taskService.findTaskByID("T005"));
    }

    @Test
    void findTaskByName() {
        Assertions.assertNotNull(taskService.findTaskByName("Implement API"));
    }

    @Test
    void createTask() {
        taskService.createTask("P001", "Deploy Database", Task.STATUS.COMPLETED);
        Assertions.assertNotNull(taskService.findTaskByName("Deploy Database"));
    }

    @Test
    void removeTask() {
        taskService.removeTask("T001");
        Assertions.assertNull(taskService.findTaskByName("T001"));
    }

    @Test
    void updateTask() {
        taskService.updateTask("T006", Completable.STATUS.COMPLETED);
        Assertions.assertEquals(Completable.STATUS.COMPLETED, taskService.findTaskByID("T006").Status);
    }
}