package models.projects;

import java.util.Date;


public class Application<StatType>{
    protected String userID;
    protected String projectName;
    protected StatType status;
    protected Date applicationDate;

    public Application(String user, String projectName, StatType status, Date applicationDate) {
        this.userID = user;
        this.projectName = projectName;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    public String getUser() {
        return userID;
    }

    public String getProjectName() {
        return projectName;
    }

    public StatType getStatus() {
        return status;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setStatus(StatType newStatus) {
        this.status = newStatus;
    }

    @Override
    public String toString() {
        return String.format("Application[User=%s, Project=%s, Status=%s, ApplicationDate=%s]",
                userID, projectName, status, applicationDate);
    }
}
