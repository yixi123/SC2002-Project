package services.controller;

import models.users.User;

public abstract class UserController {
  AuthController auth = new AuthController();

  public abstract void viewProjectList();
}
