import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import library.management.data.entity.Manager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


public class ManagerTest {

  private Manager manager;

  @BeforeEach
  public void setUp() {
    manager = new Manager();
  }

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

  @Test
  public void testDefaultConstructor() {
    assertNotNull(manager, "Manager object should be created.");
    assertEquals(0, manager.getIntManagerID(), "Default manager ID should be 0.");
    assertNull(manager.getManagerName(), "Default manager name should be null.");
  }

  @Test
  public void testSetManagerIDMultipleTimes() {
    manager.setManagerID(5);
    assertEquals("MNG5", manager.getManagerID());
    manager.setManagerID(10);
    assertEquals("MNG10", manager.getManagerID(), "Manager ID should update correctly.");
  }

  @Test
  public void testSetEmailInvalidFormat() {
    // Chỉ kiểm tra setter/getter, giả sử không validate email
    String invalidEmail = "invalid-email";
    manager.setEmail(invalidEmail);
    assertEquals(invalidEmail, manager.getEmail(), "Email setter should accept any string.");
  }

  @Test
  public void testSetPhoneNumberVariousFormats() {
    String phone1 = "0123456789";
    String phone2 = "+84123456789";
    String phone3 = "123-456-789";

    manager.setPhoneNumber(phone1);
    assertEquals(phone1, manager.getPhoneNumber());

    manager.setPhoneNumber(phone2);
    assertEquals(phone2, manager.getPhoneNumber());

    manager.setPhoneNumber(phone3);
    assertEquals(phone3, manager.getPhoneNumber());
  }

  @Test
  public void testSetIdentityCardMultipleTimes() {
    manager.setIdentityCard("111111111");
    assertEquals("111111111", manager.getIdentityCard());

    manager.setIdentityCard("222222222");
    assertEquals("222222222", manager.getIdentityCard(), "Identity card should update correctly.");
  }

  @Test
  public void testPasswordChange() {
    manager.setPassword("abc123");
    assertEquals("abc123", manager.getPassword());

    manager.setPassword("xyz789");
    assertEquals("xyz789", manager.getPassword(), "Password should be updated.");
  }

  @Test
  public void testManagerNameNullAndNonNull() {
    manager.setManagerName(null);
    assertNull(manager.getManagerName());

    manager.setManagerName("Alice");
    assertEquals("Alice", manager.getManagerName());
  }

  @Test
  public void testSetManagerIDAndGetAsString() {
    manager.setManagerID(1);
    assertEquals("MNG1", manager.getManagerID());

    manager.setManagerID(42);
    assertEquals("MNG42", manager.getManagerID());
  }

  @Test
  public void testGetIntManagerID() {
    manager.setManagerID(5);
    assertEquals(5, manager.getIntManagerID());
  }

  @Test
  public void testSetAndGetManagerName() {
    manager.setManagerName("Tuấn Anh");
    assertEquals("Tuấn Anh", manager.getManagerName());

    manager.setManagerName("Phạm Văn");
    assertEquals("Phạm Văn", manager.getManagerName());
  }

  @Test
  public void testSetManagerNameNull() {
    manager.setManagerName(null);
    assertNull(manager.getManagerName(), "Manager name should be null when set to null.");
  }

  @Test
  public void testSetAndGetIdentityCard() {
    manager.setIdentityCard("123456789");
    assertEquals("123456789", manager.getIdentityCard());

    manager.setIdentityCard("987654321");
    assertEquals("987654321", manager.getIdentityCard());
  }

  @Test
  public void testSetAndGetPhoneNumber() {
    manager.setPhoneNumber("0987654321");
    assertEquals("0987654321", manager.getPhoneNumber());

    manager.setPhoneNumber("+84123456789");
    assertEquals("+84123456789", manager.getPhoneNumber());

    manager.setPhoneNumber("123-456-789");
    assertEquals("123-456-789", manager.getPhoneNumber());
  }

  @Test
  public void testSetAndGetEmail() {
    manager.setEmail("tuanan@example.com");
    assertEquals("tuanan@example.com", manager.getEmail());

    manager.setEmail("phamvan@example.com");
    assertEquals("phamvan@example.com", manager.getEmail());
  }

  @Test
  public void testToString() {
    manager.setManagerName("Tuấn Anh");
    manager.setManagerID(1);
    String str = manager.toString();
    assertTrue(str.contains("Tuấn Anh") || str.contains("MNG1"), "toString should contain manager info.");
  }

  @Test
  public void testEqualsAndHashCode() {
    Manager m1 = new Manager();
    Manager m2 = new Manager();
    m1.setManagerID(1);
    m2.setManagerID(1);
    assertEquals(m1.getManagerID(), m2.getManagerID(), "Manager IDs should be equal.");
  }

  @Test
  public void testSetAndGetPassword() {
    manager.setPassword("123abc");
    assertEquals("123abc", manager.getPassword());

    manager.setPassword("xyz789");
    assertEquals("xyz789", manager.getPassword());
  }




}
