package models;

import interfaces.Completable;

public class Task implements Completable {
    public String ProjectID;
    public String ID;
    public String Name;
    public STATUS Status;
    public boolean deleted = false;
    public Task(String ProjectID, String ID, String Name, STATUS Status) {
        this.ID = ID;
        this.ProjectID = ProjectID;
        this.Name = Name;
        this.Status = Status;
    }
}
