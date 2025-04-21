package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import database.dataclass.projects.ProjectDB;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.FilterSettings;
import models.projects.ProjectApplication;
import models.users.Applicant;
import services.controller.ApplicantController;
import utils.FilterUtil;

/**
 * The ApplicantView class provides the user interface for applicants.
 * It includes functionalities for viewing projects, applying for projects,
 * and managing enquiries.
 */
public class ApplicantView extends UserView {
    /**
     * Handles applicant-related actions triggered from the view.
     */
	private static ApplicantController app = new ApplicantController();

    /**
     * Displays the main menu for the applicant.
     *
     * @param sc The Scanner object for user input.
     * @throws Exception If an error occurs during menu navigation.
     */
    public void enterMainMenu(Scanner sc) throws Exception{
        int choice = -1;
		
	 	do{
            System.out.println();
            System.out.println(ViewFormatter.breakLine());
            System.out.println("           Applicant Portal");
            System.out.println(ViewFormatter.breakLine());
            System.out.println("1. Enter Project Protal");
            System.out.println("2. Adjust Filter Settings");
            System.out.println("3. View Application Status");
            System.out.println("4. Withdraw Current Application");
            System.out.println("5. View My Enquiry");
            System.out.println("6. Change My Password");
            System.out.println("0. Logout");
            System.out.println(ViewFormatter.breakLine());
            System.out.print("Enter your choice: ");
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please try again!");
                continue;
            } finally {
                sc.nextLine();
            }
            System.out.println(ViewFormatter.breakLine());

            switch (choice) {
                case 0 -> {
                    System.out.println("Logging out...");
					System.out.println(ViewFormatter.breakLine());
                    return;
                }
                case 1 -> app.enterProjectPortal(sc);
                case 2 -> app.adjustFilterSettings(sc);
                case 3 -> app.viewApplicationStatus();
                case 4 -> app.withdrawProject(sc);
                case 5 -> app.viewMyEnquiry(sc);
                case 6 -> app.changeMyPassword(sc);
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println(ViewFormatter.breakLine());
                }
            }
        } while (choice != 0 && choice != 6);
	}

    /**
     * Displays the project portal for applicants based on filter settings.
     *
     * @param sc The Scanner object for user input.
     * @param filterSettings The filter settings to apply to the project list.
     * @throws Exception If an error occurs during project portal navigation.
     */
	public void displayProjectPortal(Scanner sc, FilterSettings filterSettings) throws Exception {
		List<BTOProject> projects =  ProjectDB.getDB();
        List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

		if (filteredProjects.isEmpty()) {
			System.out.println("No projects available based on\n the current filter settings.");
			System.out.println(ViewFormatter.breakLine());
		} else {
			do{
				System.out.println("Available Projects:");
				System.out.println("Choose a project to apply for or enquire about:");
				System.out.println(ViewFormatter.breakLine());
				for (int i = 0; i < filteredProjects.size(); i++) {
					System.out.println((i + 1) + ". " + filteredProjects.get(i).shortToString());
				}
				System.out.println("0. Return to menu");
				System.out.println(ViewFormatter.breakLine());
				System.out.println("Enter your choice: ");
				int projectChoice;
                try {
                    projectChoice = sc.nextInt() - 1;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please try again!");
                    continue;
                } finally {
                    sc.nextLine();
                }
                System.out.println(ViewFormatter.breakLine());
				if (projectChoice >= 0 && projectChoice < filteredProjects.size()) {
					displayProjectAction(sc, filteredProjects.get(projectChoice));
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
    public void displayProjectAction(Scanner sc, BTOProject selectedProject) throws Exception {
		do{
			System.out.println("\nYou have selected: " + selectedProject.getProjectName());
            System.out.println(ViewFormatter.breakLine());
			System.out.println("1. Apply for this project");
			System.out.println("2. Ask questions about this project");
			System.out.println("0. Back to project list");
            System.out.println(ViewFormatter.breakLine());
			System.out.print("Enter your choice: ");
			int actionChoice;
            try {
                actionChoice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please try again!");
                continue;
            } finally {
                sc.nextLine();
            }
            System.out.println(ViewFormatter.breakLine());
			
			switch (actionChoice) {
				case 1 -> app.applyForProject(sc, selectedProject);
				case 2 -> app.addEnquiry(sc, selectedProject);
				case 0 -> {System.out.println("Back to project list..."); return;}
				default -> System.out.println("Invalid choice. Try again!");
			}
		}while(true);
    }

    /**
     * Displays the application status for the applicant.
     */
	public void displayApplicationStatus(){
		Applicant applicant = app.retreiveApplicant();
        List<ProjectApplication> applicationList = applicant.getMyApplication();

        if (applicationList.isEmpty()) {
            System.out.println("You have not applied for any projects yet.");
            System.out.println(ViewFormatter.breakLine());
            return;
        }
        System.out.println("Your Applications:");
        for (ProjectApplication application : applicationList) {
            if (application.getStatus() != ProjectAppStat.UNSUCCESSFUL && application.getStatus() != ProjectAppStat.WITHDRAWN) {
                System.out.println("<Current Application>");
            } 
            System.out.println(application.toString());
            System.out.println(ViewFormatter.breakLine());
        }
	}

    /**
     * Displays the action menu for a selected enquiry.
     *
     * @param sc The Scanner object for user input.
     * @param selectedEnquiry The selected enquiry to view or edit.
     */
	public void viewEnquiryActionMenu(Scanner sc, Enquiry selectedEnquiry) {
        Boolean isReplied = selectedEnquiry.getReplierUserID() != null;
        if (isReplied) {
            System.out.println("This enquiry has already been replied to.");
            System.out.println(ViewFormatter.breakLine());
            System.out.println("Reply Content: " + selectedEnquiry.getReplyContent());
            System.out.println("Replied by: " + selectedEnquiry.getReplierUserID() + " on " + selectedEnquiry.getReplierTimestamp());
            System.out.println(ViewFormatter.breakLine());
            return;
        } else {
            System.out.println("This enquiry has not been replied to yet.");
            System.out.println(ViewFormatter.breakLine());
        }
        System.out.println("You can: ");
        System.out.println("1. Edit this enquiry");
        System.out.println("2. Delete this enquiry");
        System.out.println(ViewFormatter.breakLine());
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
        System.out.println(ViewFormatter.breakLine());
        switch (actionChoice) {
            case 1 -> app.editEnquiry(sc, selectedEnquiry);
            case 2 -> app.deleteEnquiry(selectedEnquiry);
            default -> System.out.println("Invalid choice. Returning to menu.\n" + ViewFormatter.breakLine());
        }
    }

}
