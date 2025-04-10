package services;

import java.util.ArrayList;
import java.util.List;
import models.Application;
import utils.FileLoader;
import utils.FileSaver;

public class OfficerApplicationService {
    private static List<Application> applications = FileLoader.loadOfficerApplications("code/database/OfficerApplicationList.csv");

    public List<Application> getApplications() {
        return applications;
    }

    public void addApplication(Application application) {
        applications.add(application);
        saveApplications();
    }

    public void updateApplicationStatus(String user, String project, String newStatus) {
        for (Application application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                application.setStatus(newStatus);
                saveApplications();
                return;
            }
        }
        System.out.println("Application not found.");
    }

    public List<Application> getApplicationsByUser(String nric) {
        List<Application> result = new ArrayList<>();
        for (Application application : applications) {
            if (application.getUser().equalsIgnoreCase(nric) && !application.getStatus().equalsIgnoreCase("Withdrawn")) {
                result.add(application);
            }
        }
        return result;
    }

    public List<Application> getApplicationsByProject(String project) {
        List<Application> result = new ArrayList<>();
        for (Application application : applications) {
            if (application.getProjectName().equalsIgnoreCase(project)) {
                result.add(application);
            }
        }
        return result;
    }

    private void saveApplications() {
        FileSaver.saveOfficerApplications("code/database/OfficerApplicationList.csv", applications);
    }
}
