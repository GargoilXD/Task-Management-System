package models;

public class StatusReport {
    public String ProjectID;
    public String ProjectName;
    public int UnCompletedTasks;
    public int CompletedTasks;
    public int Tasks;

    public StatusReport(String projectID, String projectName, int unCompletedTasks, int completedTasks, int tasks) {
        this.ProjectID = projectID;
        this.ProjectName = projectName;
        this.UnCompletedTasks = unCompletedTasks;
        this.CompletedTasks = completedTasks;
        this.Tasks = tasks;
    }
}
