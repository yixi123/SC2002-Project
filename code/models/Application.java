package models;

import java.util.Date;

public class Application {
    protected String user;
    protected String projectName;
    protected String status;
    protected Date applicationDate;

    public Application(String user, String projectName, String status, Date applicationDate) {
        this.user = user;
        this.projectName = projectName;
        this.status = status;
        this.applicationDate = applicationDate;
    }

    public String getUser() {
        return user;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getStatus() {
        return status;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Application[User=%s, Project=%s, Status=%s, ApplicationDate=%s]",
                user, projectName, status, applicationDate);
    }
}
