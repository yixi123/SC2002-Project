package services.controller;

import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import database.dataclass.projects.EnquiryDB;
import database.dataclass.projects.ProjectDB;

import models.enums.FlatType;
import models.enums.OfficerAppStat;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.Applicant;
import models.users.HDBManager;


import utils.FilterUtil;

import view.ManagerView;

import services.interfaces.IEnquiryService;
import services.interfaces.IOfficerApplicationService;
import services.interfaces.IReportPrintService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IProjectManagementService;
import services.interfaces.IProjectViewService;

import services.subservices.EnquiryService;
import services.subservices.OfficerApplicationService;
import services.subservices.ProjectApplicationService;
import services.subservices.ProjectManagementService;
import services.subservices.ProjectViewService;
import services.subservices.ReportPrintService;


public class ManagerController extends UserController{
  IProjectApplicationService projectAppService = new ProjectApplicationService();;
  IProjectManagementService projectManagementService = new ProjectManagementService();
  IOfficerApplicationService officerApplicationService = new OfficerApplicationService();
  IProjectViewService projectViewService = new ProjectViewService();
  IEnquiryService enquiryService = new EnquiryService();
  IReportPrintService reportPrintService = new ReportPrintService();

  private static ManagerView managerView = new ManagerView();


  public ManagerController(){
  }

  public ManagerController( IProjectApplicationService projectAppService, IProjectManagementService projectManagementService, 
    IOfficerApplicationService officerApplicationService, IProjectViewService projectViewService, IEnquiryService enquiryService, IReportPrintService printService){
      this.projectAppService = projectAppService;
      this.projectManagementService = projectManagementService;
      this.officerApplicationService = officerApplicationService;
      this.projectViewService = projectViewService;
      this.enquiryService = enquiryService;
      this.reportPrintService = printService;
  }

  public HDBManager retreiveManager(){
    HDBManager currentUser = (HDBManager) auth.getCurrentUser();
    return currentUser;
  }

  public void start(Scanner sc){
    int option = 0;
      try{
          managerView.enterMainMenu(sc);
      }catch(IllegalArgumentException e){
          System.out.println(e.getMessage());
      }catch(Exception e){
          System.out.println("Unexpected Error has occured: " + e.getMessage());
          System.out.println("Returning to Homepage...");
      }
  }

  public void manageChosenProject(Scanner sc, BTOProject chosenProject) throws Exception{
      int option = 0;
      try{
          managerView.enterManagementPortal(sc, chosenProject);
      }catch(IllegalArgumentException e){
          System.out.println(e.getMessage());
      }catch(IndexOutOfBoundsException e){
          System.out.println("No projects available for the chosen project.");
      }
  }

  public void viewAllProjectList(Scanner sc) throws Exception{
      try{
          managerView.displayReadOnlyAllProject(sc, filterSettings);
      }catch(IllegalArgumentException e){
          System.out.println(e.getMessage());
      }catch(IndexOutOfBoundsException e){
          System.out.println("No projects available");
      }
  }

  public void readOnlyProjectAction(Scanner sc, String projectName) throws Exception {
    try{
      managerView.displayReadOnlyProjectPortal(sc, projectName);
    }catch(IllegalArgumentException e){
      System.out.println(e.getMessage());
      return;
    }catch(IndexOutOfBoundsException e){
      System.out.println("No projects available for the chosen project.");
    }
  }

  public void manageMyProjects(Scanner sc) throws Exception{
    try{
      managerView.displayMyProjects(sc, filterSettings);
    }catch(IllegalArgumentException e){
      System.out.println(e.getMessage());
    }catch(IndexOutOfBoundsException e){
      System.out.println("No projects available on chosen project.");
    }
  }


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

  public void pendingProjectApplicationAction(Scanner sc, ProjectApplication selectedProjectApp, BTOProject selectedProject) {
    System.out.println("1. Approve Project Application");
    System.out.println("2. Reject Project Application");
    System.out.print("Enter your choice: ");
    
    int actionChoice = sc.nextInt();
    switch(actionChoice){
      case 1 -> approveProjectApp(selectedProjectApp, selectedProject);
      case 2 -> rejectProjectApp(selectedProjectApp);
      default -> { System.out.println("Invalid choice. Return to menu.");}
    }
  }

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

