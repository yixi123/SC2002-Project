package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.projects.ProjectDB;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.FilterSettings;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.HDBOfficer;
import services.controller.OfficerController;
import utils.FilterUtil;

/**
 * The OfficerView class provides the user interface for HDB officers.
 * It extends the ApplicantView class and includes functionalities specific
 * to officers, such as managing projects, viewing successful applicants,
 * and handling enquiries.
 */

public class OfficerView extends ApplicantView {

    private static OfficerController app = new OfficerController();

	/**
     * Displays the main menu for the officer.
     * 
     * @param sc The Scanner object for user input.
     * @throws Exception If an error occurs during menu navigation.
     */
	@Override
	public void enterMainMenu(Scanner sc) throws Exception {
		while (true) {
			System.out.println();
			System.out.println(ViewFormatter.breakLine());
			System.out.println("                HDB Officer                ");
			System.out.println(ViewFormatter.breakLine());
			System.out.println("1) Officer functions");
			System.out.println("2) Applicant functions");
			System.out.println("3) Change My Password");
			System.out.println("4) Logout");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Select an option: ");
			int choice; 
			try{
				choice = sc.nextInt();
			}catch (InputMismatchException e){
				System.out.println("Invalid input! Please try again!");
				continue;
			} finally {
				sc.nextLine();
			}

			switch (choice) {
				case 1 : officerMenu(sc); break;
				case 2 : {
					try {
						applicantMenu(sc);
					}catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}
					break;
				}
				case 3 : app.changeMyPassword(sc); break;
				case 4 : {
					System.out.println("Logging out...");
					return;    // exit start()
					}
				default : System.out.println("Invalid choice. Try again.");
			}
		}
	}

	/**
     * Displays the officer-specific menu.
     * 
     * @param sc The Scanner object for user input.
     * @throws Exception If an error occurs during menu navigation.
     */
	private void officerMenu(Scanner sc) throws Exception {
		while (true) {
			System.out.println();
			System.out.println(ViewFormatter.breakLine());
			System.out.println("             Officer Portal                ");
			System.out.println(ViewFormatter.breakLine());
			System.out.println("1. Enter Project Portal for Officer");
			System.out.println("2. View Handled Project");
			System.out.println("3. View Successful Applicants");
			System.out.println("4. Book Applicant Flat");
			System.out.println("5. Generate Receipt");
			System.out.println("6. Manage Enquiries");
			System.out.println("7. View My Officer Application Status");
			System.out.println("0. Back");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Enter your Choice: ");
			int opt; 
			try{
				opt = sc.nextInt();
			}catch (InputMismatchException e){
				System.out.println("Invalid input! Please try again!");
				continue;
			} finally {
				sc.nextLine();
			}

			switch (opt) {
				case 1 : app.enterOfficerProjectPortal(sc);break;
				case 2 : app.viewHandledProject();break;
				case 3 : app.viewSuccessfulApplicantsList();break;
				case 4 : {
					System.out.print("Applicant NRIC: ");
					String id4 = sc.nextLine();
					app.handleApplicantsSuccessfulApp(id4);
					break;
				}
				case 5 : {
					System.out.print("Applicant NRIC: ");
					String id5 = sc.nextLine();
					app.generateReceipt(id5);
					break;
				}
				case 6 : app.viewEnquiries(sc);break;
				case 7 : app.viewOfficerApplicationStatus();break;
				case 0 : {
						return;  // back to start()
				}
				default : System.out.println("Invalid.");
			}
		}
  	}

	/**
     * Displays the action menu for a selected enquiry.
     * 
     * @param sc The Scanner object for user input.
     * @param selectedEnquiry The selected enquiry to view or reply to.
     */
	public void viewEnquiryActionMenuForOfficer(Scanner sc, Enquiry selectedEnquiry) {
		if (selectedEnquiry == null) {
			System.out.println("No enquiry selected.");
			return;
		}

        Boolean isReplied = selectedEnquiry.getReplierUserID() != null;
        if (isReplied) {
            System.out.println("This enquiry has already been replied to.");
            System.out.println("Reply Content: " + selectedEnquiry.getReplyContent());
            System.out.println("Replied by: " + selectedEnquiry.getReplierUserID() + " on " + selectedEnquiry.getReplierTimestamp());
            return;
        } else {
            System.out.println("This enquiry has not been replied to yet.");
        }
       
		System.out.println(ViewFormatter.breakLine());
        System.out.println("1. Reply to this enquiry");
        System.out.println("2. Back to menu");
        System.out.print("Enter your choice: ");
		int actionChoice;
        try{
			actionChoice = sc.nextInt();
		}catch (InputMismatchException e){
			System.out.println("Invalid input! Please try again!");
			return;
		} finally {
			sc.nextLine();
		}
		System.out.println(ViewFormatter.breakLine());
		switch (actionChoice) {
            case 1 : app.replyEnquiry(sc, selectedEnquiry);break;
            case 2 : System.out.println("Returning to menu.");break;
            default : System.out.println("Invalid choice. Returning to menu.");
        }
    }
	/**
     * Displays the project portal for officers based on filter settings.
     * 
     * @param sc The Scanner object for user input.
     * @param filterSettings The filter settings to apply to the project list.
     * @throws Exception If an error occurs during project portal navigation.
     */
	public void displayOfficerProjectPortal(Scanner sc, FilterSettings filterSettings) throws Exception  {
    List<BTOProject> projects =  ProjectDB.getDB();
    List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

    if (filteredProjects.isEmpty()) {
			System.out.println("No projects available based on\n the current filter settings.");
			System.out.println(ViewFormatter.breakLine());
		} else {
			do{
				System.out.println("Available Projects:");
				System.out.println(ViewFormatter.breakLine());
				System.out.println("Choose a project to register as\n officer for or enquire about:");
				System.out.println(ViewFormatter.breakLine());
				for (int i = 0; i < filteredProjects.size(); i++) {
					System.out.println((i + 1) + ". " + filteredProjects.get(i).toString());
				}
				System.out.println("0. Return to menu");
				System.out.println(ViewFormatter.breakLine());
				System.out.println("Enter your choice: ");
				int projectChoice;
				try{
					projectChoice = sc.nextInt() - 1;
				}catch (InputMismatchException e){
					System.out.println("Invalid input! Please try again!");
					continue;
				} finally {
					sc.nextLine();
				}
				System.out.println(ViewFormatter.breakLine());
				if (projectChoice >= 0 && projectChoice < filteredProjects.size()) {
					displayOfficerProjectAction(sc, filteredProjects.get(projectChoice));
				}
				else if (projectChoice == -1) {
					System.out.println("Returning to menu.");
					System.out.println(ViewFormatter.breakLine());
					return;
				} 
				else {
					System.out.println("Invalid project choice. Try again!");
					System.out.println(ViewFormatter.breakLine());
				}
			}while(true);
		}
	}

	/**
     * Displays the action menu for a selected project.
     * 
     * @param sc The Scanner object for user input.
     * @param selectedProject The selected project to perform actions on.
     * @throws Exception If an error occurs during project action navigation.
     */
  	public void displayOfficerProjectAction(Scanner sc, BTOProject selectedProject) throws Exception {
		do{
			System.out.println("You have selected: " + selectedProject.getProjectName());
			System.out.println(ViewFormatter.breakLine());
			System.out.println("1. Register as officer for this project");
			System.out.println("2. Back to project list");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Enter your choice: ");
			int actionChoice;
			try{
				actionChoice = sc.nextInt();
			}catch (InputMismatchException e){
				System.out.println("Invalid input! Please try again!");
				continue;
			} finally {
				sc.nextLine();
			}
			System.out.println(ViewFormatter.breakLine());
			switch (actionChoice) {
				case 1 : app.registerAsOfficer(selectedProject);break;
				case 2 : {
					System.out.println("Back to project list...");
					System.out.println(ViewFormatter.breakLine());		 
					return;
				}
				default : {
					System.out.println("Invalid choice. Try again!");
					System.out.println(ViewFormatter.breakLine());
				}
			}
		}while(true);
  	}

	/**
     * Displays the applicant menu for officers acting as applicants.
     * 
     * @param sc The Scanner object for user input.
     * @throws Exception If an error occurs during menu navigation.
     */
  	private void applicantMenu(Scanner sc) throws Exception {
		int choice = -1;
			
		do{
			System.out.println();
			System.out.println(ViewFormatter.breakLine());
			System.out.println("       Officer as Applicant Portal         ");
			System.out.println(ViewFormatter.breakLine());
			System.out.println("1. Enter Project Portal");
			System.out.println("2. Adjust Filter Settings");
			System.out.println("3. View Application Status");
			System.out.println("4. Withdraw Current Application");
			System.out.println("5. View My Enquiry");
			System.out.println("0. Back");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Enter your choice: ");
			try{
				choice = sc.nextInt();
			}catch (InputMismatchException e){
				System.out.println("Invalid input! Please try again!");
				continue;
			} finally {
				sc.nextLine();
			}

			switch (choice) {
				case 0 : {
					System.out.println("Logging out...");
					System.out.println(ViewFormatter.breakLine());
					return;
				}
				case 1 : app.enterProjectPortal(sc);break;
				case 2 : app.adjustFilterSettings(sc);break;
				case 3 : app.viewApplicationStatus();break;
				case 4 : app.withdrawProject(sc);break;
				case 5 : app.viewMyEnquiry(sc);break;
				default : {
					System.out.println("Invalid choice. Please try again.");
					System.out.println(ViewFormatter.breakLine());
				}
			}
		} while (choice != 0 && choice != 6);
  	}

	/**
     * Displays the action menu for a selected project as an applicant.
     * 
     * @param sc The Scanner object for user input.
     * @param selectedProject The selected project to perform actions on.
     * @throws Exception If an error occurs during project action navigation.
     */
	@Override
    public void displayProjectAction(Scanner sc, BTOProject selectedProject) throws Exception {
		do{
			System.out.println("\nYou have selected: " + selectedProject.getProjectName());
			System.out.println(ViewFormatter.breakLine());
			System.out.println("1. Apply for this project");
			System.out.println("2. Ask questions about this project");
			System.out.println("3. Back to project list");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Enter your choice: ");

			int actionChoice;
			try{
				actionChoice = sc.nextInt();
			}catch (InputMismatchException e){
				System.out.println("Invalid input! Please try again!");
				continue;
			} finally {
				sc.nextLine();
			}
			System.out.println(ViewFormatter.breakLine());
			switch (actionChoice) {
				case 1 : app.applyForProject(sc, selectedProject);break;
				case 2 : app.addEnquiry(sc, selectedProject);break;
				case 3 : {
					System.out.println("Back to project list...");
					System.out.println(ViewFormatter.breakLine());
					return;
				}
				default : {
					System.out.println("Invalid choice. Try again!");
					System.out.println(ViewFormatter.breakLine());
				}
			}
		}while(true);
    }

	/**
     * Displays the project currently handled by the officer.
     */
	public void displayHandledProject(){
		BTOProject project = app.retrieveOfficer().getActiveProject();

		if (project == null) {
			System.out.println("No project assigned.");
			System.out.println(ViewFormatter.breakLine());
		} else {
			System.out.println(project);
		}
	}

	/**
     * Displays the successful applicants for the project handled by the officer.
     */
	public void displayHandledProjectSuccessfulApplicants(){
		BTOProject project = app.retrieveOfficer().getActiveProject();
		if (project == null) {
			System.out.println("No Project Assigned!");
			System.out.println(ViewFormatter.breakLine());
			return;
		}
		List<ProjectApplication> apps = ProjectAppDB.getApplicationsByProject(project.getProjectName());
		if (apps == null || apps.isEmpty()) {
			System.out.println("No applications found for project " + project.getProjectName());
			System.out.println(ViewFormatter.breakLine());
			return;
		}

		apps.stream()
			.filter(a -> a.getStatus() == ProjectAppStat.SUCCESSFUL)
			.forEach(System.out::println);
		
		System.out.println(ViewFormatter.breakLine());
	}

	/**
     * Displays the officer's application status for projects.
     */
	public void displayOfficerApplicationStatus(){
		HDBOfficer officer = app.retrieveOfficer();
        List<OfficerApplication> applicationList = officer.getOfficerApplications();

        if (applicationList.isEmpty()) {
            System.out.println("You have not registerd for any projects yet.");
            System.out.println(ViewFormatter.breakLine());
            return;
        }
		OfficerApplication lastApplication = applicationList.removeLast();
        System.out.println("Your Applications:");
        for (OfficerApplication application : applicationList) {
            System.out.println(application.toString());
            System.out.println(ViewFormatter.breakLine());
        }
		if (lastApplication != null) {
			System.out.println("<Current Application>");
			System.out.println(lastApplication.toString());
			System.out.println(ViewFormatter.breakLine());
		}
	}
}
