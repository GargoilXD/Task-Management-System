package Tests;

import Main.interfaces.Completable;
import Main.models.StatusReport;
import Main.models.Task;
import Main.utilities.FileUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Main.services.ProjectService;
import Main.services.ReportService;
import Main.services.TaskService;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {
    static ProjectService projectService;
    static TaskService taskService;
    static ReportService reportService;

    @BeforeAll
    static void setUp() {
        projectService = new ProjectService(FileUtilities.loadProjects());
        taskService = new TaskService(FileUtilities.loadTasks());
        reportService = new ReportService(projectService, taskService);
    }

    @Test
    void updateReports() {
        reportService.updateReports();
        double AverageCompletion = 0;
        for (StatusReport report: reportService.reports.toArray()) {
            int CompletedTasks = 0;
            int UnCompletedTasks = 0;
            int Tasks = 0;
            for (Task task : taskService.getProjectTasks(report.ProjectID)) {
                if (task.Status == Completable.STATUS.COMPLETED) {
                    CompletedTasks += 1;
                } else {
                    UnCompletedTasks += 1;
                }
                Tasks += 1;
            }
            double progress = ((double) CompletedTasks / (double) Tasks) * 100;
            AverageCompletion += progress;
            assertEquals(CompletedTasks, report.CompletedTasks);
            assertEquals(UnCompletedTasks, report.UnCompletedTasks);
            assertEquals(Tasks, report.Tasks);
            assertEquals(progress, report.Progress);
        }
        AverageCompletion /= reportService.reports.size;
        assertEquals(AverageCompletion, reportService.AverageCompletion);
    }
}