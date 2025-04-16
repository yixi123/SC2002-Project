package models.projects;

import java.util.Date;
import models.enums.ProjectAppStat;

public class OfficerApplication extends Application {

    public OfficerApplication(String userID, String projectName, ProjectAppStat status, Date applicationDate) {
        super(userID, projectName, status, applicationDate);
    }

    @Override
    public String toString() {
        return String.format("OfficerApplication[User=%s, Project=%s, Status=%s, ApplicationDate=%s]",
                userID, projectName, status, applicationDate);
    }
}
