package services;

import models.Projects.HardwareProject;
import models.Projects.Project;
import models.Projects.SoftwareProject;
import utilities.KArray;

public class ProjectService {
    public enum FILTER {
        ALL(0, 0),
        SOFTWARE(0, 0),
        HARDWARE(0, 0),
        BUDGET(0, 0);

        public int min;
        public int max;
        FILTER(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
    KArray<Project> projects =  new KArray<Project>();

    public ProjectService(Project[] projects) {
        for (Project project : projects) {
            this.projects.add(project);
        }
    }
    public Project[] getProjects() {
        return projects.toArray();
    }
    public Project[] filterProjects(FILTER filter) {
        KArray<Project> filteredProjects = new KArray<Project>();
        for (Project project : projects.toArray()) {
            switch (filter) {
                case ALL:
                    filteredProjects.add(project);
                    break;
                case SOFTWARE:
                    if (project instanceof SoftwareProject) filteredProjects.add(project);
                    break;
                case HARDWARE:
                    if (project instanceof HardwareProject) filteredProjects.add(project);
                    break;
                case BUDGET:
                    if (project.Budget >= filter.min && project.Budget <= filter.max)filteredProjects.add(project);
                    break;
            }
        }
        return filteredProjects.toArray();
    }
    public Project findProjectByID(String ID) {
        return projects.customFind((project) -> project.ID.equals(ID));
    }
    public Project findProjectByName(String Name) {
        return projects.customFind((project) -> project.Name.equals(Name));
    }
    public void createProject(String Name, String Description, int TeamSize, double Budget, boolean isSoftwareProject) {
        if (findProjectByName(Name) != null) return;
        if (isSoftwareProject) {
            projects.add(new SoftwareProject(Name, Description, TeamSize, Budget));
        } else {
            projects.add(new HardwareProject(Name, Description, TeamSize, Budget));
        }
    }
    public void removeProject(String ID) {
        projects.removeElement(projects.customFind((project) -> project.ID.equals(ID)));
    }
}
