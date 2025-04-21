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
 * The IFileLoader interface defines methods for loading data from files into various models.
 */
public interface IFileLoader {

    /**
     * Loads all applicants from a file.
     *
     * @return A list of Applicant objects.
     */
    public List<Applicant> loadApplicants();

    /**
     * Loads all officers from a file.
     *
     * @return A list of HDBOfficer objects.
     */
    public List<HDBOfficer> loadOfficers();

    /**
     * Loads all managers from a file.
     *
     * @return A list of HDBManager objects.
     */
    public List<HDBManager> loadManagers();

    /**
     * Loads all projects from a file.
     *
     * @return A list of BTOProject objects.
     */
    public List<BTOProject> loadProjects();

    /**
     * Loads all enquiries from a file.
     *
     * @return A list of Enquiry objects.
     */
    public List<Enquiry> loadEnquiries();

    /**
     * Loads all project applications from a file.
     *
     * @return A list of ProjectApplication objects.
     */
    public List<ProjectApplication> loadProjectApplications();

    /**
     * Loads all officer applications from a file.
     *
     * @return A list of OfficerApplication objects.
     */
    public List<OfficerApplication> loadOfficerApplications();
}