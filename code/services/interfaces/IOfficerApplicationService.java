package services.interfaces;

import java.util.List;
import java.util.Scanner;
import models.enums.OfficerAppStat;
import models.projects.BTOProject;
import models.projects.OfficerApplication;
import models.users.HDBOfficer;

/**
 * Interface for managing officer applications including creation, status update,
 * and retrieval by project or user.
 */
public interface IOfficerApplicationService {

  /**
   * Adds a new officer application for a given project and user.
   * @param projectName Name of the project
   * @param userID NRIC of the user
   */
  void addApplication(String projectName, String userID);

  /**
   * Updates the status of an officer application.
   * @param application The officer application
   * @param newStatus The new status to assign
   */
  void updateApplicationStatus(OfficerApplication application, OfficerAppStat newStatus);

  /**
   * Retrieves all officer applications for a specific project.
   * @param projectName The project name
   * @return list of officer applications
   */
  List<OfficerApplication> getProjectApplications(String projectName);

  /**
   * Allows selection of an officer application from a list.
   * @param sc Scanner for user input
   * @param applications List of officer applications
   * @return selected application
   */
  OfficerApplication chooseFromApplicationList(Scanner sc, List<OfficerApplication> applications);

  /**
   * Retrieves all officer applications submitted by a specific user.
   * @param nric NRIC of the officer
   * @return list of officer applications
   */
  List<OfficerApplication> getApplicationsByUser(String nric);

  /**
   * Submits an officer application for the given officer and project.
   * @param officer The officer applying
   * @param project The project to apply to
   */
  void applyForOfficer(HDBOfficer officer, BTOProject project);
}
