package services.controller;

import java.util.List;
import java.util.Scanner;

import database.dataclass.projects.ProjectDB;
import models.projects.BTOProject;
import utils.FilterUtil;
import services.interfaces.IOfficerApplicationService;
import services.interfaces.IProjectApplicationService;
import services.interfaces.IProjectManagementService;
import services.interfaces.IProjectViewService;
import services.subservices.OfficerApplicationService;
import services.subservices.ProjectApplicationService;
import services.subservices.ProjectManagementService;
import services.subservices.ProjectViewService;

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

  public void createBTOProjects() {
    
  }

  public void editBTOProjects() {
    
  }

  public void deleteBTROProjects() {
    
  }

  public void toggleProjectVisibility() {
    
  }

  public void approveProjectApp() {
    
  }

  public void approvOfficerApp() {
    
  }

  public void generateReport() {
    
  }

  public void viewEnquiry() {
    
  }

  public void replyEnquiry() {
    
  }

}
