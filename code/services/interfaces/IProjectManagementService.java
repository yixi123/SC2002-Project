package services.interfaces;

import java.util.List;
import java.util.Scanner;

import models.projects.BTOProject;

/**
 * Interface for BTO project management.
 * Includes operations to create, edit, and delete BTO projects.
 */
public interface IProjectManagementService {

  /**
   * Deletes a specified BTO project from the system.
   *
   * @param sc Scanner for user input
   * @param project The project to delete
   */
  void deleteProject(Scanner sc, BTOProject project);

  /**
   * Creates a new BTO project under the specified manager's responsibility.
   *
   * @param sc Scanner for user input
   * @param managerId The NRIC of the manager creating the project
   * @param projectList The manager’s list of managed projects
   */
  void createProject(Scanner sc, String managerId, List<BTOProject> projectList);

  /**
   * Opens an editing session for the specified project.
   *
   * @param sc Scanner for user input
   * @param project The project to edit
   */
  void editProject(Scanner sc, BTOProject project);
}



  // public void editProjectOpeningDate(BTOProject project);
  // public void editProjectClosingDate( BTOProject project);
  // public void editProjectVisibility(BTOProject project);
  // public void editProjectOfficerMaxSlot(BTOProject project);

