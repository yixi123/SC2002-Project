package services.interfaces;

import java.util.Map;
import java.util.Scanner;

import models.projects.BTOProject;
import models.projects.ProjectApplication;

import models.users.Applicant;

/**
 * Interface for generating and filtering BTO project reports.
 * Reports may include application outcomes and allocation data.
 */
public interface IReportPrintService {

    /**
     * Generates a complete report for a specified BTO project.
     *
     * @param sc Scanner for user input
     * @param project The BTO project to report on
     * @return A formatted report as a String
     */
    String printReport(Scanner sc, BTOProject project);

    /**
     * Applies filters to a raw map of applicants and their applications
     * before report generation.
     *
     * @param sc Scanner for user input
     * @param applicantsAndApplication A map of applicants and their applications
     * @return A filtered version of the map based on user-defined criteria
     */
    Map<Applicant, ProjectApplication> filterReportContent(
            Scanner sc,
            Map<Applicant, ProjectApplication> applicantsAndApplication
    );
}

