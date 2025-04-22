package services.controller;

import java.util.Scanner;
import models.projects.FilterSettings;

import view.UserView;

/**
 * Abstract base class for all user controllers in the BTO system,
 * including Applicant, Officer, and Manager controllers.
 * Provides common methods like password change and filter settings.
 */
public abstract class UserController {

    /** The filter settings applied to project views. */
    protected FilterSettings filterSettings = new FilterSettings();

    /** Controller responsible for authentication-related features. */
    protected AuthController auth = new AuthController();

    /** Shared view interface for displaying user-facing menus. */
    static UserView userView = new UserView();

    /**
     * Starts the session for the current user.
     * Must be implemented by subclasses.
     *
     * @param sc the Scanner object for user input
     */
    public abstract void start(Scanner sc);

    /**
     * Allows the current user to change their password through the AuthController.
     *
     * @param sc Scanner object for password input
     */
    public void changeMyPassword(Scanner sc){
        auth.enterChangePasswordPage(sc);
    }

    /**
     * Allows the user to adjust their project filter settings using the view.
     *
     * @param sc Scanner object for user input
     */
    public void adjustFilterSettings(Scanner sc) {
        try{
            filterSettings = userView.adjustFilterSettings(sc, filterSettings);
        } catch(IllegalArgumentException e){
            System.out.println("Illegal input!");
        } catch(Exception e){
            System.out.println("Unexpected Exception: "+ e.getMessage());
        }
    }
}