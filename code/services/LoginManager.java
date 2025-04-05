package services;

import java.util.*;
import models.*;
import utils.FileLoader;

public class LoginManager {
    private static List<Applicant> applicants = FileLoader.loadApplicants("code/database/ApplicantList.csv");
    private static List<HDBOfficer> officers = FileLoader.loadOfficers("code/database/OfficerList.csv");
    private static List<HDBManager> managers = FileLoader.loadManagers("code/database/ManagerList.csv");

    public User authenticate(String nric, String password) {
        for (Applicant applicant : applicants) {
            if (applicant.getNric().equals(nric) && applicant.getPassword().equals(password)) {
                return applicant;
            }
        }
        for (HDBOfficer officer : officers) {
            if (officer.getNric().equals(nric) && officer.getPassword().equals(password)) {
                return officer;
            }
        }
        for (HDBManager manager : managers) {
            if (manager.getNric().equals(nric) && manager.getPassword().equals(password)) {
                return manager;
            }
        }
        throw new IllegalArgumentException("Invalid NRIC or password.");
    }
}
