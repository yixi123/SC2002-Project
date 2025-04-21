package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;

/**
 * The IFileSaver interface defines methods for saving various types of data to files.
 * It provides methods to save applicants, officers, managers, projects, enquiries, and applications.
 */
public interface IFileSaver {
    /**
     * Saves a list of applicants to a file.
     *
     * @param applicants The list of Applicant objects to save.
     */
    public void saveApplicants(List<Applicant> applicants);

    /**
     * Saves a list of officers to a file.
     *
     * @param officers The list of HDBOfficer objects to save.
     */
    public void saveOfficers(List<HDBOfficer> officers);

    /**
     * Saves a list of managers to a file.
     *
     * @param managers The list of HDBManager objects to save.
     */
    public void saveManagers(List<HDBManager> managers);

    /**
     * Saves a list of projects to a file.
     *
     * @param projects The list of BTOProject objects to save.
     */
    public void saveProjects(List<BTOProject> projects);

    /**
     * Saves a list of enquiries to a file.
     *
     * @param enquiries The list of Enquiry objects to save.
     */
    public void saveEnquiries(List<Enquiry> enquiries);

    /**
     * Saves a list of project applications to a file.
     *
     * @param applications The list of ProjectApplication objects to save.
     */
    public void saveProjectApplications(List<ProjectApplication> applications);

    /**
     * Saves a list of officer applications to a file.
     *
     * @param applications The list of OfficerApplication objects to save.
     */
    public void saveOfficerApplications(List<OfficerApplication> applications);

    /**
     * Writes a string content to a file.
     *
     * @param content The string content to write.
     * @param filePath The path to the file where the content will be written.
     */
    public static void writeStringToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("File written successfully to: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
