package services.interfaces;

import java.util.Scanner;

import models.users.User;

/**
 * Interface defining account-related actions such as changing passwords
 * and adding new applicant users.
 */
public interface IAccountService {

  /**
   * Allows the current user to change their password.
   * @param sc Scanner for user input
   * @param currentUser The user whose password is to be changed
   */
  void changePasswordPage(Scanner sc, User currentUser);

  /**
   * Displays the interface to add a new applicant user.
   * @param sc Scanner for user input
   */
  void addApplicantPage(Scanner sc);
}

