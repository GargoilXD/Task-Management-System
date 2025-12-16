package Tests;

import Main.interfaces.Completable;
import Main.models.Projects.HardwareProject;
import Main.models.Projects.Project;
import Main.models.Projects.SoftwareProject;
import Main.models.StatusReport;
import Main.models.Task;
import org.junit.jupiter.api.Test;
import Main.services.ProjectService;
import Main.services.ReportService;
import Main.services.TaskService;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {
    ProjectService projectService = new ProjectService(
        new Project[] {
                new SoftwareProject("Alpha Tracker", "Task tracking app for startups", 5, 15000),
                new HardwareProject("IoT Sensor Kit", "Sensor prototype for smart devices", 3, 1000),
                new SoftwareProject("Cloud Backup Tool", "Automated backup solution for SMBs", 4, 12000),
                new HardwareProject("Smart Thermostat", "Energy-efficient home climate control", 6, 2500),
                new SoftwareProject("HR Onboarding Portal", "Streamlined employee onboarding system", 6, 18000),
                new SoftwareProject("EduQuiz Platform", "Interactive quiz app for educators", 4, 9500),
                new HardwareProject("Solar-Powered Charger", "Portable charger using renewable energy", 5, 1800),
    });
    TaskService taskService = new TaskService(
        new Task[] {
                new Task("P001", "Design Database", Task.STATUS.COMPLETED),
                new Task("P001", "Implement API", Task.STATUS.IN_PROGRESS),
                new Task("P001", "Write Unit Tests", Task.STATUS.PENDING),
                new Task("P002", "Gather Materials", Task.STATUS.COMPLETED),
                new Task("P002", "Build prototype", Task.STATUS.IN_PROGRESS),
                new Task("P003", "Define Backup Strategy", Task.STATUS.COMPLETED),
                new Task("P003", "Develop Sync Engine", Task.STATUS.IN_PROGRESS),
                new Task("P003", "Create UI Dashboard", Task.STATUS.PENDING),
                new Task("P004", "Circuit Design", Task.STATUS.COMPLETED),
                new Task("P004", "Firmware Development", Task.STATUS.IN_PROGRESS),
                new Task("P004", "Enclosure Prototyping", Task.STATUS.PENDING),
                new Task("P005", "User Authentication", Task.STATUS.COMPLETED),
                new Task("P005", "Document Upload Module", Task.STATUS.IN_PROGRESS),
                new Task("P005", "Integration with Payroll", Task.STATUS.PENDING),
                new Task("P006", "User Registration Flow", Task.STATUS.COMPLETED),
                new Task("P006", "Quiz Builder UI", Task.STATUS.IN_PROGRESS),
                new Task("P006", "Real-time Grading Engine", Task.STATUS.PENDING),
                new Task("P007", "Solar Panel Sourcing", Task.STATUS.COMPLETED),
                new Task("P007", "Battery Integration", Task.STATUS.COMPLETED),
                new Task("P007", "Safety & Overcharge Protection", Task.STATUS.IN_PROGRESS),
    });
    ReportService reportService = new ReportService(projectService, taskService);
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