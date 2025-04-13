package services.controller;

import models.User;

public abstract class UserController {
  AuthController auth = new AuthController();

  public abstract void viewProjectList();
}
