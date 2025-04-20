package services.controller;

import java.util.Scanner;
import models.projects.FilterSettings;

import view.UserView;

public abstract class UserController {
    protected FilterSettings filterSettings = new FilterSettings();
    protected AuthController auth = new AuthController();

    static UserView userView = new UserView();

    public abstract void start(Scanner sc);

    public void changeMyPassword(Scanner sc){
        auth.enterChangePasswordPage(sc);
    }

    public void adjustFilterSettings(Scanner sc) {
        try{
            userView.adjustFilterSettings(sc);
        }catch(IllegalArgumentException e){
            System.out.println("Illegal input!");
        }catch(Exception e){
            System.out.println("Unexpected Exception: "+ e.getMessage());
        }
    }
}
