package library.management.service;

import library.management.data.DAO.ManagerDAO;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.Manager;
import library.management.data.entity.User;

public class AuthService {

  public Object signIn(String username, String password) {
    System.out.println("Attempting auth for user: " + username);
    System.out.println("Password entered: " + password);

    Manager manager = ManagerDAO.getInstance().checkManager(username, password);
    if (manager != null) {
      return manager;
    }
    User user = UserDAO.getInstance().checkUserLogin(username, password);
    if (user != null) {
      return user;
    }
    return null;
  }
}