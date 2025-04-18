package services.subservices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import database.dataclass.projects.ProjectDB;
import database.dataclass.users.ManagerDB;
import models.projects.BTOProject;
import models.users.HDBManager;
import services.interfaces.IProjectManagementService;

public class ProjectManagementService implements IProjectManagementService {

  @Override
  public void editProject(Scanner sc, BTOProject project) {
      int choice = 0;

      try{
        do{
          System.out.println("Project Editor");
          System.out.println("-----------------------------------");
          System.out.println("Project: " + project.getProjectName());
          System.out.println("-----------------------------------");
          System.out.println("1. Edit Opening Date");
          System.out.println("2. Edit Closing Date");
          System.out.println("3. Edit Two-Room Slots");
          System.out.println("4. Edit Three-Room Slots");
          System.out.println("5. Edit Officer Slots");
          System.out.println("6. Toggle Project Visibility");
          System.out.println("0. Back to Main Menu");
          System.out.println("-----------------------------------");
          System.out.print("Enter your selection:");
          
          choice = sc.nextInt(); sc.nextLine();
          switch(choice){
            case(1):
              if (project.isActive()){
                System.out.println("Project is currently active. Cannot edit opening date.");
                break;
              }
              System.out.print("New Opening Date (YYYY-MM-DD): ");
              project.setOpeningDate(new SimpleDateFormat("dd-MM-yyyy").parse(sc.nextLine()));
              System.out.println("\nOpening date updated successfully.");
    
              break;
            case(2):  
              System.out.print("New Closing Date (YYYY-MM-DD): ");
              project.setClosingDate(new SimpleDateFormat("dd-MM-yyyy").parse(sc.nextLine()));
              System.out.println("\nClosing date updated successfully.");
    
              break;
            case(3):
              System.out.print("New Two-Room Slots: ");
              project.setTwoRoomUnits(sc.nextInt());
              System.out.println("\nTwo-Room Slots updated successfully.");
              sc.nextLine();
              break;
            case(4): 
              System.out.print("New Three-Room Slots: ");
              project.setThreeRoomUnits(sc.nextInt());
              System.out.println("\nThree-Room Slots updated successfully.");
              sc.nextLine();
              break;

            case(5): 
              System.out.print("New Officer Slots: ");
              project.setOfficerSlot(sc.nextInt()); sc.nextLine();
              System.out.println("\nOfficer Slots updated successfully.");
              break;

            case(6):
              project.setVisibility(!project.isVisible());
              break;

            case(0):
              System.out.println("Back to Main Menu");
              break;
            default:
              System.out.println("Invalid choice. Please try again.");
              break;}
        }
        while(choice != 0);
      }
      catch(ParseException e){
        System.out.println("Invalid date format. Please enter the date in the format dd-MM-yyyy.");
      }
      finally{
        ProjectDB.updateProject(project);
        System.out.println("\n-----------------------------------");
        System.out.println("Project details updated successfully.");
        System.out.println("Exiting edit project menu.");
      }
  }

  @Override
  public void deleteProject(Scanner sc, BTOProject project) {

      if (project.isActive()) {
          System.out.println("Project is currently active.\nCannot delete an active project.");
          return;
      }
     
      try{
          System.out.print("Are you sure you want to delete this project?\n (Y/N): ");
          String confirmation = sc.nextLine().toUpperCase();
          if (confirmation.equals("Y")) {
              ProjectDB.removeProject(project);
              System.out.println("Project deleted successfully.");
          } else if (confirmation.equals("N")) {
              System.out.println("Project deletion cancelled.");
              return;
          }
      } catch (Exception e) {
          System.out.println("Invalid input. Please enter Y or N.");
          return;
      }

      System.out.println("Project deleted successfully.");
  }

