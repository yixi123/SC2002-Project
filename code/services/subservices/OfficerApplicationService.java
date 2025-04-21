package services.subservices;

import database.dataclass.projects.OfficerAppDB;
import database.dataclass.projects.ProjectDB;
import database.dataclass.users.OfficerDB;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.View;

import models.enums.OfficerAppStat;
import models.projects.BTOProject;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.HDBOfficer;
import services.interfaces.IOfficerApplicationService;
import view.ViewFormatter;

/**
 * Service class that handles officer application processes,
 * including submission, filtering, retrieval, and status updates.
 * It interacts with OfficerAppDB, OfficerDB, and ProjectDB to access and modify relevant data.
 */
public class OfficerApplicationService implements IOfficerApplicationService {

    /**
     * The list of officer applications loaded from the database.
     */
    private static List<OfficerApplication> applications = OfficerAppDB.getDB();

    /**
     * Returns the list of all officer applications currently in memory.
     *
     * @return List of officer applications
     */
    public static List<OfficerApplication> getApplications() {
        return applications;
    }

    /**
     * Allows the user to choose a specific officer application from a provided list,
     * optionally filtering for only PENDING applications.
     *
     * @param sc Scanner for user input
     * @param applications List of applications to choose from
     * @return The selected OfficerApplication, or null if cancelled or invalid
     */
    public OfficerApplication chooseFromApplicationList(Scanner sc, List<OfficerApplication> applications) {
        System.out.print("Display only 'pending' applications?\n (yes/no): ");
        String filterChoice = sc.nextLine().trim().toLowerCase();
        System.out.println(ViewFormatter.breakLine());
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
        System.out.println(ViewFormatter.breakLine());
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.println(ViewFormatter.breakLine());

        if (choice < 1 || choice > applications.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return null;
        }
        OfficerApplication selectedApplication = applications.get(choice - 1);
        return selectedApplication;
    }

    /**
     * Adds a new officer application with status PENDING and current date.
     *
     * @param projectName The project to apply to
     * @param userID The NRIC of the applying officer
     */
    public void addApplication(String projectName, String userID){
        OfficerAppDB.addApplication(new OfficerApplication(userID, projectName, OfficerAppStat.PENDING, new Date()));
    }

    /**
     * Updates the application status for a specific user-project combination.
     *
     * @param user The NRIC of the officer
     * @param project The name of the project
     * @param newStatus The new status to set (e.g., APPROVED, REJECTED)
     */
    public void updateApplicationStatus(String user, String project, OfficerAppStat newStatus) {
        for (OfficerApplication application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                updateApplicationStatus(application, newStatus);
                return;
            }
        }
        System.out.println("Application not found.");
    }

    /**
     * Updates the status of a specific OfficerApplication object.
     *
     * @param application The application to update
     * @param newStatus The status to be set
     */
    public void updateApplicationStatus(OfficerApplication application, OfficerAppStat newStatus) {
        if (newStatus == application.getStatus()) {
            System.out.println("Application status is already " + newStatus);
            return;
        }
        application.setStatus(newStatus);
        OfficerAppDB.updateApplication();
        System.out.println("Application status updated to: " + newStatus);
    }

    /**
     * Retrieves all officer applications submitted by a specific officer.
     *
     * @param userId The NRIC of the officer
     * @return List of officer applications
     */
    public List<OfficerApplication> getApplicationsByUser(String userId) {
        return OfficerAppDB.getApplicationsByUser(userId);
    }

    /**
     * Retrieves all officer applications submitted for a specific BTO project.
     *
     * @param projectName The name of the project
     * @return List of officer applications for the project
     */
    public List<OfficerApplication> getProjectApplications(String projectName) {
        return OfficerAppDB.getApplicationsByProject(projectName);
    }

    /**
     * Registers an officer to a BTO project after validating:
     * - Project exists and has available slots
     * - The officer has not already applied as an applicant
     * - The project does not overlap with existing officer applications
     *
     * @param officer The officer attempting to register
     * @param project The project to register for
     */
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
