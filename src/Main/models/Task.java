package Main.models;

import Main.interfaces.Completable;

// This is the User Class
public class Task implements Completable {
    public String ProjectID;
    public String ID;
    public String Name;
    public STATUS Status;
    // Static variable that all objects share. For keeping track of user IDs
    static int LastID = 1;

    public Task(String ProjectID, String Name, STATUS Status) {
        this.ID = String.format("T%03d", LastID);
        LastID++;
        this.ProjectID = ProjectID;
        this.Name = Name;
        this.Status = Status;
    }
    public Task(String ID, String ProjectID, String Name, STATUS Status) {
        this.ID = ID;
        this.ProjectID = ProjectID;
        this.Name = Name;
        this.Status = Status;
        LastID++;
    }
}