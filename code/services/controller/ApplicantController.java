package services.controller;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.projects.ProjectDB;
import java.util.List;
import java.util.Scanner;
import models.enums.FlatType;
import models.enums.MaritalStatus;
import models.projects.*;
import models.users.Applicant;

import services.interfaces.IEnquiryService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IProjectManagementService;
import services.interfaces.IProjectViewService;

import services.subservices.ProjectApplicationService;
import services.subservices.ProjectViewService;
import services.subservices.EnquiryService;

import utils.FilterUtil;
import view.ApplicantView;

public class ApplicantController extends UserController {
    
    ApplicantView applicantView = new ApplicantView();

    IProjectViewService projectViewService = new ProjectViewService();
    IProjectApplicationService projectApplicationService = new ProjectApplicationService();
    IEnquiryService enquiryService = new EnquiryService();

    public Applicant retreiveApplicant(){
        return (Applicant) AuthController.getCurrentUser();
    }

    @Override
    public void start(Scanner sc){
        ApplicantController app = new ApplicantController();

        int choice;

        do{
            System.out.println("--------------------------------");
            System.out.println("\tApplicant Portal");
            System.out.println("--------------------------------");
            System.out.println("1. View Project List");
            System.out.println("2. Adjust Filter Settings");
            System.out.println("3. View Application Status");
            System.out.println("4. Withdraw Project");
            System.out.println("5. View My Enquiry");
            System.out.println("6. Logout");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> app.viewProjectList(sc);
                case 2 -> app.adjustFilterSettings(sc);
                case 3 -> app.viewApplicationStatus();
                case 4 -> app.withdrawProject();
                case 5 -> app.viewMyEnquiry(sc);
                case 6 -> System.out.println("Logging out...");
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    start(sc);
                }
            }
        } while (choice != 6);
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
        Applicant applicant = retreiveApplicant();

        if (ProjectAppDB.getApplicationByUser(retreiveApplicant().getNric()) != null) {
            System.out.println("You have already applied for a project. Please check your application status.");
            return;
        }
        else{
            applyForProject(sc, selectedProject);
        }

    }

    public void viewApplicationStatus() {
        Applicant applicant = retreiveApplicant();
        projectApplicationService.viewApplicationStatus(applicant.getNric());
    }

    public void withdrawProject() {
        Applicant applicant = retreiveApplicant();
        projectApplicationService.withdrawApplication(applicant.getAppliedProject().getProjectName(), applicant.getNric());
    }

    public void addEnquiry(Scanner sc) {
        
    }

    public void viewMyEnquiry(Scanner sc){
        
    }
}