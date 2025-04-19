package view;

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

public class OfficerView extends ApplicantView {

    private static OfficerController app = new OfficerController();

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
			int choice = sc.nextInt(); sc.nextLine();

			switch (choice) {
				case 1 -> officerMenu(sc);
				case 2 -> {
					try {
						applicantMenu(sc);
					}catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}
				}
				case 3 -> app.changeMyPassword(sc);
				case 4 -> {
					System.out.println("Logging out...");
					return;    // exit start()
					}
				default -> System.out.println("Invalid choice. Try again.");
			}
		}
	}

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
			int opt = sc.nextInt(); sc.nextLine();

			switch (opt) {
				case 1 -> app.enterOfficerProjectPortal(sc);
				case 2 -> app.viewHandledProject();
				case 3 -> app.viewSuccessfulApplicantsList();
				case 4 -> {
					System.out.print("Applicant NRIC: ");
					String id4 = sc.nextLine();
					app.handleApplicantsSuccessfulApp(id4);
				}
				case 5 -> {
					System.out.print("Applicant NRIC: ");
					String id5 = sc.nextLine();
					app.generateReceipt(id5);
				}
				case 6 -> app.viewEnquiries(sc);
				case 7 -> app.viewOfficerApplicationStatus();
				case 0 -> {
						return;  // back to start()
				}
				default -> System.out.println("Invalid.");
			}
		}
  	}

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

        int actionChoice = sc.nextInt();
        sc.nextLine(); // Consume newline
		System.out.println(ViewFormatter.breakLine());
        switch (actionChoice) {
            case 1 -> app.replyEnquiry(sc, selectedEnquiry);
            case 2 -> System.out.println("Returning to menu.");
            default -> System.out.println("Invalid choice. Returning to menu.");
        }
    }

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
				System.out.println("Choose a project to register as officer for\n or enquire about:");
				System.out.println(ViewFormatter.breakLine());
				for (int i = 0; i < filteredProjects.size(); i++) {
					System.out.println((i + 1) + ". " + filteredProjects.get(i).toString());
				}
				System.out.println("0. Return to menu");
				System.out.println(ViewFormatter.breakLine());
				System.out.println("Enter your choice: ");
				int projectChoice = sc.nextInt() - 1;
				sc.nextLine(); // Consume newline
				System.out.println(ViewFormatter.breakLine());
				if (projectChoice >= 0 && projectChoice < filteredProjects.size()) {
					  displayOfficerProjectPortal(sc, filteredProjects.get(projectChoice));
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

  	public void displayOfficerProjectPortal(Scanner sc, BTOProject selectedProject) throws Exception {
		do{
			System.out.println("You have selected: " + selectedProject.getProjectName());
			System.out.println(ViewFormatter.breakLine());
			System.out.println("1. Register as officer for this project");
			System.out.println("2. Back to project list");
			System.out.println(ViewFormatter.breakLine());
			System.out.print("Enter your choice: ");
			int actionChoice = sc.nextInt();
			sc.nextLine(); // Consume newline
			System.out.println(ViewFormatter.breakLine());
			switch (actionChoice) {
				case 1 -> app.registerAsOfficer(selectedProject);
				case 2 -> {
					System.out.println("Back to project list...");
					System.out.println(ViewFormatter.breakLine());		 
					return;
				}
				default -> {
					System.out.println("Invalid choice. Try again!");
					System.out.println(ViewFormatter.breakLine());
				}
			}
		}while(true);
  	}

  	private void applicantMenu(Scanner sc) throws Exception {
		int choice;
			
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
			choice = sc.nextInt();
			sc.nextLine();

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
				default -> {
					System.out.println("Invalid choice. Please try again.");
					System.out.println(ViewFormatter.breakLine());
				}
			}
		} while (choice != 0 && choice != 6);
  	}

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

			int actionChoice = sc.nextInt();
			sc.nextLine(); // Consume newline
			System.out.println(ViewFormatter.breakLine());
			switch (actionChoice) {
				case 1 -> app.applyForProject(sc, selectedProject);
				case 2 -> app.addEnquiry(sc, selectedProject);
				case 3 -> {
					System.out.println("Back to project list...");
					System.out.println(ViewFormatter.breakLine());
					return;
				}
				default -> {
					System.out.println("Invalid choice. Try again!");
					System.out.println(ViewFormatter.breakLine());
				}
			}
		}while(true);
    }

	public void displayHandledProject(){
		BTOProject project = app.retrieveOfficer().getActiveProject();

		if (project == null) {
			System.out.println("No project assigned.");
			System.out.println(ViewFormatter.breakLine());
		} else {
			System.out.println(project);
		}
	}

	public void displayHandledProjectSuccessfulApplicants(){
		BTOProject project = app.retrieveOfficer().getActiveProject();
		if (project == null) {
			System.out.println("No Project Assigned!");
			System.out.println(ViewFormatter.breakLine());
			return;
		}
		List<ProjectApplication> apps = ProjectAppDB.getApplicationsByProject(project.getProjectName());
		if (apps == null || apps.isEmpty()) {
			System.out.println("No applications found\n for project " + project.getProjectName());
			System.out.println(ViewFormatter.breakLine());
			return;
		}

		apps.stream()
			.filter(a -> a.getStatus() == ProjectAppStat.SUCCESSFUL)
			.forEach(System.out::println);
		
		System.out.println(ViewFormatter.breakLine());
	}

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
