package models.projects;

import java.util.Date;
import models.enums.ProjectAppStat;

public class Application {
    protected String userID;
    protected String projectName;
    protected ProjectAppStat status;
    protected Date applicationDate;

    public Application(String user, String projectName, ProjectAppStat status, Date applicationDate) {
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

    public ProjectAppStat getStatus() {
        return status;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setStatus(ProjectAppStat status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Application[User=%s, Project=%s, Status=%s, ApplicationDate=%s]",
                userID, projectName, status, applicationDate);
    }
}
