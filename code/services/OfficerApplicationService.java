package services;

import java.util.ArrayList;
import java.util.List;
import models.Application;
import utils.FileLoader;
import utils.FileSaver;

public class OfficerApplicationService {
    private static List<Application> applications = FileLoader.loadOfficerApplications("code/database/OfficerApplicationList.csv");

    public static List<Application> getApplications() {
        return applications;
    }

    public static void addApplication(Application application) {
        applications.add(application);
        saveApplications();
    }

    public static void updateApplicationStatus(String user, String project, String newStatus) {
        for (Application application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                application.setStatus(newStatus);
                saveApplications();
                return;
            }
        }
        System.out.println("Application not found.");
    }

    public static List<Application> getApplicationsByUser(String nric) {
        List<Application> result = new ArrayList<>();
        for (Application application : applications) {
            if (application.getUser().equalsIgnoreCase(nric) && !application.getStatus().equalsIgnoreCase("Withdrawn")) {
                result.add(application);
            }
        }
        return result;
    }

    public static List<Application> getApplicationsByProject(String project) {
        List<Application> result = new ArrayList<>();
        for (Application application : applications) {
            if (application.getProjectName().equalsIgnoreCase(project)) {
                result.add(application);
            }
        }
        return result;
    }

    private static void saveApplications() {
        FileSaver.saveOfficerApplications("code/database/OfficerApplicationList.csv", applications);
    }
}
