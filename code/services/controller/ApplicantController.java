package services.controller;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.projects.ProjectDB;
import java.util.List;
import java.util.Scanner;
import models.projects.*;
import models.users.Applicant;
import services.interfaces.IEnquiryService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IProjectViewService;
import services.subservices.EnquiryService;
import services.subservices.ProjectApplicationService;
import services.subservices.ProjectViewService;
import utils.FilterUtil;
import view.ApplicantView;

public class ApplicantController extends UserController {
    
    ApplicantView applicantView = new ApplicantView();

    IProjectViewService projectViewService = new ProjectViewService();
    IProjectApplicationService projectApplicationService = new ProjectApplicationService();
    IEnquiryService enquiryService = new EnquiryService();

    public Applicant retreiveApplicant(){
        Applicant currentUser = (Applicant) AuthController.getCurrentUser();
        currentUser.setCurrentApplication(ProjectAppDB.getApplicationByUser(currentUser.getNric()));
        return currentUser;
    }

    @Override
    public void start(Scanner sc){
        ApplicantController app = new ApplicantController();
        filterSettings.setVisibility(true);
        int choice;

        do{
            System.out.println("--------------------------------");
            System.out.println("\tApplicant Portal");
            System.out.println("--------------------------------");
            System.out.println("1. Enter Project Protal");
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
                case 1 -> app.displayProjectProtal(sc);
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

    public void displayProjectProtal(Scanner sc){
        List<BTOProject> projects =  ProjectDB.getDB();
        List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

        BTOProject selectedProject = projectViewService.chooseFromProjectList(sc, filteredProjects);
        if (selectedProject == null) {
            return;
        }
        displayProjectOption(sc, selectedProject);
    }

    public void displayProjectOption(Scanner sc, BTOProject selectedProject) {
        System.out.println("You have selected: " + selectedProject.getProjectName());
        System.out.println("1. Apply for this project");
        System.out.println("2. Ask questions about this project");
        System.out.println("3. Back to project list");
        System.out.print("Enter your choice: ");

        int actionChoice = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (actionChoice) {
            case 1 -> applyForProject(sc, selectedProject);
            case 2 -> addEnquiry(sc, selectedProject);
            case 3 -> System.out.println("Back to project list.");
            default -> System.out.println("Invalid choice. Returning to menu.");
        }
    }

    public void applyForProject(Scanner sc, BTOProject selectedProject) {
        Applicant applicant = retreiveApplicant();

        if (ProjectAppDB.getApplicationByUser(applicant.getNric()) != null) {
            System.out.println("You have already applied for a project. Please check your application status.");
        }
        else{
            projectApplicationService.applyForProject(sc, applicant, selectedProject.getProjectName());
        }

    }

    public void viewApplicationStatus() {
        Applicant applicant = retreiveApplicant();
        ProjectApplication currentApplication = applicant.getCurrentApplication();
        if (currentApplication == null) {
            System.out.println("You have not applied for any projects yet.");
            return;
        }
        System.out.println(currentApplication.toString());
    }

    public void withdrawProject() {
        Applicant applicant = retreiveApplicant();
        projectApplicationService.withdrawApplication(applicant.getCurrentApplication());
        

    }

    public void addEnquiry(Scanner sc, BTOProject selectedProject) {
        Applicant applicant = retreiveApplicant();
        System.out.print("Enter your enquiry: ");
        String content = sc.nextLine();
        enquiryService.addEnquiry(applicant.getNric(), selectedProject.getProjectName(), content);
    }

    public void viewMyEnquiry(Scanner sc){
        Applicant applicant = retreiveApplicant();
        List<Enquiry> enquiryList =  enquiryService.getMyEnquiries(applicant.getNric());
        Enquiry selectedEnquiry = enquiryService.chooseFromEnquiryList(sc, enquiryList);
        if (selectedEnquiry == null) {
            return;
        }
        enquiryService.enquiryOption(sc, selectedEnquiry);
    }
}