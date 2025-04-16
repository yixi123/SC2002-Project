package models.users;

import java.util.ArrayList;
import java.util.List;
import models.enums.MaritalStatus;

public class HDBManager extends User {
    private List<String> managedProjectsID;

    public HDBManager(String name, String nric, String password, int age, MaritalStatus maritalStatus) {
        super(name, nric, password, age, maritalStatus);
        managedProjectsID = new ArrayList<>();
    }

    public List<String> getManagedProjectsID(){
        return managedProjectsID;
    }

    public void addManagedProjectsID(String newProjectID){
        managedProjectsID.add(newProjectID);
    }

    public void setManagedProjectsList(List<String> ProjectIDList){
        managedProjectsID = ProjectIDList;
    }
}
