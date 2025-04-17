package services.subservices;

import database.dataclass.projects.OfficerAppDB;
import java.util.ArrayList;
import java.util.List;
import models.enums.ProjectAppStat;
import java.util.Scanner;

import database.dataclass.projects.OfficerAppDB;
import models.projects.Application;
import models.projects.OfficerApplication;
import models.enums.OfficerAppStat;
import models.users.HDBOfficer;

import services.interfaces.IOfficerApplicationService;



public class OfficerApplicationService implements IOfficerApplicationService {

    public void applyForOfficer(Scanner sc, HDBOfficer user, String selectedProjectName){}

    public static void updateApplicationStatus(String user, String project, ProjectAppStat newStatus) {
        for (Application application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {}
        }
    }

    public void updateApplicationStatus(String user, String projectName, OfficerAppStat newStatus) {
        List<OfficerApplication> applications = OfficerAppDB.getDB();
        for (OfficerApplication application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(projectName)) {
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
            if (application.getUser().equalsIgnoreCase(nric) && !(application.getStatus() == ProjectAppStat.WITHDRAW)) {
                result.add(application);
            }
        }
        return result;
    }
  public OfficerAppStat viewApplicationStatus(String userID){
    return null;
  }

}
