package services;

import models.Projects.HardwareProject;
import models.Projects.Project;
import models.Projects.SoftwareProject;
import utilities.exceptions.EntityAlreadyExists;
import utilities.exceptions.EntityDoesNotExist;
import utilities.KArray;

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
    KArray<Project> projects = new KArray<>(Project.class);

    public ProjectService(Project[] projects) {
        for (Project project : projects) {
            this.projects.add(project);
        }
    }
    public Project[] getProjects() {
        return projects.toArray();
    }
    public Project[] filterProjects(FILTER filter) {
        KArray<Project> filteredProjects = new KArray<>(Project.class);
        for (Project project : projects.toArray()) {
            switch (filter) {
                case FILTER.ALL ignored:
                    filteredProjects.add(project);
                    break;
                case FILTER.SOFTWARE ignored:
                    if (project instanceof SoftwareProject) filteredProjects.add(project);
                    break;
                case FILTER.HARDWARE ignored:
                    if (project instanceof HardwareProject) filteredProjects.add(project);
                    break;
                case FILTER.BUDGET budget:
                    if (project.Budget >= budget.min && project.Budget <= budget.max) filteredProjects.add(project);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + filter);
            }
        }
        return filteredProjects.toArray();
    }
    public Project findProjectByID(String ID) {
        return projects.customFind((project) -> project.ID.equals(ID));
    }
    public Project findProjectByName(String Name) {
        return projects.customFind((project) -> project.Name.equalsIgnoreCase(Name));
    }
    public void createProject(String Name, String Description, int TeamSize, double Budget, boolean isSoftwareProject) {
        if (findProjectByName(Name) != null) throw new EntityAlreadyExists("Project already exists");
        if (isSoftwareProject) {
            projects.add(new SoftwareProject(Name, Description, TeamSize, Budget));
        } else {
            projects.add(new HardwareProject(Name, Description, TeamSize, Budget));
        }
    }
    public void removeProject(String ID) {
        Project found = findProjectByID(ID);
        if (found == null) throw new EntityDoesNotExist("Project with ID " + ID + " does not exist");
        projects.removeElement(found);
    }
}
