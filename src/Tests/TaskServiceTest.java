package Tests;

import Main.interfaces.Completable;
import Main.models.Task;
import Main.utilities.FileUtilities;
import Main.utilities.exceptions.EntityAlreadyExists;
import Main.utilities.exceptions.EntityDoesNotExist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Main.services.TaskService;

class TaskServiceTest {
    static TaskService taskService;

    @BeforeAll
    static void start() {
        taskService = new TaskService(FileUtilities.loadTasks());
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
    void createTaskAndCatchException() {
        Assertions.assertThrows(EntityAlreadyExists.class, () -> {
            taskService.createTask("P001", "Smile for 2 hours", Completable.STATUS.IN_PROGRESS);
            taskService.createTask("P001", "Smile for 2 hours", Completable.STATUS.IN_PROGRESS);
        });
    }

    @Test
    void removeTask() {
        taskService.removeTask("T001");
        Assertions.assertNull(taskService.findTaskByName("T001"));
    }
    @Test
    void removeProjectAndCatchException() {
        Assertions.assertThrows(EntityDoesNotExist.class, () -> {
            taskService.removeTask("T004");
            taskService.removeTask("T004");
        });
    }

    @Test
    void updateTask() {
        taskService.updateTask("T006", Completable.STATUS.COMPLETED);
        Assertions.assertEquals(Completable.STATUS.COMPLETED, taskService.findTaskByID("T006").Status);
    }

    @Test
    void updateTaskAndCatchException() {
        Assertions.assertThrows(EntityDoesNotExist.class, () -> taskService.updateTask("T067", Completable.STATUS.COMPLETED));
    }
}