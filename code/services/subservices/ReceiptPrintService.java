package services.subservices;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.projects.ProjectDB;
import java.util.List;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.ProjectApplication;
import services.interfaces.IReceiptPrintService;

/**
 * Service class responsible for generating booking receipts
 * for applicants who have successfully secured a BTO flat.
 * A receipt is only generated for applications with status {@code BOOKED}.
 */
public class ReceiptPrintService implements IReceiptPrintService {

    /**
     * Generates a formatted receipt string for a given applicant
     * if they have a valid and most recent project application
     * with status {@code BOOKED}.
     *
     * @param applicantId The NRIC of the applicant
     * @return A booking receipt string if applicable, or {@code null} if:
     *         - No application found
     *         - Most recent application is not booked
     *         - Project cannot be retrieved
     */
    @Override
    public String printReceipt(String applicantId) {
        // most recent application
        List<ProjectApplication> listApplications = ProjectAppDB.getApplicationByUser(applicantId);
        if (listApplications == null || listApplications.isEmpty()) {
            return null;
        }
        ProjectApplication app = listApplications.get(listApplications.size() - 1);
        if (app == null
                || app.getStatus() != ProjectAppStat.BOOKED) {
            return null;
        }

        // project details
        BTOProject proj = ProjectDB.getProjectByName(app.getProjectName());
        String flatType = app.getFlatType().toString();
        String date    = app.getApplicationDate().toString();

        // receipt text
        StringBuilder sb = new StringBuilder();
        sb.append("\n==============BOOKING RECEIPT============\n")
                .append("Applicant ID: ").append(applicantId).append("\n")
                .append("Project:      ").append(proj.getProjectName()).append("\n")
                .append("Flat Type:    ").append(flatType).append("\n")
                .append("Booking Date: ").append(date).append("\n")
                .append("Status:       ").append(app.getStatus()).append("\n")
                .append("=========================================\n");

        return sb.toString();
    }
}
