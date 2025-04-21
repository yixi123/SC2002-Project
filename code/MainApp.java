import database.dataclass.projects.*;
import database.dataclass.users.*;
import exception.AuthException;
import java.util.Scanner;
import models.projects.FilterSettings;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;
import models.users.User;
import services.controller.ApplicantController;
import services.controller.AuthController;
import services.controller.ManagerController;
import services.controller.OfficerController;
import view.ViewFormatter;

/**
 * The MainApp class serves as the entry point for the BTO Project Management System.
 * It initializes the databases, handles user authentication, and navigates users to their respective menus.
 */
public class MainApp {
    static FilterSettings filterSettings = new FilterSettings(); // Initialize filter settings

    /**
     * The main method initializes the application and handles unexpected errors.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        initiateDB(); // Initialize databases

        AuthController auth = new AuthController();
        Scanner sc = new Scanner(System.in);
        
        try {
            entryMenu(sc, auth);
        } 
        // Security Measure: Handle unexpected errors
        catch (NullPointerException e) {
            System.out.println("System crashed! Please restart!");
            System.out.println("Null Pointer Error: " + e.getMessage());
            saveDB();
        } 
        catch (Error e) {
            System.out.println("System crashed! Please restart!");
            System.out.println("Error: " + e.getMessage());
            saveDB();
        } 
        catch (Exception e) {
            System.out.println("System crashed! Please restart!");
            System.out.println("Error: " + e.getMessage());
            saveDB();
        } 
        finally {
            sc.close();
        }
    }

    /**
     * Displays the entry menu for the application and handles user choices.
     *
     * @param sc   The Scanner object for user input.
     * @param auth The AuthController object for handling authentication.
     */
    public static void entryMenu(Scanner sc, AuthController auth) {
        int choice = 0;

        do {
            try {
                System.out.println("\n\n" + ViewFormatter.thickBreakLine());
                System.out.println("     BTO Project Management System       ");
                System.out.println(ViewFormatter.thickBreakLine());
                System.out.println("1. Log In");
                System.out.println("2. Register as Applicant");
                System.out.println("3. Exit App");
                System.out.println(ViewFormatter.breakLine());
                System.out.print("Enter your choice (1-3): ");
                choice = sc.nextInt();
                sc.nextLine();
                System.out.println(ViewFormatter.breakLine());
                
                switch (choice) {
                    case 1:
                        User user = auth.enterLoginPage(sc);
                        navigateMenu(user, sc);
                        break;
                    case 2:
                        auth.enterAddApplicantPage(sc);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } 
            catch (AuthException e) {
                System.out.println(e.getMessage());
            } 
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (choice != 3);

        System.out.println(ViewFormatter.breakLine());
        System.out.println("               Thank you!                ");
        System.out.println(ViewFormatter.thickBreakLine());
    }

    /**
     * Navigates the user to their respective menu based on their role.
     *
     * @param user The authenticated user.
     * @param sc   The Scanner object for user input.
     */
    public static void navigateMenu(User user, Scanner sc) {
        if (user instanceof HDBOfficer) {
            OfficerController app = new OfficerController();
            app.start(sc);
        } 
        else if (user instanceof Applicant) {
            ApplicantController app = new ApplicantController();
            app.start(sc);
        } 
        else if (user instanceof HDBManager) {
            ManagerController app = new ManagerController();
            app.start(sc);
        }
    }

    /**
     * Initializes the databases by loading data from files.
     */
    public static void initiateDB() {
        // Load data from files into respective databases
        ApplicantDB.initiateDB();
        OfficerDB.initiateDB();
        ManagerDB.initiateDB();
        ProjectDB.initiateDB();
        EnquiryDB.initiateDB();
        ProjectAppDB.initiateDB();
        OfficerAppDB.initiateDB();
    }

    /**
     * Saves the current state of the databases to files.
     */
    public static void saveDB() {
        // Save database state
    }
}
