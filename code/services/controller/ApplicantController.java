package services.controller;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.projects.ProjectDB;
import java.util.List;
import java.util.Scanner;
import models.enums.ProjectAppStat;
import models.projects.*;
import models.projects.BTOProject;
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
    
    private ApplicantView applicantView = new ApplicantView();

    IProjectViewService projectViewService = new ProjectViewService();
    IProjectApplicationService projectApplicationService = new ProjectApplicationService();
    IEnquiryService enquiryService = new EnquiryService();

    public ApplicantController(){
    }

    public Applicant retreiveApplicant(){
        Applicant currentUser = (Applicant) auth.getCurrentUser();
        currentUser.setMyApplication(ProjectAppDB.getApplicationByUser(currentUser.getNric()));
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
            System.out.println("4. Withdraw Current Application");
            System.out.println("5. View My Enquiry");
            System.out.println("6. Change My Password");
            System.out.println("0. Logout");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0 -> {
                    System.out.println("Logging out...");
                    auth.logout();
                    return;
                }
                case 1 -> app.displayProjectProtal(sc);
                case 2 -> app.adjustFilterSettings(sc);
                case 3 -> app.viewApplicationStatus();
                case 4 -> app.withdrawProject();
                case 5 -> app.viewMyEnquiry(sc);
                case 6 -> app.changeMyPassword(sc);
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    start(sc);
                }
            }
        } while (choice != 0 || choice != 6);
    }

    public void displayProjectProtal(Scanner sc){
        List<BTOProject> projects =  ProjectDB.getDB();
        List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

        BTOProject selectedProject = projectViewService.chooseFromProjectList(sc, filteredProjects);
        if (selectedProject == null) {
            return;
        }
        displayProjectAction(sc, selectedProject);
    }

    public void displayProjectAction(Scanner sc, BTOProject selectedProject) {
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
        List<ProjectApplication> applicationList = applicant.getMyApplication();
        if (applicationList.isEmpty()) {
            System.out.println("You have not applied for any projects yet.");
            return;
        }
        System.out.println("Your Applications:");
        for (ProjectApplication application : applicationList) {
            if (application.getStatus() != ProjectAppStat.UNSUCCESSFUL && application.getStatus() != ProjectAppStat.WITHDRAWN) {
                System.out.println("<Current Application>");
            } 
            System.out.println(application.toString());
            System.out.println("-----------------------------------------");
        }
    }

    public void withdrawProject() {
        Applicant applicant = retreiveApplicant();
        List<ProjectApplication> applicationList = applicant.getMyApplication();
        if (applicationList.isEmpty()) {
            System.out.println("You have not applied for any projects yet.");
            return;
        }
        ProjectApplication currentApplication;
        for (ProjectApplication application : applicationList) {
            if (application.getStatus() != ProjectAppStat.UNSUCCESSFUL && application.getStatus() != ProjectAppStat.WITHDRAWN) {
                System.out.println("<Current Application>");
                System.out.println(application.toString());
                System.out.println("-----------------------------------------");
                currentApplication = application;
                System.out.println("Are you sure you want to withdraw this application? (yes/no)");
                String response = new Scanner(System.in).nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    projectApplicationService.withdrawApplication(currentApplication);
                    System.out.println("Application withdrawn successfully.");
                } else {
                    System.out.println("Withdrawal cancelled.");
                }
                break;
            } 
        }        

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
        enquiryService.viewEnquiryActionMenu(sc, selectedEnquiry);
    }

    public void changeMyPassword(Scanner sc){
        auth.changePasswordPage(sc);
    }
}