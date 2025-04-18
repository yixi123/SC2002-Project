package services.subservices;

import database.dataclass.projects.OfficerAppDB;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import models.enums.OfficerAppStat;
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

    public void updateApplicationStatus(String user, String project, OfficerAppStat newStatus) {
        for (OfficerApplication application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                updateApplicationStatus(application, newStatus);
            }
        }
        System.out.println("Application not found.");
    }

    public void updateApplicationStatus(OfficerApplication application, OfficerAppStat newStatus) {
        if (newStatus == application.getStatus()) {
            System.out.println("Application status is already " + newStatus);
            return;
        }
        application.setStatus(newStatus);
        saveApplications();
        System.out.println("Application status updated to: " + newStatus);
    }

    public static List<Application> getApplicationsByUser(String nric) {
        List<Application> result = new ArrayList<>();
        for (Application application : applications) {
            if (application.getUser().equalsIgnoreCase(nric)) {
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
    public List<OfficerApplication> getProjectApplications(String projectName) {
        return OfficerAppDB.getApplicationsByProject(projectName);
    }

    @Override
    public OfficerApplication chooseFromApplicationList(Scanner sc, List<OfficerApplication> applications) {
        System.out.println("Display only 'pending' applications? (yes/no): ");
        String filterChoice = sc.nextLine().trim().toLowerCase();
        if (filterChoice.equals("yes")) {
            applications.removeIf(app -> !app.getStatus().equals(OfficerAppStat.PENDING));
        }
        
        System.out.println("Choose an application to view details:");
        for (int i = 0; i < applications.size(); i++) {
            System.out.println((i + 1) + ". " + applications.get(i).getProjectName() + " - " + applications.get(i).getStatus());
        }
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (choice < 1 || choice > applications.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return null;
        }
        OfficerApplication selectedApplication = applications.get(choice - 1);
        return selectedApplication;
    }
}
