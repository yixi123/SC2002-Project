package services.subservices;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.users.ApplicantDB;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import models.enums.FlatType;
import models.enums.MaritalStatus;
import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;
import models.users.Applicant;
import services.interfaces.IProjectApplicationService;
import view.ViewFormatter;

/**
 * Service class that handles all functionalities related to project applications
 * submitted by applicants, including application submission, status updates,
 * withdrawals, bookings, and eligibility checks.
 *
 * This class interacts with {@code ProjectAppDB} and {@code ApplicantDB}.
 */
public class ProjectApplicationService implements IProjectApplicationService{

    /**
     * Initiates the application process for a project, including room type selection
     * and eligibility validation based on applicant's age and marital status.
     *
     * @param sc Scanner for user input
     * @param user The applicant submitting the application
     * @param selectedProjectName The name of the project to apply for
     */
    public void applyForProject(Scanner sc, Applicant user, String selectedProjectName){
        boolean twoRoomEligibility = checkApplicationEligibility(user.getAge(), user.getMaritalStatus(), FlatType.TWO_ROOM);
        boolean threeRoomEligibility = checkApplicationEligibility(user.getAge(), user.getMaritalStatus(), FlatType.THREE_ROOM);
        int roomTypeChoice;

        do{
            try{
                System.out.printf("\nSelected Project: %s\n", selectedProjectName);
                System.out.println(ViewFormatter.breakLine());
                System.out.println("Choose room type:");
                System.out.printf("1. 2-Room %s\n", twoRoomEligibility? "": "[UNELIGIBLE]");
                System.out.printf("2. 3-Room %s\n", threeRoomEligibility? "": "[UNELIGIBLE]");
                System.out.println("3. Cancel");
                System.out.println(ViewFormatter.breakLine());
                System.out.print("Enter your choice: ");
                roomTypeChoice = sc.nextInt();
                sc.nextLine();

                if (roomTypeChoice == 3) return;
                if ((roomTypeChoice == 1 && !twoRoomEligibility) || 
                    (roomTypeChoice == 2 && !threeRoomEligibility)){
                        throw new IllegalArgumentException("You are not Eligible!");
                }
                break;
            }
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            } 
            catch(InputMismatchException e){
                System.out.println("Invalid input! Please try again!");
                sc.nextLine(); // Clear the invalid input
            } 
        }while(true);

        FlatType roomType = (roomTypeChoice == 1) ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
        
