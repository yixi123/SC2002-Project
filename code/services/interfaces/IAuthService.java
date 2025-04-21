package services.interfaces;

import java.util.Scanner;

import exception.AuthException;
import models.users.User;

/**
 * Interface defining authentication services such as login and logout.
 */
public interface IAuthService {

  /**
   * Logs in a user using provided credentials.
   * @param sc Scanner for user input
   * @return the authenticated user
   * @throws AuthException if login fails
   */
  User login(Scanner sc) throws AuthException;

  /**
   * Displays logout confirmation message.
   */
  void logoutDisplay();
}
