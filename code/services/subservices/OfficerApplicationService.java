package services.subservices;

import database.dataclass.projects.OfficerAppDB;
import database.dataclass.projects.ProjectDB;
import database.dataclass.users.OfficerDB;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import models.enums.OfficerAppStat;
import models.projects.BTOProject;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.HDBOfficer;
import services.interfaces.IOfficerApplicationService;
import view.ViewFormatter;


public class OfficerApplicationService implements IOfficerApplicationService {
    private static List<OfficerApplication> applications = OfficerAppDB.getDB();

    public static List<OfficerApplication> getApplications() {
        return applications;
    }

    public OfficerApplication chooseFromApplicationList(Scanner sc, List<OfficerApplication> applications) {
        System.out.println("Display only 'pending' applications? (yes/no): ");
        String filterChoice = sc.nextLine().trim().toLowerCase();
        if (filterChoice.equals("yes")) {
            applications.removeIf(app -> !app.getStatus().equals(OfficerAppStat.PENDING));
        }
        if (applications.isEmpty()) {
            System.out.println("No applications available.");
            return null;
        }
        System.out.println("Choose an application to view details:");
        for (int i = 0; i < applications.size(); i++) {
            String applicantName = OfficerDB.getUsernameByID(applications.get(i).getUser());
            System.out.println((i + 1) + ". " + applicantName + " - " + applications.get(i).getProjectName() + " - " + applications.get(i).getStatus());
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

    public void addApplication(String projectName, String userID){
        OfficerAppDB.addApplication(new OfficerApplication(userID, projectName, OfficerAppStat.PENDING, new Date()));
    }

    public void updateApplicationStatus(String user, String project, OfficerAppStat newStatus) {
        for (OfficerApplication application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                updateApplicationStatus(application, newStatus);
                return;
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
        OfficerAppDB.updateApplication(application);
        System.out.println("Application status updated to: " + newStatus);
    }

    public List<OfficerApplication> getApplicationsByUser(String userId) {
        return OfficerAppDB.getApplicationsByUser(userId);
    }

    public List<OfficerApplication> getProjectApplications(String projectName) {
        return OfficerAppDB.getApplicationsByProject(projectName);
    }

    public void applyForOfficer(HDBOfficer officer, BTOProject project) {
        if (project == null) {
			System.out.println("Project not found.");
			return;
		}
		if (project.getOfficerSlot() <= 0) {
			System.out.println("No officer slots available for this project.");
			return;
		}
        List<ProjectApplication> applications = officer.getMyApplication();
        if (applications.stream().anyMatch(app -> app.getProjectName().equals(project.getProjectName()))) {
            System.out.println(
            "Cannot register as an officer for \"" + project.getProjectName() 
            + "\"\n because you’ve already applied for it as an applicant.\n" 
            + "Please check your application status."
            );
            System.out.println(ViewFormatter.breakLine());
            return;
        }

		List<OfficerApplication> apps = officer.getOfficerApplications();

		apps.removeIf(app -> app.getStatus() == OfficerAppStat.REJECTED);

		for (OfficerApplication app: apps) {
			BTOProject p = ProjectDB.getProjectByName(app.getProjectName());
			if (p != null && (!p.getOpeningDate().after(project.getClosingDate()) || !p.getClosingDate().before(project.getOpeningDate()))) {
				System.out.println("This project overlaps with\n your current application.");
				System.out.println("Current application: " + app.getProjectName() + "\n| Status: " + app.getStatus());
                System.out.println(ViewFormatter.breakLine());
				return;
			}
		}
		addApplication(project.getProjectName(), officer.getNric());
		System.out.println("Registration PENDING approval.");
        System.out.println(ViewFormatter.breakLine());
    }


}
