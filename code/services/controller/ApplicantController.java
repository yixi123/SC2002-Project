package services.controller;

import database.dataclass.projects.ProjectDB;
import java.util.List;
import java.util.Scanner;
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
        System.out.println("2. Apply for a Project");
        System.out.println("3. Adjust Filter Settings");
        System.out.println("4. View Application Status");
        System.out.println("5. Withdraw Project");
        System.out.println("6. View Enquiry");
        System.out.println("7. Add Enquiry");
        System.out.println("8. Logout");
        System.out.println("--------------------------------");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> app.viewProjectList(sc);
            case 2 -> app.applyProject(sc);
            case 3 -> app.adjustFilterSettings(sc);
            case 4 -> app.viewApplicationStatus(sc);
            case 5 -> app.withdrawProject(sc);
            case 6 -> app.viewEnquiry(sc);
            case 7 -> app.addEnquiry(sc);
            case 8 -> System.out.println("Logging out...");
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
            for (BTOProject project : filteredProjects) {
                System.out.println(project);
            }
        }

    }

    public void applyProject(Scanner sc){
        List<BTOProject> projects =  ProjectDB.getDB();
        List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

        if (filteredProjects.isEmpty()) {
            System.out.println("No projects available based on the current filter settings.");
        } else {
            System.out.println("Please select a project to apply for:");
            for (int i = 0; i < filteredProjects.size(); i++) {
                System.out.println((i + 1) + ". " + filteredProjects.get(i).getProjectName());
            }
            int projectChoice = sc.nextInt() - 1;
            sc.nextLine(); // Consume newline
            if (projectChoice >= 0 && projectChoice < filteredProjects.size()) {
                BTOProject selectedProject = filteredProjects.get(projectChoice);
                System.out.println("Choose room type:");
                System.out.println("1. 2-Room");
                System.out.println("2. 3-Room");
                System.out.print("Enter your choice (1 or 2): ");
                int roomTypeChoice = sc.nextInt();
                sc.nextLine(); // Consume newline
                String roomType;
                switch (roomTypeChoice) {
                    case 1 -> roomType = "2-Room";
                    case 2 -> roomType = "3-Room";
                    default -> {
                        System.out.println("Invalid choice. Returning to menu.");
                        return;
                    }
                }

                ProjectApplicationService.addApplication(selectedProject, retreiveApplicant().getNric(), roomType);
                System.out.println("You have successfully applied for " + selectedProject.getProjectName() + ".");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
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