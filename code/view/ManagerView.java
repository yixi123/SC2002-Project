package view;

import java.util.List;
import java.util.Scanner;

import database.dataclass.projects.ProjectDB;
import models.projects.BTOProject;
import models.projects.FilterSettings;
import services.controller.ManagerController;
import utils.FilterUtil;

public class ManagerView extends UserView {

	private static ManagerController app = new ManagerController();

	public void enterMainMenu(Scanner sc) 
	throws Exception, IllegalArgumentException{
		int option = -1;

		do{
          System.out.println("\n             Manager Portal              ");
          System.out.println(ViewFormatter.breakLine());
          System.out.println("1. Project Management Portal");
          System.out.println("2. View All Projects");
          System.out.println("3. Create A New Project");
          System.out.println("4. Adjust Filter Settings");
          System.out.println("5. Change My Password");
          System.out.println("0. Logout");
          System.out.println(ViewFormatter.breakLine());
          System.out.print("Please select an option: ");
          option = sc.nextInt(); sc.nextLine();
          System.out.println(ViewFormatter.breakLine());

          switch(option){
            case 1 -> app.manageMyProjects(sc);
            case 2 -> app.viewAllProjectList(sc);
            case 3 -> app.createBTOProjects(sc);
            case 4 -> app.adjustFilterSettings(sc);
            case 5 -> app.changeMyPassword(sc);
            case 0 -> {
							System.out.println("Logging out...");
							System.out.println(ViewFormatter.breakLine());
						}
            default -> {
							System.out.println("Invalid choice. Returning to menu.");
							System.out.println(ViewFormatter.breakLine());
						}
          }
        }while(option != 0 && option != 5);
	}

	public void displayReadOnlyAllProject(Scanner sc, FilterSettings filterSettings) 
	throws Exception, IllegalArgumentException, IndexOutOfBoundsException{
		List<BTOProject> projects =  ProjectDB.getDB();
		List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

		if (filteredProjects.isEmpty()) {
			System.out.println("No projects available based on\n the current filter settings.");
			System.out.println(ViewFormatter.breakLine());
			return;
		}

		int choice = 0;

		do{
			System.out.println("\nProject List [View Only]");
			System.out.println(ViewFormatter.breakLine());
			System.out.println("Enter 'MyProject Portal' for\nProject and Enquiry Management");
			System.out.println(ViewFormatter.breakLine());
			
			int index = 1;
			for(BTOProject project: filteredProjects){
				System.out.printf("[%d] %s\n\n", index, project.shortToString());
				index++;
			}
			System.out.println("[0] Back to Main Menu");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Select the project: ");
			choice = sc.nextInt(); sc.nextLine();

			if(choice == 0){
				System.out.println("Returning to Main Menu...");
				return;
			}
			app.readOnlyProjectAction(sc, filteredProjects.get(choice - 1).getProjectName());
		}while(choice != 0);
	}

	public void displayReadOnlyProjectPortal(Scanner sc, String projectName) 
	throws Exception, IllegalArgumentException, IndexOutOfBoundsException{
		int actionChoice = -1;
		do{
			System.out.println("You have selected: " + projectName);
			System.out.println(ViewFormatter.breakLine());
			System.out.println("1. View Project Enquiry List");
			System.out.println("0. Back to Project List View");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Enter your choice: ");
			actionChoice = sc.nextInt(); sc.nextLine();
			System.out.println(ViewFormatter.breakLine());
			switch(actionChoice){
					case 1 -> { app.viewPublicEnquiry(projectName);}
					case 0 -> { System.out.println("Returning to Project List View."); return;}
					default -> { System.out.println("Invalid choice. Try Again!"); return;}
			}
    }while(actionChoice != 0);
	}

	public void enterManagementPortal(Scanner sc, BTOProject chosenProject) 
	throws Exception, IllegalArgumentException, IndexOutOfBoundsException{
		int option = -1;

		do{
			System.out.println("\n       Project Management Portal         ");
			System.out.println(ViewFormatter.breakLine());
			System.out.printf("Project: %s\n", chosenProject.getProjectName());
			System.out.println(ViewFormatter.breakLine());
			System.out.println("1. Edit Project Detail");
			System.out.println("2. Delete Project");
			System.out.println("3. Generate Report");
			System.out.println("4. View/Manage Project Applications");
			System.out.println("5. View/Manage Officer Applications");
			System.out.println("6. View/Reply Enquiry");
			System.out.println("0. Back to Project List");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Please select an option: ");
			option = sc.nextInt(); sc.nextLine();
			System.out.println(ViewFormatter.breakLine());
			switch(option){
				case 1 -> app.editBTOProjects(sc, chosenProject);
				case 2 -> app.deleteBTOProjects(sc, chosenProject);
				case 3 -> app.generateReport(sc, chosenProject);
				case 4 -> app.viewProjectApplicationList(sc, chosenProject);
				case 5 -> app.viewOfficerApplicationList(sc, chosenProject);
				case 6 -> app.viewMyProjectEnquiry(sc, chosenProject.getProjectName());
				case 0 -> { System.out.println("Returning to Project List..."); return;}
				default -> throw new IllegalArgumentException("Invalid choice. Returning to menu.");
			}
		}
		while(true);
	}

	public void displayMyProjects(Scanner sc, FilterSettings filterSettings) 
	throws Exception, IllegalArgumentException, IndexOutOfBoundsException{
		List<BTOProject> projects = ProjectDB.getDB();
		filterSettings.setManager(app.retreiveManager().getNric());
		List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);
		int choice = 0;

		if (filteredProjects.isEmpty()) {
			System.out.println("No projects available based on\n the current filter settings.");
			System.out.println(ViewFormatter.breakLine());
			return;
		}

		System.out.println();
		System.out.println(ViewFormatter.breakLine());
		System.out.println("      Project Management Portal");
		System.out.println(ViewFormatter.breakLine());
		System.out.println("Select your project to manage:");
		System.out.println(ViewFormatter.breakLine());
		do{
			int index = 1; 
			for(BTOProject project: filteredProjects){
				System.out.printf("[%d] %s\n\n", index, project.shortToString());
				index += 1;
			}
			System.out.println("[0] Back to Main Menu");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Enter your choice: ");
			choice = sc.nextInt(); sc.nextLine();
			System.out.println(ViewFormatter.breakLine());
			
			if(choice == 0){
				System.out.println("Returning to Main Menu...");
				return;
			}

			app.manageChosenProject(sc, filteredProjects.get(choice - 1));
		} while(true); 
	}
}
