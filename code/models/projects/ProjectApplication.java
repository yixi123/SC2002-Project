package models.projects;

import java.text.SimpleDateFormat;
import java.util.Date;
import models.enums.FlatType;
import models.enums.ProjectAppStat;

/**
 * Represents an application submitted by a user to apply for a BTO flat.
 * Extends the generic {@code Application} class with {@code ProjectAppStat} as the status type.
 */
public class ProjectApplication extends Application<ProjectAppStat> {

    /**
     * The flat type requested by the applicant (e.g., 2-room, 3-room).
     */
    private FlatType flatType;

    /**
     * Constructs a ProjectApplication instance.
     *
     * @param userID           the ID of the applicant
     * @param projectName      the name of the BTO project
     * @param status           the application status
     * @param applicationDate  the submission date of the application
     * @param flatType         the type of flat being applied for
     */
    public ProjectApplication(String userID, String projectName, ProjectAppStat status, Date applicationDate, FlatType flatType) {
        super(userID, projectName, status, applicationDate);
        this.flatType = flatType;
    }

    /**
     * Gets the type of flat requested in this application.
     *
     * @return the flat type
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Returns a formatted string representation of this project application.
     *
     * @return a string summarizing the application details
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("userID='").append(userID).append('\'')
                .append("\nprojectName='").append(projectName).append('\'')
                .append("\nflatType=").append(flatType)
                .append("\nstatus=").append(status)
                .append("\napplicationDate=").append(dateFormatter.format(applicationDate));
        return sb.toString();
    }
}