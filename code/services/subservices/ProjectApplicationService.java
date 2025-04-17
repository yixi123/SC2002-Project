package services.subservices;

import database.dataclass.projects.ProjectAppDB;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import models.enums.FlatType;
import models.enums.MaritalStatus;
import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;
import models.users.Applicant;
import services.interfaces.IProjectApplicationService;



public class ProjectApplicationService implements IProjectApplicationService{

    public void applyForProject(Scanner sc, Applicant user, String selectedProjectName){
        boolean twoRoomEligibility = checkApplicationEligibility(user.getAge(), user.getMaritalStatus(), FlatType.TWO_ROOM);
        boolean threeRoomEligibility = checkApplicationEligibility(user.getAge(), user.getMaritalStatus(), FlatType.THREE_ROOM);
        int roomTypeChoice;

        do{
            try{
                System.out.printf("Selected Project: %s\n", selectedProjectName);
                System.out.println("------------------------------");
                System.out.println("Choose room type:");
                System.out.printf("1. 2-Room %s\n", twoRoomEligibility? "": "[UNELIGIBLE]");
                System.out.printf("2. 3-Room %s\n", threeRoomEligibility? "": "[UNELIGIBLE]");
                System.out.println("3. Cancel");
                System.out.println("------------------------------");
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
        List<ProjectApplication> applications = ProjectAppDB.getDB();
        applications.add(new ProjectApplication(userID, projectName, ProjectAppStat.PENDING, new Date(), flatType));
        
        ProjectAppDB.updateDB(applications);
    }

    public void updateApplicationStatus(String user, String project, ProjectAppStat newStatus) {
        List<ProjectApplication> applications = ProjectAppDB.getDB();
        for (ProjectApplication application : applications) {
            if (application.getUser().equals(user) && application.getProjectName().equals(project)) {
                application.setStatus(newStatus);
                ProjectAppDB.updateDB(applications);
                return;
            }
        }
        System.out.println("Application not found!");
    }

    public void withdrawApplication(String userId, String project){
        updateApplicationStatus(userId, project, ProjectAppStat.WITHDRAW_REQ);
    }

    public void bookApplication(String userId, String project){
        updateApplicationStatus(userId, project, ProjectAppStat.BOOK_REQ);
    }

    public ProjectAppStat viewApplicationStatus(String user){
        return ProjectAppDB.getApplicationByUser(user).getStatus();
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
}
