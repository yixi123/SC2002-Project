package services.subservices;

import database.dataclass.projects.OfficerAppDB;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.enums.OfficerAppStat;
import models.enums.ProjectAppStat;
import java.util.Scanner;

import database.dataclass.projects.OfficerAppDB;
import models.projects.Application;
import models.projects.OfficerApplication;
import models.users.HDBOfficer;
import services.interfaces.IOfficerApplicationService;


public class OfficerApplicationService implements IOfficerApplicationService {
    private static List<OfficerApplication> applications = OfficerAppDB.getDB();

    public static List<OfficerApplication> getApplications() {
        return applications;
    }

    public void addApplication(String projectName, String userID){
        applications.add(new OfficerApplication(userID, projectName, OfficerAppStat.PENDING, new Date()));
        saveApplications();
    }

    public static void updateApplicationStatus(String user, String project, ProjectAppStat newStatus) {
        for (Application application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                application.setStatus(newStatus);
                OfficerAppDB.updateDB(applications);
                return;
            }
        }
        System.out.println("Application not found.");
    }

    public static List<Application> getApplicationsByUser(String nric) {
        List<Application> result = new ArrayList<>();
        for (Application application : applications) {
            if (application.getUser().equalsIgnoreCase(nric) && !(application.getStatus() == ProjectAppStat.WITHDRAWN)) {
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

    @Override
    public void applyForOfficer(Scanner sc, HDBOfficer user, String selectedProjectName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyForOfficer'");
    }

    @Override
    public OfficerAppStat viewApplicationStatus(String userID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewApplicationStatus'");
    }

    @Override
    public void updateApplicationStatus(String user, String projectName, OfficerAppStat newStatus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateApplicationStatus'");
    }

}
