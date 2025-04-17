package services.subservices;

import java.util.List;
import java.util.Scanner;
import models.projects.BTOProject;
import services.interfaces.IProjectViewService;

public class ProjectViewService implements IProjectViewService {

  @Override
  public BTOProject chooseFromProjectList(Scanner sc, List<BTOProject> filteredProjects) {
    if (filteredProjects.isEmpty()) {
        System.out.println("No projects available based on the current filter settings.");
    } else {
        System.out.println("Available Projects:");
        System.out.println("------------------------------");
        for (int i = 0; i < filteredProjects.size(); i++) {
            System.out.println((i + 1) + ". " + filteredProjects.get(i).toString());
        }
        System.out.println("------------------------------");
        System.out.println("0. Return to menu");
        System.out.println("Please select a project by entering the corresponding number: ");
        int projectChoice = sc.nextInt() - 1;
        sc.nextLine(); // Consume newline
        if (projectChoice >= 0 && projectChoice < filteredProjects.size()) {

            BTOProject selectedProject = filteredProjects.get(projectChoice);
            return selectedProject;
        }
        else if (projectChoice == -1) {
            System.out.println("Returning to menu.");
        } 
        else {
            System.out.println("Invalid project choice. Returning to menu.");
        }
    }
    return null;
  }

  @Override
  public void adjustFilterSettings() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'adjustFilterSettings'");
  }
}
