package view;

import java.util.Scanner;
import models.projects.BTOProject;
import services.controller.OfficerController;

public class OfficerView extends ApplicantView {

    OfficerController app = new OfficerController();

    @Override
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
				case 3 -> {System.out.println("Back to project list..."); break;}
				default -> System.out.println("Invalid choice. Try again!");
			}
		}while(true);
    }
}
