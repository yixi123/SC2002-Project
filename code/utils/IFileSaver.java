package utils;

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
}
