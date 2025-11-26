package models;

public class Task {
    public enum STATUS {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }
    public String ProjectID;
    public String ID;
    public String Name;
    public STATUS Status;
    public Task(String ID, String ProjectID, String Name, STATUS Status) {
        this.ID = ID;
        this.ProjectID = ProjectID;
        this.Name = Name;
        this.Status = Status;
    }
}
