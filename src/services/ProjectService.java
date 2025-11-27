package services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import models.Task;
import utils.OperationResult;

public class ProjectService {
    static final int MAX_PROJECT_COUNT = 10;
    public Project[] Projects =  new Project[MAX_PROJECT_COUNT];
    public int projectCount = 0;
    TaskService taskService;
    public ProjectService(TaskService taskService) {
        this.taskService = taskService;
    }
    public Project findProject(String projectID) {
        for (int i = 0; i < projectCount; i++) {
            if (Projects[i].ID.equals(projectID)) {
                return Projects[i];
            }
        }
        return null;
    }
    public OperationResult createProject(Project project) {
        if (projectCount == MAX_PROJECT_COUNT) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Project limit reached");
        }
        if (findProject(project.ID) != null) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Project ID already exists");
        }
        Projects[projectCount] = project;
        projectCount++;
        return new OperationResult(OperationResult.STATUS.SUCCESS);
    }
    public void addTaskToProject(Task task) {
        Project project = findProject(task.ProjectID);
        if (project == null) {
            return;
        }
        task.ID = String.format("T00%s", project.LastTaskID);
        project.LastTaskID++;
        taskService.addTask(task);
    }
    public void updateTaskStatus(Task task) {
        Project project = findProject(task.ProjectID);
        if (project == null) {
            return;
        }
        taskService.
    }
    public void deleteTaskFromProject(String projectID, String taskID) {

    }
    public Task[] filterTasksByProjectID(String projectID) {
        return taskService.filterTasksByProjectID(projectID);
    }
    public enum FILTER {
        ALL,
        SOFTWARE,
        HARDWARE
    }
    public void printProjects(FILTER filter) {
        IO.println("-".repeat(20));
        IO.println("ID | PROJECT NAME          | TYPE      | TEAM SIZE | BUDGET");
        IO.println("-".repeat(20));
        for (int i = 0; i < projectCount; i++) {
            switch (filter) {
                case ALL:
                    IO.println(String.format("%s | %s | %s | %s | %s", Projects[i].ID, Projects[i].Name, Projects[i] instanceof SoftwareProject? "Software" : "Hardware", Projects[i].TeamSize, Projects[i].Budget));
                    IO.println("-".repeat(20));
                    break;
                case SOFTWARE:
                    if (Projects[i] instanceof SoftwareProject) {
                        IO.println(String.format("%s | %s | %s | %s | %s", Projects[i].ID, Projects[i].Name, "Software", Projects[i].TeamSize, Projects[i].Budget));
                    }
                    break;
                case HARDWARE:
                    if (Projects[i] instanceof HardwareProject) {
                        IO.println(String.format("%s | %s | %s | %s | %s", Projects[i].ID, Projects[i].Name, "Hardware", Projects[i].TeamSize, Projects[i].Budget));
                    }
                    break;
            }
        }
    }
    public String printProjectsByBudget(double min, double max) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < projectCount; i++) {
            if (Projects[i].Budget >= min && Projects[i].Budget <= max) {
                output.append(Projects[i]);
            }
            break;
        }
        return output.toString();
    }

}
