package services.controller;

import database.dataclass.projects.ProjectDB;
import java.util.List;
import java.util.Scanner;
import models.enums.FlatType;
import models.enums.MaritalStatus;
import models.projects.*;
import models.users.Applicant;
import services.subservices.ProjectApplicationService;
import utils.FilterUtil;
import view.ApplicantView;

public class ApplicantController extends UserController {
    
    ApplicantView applicantView = new ApplicantView();

    public Applicant retreiveApplicant(){
        return (Applicant) AuthController.getCurrentUser();
    }

    @Override
    public void start(Scanner sc){
        ApplicantController app = new ApplicantController();

        System.out.println("--------------------------------");
        System.out.println("\tApplicant Portal");
        System.out.println("--------------------------------");
        System.out.println("1. View Project List");
        System.out.println("2. Adjust Filter Settings");
        System.out.println("3. View Application Status");
        System.out.println("4. Withdraw Project");
        System.out.println("5. View Enquiry");
        System.out.println("6. Logout");
        System.out.println("--------------------------------");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> app.viewProjectList(sc);
            case 2 -> app.adjustFilterSettings(sc);
            case 3 -> app.viewApplicationStatus(sc);
            case 4 -> app.withdrawProject(sc);
            case 5 -> app.viewEnquiry(sc);
            case 6 -> System.out.println("Logging out...");
            default -> {
                System.out.println("Invalid choice. Please try again.");
                start(sc);
            }
        }
    }

    public void viewProjectList(Scanner sc){
        List<BTOProject> projects =  ProjectDB.getDB();
        List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects available based on the current filter settings.");
        } else {
            for (int i = 0; i < filteredProjects.size(); i++) {
                System.out.println((i + 1) + ". " + filteredProjects.get(i).getProjectName());
            }
            int projectChoice = sc.nextInt() - 1;
            sc.nextLine(); // Consume newline
            if (projectChoice >= 0 && projectChoice < filteredProjects.size()) {
                BTOProject selectedProject = filteredProjects.get(projectChoice);
                System.out.println("You have selected: " + selectedProject.getProjectName());
                System.out.println("1. Apply for this project");
                System.out.println("2. Ask questions about this project");
                System.out.println("3. Back to project list");
                System.out.print("Enter your choice: ");
                int actionChoice = sc.nextInt();
                sc.nextLine(); // Consume newline
                switch (actionChoice) {
                    case 1 -> applyForProject(sc, selectedProject);
                    case 2 -> addEnquiry(sc);
                    case 3 -> System.out.println("Back to project list.");
                    default -> System.out.println("Invalid choice. Returning to menu.");
                }
            }
            else {
                System.out.println("Invalid project choice. Returning to menu.");
            }
        }
    }

    public void applyForProject(Scanner sc, BTOProject selectedProject) {
        if (ProjectApplicationService.getApplicationByUser(retreiveApplicant().getNric()) != null) {
            System.out.println("You have already applied for a project. Please check your application status.");
            return;
        }
        System.out.println("Choose room type:");
        System.out.println("1. 2-Room");
        System.out.println("2. 3-Room");
        System.out.print("Enter your choice: ");
        int roomTypeChoice = sc.nextInt();
        sc.nextLine(); // Consume newline

        FlatType roomType;
        switch (roomTypeChoice) {
            case 1 -> {
                roomType = FlatType.TWO_ROOM;
                if (selectedProject.getTwoRoomUnits() <= 0) {
                    System.out.println("No 2-room units available for this project. Please choose another room type.");
                    return;
                }
                if (retreiveApplicant().getMaritalStatus() == MaritalStatus.SINGLE && retreiveApplicant().getAge() < 35) {
                    System.out.println("You must be at least 35 years old to apply for a 2-room unit as a single applicant. Please choose another room type.");
                    return;
                }   
                if (retreiveApplicant().getAge() < 21) {
                    System.out.println("You must be at least 21 years old to apply for a 2-room unit. Please choose another room type.");
                    return;
                }
            }
            case 2 -> {
                roomType = FlatType.THREE_ROOM;
                if (selectedProject.getThreeRoomUnits() <= 0) {
                    System.out.println("No 3-room units available for this project. Please choose another room type.");
                    return;
                }
                if (retreiveApplicant().getMaritalStatus() != MaritalStatus.MARRIED) {
                    System.out.println("3-room units are only available for married applicants. Please choose another room type.");
                    return;
                }
                if (retreiveApplicant().getAge() < 21) {
                    System.out.println("You must be at least 21 years old to apply for a 3-room unit. Please choose another room type.");
                    return;
                }
            }
            default -> {
                System.out.println("Invalid choice. Returning to menu.");
                return;
            }
        }
        
        ProjectApplicationService.addApplication(selectedProject, retreiveApplicant().getNric(), roomType);
        System.out.println("You have successfully applied for " + selectedProject.getProjectName() + ".");
    }

    public void viewApplicationStatus(Scanner sc) {
        
    }

    public void withdrawProject(Scanner sc) {

    }

    public void addEnquiry(Scanner sc) {

    }

    public void viewEnquiry(Scanner sc){
        
    }
}