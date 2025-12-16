package Main.models;

public class StatusReport {
    public String ProjectID;
    public String ProjectName;
    public int UnCompletedTasks;
    public int CompletedTasks;
    public int Tasks;
    public double Progress;

    public StatusReport(String projectID, String projectName, int unCompletedTasks, int completedTasks, int tasks, double Progress) {
        this.ProjectID = projectID;
        this.ProjectName = projectName;
        this.UnCompletedTasks = unCompletedTasks;
        this.CompletedTasks = completedTasks;
        this.Tasks = tasks;
        this.Progress = Progress;
    }
}
