
package utils;

import java.util.List;

import models.projects.BTOProject;
import models.projects.Enquiry;
import models.projects.OfficerApplication;
import models.projects.ProjectApplication;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;

public interface IFileLoader{
  public List<Applicant> loadApplicants();

    public List<HDBOfficer> loadOfficers();

    public List<HDBManager> loadManagers();

    public List<BTOProject> loadProjects();

    public List<Enquiry> loadEnquiries();

    public List<ProjectApplication> loadProjectApplications();

    public List<OfficerApplication> loadOfficerApplications();
}