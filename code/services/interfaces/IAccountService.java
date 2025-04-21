package services.interfaces;

import java.util.Scanner;

import models.users.User;

public interface IAccountService {
  public void changePasswordPage(Scanner sc, User currentUser);
  public void addApplicantPage(Scanner sc);
}
