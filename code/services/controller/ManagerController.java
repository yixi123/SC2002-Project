package services.controller;

import java.util.List;
import java.util.Scanner;

import database.dataclass.projects.ProjectDB;
import models.projects.BTOProject;
import models.users.HDBManager;
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

  public HDBManager retreiveManager(){
    HDBManager currentUser = (HDBManager) auth.getCurrentUser();
    return currentUser;
  }

  public void start(Scanner sc){
    int option = 0;
    try{
        System.out.println("Manager            ");
        System.out.println("-------------------------------------");
        System.out.println("1. Enter Project Portal");
        System.out.println("2. View All Projects");
        System.out.println("3. View/Manage My Projects");
        System.out.println("4. Create A New Project");
        System.out.println("4. Adjust Filter Settings");
        System.out.println("4. View Enquiries");
        System.out.println("5. Logout");
        System.out.println("-------------------------------------");
        System.out.print("Please select an option: ");
        option = sc.nextInt(); sc.nextLine();
        System.out.println("\n-------------------------------------");
    }catch(IllegalArgumentException e){
      
    }

  }

  public void manageChosenProject(Scanner sc, BTOProject chosenProject){
    int option = 0;
    do{
      try{
          System.out.println("Manager");
          System.out.println("-------------------------------------");
          System.out.printf("Project: %s\n", chosenProject.getProjectName());
          System.out.println("-------------------------------------");
          System.out.println("1. Edit Project Detail");
          System.out.println("2. Delete Project");
          System.out.println("5. View/Manage Project Applications");
          System.out.println("6. View/Manage Officer Applications");
          System.out.println("7. View/Reply Enquiry");
          System.out.println("0. Back to Main Menu");
          System.out.println("-------------------------------------");
          System.out.print("Please select an option: ");
          option = sc.nextInt(); sc.nextLine();
          System.out.println("\n-------------------------------------");

        }catch(IllegalArgumentException e){

        }
    }while(option != 7);

  }

  public void viewProjectsList(){
    List<BTOProject> projects =  ProjectDB.getDB();
    List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);
    
    filteredProjects.forEach(project -> System.out.println(project.toString()));
  }

  public void manageMyProjects(Scanner sc){
    List<BTOProject> projects = ProjectDB.getDB();
    filterSettings.setManager(auth.getCurrentUser().getName());
    List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

    int index = 1;
    for(BTOProject project: filteredProjects){
      System.out.printf("[%d] %s\n", index, project.shortToString());
      index += 1;
    }
    System.out.println("0. Back to Main Menu");
    System.out.println("-------------------------------------");
    System.out.println("Select the project you would like to manage:");
    int choice = sc.nextInt(); sc.nextLine();
    
    manageChosenProject(sc, filteredProjects.get(choice - 1));
  }

  public void viewOfficerApplicantList(){
    
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
