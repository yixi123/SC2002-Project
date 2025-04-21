package services.controller;

import database.dataclass.projects.ProjectAppDB;
import java.util.Date;
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

import view.ApplicantView;

public class ApplicantController extends UserController {
    
    private static ApplicantView applicantView = new ApplicantView();

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
        try{
            applicantView.enterMainMenu(sc);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            auth.logout();
        }
    }

    public void enterProjectPortal(Scanner sc) throws Exception{
        filterSettings.setVisibility(true);
        filterSettings.setActiveDate(new Date());
        applicantView.displayProjectPortal(sc, filterSettings);
    }


    public void applyForProject(Scanner sc, BTOProject selectedProject) {
        Applicant applicant = retreiveApplicant();
        ProjectApplication currentApplication = applicant.getActiveApplication();
        
        if (currentApplication != null) {
            System.out.println("You have already applied for a project.\n Please check your application status.");
        }
        else{
            projectApplicationService.applyForProject(sc, applicant, selectedProject.getProjectName());
        }

    }

    public void viewApplicationStatus() {
        applicantView.displayApplicationStatus();
    }

    public void withdrawProject(Scanner sc) {
        Applicant applicant = retreiveApplicant();
        List<ProjectApplication> applicationList = applicant.getMyApplication();
        if (applicationList.isEmpty()) {
            System.out.println("You have not applied for any projects yet.");
            return;
        }

        ProjectApplication currentApplication = applicant.getActiveApplication();

        if (currentApplication == null) {
            System.out.println("You have no active applications yet.");
            return;
        }
        
        System.out.println("<Current Application>");
        System.out.println(currentApplication.toString());
        System.out.println("-----------------------------------------");
        System.out.println("Are you sure you want to withdraw this application? (yes/no)");
        String response = sc.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            projectApplicationService.withdrawApplication(currentApplication);
            System.out.println("Application withdrawn successfully.");
        } else {
            System.out.println("Withdrawal cancelled.");
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
        applicantView.viewEnquiryActionMenu(sc, selectedEnquiry);
    }

    public void editEnquiry(Scanner sc, Enquiry selectedEnquiry) {
        enquiryService.editEnquiry(sc, selectedEnquiry);
    }

    public void deleteEnquiry(Enquiry selectedEnquiry) {
        enquiryService.deleteEnquiry(selectedEnquiry);
    }

    public void changeMyPassword(Scanner sc){
        auth.enterChangePasswordPage(sc);
    }
}