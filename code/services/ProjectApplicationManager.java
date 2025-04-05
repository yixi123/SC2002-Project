package services;

import java.util.ArrayList;
import java.util.List;
import models.ProjectApplication;
import utils.FileLoader;
import utils.FileSaver;

public class ProjectApplicationManager {
    private static List<ProjectApplication> applications = FileLoader.loadProjectApplications("code/database/ProjectApplicationList.csv");

    public List<ProjectApplication> getApplications() {
        return applications;
    }

    public void addApplication(ProjectApplication application) {
        applications.add(application);
        saveApplications();
    }

    public void updateApplicationStatus(String user, String project, String newStatus) {
        for (ProjectApplication application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                application.setStatus(newStatus);
                saveApplications();
                return;
            }
        }
        System.out.println("Application not found.");
    }

    public ProjectApplication getApplicationByUser(String user) {
        for (ProjectApplication application : applications) {
            if (application.getUser().equalsIgnoreCase(user) && !application.getStatus().equalsIgnoreCase("Withdrawn")) {
                return application;
            }
        }
        return null; // No application found for the user
    }
    
    public List<ProjectApplication> getApplicationsByProject(String project) {
        List<ProjectApplication> result = new ArrayList<>();
        for (ProjectApplication application : applications) {
            if (application.getProjectName().equalsIgnoreCase(project)) {
                result.add(application);
            }
        }
        return result;
    }

    private void saveApplications() {
        FileSaver.saveProjectApplications("code/database/ProjectApplicationList.csv", applications);
    }
}
