package models.projects;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A generic class representing an application to a project.
 * This class is parameterized by a status enum type (StatType),
 * allowing it to be reused for various application types like project and officer applications.
 *
 * @param <StatType> the enum type used for representing application status
 */
public class Application<StatType> {

    /**
     * ID of the user who submitted the application.
     */
    protected String userID;

    /**
     * Name of the project being applied to.
     */
    protected String projectName;

    /**
     * Current status of the application.
     */
    protected StatType status;

    /**
     * Date the application was submitted.
     */
    protected Date applicationDate;

    /**
     * Constructs an Application instance.
     *
     * @param user            the ID of the user
     * @param projectName     the name of the project
     * @param status          the application status
     * @param applicationDate the submission date of the application
     */
    public Application(String user, String projectName, StatType status, Date applicationDate) {
        this.userID = user;
        this.projectName = projectName;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    /**
     * Gets the ID of the user who submitted the application.
     *
     * @return the user ID
     */
    public String getUser() {
        return userID;
    }

    /**
     * Gets the name of the project this application is for.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the current status of the application.
     *
     * @return the application status
     */
    public StatType getStatus() {
        return status;
    }

    /**
     * Gets the date the application was submitted.
     *
     * @return the application date
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * Updates the status of the application.
     *
     * @param newStatus the new status to set
     */
    public void setStatus(StatType newStatus) {
        this.status = newStatus;
    }

    /**
     * Returns a string representation of the application.
     *
     * @return a formatted string with application details
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
