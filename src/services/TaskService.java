package services;

import interfaces.Completable;
import models.Task;

// This is the TaskService class, responsible for Managing tasks.
// The memory management approach was to delete tasks by changing the delete flag
// Then replacing deleted tasks with new ones
public class TaskService {
    static final int MAX_TASK_COUNT = 50;
    public Task[] tasks = new Task[MAX_TASK_COUNT];
    // This counts the tasks
    public int taskIndex = 0;

    // Gets all undeleted Tasks
    public Task[] getTasks() {
        // Create an array with size of taskIndex, that's the maximum possible size.
        Task[] Tasks = new Task[taskIndex];
        int TasksIndex = 0;
        for (int index = 0; index < taskIndex; index++) {
            if (!tasks[index].deleted) {
                Tasks[TasksIndex] = tasks[index];
                TasksIndex++;
            }
        }
        // Shrink the array to the real size.
        Task[] shrunken = new Task[TasksIndex];
        System.arraycopy(Tasks, 0, shrunken, 0, TasksIndex);
        return shrunken;
    }
    public Task findTask(String projectID, String taskID) {
        for (int index = 0; index < taskIndex; index++) {
            // Also check if task is deleted
            if (tasks[index].ID.equals(taskID) && tasks[index].projectID.equals(projectID) && !tasks[index].deleted) {
                return tasks[index];
            }
        }
        return null;
    }
    public Task findTaskByName(String projectID, String taskName) {
        for (int index = 0; index < taskIndex; index++) {
            // Also check if task is deleted
            if (tasks[index].name.equals(taskName) && tasks[index].projectID.equals(projectID) && !tasks[index].deleted) {
                return tasks[index];
            }
        }
        return null;
    }
    public Task findTaskByID(String ID) {
        for (int index = 0; index < taskIndex; index++) {
            // Also check if task is deleted
            if (tasks[index].ID.equals(ID) && !tasks[index].deleted) {
                return tasks[index];
            }
        }
        return null;
    }
    public Task[] getProjectTasks(String projectID) {
        // Create an array with size of taskIndex, that's the maximum possible size.
        Task[] projectTasks = new Task[taskIndex];
        int projectTasksIndex = 0;
        for (int index = 0; index < taskIndex; index++) {
            if (tasks[index].projectID.equals(projectID) && !tasks[index].deleted) {
                projectTasks[projectTasksIndex] = tasks[index];
                projectTasksIndex++;
            }
        }
        // Shrink the array to the real size.
        Task[] shrunken = new Task[projectTasksIndex];
        System.arraycopy(projectTasks, 0, shrunken, 0, projectTasksIndex);
        return shrunken;
    }
    // Finds a deleted task
    Task getDeletedTask() {
        for (int index = 0; index < taskIndex; index++) {
            if (tasks[index].deleted) {
                return tasks[index];
            }
        }
        return null;
    }
    public boolean addTask(String projectID, String name, Completable.STATUS status) {
        if (findTaskByName(projectID, name) != null) {
            System.out.println("Task already exists");
            return false;
        }
        Task task = getDeletedTask();
        if (task == null) {
            if (taskIndex >= MAX_TASK_COUNT) {
                System.out.println("Maximum task count reached");
                return false;
            }
            tasks[taskIndex] = new Task(projectID, String.format("T%03d", taskIndex + 1), name, status);
            taskIndex++;
        } else {
            // Old: task = new Task(projectID, taskID, name, status);
            // Wasn't sure if assigning a new object will cause memory problems, so I left this
            // Task ID is not changed, it's preserved
            task.projectID = projectID;
            task.name = name;
            task.status = status;
            // The slot is freed
            task.deleted = false;
        }
        System.out.println("Task Added");
        return true;
    }
    public boolean removeTask(String projectID, String taskID) {
        Task task = findTask(projectID, taskID);
        if (task == null) {
            System.out.println("Task Not Found");
            return false;
        }
        // The slot is freed
        task.deleted = true;
        System.out.println("Task Deleted");
        return true;
    }
    public boolean updateTask(String projectID, String taskID, Completable.STATUS status) {
        Task task = findTask(projectID, taskID);
        if (task == null) {
            System.out.println("Task Not Found");
            return false;
        }
        task.status = status;
        System.out.println("Task Updated");
        return true;
    }
}
