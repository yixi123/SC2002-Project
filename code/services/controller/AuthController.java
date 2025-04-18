package services.controller;

import database.dataclass.users.ApplicantDB;
import database.dataclass.users.ManagerDB;
import database.dataclass.users.OfficerDB;
import exception.AuthException;
import java.util.*;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;
import models.users.User;

public class AuthController {
    private static int attempt = 1;
    private static final int MAX_ATTEMPTS = 3;
    
    private static User currentUser = null;

    public User login(Scanner sc) throws AuthException{
        System.out.println("\t Login Page \t\t");
        System.out.println("-----------------------------------------");
        System.out.print("Enter NRIC: ");
        String nric = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.println("-----------------------------------------");

        try{
            currentUser = authenticate(nric, password);
            System.out.println("Login successful! Welcome " + currentUser.getName() + ".");
            System.out.println("-----------------------------------------");
            return currentUser;
        }
        catch(AuthException e){
            System.out.println(e.getMessage() + "\nPlease try again.");
            System.out.println("-----------------------------------------");
            attempt += 1;
            if(attempt <= MAX_ATTEMPTS){
                System.out.printf("Attempt (%d / 3)", attempt);
                System.out.println("-----------------------------------------");
                return login(sc);
            }
            else{
                attempt = 1;
                System.out.println("You have reached your attempt limits!");
                throw new AuthException("Please try again later");
            }
        }
        
        
    }

    public User authenticate(String nric, String password) throws AuthException {
        List<? extends User> users= ApplicantDB.getDB();
        for (User applicant: users) {
            if (applicant.getNric().equals(nric) && applicant.getPassword().equals(password)) {
                return applicant;
            }
        }

        users = OfficerDB.getDB();
        for (User officer : users) {
            if (officer.getNric().equals(nric) && officer.getPassword().equals(password)) {
                return officer;
            }
        }

        users = ManagerDB.getDB();
        for (User manager : users) {
            if (manager.getNric().equals(nric) && manager.getPassword().equals(password)) {
                return manager;
            }
        }
        throw new AuthException("Invalid NRIC or password.");
    }

    public void changePassword(User user, String oldPassword, String newPassword) {
        try{
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                if (user instanceof Applicant) {
                    ApplicantDB.updateUser((Applicant) user);
                } else if (user instanceof HDBOfficer) {
                    OfficerDB.updateUser((HDBOfficer) user);
                } else if (user instanceof HDBManager) {
                    ManagerDB.updateUser((HDBManager) user);
                }
            } else {
                throw new IllegalArgumentException("Invalid password.");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public static User getCurrentUser(){
        return currentUser;
    }

}
