package models.projects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.enums.OfficerAppStat;

public class OfficerApplication extends Application<OfficerAppStat> {

    public OfficerApplication(String userID, String projectName, OfficerAppStat status, Date applicationDate) {
        super(userID, projectName, status, applicationDate);
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
