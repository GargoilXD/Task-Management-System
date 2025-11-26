package services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import utils.OperationResult;

public class ProjectService {
    /*private static ProjectService projectService;
    private ProjectService() {}
    public static ProjectService getInstance() {
        if (projectService == null) {
            projectService = new ProjectService();
        }
        return projectService;
    }*/
    static final int MAX_PROJECT_COUNT = 100;
    public Project[] Projects =  new Project[MAX_PROJECT_COUNT];
    public int projectCount = 0;

    public Project findProject(String projectID) {
        for (int i = 0; i < projectCount; i++) {
            if (Projects[i].ID.equals(projectID)) {
                return Projects[i];
            }
        }
        return null;
    }
    public OperationResult createProject(Project project) {
        if (projectCount == MAX_PROJECT_COUNT) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Project limit reached");
        }
        if (findProject(project.ID) != null) {
            return new OperationResult(OperationResult.STATUS.FAILURE, "Project ID already exists");
        }
        Projects[projectCount] = project;
        projectCount++;
        return new OperationResult(OperationResult.STATUS.SUCCESS);
    }
    public enum FILTER {
        ALL,
        SOFTWARE,
        HARDWARE
    }
    public String printProjects(FILTER filter) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < projectCount; i++) {
            switch (filter) {
                case ALL:
                    output.append(Projects[i]);
                    break;
                case SOFTWARE:
                    if (Projects[i] instanceof SoftwareProject) {
                        output.append(Projects[i]);
                    }
                    break;
                case HARDWARE:
                    if (Projects[i] instanceof HardwareProject) {
                        output.append(Projects[i]);
                    }
                    break;
            }

        }
        return output.toString();
    }
    public String printProjectsByBudget(double min, double max) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < projectCount; i++) {
            if (Projects[i].budget >= min && Projects[i].budget <= max) {
                output.append(Projects[i]);
            }
            break;
        }
        return output.toString();
    }
}
