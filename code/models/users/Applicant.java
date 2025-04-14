package models.users;

import java.util.Date;
import java.util.List;

import models.projects.ProjectApplication;
import services.ProjectApplicationService;

public class Applicant extends User {
    protected ProjectApplication appliedProject;

    public Applicant(String name, String nric, String password, int age, String maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        appliedProject = ProjectApplicationService.getApplicationByUser(nric);
    }
    
    public ProjectApplication getAppliedProject() {
        return appliedProject;
    }

    public void setAppliedProject(ProjectApplication project){
        appliedProject = project;
    }
}
