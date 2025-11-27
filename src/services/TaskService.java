package services;
import interfaces.Completable;
import models.Project;
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
    public void addTask(String projectID, String name, Completable.STATUS status) {
        //if (findTask(projectID, taskID) != null) return;
        Task task = getDeletedTask();
        if (task == null) {
            if (taskIndex >= MAX_TASK_COUNT) return;
            tasks[taskIndex] = new Task(projectID, String.format("T00%s", taskIndex), name, status);
            taskIndex++;
        } else {
            //task = new Task(projectID, taskID, name, status);
            task.ID = String.format("T00%s", taskIndex);
            task.projectID = projectID;
            task.name = name;
            task.status = status;
            task.deleted = false;
        }
    }
    public void removeTask(String projectID, String taskID) {
        Task task = findTask(projectID, taskID);
        if (task == null) return;
        task.deleted = true;
    }
    public void updateTask(String projectID, String taskID, Completable.STATUS status) {
        Task task = findTask(projectID, taskID);
        if (task == null) return;
        task.status = status;
    }
}
