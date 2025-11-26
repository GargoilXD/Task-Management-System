package models;

public abstract class Project {
    public String ID;
    public String Name;
    public String Description;
    public int TeamSize;
    public double Budget;
    public Project(String ID, String Name, String Description, int TeamSize, double Budget) {
        this.ID = ID;
        this.Name = Name;
        this.Description = Description;
        this.TeamSize = TeamSize;
        this.Budget = Budget;
    }
}
