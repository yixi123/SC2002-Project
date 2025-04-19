package services.interfaces;

import java.util.List;
import java.util.Scanner;

import models.projects.BTOProject;

public interface IProjectManagementService {
  public void deleteProject(Scanner sc, BTOProject project);
  public void createProject(Scanner sc, String managerId, List<String> projectIDList);
  public void editProject(Scanner sc, BTOProject project);


  // public void editProjectOpeningDate(BTOProject project);
  // public void editProjectClosingDate( BTOProject project);
  // public void editProjectVisibility(BTOProject project);
  // public void editProjectOfficerMaxSlot(BTOProject project);
}
