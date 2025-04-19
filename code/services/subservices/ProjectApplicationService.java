package services.subservices;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.users.ApplicantDB;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import models.enums.FlatType;
import models.enums.MaritalStatus;
import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;
import models.users.Applicant;
import services.interfaces.IProjectApplicationService;
import view.ViewFormatter;



public class ProjectApplicationService implements IProjectApplicationService{

    public void applyForProject(Scanner sc, Applicant user, String selectedProjectName){
        boolean twoRoomEligibility = checkApplicationEligibility(user.getAge(), user.getMaritalStatus(), FlatType.TWO_ROOM);
        boolean threeRoomEligibility = checkApplicationEligibility(user.getAge(), user.getMaritalStatus(), FlatType.THREE_ROOM);
        int roomTypeChoice;

        do{
            try{
                System.out.printf("Selected Project: %s\n", selectedProjectName);
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
        }while(true);

        FlatType roomType = (roomTypeChoice == 1) ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
        
        addApplication(selectedProjectName, user.getNric(), roomType);
        System.out.println("You have successfully applied for " + selectedProjectName + ".");
    }

    public void addApplication(String projectName, String userID, FlatType flatType) {
        ProjectAppDB.addApplication(new ProjectApplication(userID, projectName, ProjectAppStat.PENDING, new Date(), flatType));
    }

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

    @Override
    public void withdrawApplication(ProjectApplication application){
        updateApplicationStatus(application, ProjectAppStat.WITHDRAW_REQ);
    }

    public void withdrawApplication(String userId, String project){
        updateApplicationStatus(userId, project, ProjectAppStat.WITHDRAW_REQ);
    }

    @Override
    public void bookApplication(String userId, String project){
        if (project == null) {
			System.out.println("No project assigned.");
			return;
		}
		ProjectApplication app = getApplicationByUserAndProject(userId, project);
		if (app == null) {
			System.out.println("No application found for applicant: " + userId);
			return;
		}
		if (app.getStatus() == ProjectAppStat.BOOKED) {
			System.out.println("Application is already booked.");
			return;
		}
		if (app.getStatus() != ProjectAppStat.SUCCESSFUL) {
			System.out.println("Application is not successful/approved yet\n cannot proceed to booking.");
			return;
		}

        updateApplicationStatus(userId, project, ProjectAppStat.BOOKED);
    }

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

    @Override
    public List<ProjectApplication> getProjectApplications(String projectName) {
        return ProjectAppDB.getApplicationsByProject(projectName);
    }

    @Override
    public ProjectApplication getApplicationByUserAndProject(String userId, String projectName) {
       return ProjectAppDB.getApplicationsByUserAndProject(userId, projectName);
    }

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
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (choice < 1 || choice > applications.size()) {
            System.out.println("Invalid choice. Returning to menu.");
            return null;
        }
        ProjectApplication selectedApplication = applications.get(choice - 1);
        return selectedApplication;
    }

}
