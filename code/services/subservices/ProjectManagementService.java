package services.subservices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.InputMismatchException;

import database.dataclass.projects.ProjectDB;
import models.projects.BTOProject;

import services.interfaces.IProjectManagementService;
import view.ViewFormatter;

/**
 * Service class for managing BTO projects.
 * Provides functionality for creating, editing, and deleting projects.
 * Enforces rules such as non-overlapping dates, valid pricing, and slot validation.
 */
public class ProjectManagementService implements IProjectManagementService {
    /**
     * Opens an editing menu that allows the user to modify the selected project's
     * date range, slot counts, and visibility. Prevents editing of active projects’ opening dates.
     *
     * @param sc Scanner for user input
     * @param project The BTO project to be edited
     */
  @Override
  public void editProject(Scanner sc, BTOProject project) {
      int choice = 0;

      try{
        do{
          System.out.println("\nProject Editor");
          System.out.println(ViewFormatter.breakLine());
          System.out.println("Project: " + project.getProjectName());
          System.out.println(ViewFormatter.breakLine());
          System.out.println("1. Edit Opening Date");
          System.out.println("2. Edit Closing Date");
          System.out.println("3. Edit Two-Room Slots");
          System.out.println("4. Edit Three-Room Slots");
          System.out.println("5. Edit Officer Slots");
          System.out.println("6. Toggle Project Visibility");
          System.out.println("0. Save edit and back to Main Menu");
          System.out.println(ViewFormatter.breakLine());
          System.out.print("Enter your selection:");
          try {
              choice = sc.nextInt();
          } catch (InputMismatchException e) {
              System.out.println("Invalid input! Please try again!");
              continue;
          } finally {
              sc.nextLine();
          }
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
              try {
                  project.setTwoRoomUnits(sc.nextInt());
              } catch (InputMismatchException e) {
                  System.out.println("Invalid input! Please try again!");
                  continue;
              } finally {
                  sc.nextLine();
              }
              System.out.println("\nTwo-Room Slots updated successfully.");
              break;
            case(4): 
              System.out.print("New Three-Room Slots: ");
              try {
                  project.setThreeRoomUnits(sc.nextInt());
              } catch (InputMismatchException e) {
                  System.out.println("Invalid input! Please try again!");
                  continue;
              } finally {
                  sc.nextLine();
              }
              System.out.println("\nThree-Room Slots updated successfully.");
              break;

            case(5): 
              System.out.print("New Officer Slots: ");
              try {
                  project.setOfficerSlot(sc.nextInt());
              } catch (InputMismatchException e) {
                  System.out.println("Invalid input! Please try again!");
                  continue;
              } finally {
                  sc.nextLine();
              }
              System.out.println("\nOfficer Slots updated successfully.");
              break;

            case(6):
              project.setVisibility(!project.isVisible());
              if (project.isVisible()) {
                System.out.println("Project is now visible to the public.");
              } else {
                System.out.println("Project is now hidden from the public.");
              }
              break;

            case(0):

              System.out.println("Back to Main Menu");
              return;
            default:
              System.out.println("Invalid choice. Please try again.");
              break;
            }
        }
        while(choice != 0);
      }
      catch(ParseException e){
        System.out.println("Invalid date format. Please enter the date in the format dd-MM-yyyy.");
      }
      finally{
        ProjectDB.updateProject();
        System.out.println(ViewFormatter.breakLine());
        System.out.println("Project details updated successfully.");
        System.out.println("Exiting edit project menu.");
      }
  }

    /**
     * Deletes a BTO project after confirming with the user.
     * Prevents deletion of active projects.
     *
     * @param sc Scanner for user input
     * @param project The BTO project to be deleted
     */
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
              return;
          } else if (confirmation.equals("N")) {
              System.out.println("Project deletion cancelled.");
              return;
          }
      } catch (Exception e) {
          System.out.println("Invalid input. Please enter Y or N.");
          return;
      }
  }

    /**
     * Facilitates the creation of a new BTO project.
     * Includes validation for:
     * - Unique project name
     * - Non-empty neighborhood
     * - Non-overlapping date range
     * - Valid pricing and slot counts
     *
     * @param sc Scanner for user input
     * @param managerID The NRIC of the manager creating the project
     * @param projectList Existing list of projects to check for date overlaps
     */
  @Override
  public void createProject(Scanner sc, String managerID, List<BTOProject> projectList) {
      System.out.println("Create New Project");
      System.out.println(ViewFormatter.breakLine());

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

      for (BTOProject project : projectList) {
        if(project != null)
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
        boolean isOverlapping = false;
        for (List<Date> dateRange : dateRangeList) {
          if (!(openingDate.after(dateRange.get(1)) || closingDate.before(dateRange.get(0)))) {
            isOverlapping = true;
            System.out.println("Project dates overlap with existing project dates.\nPlease enter a different date range.");
            break;
          }
        }
        if (isOverlapping) {
            continue;
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
        System.out.print("Enter Two-Room Slots: ");
        try {
            twoRoomUnits = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please try again!");
            continue;
        } finally {
            sc.nextLine();
        }
        if (twoRoomUnits <= 0) {
            System.out.println("Invalid number of slots.\nPlease enter a positive value.");
            continue;
        }
        break;
      }

      while(true){
        System.out.print("Enter Three-Room Slots: ");
        try {
            threeRoomUnits = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please try again!");
            continue;
        } finally {
            sc.nextLine();
        }
        if (threeRoomUnits <= 0) {
            System.out.println("Invalid number of slots.\nPlease enter a positive value!");
            continue;
        }
        break;
      }

      while(true){
        System.out.print("Enter Officer Slots: ");
        try {
            officerSlots = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please try again!");
            continue;
        } finally {
            sc.nextLine();
        }
        if (officerSlots <= 0) {
            System.out.println("Invalid number of slots.\n Please enter a positive value!");
            continue;
        }
        else if(officerSlots > 10) {
            System.out.println("Officer slots cannot exceed 10.\n Please try again!");
            continue;
        }
        break;
      }
      
      BTOProject newProject = new BTOProject(projectName, neighborhood, twoRoomUnits, threeRoomUnits, 
                                          sellingPrice2, sellingPrice3, openingDate, closingDate, managerID, 
                                          officerSlots, false);
      ProjectDB.addProject(newProject);

      System.out.printf("Project '%s' created successfully.\n", projectName);
      System.out.println(ViewFormatter.breakLine());
      return;

  }
  
}


