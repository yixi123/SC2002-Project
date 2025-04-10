package models;

import java.util.Date;

public class ProjectApplication extends Application {
    private String flatType;

    public ProjectApplication(String user, String projectName, String status, Date applicationDate, String flatType) {
        super(user, projectName, status, applicationDate);
        this.flatType = flatType;
    }

    public String getFlatType() {
        return flatType;
    }

    @Override
    public String toString() {
        return String.format("Application[User=%s, Project=%s, Status=%s, flatType=%s, ApplicationDate=%s]",
                user, projectName, status, flatType, applicationDate);
    }
}