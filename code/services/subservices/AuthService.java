package services.subservices;

import java.util.List;
import java.util.Scanner;

import database.dataclass.users.ApplicantDB;
import database.dataclass.users.ManagerDB;
import database.dataclass.users.OfficerDB;
import exception.AuthException;
import models.enums.MaritalStatus;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;
import models.users.User;
import services.interfaces.IAuthService;
import view.ViewFormatter;

public class AuthService implements IAuthService {

    private static int attempt = 1;
    private static final int MAX_ATTEMPTS = 3;

    @Override
    public User login(Scanner sc) throws AuthException{
        System.out.println("                Login Page               ");
        System.out.println(ViewFormatter.breakLine());
        System.out.print("Enter NRIC: ");
        String nric = sc.nextLine();
        // Check if NRIC is valid
        if (!nric.matches("[STFG]\\d{7}[A-Z]")) {
            System.out.println("Invalid NRIC format. Please try again.");
            return login(sc);
        }
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        System.out.println(ViewFormatter.breakLine());

        try{
            User currentUser = authenticate(nric, password);
            System.out.println("Login successful! Welcome " + currentUser.getName() + ".");
            System.out.println(ViewFormatter.breakLine());
            return currentUser;
        }
        catch(AuthException e){
            System.out.println(e.getMessage() + "\nPlease try again.");
            System.out.println(ViewFormatter.breakLine());
            attempt += 1;
            if(attempt <= MAX_ATTEMPTS){
                System.out.printf("Attempt (%d / 3)", attempt);
                System.out.println(ViewFormatter.breakLine());
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

    public void logoutDisplay(){
        System.out.println("You have logged out successfully.");
        System.out.println(ViewFormatter.breakLine());
    }

    public void changePasswordPage(Scanner sc, User currentUser){
        String oldPassword;
        String newPassword; 

        System.out.println(ViewFormatter.breakLine());
        System.out.println("           Change Your Password          ");
        System.out.println(ViewFormatter.breakLine());
        System.out.println("Enter Your Old Password:");
        oldPassword = sc.nextLine();
        System.out.println("Enter Your New Password:");
        newPassword = sc.nextLine();
        try{
            changePassword(oldPassword, newPassword, currentUser);
        }
        catch(AuthException e){
            System.out.println(e.getMessage());
        }
        System.out.println(ViewFormatter.breakLine());
    }

    public void changePassword(String oldPassword, String newPassword, User currentUser) throws AuthException{
        if (currentUser.getPassword().equals(oldPassword)) {
            currentUser.setPassword(newPassword);
            if (currentUser instanceof Applicant) {
                ApplicantDB.updateUser((Applicant) currentUser);
            } else if (currentUser instanceof HDBOfficer) {
                OfficerDB.updateUser((HDBOfficer) currentUser);
            } else if (currentUser instanceof HDBManager) {
                ManagerDB.updateUser((HDBManager) currentUser);
            }
        } else {
            throw new AuthException("Invalid Password. Please Try Again!");
        }
    }

    public void addApplicantPage(Scanner sc) {
        List<Applicant> applicants = ApplicantDB.getDB();
        System.out.println("Enter NRIC: ");
        String nric = sc.nextLine();
        // Check if NRIC is valid
        if (!nric.matches("[STFG]\\d{7}[A-Z]")) {
            System.out.println("Invalid NRIC format. Please try again.");
            return;
        }
        for (Applicant applicant : applicants) {
            if (applicant.getNric().equals(nric)) {
                System.out.println("NRIC already exists. Please try again.");
                return;
            }
        }
        System.out.println("Enter Name: ");
        String name = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();
        System.out.println("Enter Age: ");
        int age = sc.nextInt(); sc.nextLine();
        MaritalStatus maritalStatus;
        while (true) {
            System.out.println("Enter Marital Status (single/married): ");
            String maritalStatusInput = sc.nextLine().toLowerCase();
            if (maritalStatusInput.equals("single")) {
                maritalStatus = MaritalStatus.SINGLE;
                break;
            } else if (maritalStatusInput.equals("married")) {
                maritalStatus = MaritalStatus.MARRIED;
                break;
            } else {
                System.out.println("Invalid input. Please enter 'single' or 'married'.");
            }
        }

        Applicant newApplicant = new Applicant(name, nric, password, age, maritalStatus);
        ApplicantDB.addUser(newApplicant);
        System.out.println("Applicant added successfully!");
        System.out.println(ViewFormatter.breakLine());
    }
}
