package services.interfaces;

import java.util.Scanner;
import models.enums.FlatType;
import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;
import models.users.Applicant;

public interface IProjectApplicationService {
  public void applyForProject(Scanner sc, Applicant user, String selectedProjectName);
  public void addApplication(String projectName, String userID, FlatType flatType);
  public void withdrawApplication(ProjectApplication application);
  public ProjectAppStat getApplicationStatus(String userID);
  public void updateApplicationStatus(String user, String project, ProjectAppStat newStatus);
  public void bookApplication(String projectName, String userID);
}
