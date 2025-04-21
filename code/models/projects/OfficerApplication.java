package models.projects;

import java.text.SimpleDateFormat;
import java.util.Date;
import models.enums.OfficerAppStat;

/**
 * Represents an application submitted by a user to become an officer for a BTO project.
 * This class extends the generic {@code Application} class with {@code OfficerAppStat} as the status type.
 */
public class OfficerApplication extends Application<OfficerAppStat> {

    /**
     * Constructs an OfficerApplication instance.
     *
     * @param userID           the ID of the applicant
     * @param projectName      the name of the project
     * @param status           the status of the officer application
     * @param applicationDate  the date the application was submitted
     */
    public OfficerApplication(String userID, String projectName, OfficerAppStat status, Date applicationDate) {
        super(userID, projectName, status, applicationDate);
    }

    /**
     * Returns a formatted string representation of this officer application.
     *
     * @return a string summarizing the application details
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("userID='").append(userID).append('\'')
                .append("\nprojectName='").append(projectName).append('\'')
                .append("\nstatus=").append(status)
                .append("\napplicationDate=").append(dateFormatter.format(applicationDate));
        return sb.toString();
    }
}