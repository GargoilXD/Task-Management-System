package services;

import interfaces.Completable;
import models.Task;

public class TaskService {
    static final int MAX_TASK_COUNT = 50;
    public Task[] tasks = new Task[MAX_TASK_COUNT];
    public int taskIndex = 0;

    public Task findTask(String projectID, String taskID) {
        for (int index = 0; index < taskIndex; index++) {
            if (tasks[index].ID.equals(taskID) && tasks[index].projectID.equals(projectID) && !tasks[index].deleted) {
                return tasks[index];
            }
        }
        return null;
    }
    public Task findTaskByName(String projectID, String taskName) {
        for (int index = 0; index < taskIndex; index++) {
            if (tasks[index].name.equals(taskName) && tasks[index].projectID.equals(projectID) && !tasks[index].deleted) {
                return tasks[index];
            }
        }
        return null;
    }

    public Task[] getProjectTasks(String projectID) {
        Task[] projectTasks = new Task[taskIndex];
        int projectTasksIndex = 0;
        for (int index = 0; index < taskIndex; index++) {
            if (tasks[index].projectID.equals(projectID) && !tasks[index].deleted) {
                projectTasks[projectTasksIndex] = tasks[index];
                projectTasksIndex++;
            }
        }
        Task[] shrunken = new Task[projectTasksIndex];
        System.arraycopy(projectTasks, 0, shrunken, 0, projectTasksIndex);
        return shrunken;
    }

    Task getDeletedTask() {
        for (int index = 0; index < taskIndex; index++) {
            if (tasks[index].deleted) {
                return tasks[index];
            }
        }
        return null;
    }

    public boolean addTask(String projectID, String name, Completable.STATUS status) {
        if (findTaskByName(projectID, name) != null) return false;
        Task task = getDeletedTask();
        if (task == null) {
            if (taskIndex >= MAX_TASK_COUNT) return false;
            tasks[taskIndex] = new Task(projectID, String.format("T%03d", taskIndex + 1), name, status);
            taskIndex++;
        } else {
            //task = new Task(projectID, taskID, name, status);
            task.projectID = projectID;
            task.name = name;
            task.status = status;
            task.deleted = false;
        }
        return true;
    }

    public boolean removeTask(String projectID, String taskID) {
        Task task = findTask(projectID, taskID);
        if (task == null) return false;
        task.deleted = true;
        return true;
    }

    public boolean updateTask(String projectID, String taskID, Completable.STATUS status) {
        Task task = findTask(projectID, taskID);
        if (task == null) return false;
        task.status = status;
        return true;
    }
}
