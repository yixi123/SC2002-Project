package services.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import database.dataclass.projects.EnquiryDB;
import database.dataclass.projects.ProjectDB;

import models.enums.FlatType;
import models.enums.OfficerAppStat;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.HDBManager;

import view.ManagerView;
import view.ViewFormatter;
import services.interfaces.IEnquiryService;
import services.interfaces.IOfficerApplicationService;
import services.interfaces.IReportPrintService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IProjectManagementService;

import services.subservices.EnquiryService;
import services.subservices.OfficerApplicationService;
import services.subservices.ProjectApplicationService;
import services.subservices.ProjectManagementService;
import services.subservices.ReportPrintService;
import utils.IFileSaver;

/**
 * Controller class for HDB Managers.
 * Allows managers to manage projects, approve/reject applications,
 * handle enquiries, and generate reports.
 */
public class ManagerController extends UserController{
    /** Service to manage project applications. */
    IProjectApplicationService projectAppService = new ProjectApplicationService();

    /** Service to manage BTO project creation and updates. */
    IProjectManagementService projectManagementService = new ProjectManagementService();

    /** Service to handle officer applications. */
    IOfficerApplicationService officerApplicationService = new OfficerApplicationService();

    /** Service to handle enquiries. */
    IEnquiryService enquiryService = new EnquiryService();

    /** Service to generate project reports. */
    IReportPrintService reportPrintService = new ReportPrintService();

    /** View class for manager-specific UI interactions. */
    private static ManagerView managerView = new ManagerView();

    /** Default constructor. */
    public ManagerController() {
    }

    /**
     * Constructs a ManagerController with custom service implementations.
     * This constructor is intended for scenarios such as unit testing or advanced dependency injection,
     * where mock or alternative service instances may be supplied.
     *
     * @param projectAppService Service responsible for handling project applications
     * @param projectManagementService Service responsible for creating, editing, and deleting BTO projects
     * @param officerApplicationService Service managing officer application workflows
     * @param enquiryService Service for managing public and project-specific enquiries
     * @param printService Service for generating downloadable project reports
     */
    public ManagerController(IProjectApplicationService projectAppService, IProjectManagementService projectManagementService,
                             IOfficerApplicationService officerApplicationService, IEnquiryService enquiryService, IReportPrintService printService){
        this.projectAppService = projectAppService;
        this.projectManagementService = projectManagementService;
        this.officerApplicationService = officerApplicationService;
        this.enquiryService = enquiryService;
        this.reportPrintService = printService;
    }

    /**
     * Retrieves the currently logged-in user and casts it to an HDBManager.
     * Also updates the manager's internal list of managed projects
     * by fetching them from the ProjectDB using their NRIC.
     *
     * @return The current user as an HDBManager with managed projects loaded
     */
    public HDBManager retreiveManager(){
        HDBManager currentUser = (HDBManager) auth.getCurrentUser();
        currentUser.setManagedProjectsList(ProjectDB.getProjectsByManager(currentUser.getNric()));
        return currentUser;
    }

