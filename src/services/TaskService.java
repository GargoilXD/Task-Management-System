package services;

import models.Project;
import models.Task;
import utils.OperationResult;

public class TaskService {
    static final int MAX_TASK_COUNT = 100;
    public Task[] Tasks =  new Task[MAX_TASK_COUNT];
    public int taskCount = 0;
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
        for (Task t : Tasks) {
            if (t.ID.equals(taskID)) {
                return t;
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
    public OperationResult deleteTask(Task task) {
        if (findTask(task.ID) == null) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Task doesn't exists");
        }
        return new OperationResult(OperationResult.STATUS.SUCCESS);
    }
    public OperationResult updateTask(String taskID, String taskName, Task.STATUS taskStatus) {
        Task found = findTask(taskID);
        if (found == null) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Task doesn't exists");
        }
        found.Name = taskName;
        found.Status = taskStatus;
        return new OperationResult(OperationResult.STATUS.SUCCESS);
    }
    public String viewTasks() {
        StringBuilder tasks = new StringBuilder();
        for (Task task : Tasks) {
            tasks.append(task.toString()).append("\n");
        }
        return tasks.toString();
    }
}
