package services.subservices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.enums.FlatType;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.ProjectApplication;
import services.interfaces.IApplicationService;
import utils.FileLoader;
import utils.FileSaver;



public class ProjectApplicationService implements IApplicationService{
    private static List<ProjectApplication> applications = FileLoader.loadProjectApplications("code/database/ProjectApplicationList.csv");

    public static List<ProjectApplication> getApplications() {
        return applications;
    }

    public static void addApplication(BTOProject project, String userID, FlatType flatType) {
        ProjectApplication application = new ProjectApplication(userID, project.getProjectName(), ProjectAppStat.PENDING, new Date(), flatType);
        applications.add(application);
        saveApplications();
    }

    public static void updateApplicationStatus(String user, String project, ProjectAppStat newStatus) {
        for (ProjectApplication application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                application.setStatus(newStatus);
                saveApplications();
                return;
            }
        }
        System.out.println("Application not found.");
    }

    public static ProjectApplication getApplicationByUser(String nric) {
        for (ProjectApplication application : applications) {
            if (application.getUser().equalsIgnoreCase(nric) && !(application.getStatus() == ProjectAppStat.UNSUCCESSFUL)) {
                return application;
            }
        }
        return null; // No application found for the user
    }
    
    public static List<ProjectApplication> getApplicationsByProject(String project) {
        List<ProjectApplication> result = new ArrayList<>();
        for (ProjectApplication application : applications) {
            if (application.getProjectName().equalsIgnoreCase(project)) {
                result.add(application);
            }
        }
        return result;
    }

    private static void saveApplications() {
        FileSaver.saveProjectApplications("code/database/ProjectApplicationList.csv", applications);
    }
}
