
package utils;

import java.util.List;

import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;

/**
 * IFileLoader is an interface for loading application data from external sources (e.g., CSV files).
 * Defines methods to load data into in-memory models for applicants, officers, managers,
 * projects, enquiries, and applications.
 */
public interface IFileLoader{
  /**
   * Loads and returns a list of all registered applicants.
   *
   * @return List of {@code Applicant} objects
   */
  public List<Applicant> loadApplicants();

  /**
   * Loads and returns a list of all registered HDB officers.
   *
   * @return List of {@code HDBOfficer} objects
   */
  public List<HDBOfficer> loadOfficers();

  /**
   * Loads and returns a list of all HDB managers.
   *
   * @return List of {@code HDBManager} objects
   */
  public List<HDBManager> loadManagers();

  /**
   * Loads and returns a list of all BTO projects.
   *
   * @return List of {@code BTOProject} objects
   */
  public List<BTOProject> loadProjects();

  /**
   * Loads and returns a list of all public enquiries.
   *
   * @return List of {@code Enquiry} objects
   */
  public List<Enquiry> loadEnquiries();

  /**
   * Loads and returns all project applications submitted by applicants.
   *
   * @return List of {@code ProjectApplication} objects
   */
  public List<ProjectApplication> loadProjectApplications();

  /**
   * Loads and returns all officer applications submitted by HDB officers.
   *
   * @return List of {@code OfficerApplication} objects
   */
  public List<OfficerApplication> loadOfficerApplications();
}