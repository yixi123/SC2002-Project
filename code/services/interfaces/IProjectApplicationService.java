package services.interfaces;

import java.util.List;
import java.util.Scanner;
import models.enums.FlatType;
import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;
import models.users.Applicant;

public interface IProjectApplicationService {
  public void applyForProject(Scanner sc, Applicant user, String selectedProjectName);
  public void addApplication(String projectName, String userID, FlatType flatType);
  public void withdrawApplication(ProjectApplication application);
  public void updateApplicationStatus(ProjectApplication application, ProjectAppStat newStatus);
  public int bookApplication(String projectName, String userID);
  public List<ProjectApplication> getProjectApplications(String projectName);
  public ProjectApplication chooseFromApplicationList(Scanner sc, List<ProjectApplication> applications);
  public ProjectApplication getApplicationByUserAndProject(String userId, String projectName);
}
