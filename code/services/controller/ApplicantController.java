package services.controller;

import database.dataclass.projects.ProjectAppDB;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import models.projects.*;
import models.users.Applicant;

import services.interfaces.IEnquiryService;
import services.interfaces.IProjectApplicationService;

import services.subservices.EnquiryService;
import services.subservices.ProjectApplicationService;


import view.ApplicantView;

/**
 * Controller class for users with the Applicant role.
 * Allows applicants to view and filter projects, apply, withdraw,
 * manage enquiries, and check application statuses.
 */
public class ApplicantController extends UserController {

    /** View for displaying applicant-specific menus and options. */
    private static ApplicantView applicantView = new ApplicantView();

    /** Service that handles project application logic. */
    IProjectApplicationService projectApplicationService = new ProjectApplicationService();

    /** Service that handles enquiry-related operations. */
    IEnquiryService enquiryService = new EnquiryService();

    /** Default constructor. */
    public ApplicantController() { }

    /**
     * Retrieves the currently logged-in applicant and loads their applications.
     *
     * @return the current Applicant object
     */
    public Applicant retreiveApplicant() {
        Applicant currentUser = (Applicant) auth.getCurrentUser();
        currentUser.setMyApplication(ProjectAppDB.getApplicationByUser(currentUser.getNric()));
        return currentUser;
    }

    /**
     * Starts the applicant interface by displaying the main menu.
     *
     * @param sc Scanner for user input
     */
    @Override
    public void start(Scanner sc) {
        try {
            applicantView.enterMainMenu(sc);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            auth.logout();
        }
    }

    /**
     * Enters the project portal view with filtering applied for visibility and current date.
     *
     * @param sc Scanner for user input
     * @throws Exception if the portal fails to load
     */
    public void enterProjectPortal(Scanner sc) throws Exception {
        filterSettings.setVisibility(true);
        filterSettings.setActiveDate(new Date());
        applicantView.displayProjectPortal(sc, filterSettings);
    }

    /**
     * Allows the applicant to apply for a selected BTO project.
     * Prevents duplicate active applications.
     *
     * @param sc Scanner for user input
     * @param selectedProject the selected BTO project
     */
    public void applyForProject(Scanner sc, BTOProject selectedProject) {
        Applicant applicant = retreiveApplicant();
        ProjectApplication currentApplication = applicant.getActiveApplication();

        if (currentApplication != null) {
            System.out.println("You have already applied for a project.\n Please check your application status.");
        } else {
            projectApplicationService.applyForProject(sc, applicant, selectedProject.getProjectName());
        }
    }

    /**
     * Displays the current application status for the applicant.
     */
    public void viewApplicationStatus() {
        applicantView.displayApplicationStatus();
    }

    /**
     * Allows the applicant to withdraw their current active application after confirmation.
     *
     * @param sc Scanner for user input
     */
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

    /**
     * Allows the applicant to submit a new enquiry about a project.
     *
     * @param sc Scanner for user input
     * @param selectedProject the project the enquiry is related to
     */
    public void addEnquiry(Scanner sc, BTOProject selectedProject) {
        Applicant applicant = retreiveApplicant();
        System.out.print("Enter your enquiry: ");
        String content = sc.nextLine();
        enquiryService.addEnquiry(applicant.getNric(), selectedProject.getProjectName(), content);
    }

    /**
     * Displays a list of the applicant's past enquiries and lets them interact with one.
     *
     * @param sc Scanner for user input
     */
    public void viewMyEnquiry(Scanner sc) {
        Applicant applicant = retreiveApplicant();
        List<Enquiry> enquiryList = enquiryService.getMyEnquiries(applicant.getNric());
        Enquiry selectedEnquiry = enquiryService.chooseFromEnquiryList(sc, enquiryList);
        if (selectedEnquiry == null) return;

        applicantView.viewEnquiryActionMenu(sc, selectedEnquiry);
    }

    /**
     * Allows the applicant to edit a selected enquiry.
     *
     * @param sc Scanner for user input
     * @param selectedEnquiry the enquiry to edit
     */
    public void editEnquiry(Scanner sc, Enquiry selectedEnquiry) {
        enquiryService.editEnquiry(sc, selectedEnquiry);
    }

    /**
     * Deletes a selected enquiry made by the applicant.
     *
     * @param selectedEnquiry the enquiry to delete
     */
    public void deleteEnquiry(Enquiry selectedEnquiry) {
        enquiryService.deleteEnquiry(selectedEnquiry);
    }

    /**
     * Allows the applicant to change their password.
     *
     * @param sc Scanner for user input
     */
    public void changeMyPassword(Scanner sc) {
        auth.enterChangePasswordPage(sc);
    }
}