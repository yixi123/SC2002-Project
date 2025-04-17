package models.users;

import models.enums.MaritalStatus;
import models.projects.ProjectApplication;
import services.subservices.ProjectApplicationService;

public class Applicant extends User {
    protected ProjectApplication currentApplication;

    public Applicant(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
    }
    
    public ProjectApplication getCurrentApplication() {
        return currentApplication;
    }

    public void setCurrentApplication(ProjectApplication project){
        currentApplication = project;
    }
}
