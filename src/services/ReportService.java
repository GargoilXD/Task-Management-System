package services;

import interfaces.Completable;
import models.Projects.Project;
import models.StatusReport;
import models.Task;
import utilities.exceptions.EmptyProjectException;
import utilities.KArray;

public class ReportService {
    public KArray<StatusReport> reports = new KArray<StatusReport>(StatusReport.class);
    public double AverageCompletion = 0;
    ProjectService projectService;
    TaskService taskService;

    public ReportService(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }
    StatusReport generateReport(String projectID, String projectName) {
        StatusReport newReport = new StatusReport(projectID, projectName, 0 ,0, 0, 0);
        Task[] projectTasks = taskService.getProjectTasks(projectID);
        if (projectTasks.length == 0) {
            throw new EmptyProjectException("Error: No Tasks in project " + projectID);
        }
        for (Task task : projectTasks) {
            if (task.Status == Completable.STATUS.COMPLETED) {
                newReport.CompletedTasks += 1;
            } else {
                newReport.UnCompletedTasks += 1;
            }
            newReport.Tasks += 1;
        }
        newReport.Progress = (newReport.CompletedTasks / ((double) newReport.Tasks)) * 100;
        return newReport;
    }
    public void updateReports() {
        int numberValidOfProjects = 0;
        reports.clear();
        for (Project project : projectService.getProjects()) {
            try {
                StatusReport report = generateReport(project.ID, project.Name);
                AverageCompletion += report.Progress;
                numberValidOfProjects++;
                reports.add(report);
            } catch (EmptyProjectException e) {
                System.out.println(e.getMessage());
            }
        }
        AverageCompletion = (numberValidOfProjects != 0)? AverageCompletion / numberValidOfProjects : 0;
    }
}
