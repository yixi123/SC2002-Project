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
import services.interfaces.IAccountService;
import view.ViewFormatter;

public class AccountService implements IAccountService {

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
                ApplicantDB.updateUser();
            } else if (currentUser instanceof HDBOfficer) {
                OfficerDB.updateUser();
            } else if (currentUser instanceof HDBManager) {
                ManagerDB.updateUser();
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
