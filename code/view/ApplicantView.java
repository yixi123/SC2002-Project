package view;

import java.util.List;
import java.util.Scanner;

import database.dataclass.projects.ProjectDB;
import models.enums.FlatType;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.FilterSettings;
import models.projects.ProjectApplication;
import models.users.Applicant;
import services.controller.ApplicantController;
import services.subservices.EnquiryService;
import utils.FilterUtil;

public class ApplicantView {
	private static ApplicantController app = new ApplicantController();

    public void enterMainMenu(Scanner sc) throws Exception{
        int choice;
		
	 	do{
            System.out.println("--------------------------------");
            System.out.println("\tApplicant Portal");
            System.out.println("--------------------------------");
            System.out.println("1. Enter Project Protal");
            System.out.println("2. Adjust Filter Settings");
            System.out.println("3. View Application Status");
            System.out.println("4. Withdraw Current Application");
            System.out.println("5. View My Enquiry");
            System.out.println("6. Change My Password");
            System.out.println("0. Logout");
            System.out.println("--------------------------------");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0 -> {
                    System.out.println("Logging out...");
					System.out.println("--------------------------------");
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
                }
            }
        } while (choice != 0 && choice != 6);
	}

	public void displayProjectPortal(Scanner sc, FilterSettings filterSettings) throws Exception {
		List<BTOProject> projects =  ProjectDB.getDB();
        List<BTOProject> filteredProjects = FilterUtil.filterBySettings(projects, filterSettings);

		if (filteredProjects.isEmpty()) {
			System.out.println("No projects available based on\n the current filter settings.");
			System.out.println("-----------------------------------------");
		} else {
			do{
				System.out.println("Available Projects:");
				System.out.println("Choose a project to apply for\n or enquire about:");
				System.out.println("-----------------------------------------");
				for (int i = 0; i < filteredProjects.size(); i++) {
					System.out.println((i + 1) + ". " + filteredProjects.get(i).toString());
				}
				System.out.println("0. Return to menu");
				System.out.println("-----------------------------------------");
				System.out.println("Enter your choice: ");
				int projectChoice = sc.nextInt() - 1;
				sc.nextLine(); // Consume newline
				if (projectChoice >= 0 && projectChoice < filteredProjects.size()) {
					displayProjectAction(sc, filteredProjects.get(projectChoice));
				}
				else if (projectChoice == -1) {
					System.out.println("Returning to menu."); return;
				} 
				else {
					System.out.println("Invalid project choice. Try again!");
				}
			}while(true);
		}
	}

	
    public void displayProjectAction(Scanner sc, BTOProject selectedProject) throws Exception {
		do{
			System.out.println("You have selected: " + selectedProject.getProjectName());
			System.out.println("1. Apply for this project");
			System.out.println("2. Ask questions about this project");
			System.out.println("3. Back to project list");
			System.out.print("Enter your choice: ");

			int actionChoice = sc.nextInt();
			sc.nextLine(); // Consume newline
			switch (actionChoice) {
				case 1 -> app.applyForProject(sc, selectedProject);
				case 2 -> app.addEnquiry(sc, selectedProject);
				case 3 -> {System.out.println("Back to project list..."); return;}
				default -> System.out.println("Invalid choice. Try again!");
			}
		}while(true);
    }

	public void displayApplicationStatus(){
		Applicant applicant = app.retreiveApplicant();
        List<ProjectApplication> applicationList = applicant.getMyApplication();

        if (applicationList.isEmpty()) {
            System.out.println("You have not applied for any projects yet.");
            return;
        }
        System.out.println("Your Applications:");
        for (ProjectApplication application : applicationList) {
            if (application.getStatus() != ProjectAppStat.UNSUCCESSFUL && application.getStatus() != ProjectAppStat.WITHDRAWN) {
                System.out.println("<Current Application>");
            } 
            System.out.println(application.toString());
            System.out.println("-----------------------------------------");
        }
	}

	public void viewEnquiryActionMenu(Scanner sc, Enquiry selectedEnquiry) {
        Boolean isReplied = selectedEnquiry.getReplierUserID() != null;
        if (isReplied) {
            System.out.println("This enquiry has already been replied to.");
            System.out.println("Reply Content: " + selectedEnquiry.getReplyContent());
            System.out.println("Replied by: " + selectedEnquiry.getReplierUserID() + " on " + selectedEnquiry.getReplierTimestamp());
            return;
        } else {
            System.out.println("This enquiry has not been replied to yet.");
        }
        System.out.println("You can: ");
        System.out.println("1. Edit this enquiry");
        System.out.println("2. Delete this enquiry");
        System.out.print("Enter your choice: ");

        int actionChoice = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (actionChoice) {
            case 1 -> app.editEnquiry(sc, selectedEnquiry);
            case 2 -> app.deleteEnquiry(selectedEnquiry.getId());
            default -> System.out.println("Invalid choice. Returning to menu.");
        }
    }

}
