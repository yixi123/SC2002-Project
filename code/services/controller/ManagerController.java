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
        do{
          System.out.println("\n             Manager Portal              ");
          System.out.println("-----------------------------------------");
          System.out.println("1. Project Management Portal");
          System.out.println("2. View All Projects");
          System.out.println("3. Create A New Project");
          System.out.println("4. Adjust Filter Settings");
          System.out.println("0. Logout");
          System.out.println("-----------------------------------------");
          System.out.print("Please select an option: ");
          option = sc.nextInt(); sc.nextLine();
          System.out.println("\n-----------------------------------------");

          switch(option){
            case 1 -> manageMyProjects(sc);
            case 2 -> viewAllProjectList(sc);
            case 3 -> createBTOProjects(sc, null);
            case 4 -> adjustFilterSettings(sc);
            case 0 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Returning to menu.");
          }
        }while(option != 0);
      }catch(IllegalArgumentException e){
          System.out.println(e.getMessage());
      }catch(Exception e){
          System.out.println("Unexpected Error has occured: " + e.getMessage());
          System.out.println("Returning to Main Menu...");
      }
    

  }

  public void manageChosenProject(Scanner sc, BTOProject chosenProject) throws Exception{
    int option = 0;
    do{
      try{
          System.out.println("       Project Management Portal         ");
          System.out.println("-----------------------------------------");
          System.out.printf("Project: %s\n", chosenProject.getProjectName());
          System.out.println("-----------------------------------------");
          System.out.println("1. Edit Project Detail");
          System.out.println("2. Delete Project");
          System.out.println("3. Generate Report");
          System.out.println("4. View/Manage Project Applications");
          System.out.println("5. View/Manage Officer Applications");
          System.out.println("6. View/Reply Enquiry");
          System.out.println("0. Back to Main Menu");
          System.out.println("-----------------------------------------");
          System.out.print("Please select an option: ");
          option = sc.nextInt(); sc.nextLine();
          System.out.println("\n-----------------------------------------");
          switch(option){
            case 1 -> editBTOProjects(sc, chosenProject);
            case 2 -> deleteBTOProjects(sc, chosenProject);
            case 3 -> generateReport(sc, chosenProject);
            case 4 -> viewProjectApplicantionList(sc, chosenProject);
            case 5 -> viewOfficerApplicantionList(sc, chosenProject);
            case 6 -> viewMyProjectEnquiry(sc, chosenProject.getProjectName());
            case 0 -> { System.out.println("Returning to Main Menu..."); break;}
            default -> throw new IllegalArgumentException("Invalid choice. Returning to menu.");
          }
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }while(option != 0);

  }

  public void viewAllProjectList(Scanner sc) throws Exception{
    List<BTOProject> projects =  ProjectDB.getDB();
    List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

    int choice = 0;

    do{
      System.out.println("Project List View [View Only]");
      System.out.println("-----------------------------------------");
      System.out.println("Enter 'MyProject Portal' for\nProject and Enquiry Management");
      System.out.println("-----------------------------------------");
      
      int index = 1;
      for(BTOProject project: filteredProjects){
        System.out.printf("[%d] %s\n\n", index, project.shortToString());
        index++;
      }
      System.out.println("[0] Back to Main Menu");
      System.out.println("-----------------------------------------");
      System.out.print("Select the project: ");
      choice = sc.nextInt(); sc.nextLine();

      if(choice == 0){
        System.out.println("Returning to Main Menu...");
        break;
      }

      try{
        readOnlyProjectAction(sc, filteredProjects.get(choice - 1).getProjectName());
      }
      catch(IndexOutOfBoundsException e){
        System.out.println("Invalid choice. Please try again.");
      }
    }while(choice != 0);
  }

  public void readOnlyProjectAction(Scanner sc, String projectName) throws Exception {
    int actionChoice = 0;
    try{
      do{
        System.out.println("You have selected: " + projectName);
        System.out.println("-----------------------------------------");
        System.out.println("1. View Project Enquiry List");
        System.out.println("0. Back to Project List View");
        System.out.println("-----------------------------------------");
        System.out.print("Enter your choice: ");
        actionChoice = sc.nextInt(); sc.nextLine();
        System.out.println("\n-----------------------------------------");
        switch(actionChoice){
          case 1 -> { enquiryService.viewEnquiriesByProject(projectName);}
          case 2 -> { System.out.println("Returning to Project List View."); 
                      break;}
          default -> { System.out.println("Invalid choice. Return to menu.");}
        }
      }while(actionChoice != 0);
    }catch(Exception e){
      System.out.println("Error has occured: " + e.getMessage());
      return;
    }
  }

  public void manageMyProjects(Scanner sc) throws Exception{
    List<BTOProject> projects = ProjectDB.getDB();
    filterSettings.setManager(auth.getCurrentUser().getName());
    List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);
    int choice = 0;

    System.out.println("\n-----------------------------------------");
    System.out.println("      Project Management Portal");
    System.out.println("-----------------------------------------");
    System.out.println("Select your project to manage:");
    System.out.println("-----------------------------------------");
    do{
      int index = 1; 
      for(BTOProject project: filteredProjects){
        System.out.printf("[%d] %s\n\n", index, project.shortToString());
        index += 1;
      }
      System.out.println("[0] Back to Main Menu");
      System.out.println("-----------------------------------------");
      System.out.print("Enter your choice: ");
      choice = sc.nextInt(); sc.nextLine();
      System.out.println("-----------------------------------------");
      
      if(choice == 0){
        System.out.println("Returning to Main Menu...");
        break;
      }

      manageChosenProject(sc, filteredProjects.get(choice - 1));
    } while(true); 
    
    
  }


  public void viewProjectApplicantionList(Scanner sc, BTOProject selectedProject) {
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

    if (selectedProjectApp.getStatus() != ProjectAppStat.PENDING) {
        System.out.println("This application is not pending. Cannot approve or reject.");
        return;
    }
    System.out.println("You have selected: " + selectedProjectApp.toString());
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


  public void viewOfficerApplicantionList(Scanner sc, BTOProject selectedProject) {
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

  public void createBTOProjects(Scanner sc, BTOProject chosenProject) {
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
