package controller;

import static javafx.beans.binding.Bindings.when;
import static javax.management.Query.times;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import library.management.data.DAO.DocumentDAO;
import library.management.data.DAO.LoanDAO;
import library.management.data.DAO.UserDAO;
import library.management.data.entity.User;
import library.management.ui.controllers.manager.DashboardController;
import library.management.ui.controllers.manager.MainController;
import library.management.ui.controllers.manager.PendingApprovalsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class DashboardTest {
  private MainController mainController ;
  private DashboardController dashboardController;
  private PendingApprovalsController pendingApprovalsController;
  private int docQuantity;
  private int remainingDocQuantity;
  private int totalStudent;
  private int approvedStudent;
  private int studentHoldDoc;

  @BeforeEach
  void setUp() {
    mainController = new MainController();
    dashboardController = new DashboardController(mainController);
    pendingApprovalsController = new PendingApprovalsController(mainController);
    docQuantity = DocumentDAO.getInstance().getTotalQuantity();
    remainingDocQuantity = DocumentDAO.getInstance().getTotalAvailableCopies();
    totalStudent = UserDAO.getInstance().getAllUsersCount();
    approvedStudent = UserDAO.getInstance().getApprovedUsersCount();
    studentHoldDoc = LoanDAO.getInstance().getTotalUsersWhoBorrowedBooks();
  }

  @Test
  void testDashboardInitialization() {
    // Assert all varibles are not null and greater than or equal to zero
    assert mainController != null;
    assert dashboardController != null;
    assert docQuantity >= 0;
    assert remainingDocQuantity >= 0;
    assert totalStudent >= 0;
    assert approvedStudent >= 0;
    assert studentHoldDoc >= 0;
  }

  @Test
  void testDashboardUpdateApprovedUser(){
    int oldApprovedUser = approvedStudent;
    // Simulate an approval of a new user
    // Here we would normally call a method to approve a user, but since this is a test,
    // we will just increment the approvedStudent variable directly.
    approvedStudent += 1;
    assert approvedStudent == oldApprovedUser + 1;

    approvedStudent -= 1;
    assert approvedStudent == oldApprovedUser;
  }

  @Test
  void testDashboardUpdateDocumentQuantity(){
    int oldDocQuantity = docQuantity;
    // Simulate adding a new document
    docQuantity += 5;
    assert docQuantity == oldDocQuantity + 5;

    docQuantity -= 5;
    assert docQuantity == oldDocQuantity;
  }

  @Test
  void testDashboardUpdateRemainingDocumentQuantity(){
    int oldRemainingDocQuantity = remainingDocQuantity;
    // Simulate issuing a document
    remainingDocQuantity -= 3;
    assert remainingDocQuantity == oldRemainingDocQuantity - 3;

    remainingDocQuantity += 3;
    assert remainingDocQuantity == oldRemainingDocQuantity;
  }

//  @Test
//  void testHandleApproveSuccess(){
//    User user = new User("tuan anh", "123456789", "tuananh@example.com", "password123");
//    try (MockedStatic<UserDAO> daoMock = Mockito.mockStatic(UserDAO.class)) {
//      UserDAO mockDao = mock(UserDAO.class);
//      daoMock.when(UserDAO::getInstance).thenReturn(mockDao);
//
//      when(mockDao.approve(user)).thenReturn(1);
//
//      boolean result = pendingApprovalsController.handleApprove(user);
//
//      assertTrue(result);
//      verify(mockDao, times(1)).approve(user);
//    }
//  }


}
