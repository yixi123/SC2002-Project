package models.users;

import java.util.List;
import models.enums.MaritalStatus;
import models.enums.ProjectAppStat;
import models.projects.ProjectApplication;

public class Applicant extends User {
    protected List<ProjectApplication> myApplications;

    public Applicant(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
    }
    
    public List<ProjectApplication> getMyApplication() {
        return myApplications;
    }

    public void setMyApplication(List<ProjectApplication> project){
        myApplications = project;
    }

    public ProjectApplication getActiveApplication(){
        for (ProjectApplication application : myApplications) {
            if (application.getStatus() != ProjectAppStat.WITHDRAWN && application.getStatus() != ProjectAppStat.UNSUCCESSFUL) {
                return application;
            }
        }
        return null;
    }
}
