package models.projects;

import java.text.SimpleDateFormat;
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