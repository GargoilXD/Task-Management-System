package models;

// This is the abstract Project class
public abstract class Project {
    public String ID;
    public String Name;
    public String Description;
    public int TeamSize;
    public double Budget;
    public boolean Deleted = false;
    public Project(String Name, String Description, int TeamSize, double Budget) {
        this.Name = Name;
        this.Description = Description;
        this.TeamSize = TeamSize;
        this.Budget = Budget;
    }
}
