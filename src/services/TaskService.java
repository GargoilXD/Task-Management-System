package services;

import interfaces.Completable;
import models.Task;
import services.exceptions.EntityAlreadyExists;
import utilities.KArray;

import java.util.NoSuchElementException;

public class TaskService {
    KArray<Task> tasks =  new KArray<Task>(Task.class);

    public TaskService(Task[] tasks) {
        for (Task task : tasks) {
            this.tasks.add(task);
        }
    }
    public Task[] getTasks() {
        return tasks.toArray();
    }
    public Task[] getProjectTasks(String ProjectID) {
        KArray<Task> filteredTasks = new KArray<Task>(Task.class);
        for (Task task : tasks.toArray()) {
            if (task.ProjectID.equals(ProjectID)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks.toArray();
    }
    public Task findTaskByID(String ID) {
        return tasks.customFind((task) -> task.ID.equals(ID));
    }
    public Task findTaskByName(String Name) {
        return tasks.customFind((task) -> task.Name.equals(Name));
    }
    public void createTask(String ProjectID, String Name, Completable.STATUS Status) {
        if (findTaskByName(Name) != null) throw new EntityAlreadyExists("Task already exists");
        tasks.add(new Task(ProjectID, Name, Status));
    }
    public void removeTask(String ID) {
        Task found = findTaskByID(ID);
        if (found == null) throw new NoSuchElementException("Task with ID " + ID + " does not exist");
        tasks.removeElement(found);
    }
    public void updateTask(String TaskID, Completable.STATUS Status) {
        Task task = findTaskByID(TaskID);
        if (task == null) return;
        task.Status = Status;
    }
}
