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

/**
 * Service class responsible for authentication and login-related logic.
 * Handles login attempts, NRIC validation, user authentication across user types,
 * and logout display messages.
 */
public class AuthService implements IAuthService {

    /**
     * Tracks the number of failed login attempts in the current session.
     */
    private static int attempt = 1;

    /**
     * Maximum number of login attempts allowed before access is blocked.
     */
    private static final int MAX_ATTEMPTS = 3;

    /**
     * Displays the login page and handles user authentication.
     * Validates NRIC format, checks user credentials, and limits retry attempts.
     *
     * @param sc Scanner for user input
     * @return The authenticated {@code User} object
     * @throws AuthException If login fails after the maximum number of attempts
     */
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
                System.out.printf("Attempt (%d / 3)\n", attempt);
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

    /**
     * Authenticates the user by verifying NRIC and password against all user databases.
     * Searches in the following order: Applicants, Officers, Managers.
     *
     * @param nric The NRIC entered by the user
     * @param password The password entered by the user
     * @return The authenticated {@code User} if credentials match
     * @throws AuthException If credentials are incorrect or not found
     */
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

    /**
     * Displays a message indicating successful logout.
     * Used after a user logs out of the system.
     */
    public void logoutDisplay(){
        System.out.println("You have logged out successfully.");
        System.out.println(ViewFormatter.breakLine());
    }
}
