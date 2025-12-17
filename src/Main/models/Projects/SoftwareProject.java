package Main.models.Projects;

// This is the SoftwareProject class
public class SoftwareProject extends Project {
    public SoftwareProject(String Name, String Description, int TeamSize, double budget) {
        super(Name, Description, TeamSize, budget);
    }
    public SoftwareProject(String ID, String Name, String Description, int TeamSize, double budget) {
        super(ID, Name, Description, TeamSize, budget);
    }
}
