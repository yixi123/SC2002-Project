package services.interfaces;

import java.util.Map;
import java.util.Scanner;
import models.projects.ProjectApplication;

import models.users.Applicant;

public interface IReportPrintService {
    public void printReport(Scanner sc, Map<Applicant, ProjectApplication> applicantsAndApplication);

    public Map<Applicant, ProjectApplication> filterReportContent(Scanner sc, Map<Applicant, ProjectApplication> applicantsAndApplication);
}
