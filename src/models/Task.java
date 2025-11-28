package models;

import interfaces.Completable;

// This is the User Class
public class Task implements Completable {
    public String projectID;
    public String ID;
    public String name;
    public STATUS status;
    // Memory management design decision. This is the delete flag
    public boolean deleted = false;
    public Task(String projectID, String ID, String name, STATUS status) {
        this.ID = ID;
        this.projectID = projectID;
        this.name = name;
        this.status = status;
    }
}
