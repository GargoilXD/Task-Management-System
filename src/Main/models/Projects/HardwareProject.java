package Main.models.Projects;

// This is the HardwareProject class
public class HardwareProject  extends Project {
    public HardwareProject(String Name, String Description, int TeamSize, double budget) {
        super(Name, Description, TeamSize, budget);
    }
    public HardwareProject(String ID, String Name, String Description, int TeamSize, double budget) {
        super(ID, Name, Description, TeamSize, budget);
    }
}
