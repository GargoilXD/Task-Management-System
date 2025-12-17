package Main.services;

import Main.interfaces.Completable;
import Main.models.Task;
import Main.utilities.exceptions.EntityAlreadyExists;
import Main.utilities.exceptions.EntityDoesNotExist;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TaskService {
    ArrayList<Task> tasks;

    public TaskService(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public ArrayList<Task> getProjectTasks(String ProjectID) {
        return tasks.stream().filter((task -> task.ProjectID.equals(ProjectID))).collect(Collectors.toCollection(ArrayList::new));
    }
    public Task findTaskByID(String ID) {
        return tasks.stream().filter((task -> task.ID.equals(ID))).findFirst().orElse(null);
    }
    public Task findTaskByName(String Name) {
        return tasks.stream().filter((task -> task.Name.equalsIgnoreCase(Name))).findFirst().orElse(null);
    }
    public void createTask(String ProjectID, String Name, Completable.STATUS Status) {
        if (findTaskByName(Name) != null) throw new EntityAlreadyExists("Task already exists");
        tasks.add(new Task(ProjectID, Name, Status));
    }
    public void removeTask(String ID) {
        Task found = findTaskByID(ID);
        if (found == null) throw new EntityDoesNotExist("Task with ID " + ID + " does not exist");
        tasks.remove(found);
    }
    public void updateTask(String TaskID, Completable.STATUS Status) {
        Task task = findTaskByID(TaskID);
        if (task == null) throw new EntityDoesNotExist("Task with ID " + TaskID + " does not exist");
        task.Status = Status;
    }
}
