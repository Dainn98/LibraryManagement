import static org.junit.jupiter.api.Assertions.assertEquals;

import library.management.data.entity.Manager;
import org.junit.jupiter.api.Test;

public class ManagerTest {

  @Test
  public void testManagerName() {
    Manager manager = new Manager();
    manager.setManagerName("John Doe");
    assertEquals("John Doe", manager.getManagerName(), "Manager name should be 'John Doe'.");
  }

  @Test
  public void testManagerID() {
    Manager manager = new Manager();
    manager.setManagerID(1);
    assertEquals("MNG1", manager.getManagerID(), "Manager ID should be 'MNG1'.");
  }

  @Test
  public void testManagerIDAsInt() {
    Manager manager = new Manager();
    manager.setManagerID(1);
    assertEquals(1, manager.getIntManagerID(), "Manager ID should be 1.");
  }

  @Test
  public void testIdentityCard() {
    Manager manager = new Manager();
    manager.setIdentityCard("123456789");
    assertEquals("123456789", manager.getIdentityCard(), "Identity card should be '123456789'.");
  }

  @Test
  public void testPhoneNumber() {
    Manager manager = new Manager();
    manager.setPhoneNumber("0987654321");
    assertEquals("0987654321", manager.getPhoneNumber(), "Phone number should be '0987654321'.");
  }

  @Test
  public void testEmail() {
    Manager manager = new Manager();
    manager.setEmail("johndoe@example.com");
    assertEquals("johndoe@example.com", manager.getEmail(),
        "Email should be 'johndoe@example.com'.");
  }

  @Test
  public void testPassword() {
    Manager manager = new Manager();
    manager.setPassword("password123");
    assertEquals("password123", manager.getPassword(), "Password should be 'password123'.");
  }
}
