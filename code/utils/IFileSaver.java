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
 * IFileSaver is an interface for saving application data to external storage (e.g., CSV files).
 * Defines methods for persisting data models including applicants, officers,
 * managers, projects, enquiries, and applications.
 */
public interface IFileSaver {

    /**
     * Saves a list of applicants to the default applicant file.
     *
     * @param applicants List of {@code Applicant} objects to be saved
     */
    public void saveApplicants(List<Applicant> applicants);

    /**
     * Saves a list of officers to the default officer file.
     *
     * @param officers List of {@code HDBOfficer} objects to be saved
     */
    public void saveOfficers(List<HDBOfficer> officers);

    /**
     * Saves a list of managers to the default manager file.
     *
     * @param managers List of {@code HDBManager} objects to be saved
     */
    public void saveManagers(List<HDBManager> managers);

    /**
     * Saves a list of BTO projects to the default project file.
     *
     * @param projects List of {@code BTOProject} objects to be saved
     */
    public void saveProjects(List<BTOProject> projects);

    /**
     * Saves a list of enquiries to the default enquiry file.
     *
     * @param enquiries List of {@code Enquiry} objects to be saved
     */
    public void saveEnquiries(List<Enquiry> enquiries);

    /**
     * Saves a list of project applications to the default project application file.
     *
     * @param applications List of {@code ProjectApplication} objects to be saved
     */
    public void saveProjectApplications(List<ProjectApplication> applications);

    /**
     * Saves a list of officer applications to the default officer application file.
     *
     * @param applications List of {@code OfficerApplication} objects to be saved
     */
    public void saveOfficerApplications(List<OfficerApplication> applications);

    /**
     * Writes a plain text string to the specified file path.
     * This is a utility method for quick file output.
     *
     * @param content  The content to write to the file
     * @param filePath The full file path where the content will be saved
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
