package services;

import interfaces.Completable;
import models.Projects.Project;
import models.StatusReport;
import models.Task;
import utilities.KArray;

public class ReportService {
    public KArray<StatusReport> reports = new KArray<StatusReport>(StatusReport.class);
    ProjectService projectService;
    TaskService taskService;

    public ReportService(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }
    public void updateReports() {
        reports.clear();
        for (Project project : projectService.getProjects()) {
            StatusReport newReport = new StatusReport(project.ID, project.Name, 0 ,0, 0);
            for (Task task : taskService.getProjectTasks(project.ID)) {
                if (task.Status == Completable.STATUS.COMPLETED) {
                    newReport.CompletedTasks += 1;
                } else {
                    newReport.UnCompletedTasks += 1;
                }
                newReport.Tasks += 1;
            }
            reports.add(newReport);
        }
    }
}
