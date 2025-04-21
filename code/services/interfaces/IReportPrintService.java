package services.interfaces;

import java.util.Map;
import java.util.Scanner;

import models.projects.BTOProject;
import models.projects.ProjectApplication;

import models.users.Applicant;

public interface IReportPrintService {
    public String printReport(Scanner sc, BTOProject project);

    public Map<Applicant, ProjectApplication> filterReportContent(Scanner sc, Map<Applicant, ProjectApplication> applicantsAndApplication);
}
