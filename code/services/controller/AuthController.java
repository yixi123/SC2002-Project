package services.controller;

import database.dataclass.users.ApplicantDB;
import database.dataclass.users.ManagerDB;
import database.dataclass.users.OfficerDB;
import exception.AuthException;

import java.util.*;

import models.enums.MaritalStatus;
import models.users.Applicant;
import models.users.HDBManager;
import models.users.HDBOfficer;
import models.users.User;
import services.interfaces.IAccountService;
import services.interfaces.IAuthService;
import services.subservices.AccountService;
import services.subservices.AuthService;
import view.ViewFormatter;

public class AuthController {
    
    
    private static User currentUser = null;

    IAuthService authService = new AuthService();
    IAccountService accService = new AccountService();

    public User getCurrentUser(){
        return currentUser;
    }

    public User enterLoginPage(Scanner sc) throws AuthException{
        currentUser = authService.login(sc);
        return currentUser;
    }

    public void logout(){
        currentUser = null;
        authService.logoutDisplay();
    }

    public void enterChangePasswordPage(Scanner sc){
        accService.changePasswordPage(sc, currentUser);
    }


    public void enterAddApplicantPage(Scanner sc) {
        accService.addApplicantPage(sc);
    }
}
