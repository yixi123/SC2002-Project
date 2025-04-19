package services.interfaces;

import java.util.List;
import java.util.Scanner;
import models.enums.OfficerAppStat;
import models.projects.OfficerApplication;
import models.users.HDBOfficer;

public interface IOfficerApplicationService {
  public void applyForOfficer(Scanner sc, HDBOfficer user, String selectedProjectName);
  public void addApplication(String projectName, String userID);
  public OfficerAppStat viewApplicationStatus(String userID);
  public void updateApplicationStatus(OfficerApplication application, OfficerAppStat newStatus);
  public List<OfficerApplication> getProjectApplications(String projectName);
  public OfficerApplication chooseFromApplicationList(Scanner sc, List<OfficerApplication> applications);
  public List<OfficerApplication> getApplicationsByUser(String nric);
}
