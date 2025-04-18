package services.controller;

import database.dataclass.projects.ProjectDB;
import java.util.List;
import java.util.Scanner;
import models.enums.FlatType;
import models.enums.OfficerAppStat;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import services.interfaces.IOfficerApplicationService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IProjectManagementService;
import services.interfaces.IProjectViewService;
import services.subservices.OfficerApplicationService;
import services.subservices.ProjectApplicationService;
import services.subservices.ProjectManagementService;
import services.subservices.ProjectViewService;
import utils.FilterUtil;

public class ManagerController extends UserController{
  IProjectApplicationService projectAppService = new ProjectApplicationService();;
  IProjectManagementService projectManagementService = new ProjectManagementService();
  IOfficerApplicationService officerApplicationService = new OfficerApplicationService();
  IProjectViewService projectViewService = new ProjectViewService();

  public ManagerController(){
  }

  public ManagerController( IProjectApplicationService projectAppService, IProjectManagementService projectManagementService, 
    IOfficerApplicationService officerApplicationService, IProjectViewService projectViewService){
      this.projectAppService = projectAppService;
      this.projectManagementService = projectManagementService;
      this.officerApplicationService = officerApplicationService;
      this.projectViewService = projectViewService;
  }

  public void start(Scanner sc){
    int option = 0;
    try{
        System.out.println("Manager            ");
        System.out.println("-------------------------------------");
        System.out.println("1. Enter Project Portal");
        System.out.println("2. View My Projects");
        System.out.println("3. View Project List");
        System.out.println("4. Adjust Filter Settings");
        System.out.println("4. View Enquiries");
        System.out.println("5. Logout");
        System.out.print("Please select an option: ");
        option = sc.nextInt();
    }catch(IllegalArgumentException e){
      
    }

  }

  public void enterMyProjectsPortal(Scanner sc){
    int option = 0;
    do{
      try{
          System.out.println("Manager > My Projects");
          System.out.println("-------------------------------------");
          System.out.println("1. Create BTO Projects");
          System.out.println("2. Edit BTO Projects");
          System.out.println("3. Delete BTO Projects");
          System.out.println("4. Toggle Project Visibility");
          System.out.println("5. View Project Applicant List");
          System.out.println("6. View Officer Applicant List");
          System.out.println("7. Back to Main Menu");
          System.out.print("Please select an option: ");
          System.out.println("--------------------------------");
          option = sc.nextInt();
        }catch(IllegalArgumentException e){

        }
    }while(option != 7);

  }

  public void viewProjectsList(){
    List<BTOProject> projects =  ProjectDB.getDB();
    List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);
    
    filteredProjects.forEach(project -> System.out.println(project.toString()));
  }

  public void viewOfficerApplicantList(){

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


  public void createBTOProjects() {
    
  }

  public void editBTOProjects() {
    
  }

  public void deleteBTROProjects() {
    
  }

  public void toggleProjectVisibility() {
    
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

  public void generateReport() {
    
  }

  public void viewEnquiry() {
    
  }

  public void replyEnquiry() {
    
  }

}
