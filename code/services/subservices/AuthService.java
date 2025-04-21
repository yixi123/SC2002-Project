package services.subservices;

import java.util.List;
import java.util.Scanner;

import database.dataclass.users.ApplicantDB;
import database.dataclass.users.ManagerDB;
import database.dataclass.users.OfficerDB;
import exception.AuthException;

import models.users.User;
import services.interfaces.IAuthService;
import view.ViewFormatter;

public class AuthService implements IAuthService {

    private static int attempt = 1;
    private static final int MAX_ATTEMPTS = 3;

    @Override
    public User login(Scanner sc) throws AuthException{
        System.out.println("\n                Login Page               ");
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
}
