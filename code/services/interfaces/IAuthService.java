package services.interfaces;

import java.util.Scanner;

import models.users.User;

public interface IAuthService {
  public User login(Scanner sc);
  public void logoutDisplay();
  public void changePasswordPage(Scanner sc, User currentUser);
  public void addApplicantPage(Scanner sc);
}
