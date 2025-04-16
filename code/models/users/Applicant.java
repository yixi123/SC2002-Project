package models.users;

import models.enums.MaritalStatus;
import models.projects.ProjectApplication;
import services.subservices.ProjectApplicationService;

public class Applicant extends User {
    protected ProjectApplication appliedProject;

    public Applicant(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
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