        addApplication(selectedProjectName, user.getNric(), roomType);
        System.out.println("You have successfully applied for " + selectedProjectName + ".");
        System.out.println(ViewFormatter.breakLine());
    }

    /**
     * Adds a new project application to the database.
     *
     * @param projectName The name of the project
     * @param userID The NRIC of the applicant
     * @param flatType The flat type selected by the applicant
     */
    public void addApplication(String projectName, String userID, FlatType flatType) {
        ProjectAppDB.addApplication(new ProjectApplication(userID, projectName, ProjectAppStat.PENDING, new Date(), flatType));
    }

    /**
     * Updates the status of an application identified by user and project name.
     *
     * @param user The NRIC of the applicant
     * @param project The name of the project
     * @param newStatus The new application status
     */
    public void updateApplicationStatus(String user, String project, ProjectAppStat newStatus) {
        List<ProjectApplication> applications = ProjectAppDB.getDB();
        for (ProjectApplication application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                updateApplicationStatus(application, newStatus);
                return;
            }
        }
        System.out.println("Application not found!");
    }

    /**
     * Updates the status of the specified project application object.
     *
     * @param application The application to update
     * @param newStatus The new status to set
     */
    @Override
    public void updateApplicationStatus(ProjectApplication application, ProjectAppStat newStatus) {
        if (newStatus == application.getStatus()) {
            System.out.println("Application status is already " + newStatus);
            return;
        }
        application.setStatus(newStatus);
        ProjectAppDB.updateDB();
        System.out.println("Application status updated to: " + newStatus);
    }

    /**
     * Marks the given application as a withdrawal request.
     *
     * @param application The application to withdraw
     */
    @Override
    public void withdrawApplication(ProjectApplication application){
        updateApplicationStatus(application, ProjectAppStat.WITHDRAW_REQ);
    }

    /**
     * Withdraws the application for a given applicant and project.
     *
     * @param userId The NRIC of the applicant
     * @param project The project name
     */
    public void withdrawApplication(String userId, String project){
        updateApplicationStatus(userId, project, ProjectAppStat.WITHDRAW_REQ);
    }

    /**
     * Books the application for a given applicant and project.
     * Booking is only allowed for applications with status SUCCESSFUL.
     *
     * @param projectName The project name
     * @param userId The NRIC of the applicant
     * @return 1 if booking is successful, 0 otherwise
     */
    @Override
    public int bookApplication(String projectName, String userId){
        if (projectName == null) {
			System.out.println("No project assigned.");
			return 0;
		}
		ProjectApplication app = getApplicationByUserAndProject(userId, projectName);
		if (app == null) {
			System.out.println("No application found for applicant: " + userId);
			return 0;
		}
		if (app.getStatus() == ProjectAppStat.BOOKED) {
			System.out.println("Application is already booked.");
			return 0;
		}
		if (app.getStatus() != ProjectAppStat.SUCCESSFUL) {
			System.out.println("Application is not successful/approved yet\n cannot proceed to booking.");
			return 0;
		}

        updateApplicationStatus(userId, projectName, ProjectAppStat.BOOKED);
        System.out.println("Application booked successfully.");
        return 1;

    }

    /**
     * Checks if an applicant is eligible to apply for the specified flat type,
     * based on their age and marital status.
     *
     * @param age The age of the applicant
     * @param status The marital status of the applicant
     * @param flatType The flat type being applied for
     * @return {@code true} if eligible, otherwise {@code false}
     */
    private boolean checkApplicationEligibility(int age, MaritalStatus status, FlatType flatType){
        if (flatType == FlatType.TWO_ROOM) {
            return (status == MaritalStatus.SINGLE && age >= 35) || 
                    (status == MaritalStatus.MARRIED && age >= 21);
        } else if (flatType == FlatType.THREE_ROOM) {
            return (status == MaritalStatus.MARRIED && age >= 35);
        } else {
            return false;
        }
    }

    /**
     * Retrieves all applications submitted for a specific project.
     *
     * @param projectName The name of the project
     * @return List of project applications
     */
    @Override
    public List<ProjectApplication> getProjectApplications(String projectName) {
        return ProjectAppDB.getApplicationsByProject(projectName);
    }

    /**
     * Retrieves the application submitted by a specific applicant for a given project.
     *
     * @param userId The NRIC of the applicant
     * @param projectName The name of the project
     * @return The corresponding {@code ProjectApplication}, or null if not found
     */
    @Override
    public ProjectApplication getApplicationByUserAndProject(String userId, String projectName) {
       return ProjectAppDB.getApplicationsByUserAndProject(userId, projectName);
    }

    /**
     * Allows the user to choose a project application from a list.
     * Optionally filters only PENDING applications.
     *
     * @param sc Scanner for user input
     * @param applications The list of applications to choose from
     * @return The selected {@code ProjectApplication}, or null if cancelled or invalid
     */
    @Override
    public ProjectApplication chooseFromApplicationList(Scanner sc, List<ProjectApplication> applications) {
        System.out.println("Display only 'pending' applications? (yes/no): ");
        String filterChoice = sc.nextLine().trim().toLowerCase();
        if (filterChoice.equals("yes")) {
            applications.removeIf(app -> !app.getStatus().equals(ProjectAppStat.PENDING));
        }
        if (applications.isEmpty()) {
            System.out.println("No applications available.");
            return null;
        }
        
        System.out.println("Choose an application to view details:");
        for (int i = 0; i < applications.size(); i++) {
            String applicantName = ApplicantDB.getUsernameByID(applications.get(i).getUser());
            System.out.println((i + 1) + ". " + applicantName + " - " + applications.get(i).getProjectName() + " - " + applications.get(i).getStatus());
        }
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Returning to menu.");
            return null;
        } finally {
            sc.nextLine(); // Consume newline
        }
        if (choice < 1 || choice > applications.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return null;
        }
        ProjectApplication selectedApplication = applications.get(choice - 1);
        return selectedApplication;
    }

}
