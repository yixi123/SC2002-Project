package services.controller;

import java.util.*;
import models.*;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;
import models.users.User;
import utils.FileLoader;
import utils.FileSaver;

public class AuthController {
    private static List<Applicant> applicants = FileLoader.loadApplicants("code/database/ApplicantList.csv");
    private static List<HDBOfficer> officers = FileLoader.loadOfficers("code/database/OfficerList.csv");
    private static List<HDBManager> managers = FileLoader.loadManagers("code/database/ManagerList.csv");

    private static User currentUser;

    public User authenticate(String nric, String password)  {
        try{
            for (Applicant applicant : applicants) {
                if (applicant.getNric().equals(nric) && applicant.getPassword().equals(password)) {
                    currentUser = applicant; 
                    return applicant;
                }
            }
            for (HDBOfficer officer : officers) {
                if (officer.getNric().equals(nric) && officer.getPassword().equals(password)) {
                    currentUser = officer;
                    return officer;
                }
            }
            for (HDBManager manager : managers) {
                if (manager.getNric().equals(nric) && manager.getPassword().equals(password)) {
                    currentUser = manager;
                    return manager;
                }
            }
            throw new IllegalArgumentException("Invalid NRIC or password.");
        }
        catch(IllegalArgumentException e){
            return null;
        }
    }

    public void updatePassword(User user, String oldPassword, String newPassword) {
        try{
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                if (user instanceof Applicant) {
                    FileSaver.saveApplicants(applicants);
                } else if (user instanceof HDBOfficer) {
                    FileSaver.saveOfficers(officers);
                } else if (user instanceof HDBManager) {
                    FileSaver.saveManagers(managers);
                }
            } else {
                throw new IllegalArgumentException("Invalid password.");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User user){
        currentUser = user;
    }
}