  @Override
  public void createProject(Scanner sc, String managerID, List<String> projectIDList) {
      System.out.println("Create New Project");
      System.out.println("-----------------------------------");

      String projectName = "";
      String neighborhood = "";
      Date openingDate = null;
      Date closingDate = null;
      double sellingPrice2 = 0.0;
      double sellingPrice3 = 0.0;
      int twoRoomUnits = 0;
      int threeRoomUnits = 0;
      int officerSlots = 0;

      while(true){
        System.out.print("Enter Project Name: ");
        projectName = sc.nextLine();
        if (ProjectDB.getProjectByName(projectName) != null) {
            System.out.println("Project name already exists.\nPlease enter a different name.");
            continue;
        }
        break;
      }

      while(true){
        System.out.print("Enter Neighborhood: ");
        neighborhood = sc.nextLine();
        if (neighborhood.isEmpty()) {
            System.out.println("Neighborhood cannot be empty.\nPlease enter a valid neighborhood.");
            continue;
        }
        break;
      }

      List<List<Date>> dateRangeList = new ArrayList<>();

      for (String projectID : projectIDList) {
        BTOProject project = ProjectDB.getProjectByName(projectID);
        if(project != null && project.isActive())
          dateRangeList.add(new ArrayList<>(List.of(project.getOpeningDate(), project.getClosingDate())));
      }

      while(true){
        
        while(true){
          System.out.print("Enter Opening Date (DD-MM-YYYY): ");
          String openingDateStr = sc.nextLine();
          try {
              openingDate = new SimpleDateFormat("dd-MM-yyyy").parse(openingDateStr);
              break;
          } catch (ParseException e) {
              System.out.println("Invalid date format.\nPlease enter the date in the format dd-MM-yyyy.");
          }
        }

        while(true){
          System.out.print("Enter Closing Date (DD-MM-YYYY): ");
          String closingDateStr = sc.nextLine();
          try {
              closingDate = new SimpleDateFormat("dd-MM-yyyy").parse(closingDateStr);
              break;
          } catch (ParseException e) {
              System.out.println("Invalid date format.\nPlease enter the date in the format dd-MM-yyyy.");
          }
        }

        if (openingDate.after(closingDate)) {
            System.out.println("Closing date cannot be before opening date.\nPlease enter a valid date range.");
            continue;
        }

        for (List<Date> dateRange : dateRangeList) {
          if (!(openingDate.after(dateRange.get(1)) || closingDate.before(dateRange.get(0)))) {
              System.out.println("Project dates overlap with existing project dates.\nPlease enter a different date range.");
              continue;
          }
        }

        break;
      }
      
      while(true){
        System.out.print("Enter Two-Room Units Price: ");
        sellingPrice2 = sc.nextDouble(); sc.nextLine();
        if (sellingPrice2 <= 0) {
            System.out.println("Invalid price.\nPlease enter a positive value.");
            continue;
        }
        break;
      }

      while(true){
        System.out.print("Enter Three-Room Units Price: ");
        sellingPrice3 = sc.nextDouble(); sc.nextLine();
        if (sellingPrice3 <= 0) {
            System.out.println("Invalid price.\nPlease enter a positive value.");
            continue;
        }
        break;
      }

      while(true){
        System.out.println("Enter Two-Room Slots: ");
        twoRoomUnits = sc.nextInt(); sc.nextLine();
        if (twoRoomUnits <= 0) {
            System.out.println("Invalid number of slots.\nPlease enter a positive value.");
            continue;
        }
        break;
      }

      while(true){
        System.out.println("Enter Three-Room Slots: ");
        threeRoomUnits = sc.nextInt(); sc.nextLine();
        if (threeRoomUnits <= 0) {
            System.out.println("Invalid number of slots.\nPlease enter a positive value!");
            continue;
        }
        break;
      }

      while(true){
        System.out.print("Enter Officer Slots: ");
        officerSlots = sc.nextInt(); sc.nextLine();
        if (officerSlots <= 0) {
            System.out.println("Invalid number of slots.\n Please enter a positive value!");
            continue;
        }
        else if(officerSlots > 10) {
            System.out.println("Officer slots cannot exceed 10. Please try again!");
            continue;
        }
        break;
      }

      BTOProject newProject = new BTOProject(projectName, neighborhood, twoRoomUnits, threeRoomUnits, 
                                          sellingPrice2, sellingPrice3, openingDate, closingDate, managerID, 
                                          officerSlots, false);
      ProjectDB.addProject(newProject);

      System.out.printf("Project '%s' created successfully.\n", projectName);

  }
  
}


