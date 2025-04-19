package models.users;

import java.util.ArrayList;
import java.util.List;
import models.enums.MaritalStatus;
import models.projects.BTOProject;

public class HDBManager extends User {
    private List<BTOProject> managedProjects;

    public HDBManager(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        managedProjects = new ArrayList<>();
    }

    public List<BTOProject> getManagedProjectsList(){
        return managedProjects;
    }

    public void setManagedProjectsList(List<BTOProject> ProjectList){
        managedProjects = ProjectList;
    }
}
