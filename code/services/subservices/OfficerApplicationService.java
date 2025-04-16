package services.subservices;

import java.util.ArrayList;
import java.util.List;

import database.dataclass.projects.OfficerAppDB;
import models.projects.Application;
import models.projects.OfficerApplication;
import services.interfaces.IApplicationService;


public class OfficerApplicationService implements IApplicationService {
    private static List<OfficerApplication> applications = OfficerAppDB.getDB();

    public static List<OfficerApplication> getApplications() {
        return applications;
    }

    public static void addApplication(OfficerApplication application) {
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
        OfficerAppDB.updateDB(applications);
    }
}
