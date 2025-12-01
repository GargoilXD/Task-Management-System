package services;

import interfaces.Completable;
import models.Project;
import models.StatusReport;
import models.Task;

public class ReportService {
    static final int MAX_REPORT_COUNT = 10;
    public StatusReport[] reports = new StatusReport[MAX_REPORT_COUNT];
    // This counts the reports
    int reportIndex = 0;
    ProjectService projectService;
    TaskService taskService;
    public ReportService(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    public void updateReports() {
        reportIndex = 0;
        for (Project project : projectService.filterProjects(ProjectService.FILTER.ALL)) {
            Task[] projectTasks = taskService.getProjectTasks(project.ID);
            // Object reuse
            if (reports[reportIndex] != null) {
                 reports[reportIndex].ProjectID = project.ID;
                 reports[reportIndex].ProjectName = project.Name;
                reports[reportIndex].CompletedTasks = 0;
                reports[reportIndex].UnCompletedTasks = 0;
                reports[reportIndex].Tasks = 0;
            } else {
                reports[reportIndex] = new StatusReport(project.ID, project.Name, 0 ,0, 0);
            }
            for (Task task : projectTasks) {
                if (task.status == Completable.STATUS.COMPLETED) {
                    reports[reportIndex].CompletedTasks += 1;
                } else {
                    reports[reportIndex].UnCompletedTasks += 1;
                }
                reports[reportIndex].Tasks += 1;
            }
            reportIndex++;
        }
    }
}