    /**
     * Launches the main menu for the manager, allowing them to access
     * key functionalities such as managing projects, applications, and reports.
     * Handles exceptions gracefully to prevent session crashes.
     *
     * @param sc Scanner used to receive user input from the command line
     */
    public void start(Scanner sc){
        try{
            managerView.enterMainMenu(sc);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        catch(Exception e){
            System.out.println("Unexpected Error has occured: " + e.getMessage());
            System.out.println("Returning to Homepage...");
        }
    }

    /**
     * Enters the management portal for a selected BTO project.
     * This portal allows the manager to perform actions specific to the chosen project,
     * such as viewing applicants, officer applications, and responding to enquiries.
     *
     * @param sc Scanner for user input
     * @param chosenProject The project the manager wishes to manage
     * @throws Exception if the management view fails to load
     */
    public void manageChosenProject(Scanner sc, BTOProject chosenProject) throws Exception{
        try{
            managerView.enterManagementPortal(sc, chosenProject);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch(IndexOutOfBoundsException e){
            System.out.println("No projects available for the chosen project.");
        }
    }

    /**
     * Displays a read-only list of all available BTO projects in the system.
     * The view respects current filter settings (e.g., by flat type or date).
     * This is useful for managers to review system-wide project information.
     *
     * @param sc Scanner for user input
     * @throws Exception if project list fails to load or filtering errors occur
     */
    public void viewAllProjectList(Scanner sc) throws Exception{
        try{
            managerView.displayReadOnlyAllProject(sc, filterSettings);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch(IndexOutOfBoundsException e){
            System.out.println("No projects available");
        }
    }

    /**
     * Opens a read-only portal for a specific project identified by its name.
     * Allows the manager to inspect project details without making modifications.
     *
     * @param sc Scanner for user input
     * @param projectName The name of the project to be viewed
     * @throws Exception if the project cannot be found or viewed
     */
    public void readOnlyProjectAction(Scanner sc, String projectName) throws Exception {
        try{
            managerView.displayReadOnlyProjectPortal(sc, projectName);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch(IndexOutOfBoundsException e){
            System.out.println("No projects available for the chosen project.");
        }
    }

    /**
     * Displays the list of projects managed by the currently logged-in HDB Manager.
     * Projects are filtered based on the system's current filter settings.
     * This view is specifically tailored to the manager’s assigned projects.
     *
     * @param sc Scanner for user input
     * @throws Exception if no managed projects exist or view fails to load
     */
    public void manageMyProjects(Scanner sc) throws Exception{
        try{
            managerView.displayMyProjects(sc, filterSettings);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch(IndexOutOfBoundsException e){
            System.out.println("No projects available on chosen project.");
        }
    }

    /**
     * Displays a list of project applications for the selected project,
     * and allows the manager to select one for approval or rejection.
     *
     * @param sc Scanner for user input
     * @param selectedProject The selected BTO project
     */
    public void viewProjectApplicationList(Scanner sc, BTOProject selectedProject) {
        System.out.println("You have selected: " + selectedProject.getProjectName());

        List<ProjectApplication> projectApps = projectAppService.getProjectApplications(selectedProject.getProjectName());
        if (projectApps.isEmpty()) {
            System.out.println("No applications found for this project.");
            return;
        }

        ProjectApplication selectedProjectApp = projectAppService.chooseFromApplicationList(sc, projectApps);
        if (selectedProjectApp == null) {
            return;
        }

        displayProjectApplicationAction(sc, selectedProjectApp, selectedProject);
    }

    /**
     * Displays the appropriate action for a selected project application,
     * depending on whether it is pending or requesting withdrawal.
     *
     * @param sc Scanner for user input
     * @param selectedProjectApp The selected application
     * @param selectedProject The project related to the application
     */
    public void displayProjectApplicationAction(Scanner sc, ProjectApplication selectedProjectApp, BTOProject selectedProject) {

        if (selectedProjectApp.getStatus() != ProjectAppStat.PENDING && selectedProjectApp.getStatus() != ProjectAppStat.WITHDRAW_REQ) {
            System.out.println("This application is not pending or requesting to withdraw. Cannot approve or reject.");
            return;
        }
        System.out.println("You have selected: " + selectedProjectApp.toString());
        if (selectedProjectApp.getStatus() == ProjectAppStat.WITHDRAW_REQ) {
            withdrawingProjectApplicationAction(sc, selectedProjectApp, selectedProject);
        } else {
            pendingProjectApplicationAction(sc, selectedProjectApp, selectedProject);
        }

    }

    /**
     * Allows the manager to approve or reject a pending application.
     *
     * @param sc Scanner for user input
     * @param selectedProjectApp The selected project application
     * @param selectedProject The BTO project
     */
    public void pendingProjectApplicationAction(Scanner sc, ProjectApplication selectedProjectApp, BTOProject selectedProject) {
        System.out.println("1. Approve Project Application");
        System.out.println("2. Reject Project Application");
        System.out.print("Enter your choice: ");

        int actionChoice;
        try {
            actionChoice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please try again!");
            return;
        } finally {
            sc.nextLine();
        }

        switch(actionChoice){
            case 1 -> approveProjectApp(selectedProjectApp, selectedProject);
            case 2 -> rejectProjectApp(selectedProjectApp);
            default -> { System.out.println("Invalid choice. Return to menu.");}
        }
    }

    /**
     * Allows the manager to approve or reject a withdrawal request.
     *
     * @param sc Scanner for user input
     * @param selectedProjectApp The application requesting withdrawal
     * @param selectedProject The BTO project
     */
    public void withdrawingProjectApplicationAction(Scanner sc, ProjectApplication selectedProjectApp, BTOProject selectedProject) {
        System.out.println("This application is requesting to withdraw.");
        System.out.println("Do you want to approve the withdrawal request? (yes/no): ");
        String actionChoice = sc.next();
        switch(actionChoice.toLowerCase()){
            case "yes" -> withdrawProjectApp(selectedProjectApp, selectedProject);
            case "no" -> { System.out.println("Withdrawal request not approved.");}
            default -> { System.out.println("Invalid choice. Return to menu.");}
        }

    }

    /**
     * Displays officer applications for a selected project and
     * allows the manager to approve or reject one.
     *
     * @param sc Scanner for user input
     * @param selectedProject The selected BTO project
     */
    public void viewOfficerApplicationList(Scanner sc, BTOProject selectedProject) {
        System.out.println("You have selected: " + selectedProject.getProjectName());

        List<OfficerApplication> projectApps = officerApplicationService.getProjectApplications(selectedProject.getProjectName());
        if (projectApps.isEmpty()) {
            System.out.println("No applications found for this project.");
            return;
        }

        OfficerApplication selectedOfficerApp = officerApplicationService.chooseFromApplicationList(sc, projectApps);
        if (selectedOfficerApp == null) {
            return;
        }

        displayOfficerApplicationAction(sc, selectedOfficerApp, selectedProject);
    }

    /**
     * Displays the action options (approve/reject) for a selected officer application.
     *
     * @param sc Scanner for user input
     * @param selectedOfficerApp The selected officer application
     * @param selectedProject The related project
     */
    public void displayOfficerApplicationAction(Scanner sc, OfficerApplication selectedOfficerApp, BTOProject selectedProject) {

        if (selectedOfficerApp.getStatus() != OfficerAppStat.PENDING) {
            System.out.println("This application is not pending. Cannot approve or reject.");
            return;
        }
        System.out.println("You have selected: " + selectedOfficerApp.toString());
        System.out.println(ViewFormatter.breakLine());
        System.out.println("1. Approve Officer Application");
        System.out.println("2. Reject Officer Application");
        System.out.print("Enter your choice: ");

        int actionChoice;
        try {
            actionChoice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please try again!");
            return;
        } finally {
            sc.nextLine();
        }

        switch(actionChoice){
            case 1 -> approveOfficerApp(selectedOfficerApp, selectedProject);
            case 2 -> rejectOfficerApp(selectedOfficerApp);
            default -> { System.out.println("Invalid choice. Return to menu.");}
        }
    }

    /**
     * Allows the manager to create a new BTO project.
     *
     * @param sc Scanner for user input
     */
    public void createBTOProjects(Scanner sc) {
        HDBManager manager = retreiveManager();
        try{
            projectManagementService.createProject(sc, manager.getNric(), manager.getManagedProjectsList());
        }catch(Exception e){
            System.out.println("Error creating project: " + e.getMessage());
        }
    }

    /**
     * Edits the selected BTO project.
     *
     * @param sc Scanner for user input
     * @param chosenProject The project to be edited
     */
    public void editBTOProjects(Scanner sc, BTOProject chosenProject) {
        projectManagementService.editProject(sc, chosenProject);
    }

    /**
     * Deletes the selected BTO project.
     *
     * @param sc Scanner for user input
     * @param chosenProject The project to be deleted
     */
    public void deleteBTOProjects(Scanner sc, BTOProject chosenProject) {
        projectManagementService.deleteProject(sc, chosenProject);
    }

    /**
     * Approves a project application after checking slot availability.
     *
     * @param selectedProjectApp The project application to approve
     * @param selectedProject The related project
     */
    public void approveProjectApp(ProjectApplication selectedProjectApp, BTOProject selectedProject) {
        FlatType flatType = selectedProjectApp.getFlatType();
        if (flatType == FlatType.TWO_ROOM) {
            if (selectedProject.getTwoRoomUnits() <= 0) {
                System.out.println("No two-room slots available for this project.");
                return;
            }
        }
        if (flatType == FlatType.THREE_ROOM) {
            if (selectedProject.getThreeRoomUnits() <= 0) {
                System.out.println("No three-room slots available for this project.");
                return;
            }
        }
        projectAppService.updateApplicationStatus(selectedProjectApp, ProjectAppStat.SUCCESSFUL);
        System.out.println("You have successfully approved the project application for " + selectedProjectApp.getProjectName() + ".");
    }

    /**
     * Rejects a project application.
     *
     * @param selectedProjectApp The project application to reject
     */
    public void rejectProjectApp(ProjectApplication selectedProjectApp) {
        projectAppService.updateApplicationStatus(selectedProjectApp, ProjectAppStat.UNSUCCESSFUL);
        System.out.println("You have successfully rejected the project application for " + selectedProjectApp.getProjectName() + ".");
    }

    /**
     * Approves a withdrawal request from an applicant.
     *
     * @param selectedProjectApp The application requesting withdrawal
     * @param selectedProject The related project
     */
    public void withdrawProjectApp(ProjectApplication selectedProjectApp, BTOProject selectedProject) {
        projectAppService.updateApplicationStatus(selectedProjectApp, ProjectAppStat.WITHDRAWN);
        System.out.println("You have successfully approved the withdrawal request for the project application for " + selectedProjectApp.getProjectName() + ".");
    }

    /**
     * Approves an officer application and adds the officer to the project.
     *
     * @param selectedOfficerApp The officer application to approve
     * @param selectedProject The related project
     */
    public void approveOfficerApp(OfficerApplication selectedOfficerApp, BTOProject selectedProject) {
        if (selectedProject.getOfficerSlot() <= 0) {
            System.out.println("No officer slots available for this project.");
            return;
        }
        officerApplicationService.updateApplicationStatus(selectedOfficerApp, OfficerAppStat.APPROVED);
        selectedProject.addOfficer(selectedOfficerApp.getUser());
        System.out.println("You have successfully approved the officer application for " + selectedProject.getProjectName() + ".");

    }

    /**
     * Rejects an officer application.
     *
     * @param selectedOfficerApp The officer application to reject
     */
    public void rejectOfficerApp(OfficerApplication selectedOfficerApp) {
        officerApplicationService.updateApplicationStatus(selectedOfficerApp, OfficerAppStat.REJECTED);
        System.out.println("You have successfully rejected the officer application for " + selectedOfficerApp.getProjectName() + ".");
    }

    /**
     * Generates a summary report for a project and saves it to file.
     *
     * @param sc Scanner for user input
     * @param project The project to generate a report for
     */
    public void generateReport(Scanner sc, BTOProject project) {
        String report = reportPrintService.printReport(sc, project);
        if (report != null){
            IFileSaver.writeStringToFile(report, retreiveManager().getNric() + "Report.txt");
        }
    }

    /**
     * Displays all public enquiries related to the given project.
     *
     * @param projectName The name of the project
     */
    public void viewPublicEnquiry(String projectName){
        enquiryService.viewEnquiriesByProject(projectName);
    }

    /**
     * Displays and lets the manager reply to enquiries related to their project.
     *
     * @param sc Scanner for user input
     * @param projectName The name of the project
     */
    public void viewMyProjectEnquiry(Scanner sc, String projectName) {
        do{
            System.out.println("Viewing enquiry for project: " + projectName);
            List<Enquiry> enquiryList = EnquiryDB.getEnquiriesByProject(projectName);

            Enquiry selectedEnquiry = enquiryService.chooseFromEnquiryList(sc, enquiryList);

            if(selectedEnquiry != null && selectedEnquiry.getReplierUserID() == null){
                replyMyProjectEnquiry(sc, selectedEnquiry);
            }
            else if(selectedEnquiry != null && selectedEnquiry.getReplierUserID() != null){
                System.out.println("This enquiry has been replied.");
            }
            else{
                System.out.println("No enquiry is selected. Returning to main menu.");
                break;
            }
        }while(true);
    }

    /**
     * Replies to a selected enquiry on behalf of the manager.
     *
     * @param sc Scanner for user input
     * @param selectedEnquiry The enquiry to reply to
     */
    public void replyMyProjectEnquiry(Scanner sc, Enquiry selectedEnquiry) {
        enquiryService.replyEnquiry(sc, selectedEnquiry, retreiveManager().getNric());
    }

}