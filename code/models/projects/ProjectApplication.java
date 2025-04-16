package models.projects;

import java.util.Date;

public class ProjectApplication extends Application {
    private String flatType;

    public ProjectApplication(String userID, String projectName, String status, Date applicationDate, String flatType) {
        super(userID, projectName, status, applicationDate);
        this.flatType = flatType;
    }

    public String getFlatType() {
        return flatType;
    }

    @Override
    public String toString() {
        return String.format("ProjectApplication[User=%s, Project=%s, Status=%s, flatType=%s, ApplicationDate=%s]",
                userID, projectName, status, flatType, applicationDate);
    }
}