package Main.services;

import Main.models.Projects.HardwareProject;
import Main.models.Projects.Project;
import Main.models.Projects.SoftwareProject;
import Main.utilities.exceptions.EntityAlreadyExists;
import Main.utilities.exceptions.EntityDoesNotExist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProjectService {
    // FatEnum
    public static class FILTER {
        public static class ALL extends FILTER {}
        public static class SOFTWARE extends FILTER {}
        public static class HARDWARE extends FILTER {}
        public static class BUDGET extends FILTER {
            public final int min;
            public final int max;
            public BUDGET() {
                this.min = 0;
                this.max = 0;
            }
            public BUDGET(int min, int max) {
                this.min = min;
                this.max = max;
            }
        }
    }
    HashMap<String, Project> projects = new HashMap<>();

    public ProjectService(ArrayList<Project> projects) {
        for (Project project : projects) {
            this.projects.put(project.ID, project);
        }
    }
    public ArrayList<Project> getProjects() {
        return new ArrayList<>(projects.values());
    }
    public ArrayList<Project> filterProjects(FILTER filter) {
        return projects.values().stream().filter(project -> switch (filter) {
            case FILTER.ALL ignored -> true;
            case FILTER.SOFTWARE ignored -> (project instanceof SoftwareProject);
            case FILTER.HARDWARE ignored -> (project instanceof HardwareProject);
            case FILTER.BUDGET budget -> (project.Budget >= budget.min && project.Budget <= budget.max);
            default -> throw new IllegalStateException("Unexpected value: " + filter);
        }).collect(Collectors.toCollection(ArrayList::new));
    }
    public Project findProjectByID(String ID) {
        return projects.get(ID);
    }
    public Project findProjectByName(String Name) {
        for (Project project : projects.values()) {
            if (project.Name.equalsIgnoreCase(Name)) return project;
        }
        return null;
    }
    public void createProject(String Name, String Description, int TeamSize, double Budget, boolean isSoftwareProject) {
        if (findProjectByName(Name) != null) throw new EntityAlreadyExists("Project already exists");
        Project project = isSoftwareProject? new SoftwareProject(Name, Description, TeamSize, Budget) : new HardwareProject(Name, Description, TeamSize, Budget);
        projects.put(project.ID, project);
    }
    public void removeProject(String ID) {
        if (!projects.containsKey(ID)) throw new EntityDoesNotExist("Project with ID " + ID + " does not exist");
        projects.remove(ID);
    }
}
