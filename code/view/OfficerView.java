package view;

import java.util.Scanner;

import database.dataclass.projects.ProjectDB;
import models.projects.BTOProject;
import services.controller.OfficerController;

public class OfficerView extends ApplicantView {

    private static OfficerController app = new OfficerController();

	@Override
	public void enterMainMenu(Scanner sc) {
		while (true) {
			System.out.println("\n-------------------------------------------");
			System.out.println("                HDB Officer                ");
			System.out.println("-------------------------------------------");
			System.out.println("1) Officer functions");
			System.out.println("2) Applicant functions");
			System.out.println("3) Logout");
			System.out.println("-------------------------------------------");
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
				case 3 -> {
					System.out.println("Logging out...");
					return;    // exit start()
					}
				default -> System.out.println("Invalid choice. Try again.");
			}
		}
	}

	private void officerMenu(Scanner sc) {
		while (true) {
			System.out.println("\n-------------------------------------------");
			System.out.println("             Officer Portal                ");
			System.out.println("-------------------------------------------");
			System.out.println("1. View Project List");
			System.out.println("2. View Handled Project");
			System.out.println("3. View Successful Applicants");
			System.out.println("4. Book Applicant Flat");
			System.out.println("5. Generate Receipt");
			System.out.println("6. Register as Officer");
			System.out.println("7. View Enquiries");
			System.out.println("8. Reply to Enquiry");
			System.out.println("9. Update Applicant Status");
			System.out.println("0. Back");
			System.out.println("-------------------------------------------");
			System.out.print("Enter your Choice: ");
			int opt = sc.nextInt(); sc.nextLine();

			switch (opt) {
				case 1 -> app.viewProjectList();
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
				case 6 -> {
					System.out.print("Project Name to register: ");
					String pid6 = sc.nextLine();
					BTOProject project = ProjectDB.getProjectByName(pid6);
					app.registerAsOfficer(project);
				}
				case 7 -> app.viewEnquiries();
				case 8 -> app.replyEnquiries(sc);
				case 9 -> {
					System.out.print("Applicant ID: ");
					String applicantId9 = sc.nextLine();
					System.out.print("Confirm Accept Booking (Y/N): ");
					String newStatus9 = sc.nextLine().toUpperCase();
					app.updateApplicantAppStat(applicantId9, newStatus9);
				}
				case 0 -> {
						return;  // back to start()
				}
				default -> System.out.println("Invalid.");
			}
		}
  	}

  	private void applicantMenu(Scanner sc) throws Exception {
		int choice;
			
		do{
			System.out.println("\n-------------------------------------------");
			System.out.println("       Officer as Applicant Portal         ");
			System.out.println("-------------------------------------------");
			System.out.println("1. Enter Project Portal");
			System.out.println("2. Adjust Filter Settings");
			System.out.println("3. View Application Status");
			System.out.println("4. Withdraw Current Application");
			System.out.println("5. View My Enquiry");
			System.out.println("6. Change My Password");
			System.out.println("0. Back");
			System.out.println("-------------------------------------------");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 0 -> {
					System.out.println("Logging out...");
					System.out.println("-------------------------------------------");
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

	@Override
    public void displayProjectAction(Scanner sc, BTOProject selectedProject) throws Exception {
		do{
			System.out.println("You have selected: " + selectedProject.getProjectName());
			System.out.println("-------------------------------------------");
			System.out.println("1. Apply for this project");
			System.out.println("2. Ask questions about this project");
			System.out.println("3. Back to project list");
			System.out.println("-------------------------------------------");
			System.out.print("Enter your choice: ");

			int actionChoice = sc.nextInt();
			sc.nextLine(); // Consume newline
			System.out.println("\n-------------------------------------------");
			switch (actionChoice) {
				case 1 -> app.applyForProject(sc, selectedProject);
				case 2 -> app.addEnquiry(sc, selectedProject);
				case 3 -> {System.out.println("Back to project list..."); break;}
				default -> System.out.println("Invalid choice. Try again!");
			}
		}while(true);
    }
}
