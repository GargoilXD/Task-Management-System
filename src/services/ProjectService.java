package services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import models.Task;

public class ProjectService {
    static final int MAX_PROJECT_COUNT = 10;
    Project[] projects = new Project[MAX_PROJECT_COUNT];
    int projectIndex = 0;

    public Project findProject(String projectID) {
        for (int index = 0; index < projectIndex; index++) {
            if (projects[index].ID.equals(projectID)) {
                return projects[index];
            }
        }
        return null;
    }
    public void createProject(Project project) {
        if (projectIndex >= MAX_PROJECT_COUNT || findProject(project.ID) != null) return;
        projects[projectIndex] = project;
        projectIndex++;
    }
    public enum FILTER { ALL, SOFTWARE, HARDWARE, BUDGET }
    public Project[] filterProjects(FILTER filter) {
        Project[] filteredProjects = new Project[projectIndex];
        int filteredProjectsIndex = 0;
        for (int index = 0; index < projectIndex; index++) {
            switch (filter) {
                case ALL:
                    filteredProjects[filteredProjectsIndex] = projects[index];
                    filteredProjectsIndex++;
                    break;
                case SOFTWARE:
                    if (projects[index] instanceof SoftwareProject) {
                        filteredProjects[filteredProjectsIndex] = projects[index];
                        filteredProjectsIndex++;
                    }
                    break;
                case HARDWARE:
                    if (projects[index] instanceof HardwareProject) {
                        filteredProjects[filteredProjectsIndex] = projects[index];
                        filteredProjectsIndex++;
                    }
                    break;
            }
        }
        Project[] shrunken = new Project[filteredProjectsIndex];
        System.arraycopy(filteredProjects, 0, shrunken, 0, filteredProjectsIndex);
        return shrunken;
    }
    public Project[] filterProjects(int min, int max) {
        Project[] filteredProjects = new Project[projectIndex];
        int filteredProjectsIndex = 0;
        for (int index = 0; index < projectIndex; index++) {
            if (projects[index].Budget >= min && projects[index].Budget <= max) {
                filteredProjects[filteredProjectsIndex] = projects[index];
                filteredProjectsIndex++;
            }
        }
        Project[] shrunken = new Project[filteredProjectsIndex];
        System.arraycopy(filteredProjects, 0, shrunken, 0, filteredProjectsIndex);
        return shrunken;
    }
}
