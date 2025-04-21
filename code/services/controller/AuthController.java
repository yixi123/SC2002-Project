package services.controller;

import exception.AuthException;

import java.util.*;

import models.users.User;
import services.interfaces.IAccountService;
import services.interfaces.IAuthService;
import services.subservices.AccountService;
import services.subservices.AuthService;

/**
 * Controller that manages authentication-related actions,
 * such as login, logout, password changes, and account creation.
 */
public class AuthController {

    /** The currently logged-in user in the system. */
    private static User currentUser = null;

    /** Service handling authentication logic such as login/logout. */
    private IAuthService authService = new AuthService();

    /** Service handling account operations such as password change or user creation. */
    private IAccountService accService = new AccountService();

    /**
     * Gets the currently logged-in user.
     *
     * @return the current user, or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Starts the login process by prompting the user for credentials.
     * Sets the currentUser upon successful login.
     *
     * @param sc Scanner for user input
     * @return the logged-in user
     * @throws AuthException if authentication fails
     */
    public User enterLoginPage(Scanner sc) throws AuthException {
        currentUser = authService.login(sc);
        return currentUser;
    }

    /**
     * Logs out the currently logged-in user and displays logout confirmation.
     */
    public void logout() {
        currentUser = null;
        authService.logoutDisplay();
    }

    /**
     * Allows the currently logged-in user to change their password.
     *
     * @param sc Scanner for user input
     */
    public void enterChangePasswordPage(Scanner sc) {
        accService.changePasswordPage(sc, currentUser);
    }

    /**
     * Allows the creation of a new applicant account.
     *
     * @param sc Scanner for user input
     */
    public void enterAddApplicantPage(Scanner sc) {
        accService.addApplicantPage(sc);
    }
}