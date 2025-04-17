package services.interfaces;

import java.util.Date;
import java.util.Scanner;
import models.enums.FlatType;

import models.enums.ProjectAppStat;
import models.users.Applicant;

public interface IProjectApplicationService {
  public void applyForProject(Scanner sc, Applicant user, String selectedProjectName);
  public void addApplication(String projectName, String userID, FlatType flatType);
  public void withdrawApplication(String projectName, String userID);
  public ProjectAppStat viewApplicationStatus(String userID);
  public void updateApplicationStatus(String user, String project, ProjectAppStat newStatus);
  public void bookApplication(String projectName, String userID);
}
