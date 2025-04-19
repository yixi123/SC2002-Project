package models.projects;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy"); 
        StringBuilder sb = new StringBuilder();
        sb.append("userID='").append(userID).append('\'')
          .append("\nprojectName='").append(projectName).append('\'')
          .append("\nstatus=").append(status)
          .append("\napplicationDate=").append(dateFormatter.format(applicationDate));
        return sb.toString();
    }
}
