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

public interface IFileSaver {
    public void saveApplicants(List<Applicant> applicants);

    public void saveOfficers(List<HDBOfficer> officers);

    public void saveManagers(List<HDBManager> managers);

    public void saveProjects(List<BTOProject> projects);

    public void saveEnquiries(List<Enquiry> enquiries);

    public void saveProjectApplications(List<ProjectApplication> applications);

    public void saveOfficerApplications(List<OfficerApplication> applications);

    public static void writeStringToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            System.out.println("File written successfully to: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