  public void displayOfficerApplicationAction(Scanner sc, OfficerApplication selectedOfficerApp, BTOProject selectedProject) {

    if (selectedOfficerApp.getStatus() != OfficerAppStat.PENDING) {
        System.out.println("This application is not pending. Cannot approve or reject.");
        return;
    }
    System.out.println("You have selected: " + selectedOfficerApp.toString());
    System.out.println("1. Approve Officer Application");
    System.out.println("2. Reject Officer Application");
    System.out.print("Enter your choice: ");
    
    int actionChoice = sc.nextInt();
    switch(actionChoice){
      case 1 -> approveOfficerApp(selectedOfficerApp, selectedProject);
      case 2 -> rejectOfficerApp(selectedOfficerApp);
      default -> { System.out.println("Invalid choice. Return to menu.");}
    }
  }

  public void createBTOProjects(Scanner sc) {
    HDBManager manager = retreiveManager();
    try{
      projectManagementService.createProject(sc, manager.getNric(), manager.getManagedProjectsID());
    }catch(Exception e){
      System.out.println("Error creating project: " + e.getMessage());
    }
  }

  public void editBTOProjects(Scanner sc, BTOProject chosenProject) {
    projectManagementService.editProject(sc, chosenProject);
  }


  public void deleteBTOProjects(Scanner sc, BTOProject chosenProject) {
    projectManagementService.deleteProject(sc, chosenProject);
  }

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

  public void rejectProjectApp(ProjectApplication selectedProjectApp) {
    projectAppService.updateApplicationStatus(selectedProjectApp, ProjectAppStat.UNSUCCESSFUL);
    System.out.println("You have successfully rejected the project application for " + selectedProjectApp.getProjectName() + ".");
  }

  public void withdrawProjectApp(ProjectApplication selectedProjectApp, BTOProject selectedProject) {
    projectAppService.updateApplicationStatus(selectedProjectApp, ProjectAppStat.WITHDRAWN);
    System.out.println("You have successfully approved the withdrawal request for the project application for " + selectedProjectApp.getProjectName() + ".");
  }

  public void approveOfficerApp(OfficerApplication selectedOfficerApp, BTOProject selectedProject) {
    if (selectedProject.getOfficerSlot() <= 0) {
        System.out.println("No officer slots available for this project.");
        return;
    }
    officerApplicationService.updateApplicationStatus(selectedOfficerApp, OfficerAppStat.APPROVED);
    selectedProject.addOfficer(selectedOfficerApp.getUser());
    System.out.println("You have successfully approved the officer application for " + selectedProject.getProjectName() + ".");
    
  }

  public void rejectOfficerApp(OfficerApplication selectedOfficerApp) {
    officerApplicationService.updateApplicationStatus(selectedOfficerApp, OfficerAppStat.REJECTED);
    System.out.println("You have successfully rejected the officer application for " + selectedOfficerApp.getProjectName() + ".");
  }

  public void generateReport(Scanner sc, BTOProject project) {
    reportPrintService.printReport(sc, project);
  }

  public void viewPublicEnquiry(String projectName){
    enquiryService.viewEnquiriesByProject(projectName);
  }

  public void viewMyProjectEnquiry(Scanner sc, String projectName) {
    do{
      System.out.println("Viewing enquiry for project: " + projectName);
      List<Enquiry> enquiryList = EnquiryDB.getEnquiriesByProject(projectName);
      Enquiry selectedEnquiry = enquiryService.chooseFromEnquiryList(sc, enquiryList);
      if(selectedEnquiry != null && selectedEnquiry.getReplierUserID() == null){ 
        replyMyProjectEnquiry(sc, selectedEnquiry.getId());
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

  public void replyMyProjectEnquiry(Scanner sc, int enquiryId) {
    
    do{
      try{
        System.out.print("Enter your reply content: ");
        String replyContent = sc.nextLine();
        System.out.println("-----------------------------------------");
        enquiryService.replyEnquiry(enquiryId, auth.getCurrentUser().getNric(), replyContent);
        System.out.println("Reply sent successfully.");
        System.out.println("-----------------------------------------");
        break;
      }catch(Exception e){
        System.out.println("Error: " + e.getMessage());
        System.out.println("Unsuccessful Reply, Please try again.");
      }
    }while(true);
  }

}
