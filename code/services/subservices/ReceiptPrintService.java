package services.subservices;

import database.dataclass.projects.ProjectAppDB;
import database.dataclass.projects.ProjectDB;
import java.util.List;
import models.enums.ProjectAppStat;
import models.projects.BTOProject;
import models.projects.ProjectApplication;
import services.interfaces.IReceiptPrintService;


public class ReceiptPrintService implements IReceiptPrintService {

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
        sb.append("\n===== BOOKING RECEIPT =====\n")
                .append("Applicant ID: ").append(applicantId).append("\n")
                .append("Project:      ").append(proj.getProjectName()).append("\n")
                .append("Flat Type:    ").append(flatType).append("\n")
                .append("Booking Date: ").append(date).append("\n")
                .append("Status:       ").append(app.getStatus()).append("\n")
                .append("===========================\n");

        return sb.toString();
    }
}
