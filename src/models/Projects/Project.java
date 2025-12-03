package models.Projects;

public abstract class Project {
    public String ID;
    public String Name;
    public String Description;
    public int TeamSize;
    public double Budget;
    static int LastID = 1;

    public Project(String Name, String Description, int TeamSize, double Budget) {
        this.ID = String.format("P%03d", LastID);
        LastID++;
        this.Name = Name;
        this.Description = Description;
        this.TeamSize = TeamSize;
        this.Budget = Budget;
    }
}
