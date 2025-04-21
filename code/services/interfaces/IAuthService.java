package services.interfaces;

import java.util.Scanner;

import exception.AuthException;
import models.users.User;

public interface IAuthService {
  public User login(Scanner sc) throws AuthException;
  public void logoutDisplay();
}
