package services.controller;

import java.util.Scanner;

import models.users.User;

public abstract class UserController {
  AuthController auth = new AuthController();

  public static void start(Scanner sc){}
  public abstract void viewProjectList();
}
