package models.projects;

import java.util.Date;
import models.enums.FlatType;
import models.enums.ProjectAppStat;

public class ProjectApplication extends Application<ProjectAppStat> {
    private FlatType flatType;

    public ProjectApplication(String userID, String projectName, ProjectAppStat status, Date applicationDate, FlatType flatType) {
        super(userID, projectName, status, applicationDate);
        this.flatType = flatType;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    @Override
    public String toString() {
        return String.format("ProjectApplication[User=%s, Project=%s, Status=%s, flatType=%s, ApplicationDate=%s]",
                userID, projectName, status, flatType, applicationDate);
    }
}