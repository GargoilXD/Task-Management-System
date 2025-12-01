package services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;

// This is the ProjectService class, responsible for Managing projects.
// The memory management approach was to delete projects by changing the delete flag
// Then replacing deleted projects with new ones like the TaskService
public class ProjectService {
    static final int MAX_PROJECT_COUNT = 10;
    Project[] projects = new Project[MAX_PROJECT_COUNT];
    // This counts the projects
    int projectIndex = 0;

    public ProjectService(Project[] projects) {
        for (Project project : projects) {
            if (project != null) {
                this.projects[projectIndex] = project;
                project.ID = String.format("P%03d", projectIndex + 1);
                projectIndex++;
            }
        }
    }
    public Project findProject(String projectID) {
        for (int index = 0; index < projectIndex; index++) {
            // Also check if task is deleted
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
            // Also check if task is deleted
            if (projects[index].Name.equals(projectName) && !projects[index].Deleted) {
                return projects[index];
            }
        }
        return null;
    }
    public void createProject(Project project) {
        if (findProjectByName(project.Name) != null) {
            System.out.println("Project already exists");
            return;
        }
        Project deletedProject = getDeletedProject();
        if (deletedProject == null) {
            if (projectIndex >= MAX_PROJECT_COUNT) {
                System.out.println("Maximum number of projects reached");
                return;
            }
            projects[projectIndex] = project;
            project.ID = String.format("P%03d", projectIndex + 1);
            projectIndex++;
        } else {
            // Old: deletedProject = project;
            // Wasn't sure if assigning a new object will cause memory problems, so I left this
            // Project ID is not changed, it's preserved. See TaskService
            deletedProject.Name = project.Name;
            deletedProject.Description = project.Description;
            deletedProject.TeamSize = project.TeamSize;
            deletedProject.Budget = project.Budget;
            // The slot is freed
            deletedProject.Deleted = false;
        }
        System.out.println("Project created successfully");
    }
    public void removeProject(String projectID) {
        Project project = findProject(projectID);
        if (project == null) {
            System.out.println("Project not found");
            return;
        }
        // The slot is freed
        project.Deleted = true;
        System.out.println("Project deleted successfully");
    }
    // Polymorphic method!
    public enum FILTER { ALL, SOFTWARE, HARDWARE, BUDGET }
    // This one filters with ALL, SOFTWARE, HARDWARE except budget.
    // See the other function and Line 413 in main class (Third Line in projectFilterProcess).
    public Project[] filterProjects(FILTER filter) {
        // Create an array with size of taskIndex, that's the maximum possible size.
        Project[] filteredProjects = new Project[projectIndex];
        int filteredProjectsIndex = 0;
        for (int index = 0; index < projectIndex; index++) {
            if (projects[index].Deleted) {
                continue;
            }
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
                case BUDGET:
                    // Impossible case here, handled in the other function.
                    break;
            }
        }
        // Shrink the array to the real size.
        Project[] shrunken = new Project[filteredProjectsIndex];
        System.arraycopy(filteredProjects, 0, shrunken, 0, filteredProjectsIndex);
        return shrunken;
    }
    // This one filters with budget.
    public Project[] filterProjects(int min, int max) {
        Project[] filteredProjects = new Project[projectIndex];
        int filteredProjectsIndex = 0;
        for (int index = 0; index < projectIndex; index++) {
            if (projects[index].Budget >= min && projects[index].Budget <= max && !projects[index].Deleted) {
                filteredProjects[filteredProjectsIndex] = projects[index];
                filteredProjectsIndex++;
            }
        }
        Project[] shrunken = new Project[filteredProjectsIndex];
        System.arraycopy(filteredProjects, 0, shrunken, 0, filteredProjectsIndex);
        return shrunken;
    }
}
