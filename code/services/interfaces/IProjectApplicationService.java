package services.interfaces;

import java.util.List;
import java.util.Scanner;
import models.enums.FlatType;
import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;
import models.users.Applicant;

/**
 * Interface for managing project applications by applicants.
 * Includes operations for applying, withdrawing, updating status,
 * and booking BTO project units.
 */
public interface IProjectApplicationService {

  /**
   * Launches the process for an applicant to apply for a BTO project.
   *
   * @param sc Scanner for user input
   * @param user The applicant submitting the application
   * @param selectedProjectName The name of the project being applied to
   */
  void applyForProject(Scanner sc, Applicant user, String selectedProjectName);

  /**
   * Adds a new project application to the system with a specified flat type.
   *
   * @param projectName The name of the project
   * @param userID The NRIC of the applicant
   * @param flatType The type of flat the applicant is applying for
   */
  void addApplication(String projectName, String userID, FlatType flatType);

  /**
   * Withdraws a submitted project application.
   *
   * @param application The application to be withdrawn
   */
  void withdrawApplication(ProjectApplication application);

  /**
   * Updates the status of a project application.
   *
   * @param application The application to update
   * @param newStatus The new application status to assign
   */
  void updateApplicationStatus(ProjectApplication application, ProjectAppStat newStatus);

  /**
   * Confirms booking of a flat under a specific project for a given applicant.
   *
   * @param projectName The name of the project
   * @param userID The NRIC of the applicant
   * @return 1 if booking successful, 0 otherwise
   */
  int bookApplication(String projectName, String userID);

  /**
   * Retrieves a list of all applications submitted for a given project.
   *
   * @param projectName The project name
   * @return List of project applications
   */
  List<ProjectApplication> getProjectApplications(String projectName);

  /**
   * Allows user to select one project application from a given list.
   *
   * @param sc Scanner for user input
   * @param applications The list of project applications
   * @return The selected project application
   */
  ProjectApplication chooseFromApplicationList(Scanner sc, List<ProjectApplication> applications);

  /**
   * Retrieves a specific application based on applicant ID and project name.
   *
   * @param userId The NRIC of the applicant
   * @param projectName The name of the project
   * @return The matching project application, or null if not found
   */
  ProjectApplication getApplicationByUserAndProject(String userId, String projectName);
}

