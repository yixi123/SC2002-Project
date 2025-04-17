package services.interfaces;

import java.util.List;
import java.util.Scanner;
import models.projects.BTOProject;

public interface IProjectViewService {
    BTOProject chooseFromProjectList(Scanner sc, List<BTOProject> filteredProjects);

    void adjustFilterSettings();
  
}
