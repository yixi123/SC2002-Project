package services.interfaces;

import java.util.Scanner;

import models.enums.FlatType;
import models.enums.OfficerAppStat;
import models.enums.ProjectAppStat;
import models.users.Applicant;
import models.users.HDBOfficer;

public interface IOfficerApplicationService {
  public void applyForOfficer(Scanner sc, HDBOfficer user, String selectedProjectName);
  public void addApplication(String projectName, String userID);
  public OfficerAppStat viewApplicationStatus(String userID);
  public void updateApplicationStatus(String user, String projectName, OfficerAppStat newStatus);
}
