package services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;

public class ProjectService {
    static final int MAX_PROJECT_COUNT = 10;
    Project[] projects = new Project[MAX_PROJECT_COUNT];
    int projectIndex = 0;

    public Project findProject(String projectID) {
        for (int index = 0; index < projectIndex; index++) {
            if (projects[index].ID.equals(projectID) && !projects[index].Deleted) {
                return projects[index];
            }
        }
        return null;
    }
    Project getDeletedProject() {
        for (int index = 0; index < projectIndex; index++) {
            if (projects[index].Deleted) {
                return projects[index];
            }
        }
        return null;
    }
    public Project findProjectByName(String projectName) {
        for (int index = 0; index < projectIndex; index++) {
            if (projects[index].Name.equals(projectName) && !projects[index].Deleted) {
                return projects[index];
            }
        }
        return null;
    }
    public boolean createProject(Project project) {
        if (findProjectByName(project.Name) != null) return false;
        Project deletedProject = getDeletedProject();
        if (deletedProject == null) {
            if (projectIndex >= MAX_PROJECT_COUNT) return false;
            projects[projectIndex] = project;
            project.ID = String.format("P%03d", projectIndex + 1);
            projectIndex++;
        } else {
            //deletedProject = project;
            deletedProject.Name = project.Name;
            deletedProject.Description = project.Description;
            deletedProject.TeamSize = project.TeamSize;
            deletedProject.Budget = project.Budget;
            deletedProject.Deleted = false;
        }
        return true;
    }
    public boolean removeProject(String projectID) {
        Project project = findProject(projectID);
        if (project == null) return false;
        project.Deleted = true;
        return true;
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
