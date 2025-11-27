package services;

import models.Project;
import models.Task;
import utils.OperationResult;

public class TaskService {
    static final int MAX_TASK_COUNT = 50;
    public Task[] Tasks =  new Task[MAX_TASK_COUNT];
    public int TaskIndex = 0;
    public Task[] filterTasksByProjectID(String projectID) {
        int count = 0;
        for (int i = 0; i < taskCount; i++) {
            if (Tasks[i].ProjectID.equals(projectID)) {
                count++;
            }
        }
        Task[] filteredTasks = new Task[count];
        for (int i = 0; i < taskCount; i++) {
            if (Tasks[i].ProjectID.equals(projectID)) {
                filteredTasks[i] = Tasks[i];
            }
        }
        return filteredTasks;
    }
    Task findTask(String taskID) {
        for (int i = 0; i < taskCount; i++) {
            if (Tasks[i].ID.equals(taskID) && !Tasks[i].deleted) {
                return Tasks[i];
            }
        }
        return null;
    }
    public OperationResult addTask(Task task) {
        if (taskCount == MAX_TASK_COUNT) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Task limit reached");
        }
        if (findTask(task.ID) != null) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Task ID already exists");
        }

        Tasks[taskCount] = task;
        taskCount++;
        return new OperationResult(OperationResult.STATUS.SUCCESS);
    }
    public OperationResult deleteTask(String taskID) {
        Task task = findTask(taskID);
        if (task == null) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Task doesn't exists");
        }
        task.deleted = true;
        return new OperationResult(OperationResult.STATUS.SUCCESS);
    }
    public OperationResult updateTask(String taskID, String taskName, Task.STATUS taskStatus) {
        Task found = findTask(taskID);
        if (found == null) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Task doesn't exists");
        }
        found.Name = taskName;
        found. = taskStatus;
        return new OperationResult(OperationResult.STATUS.SUCCESS);
    }
}
