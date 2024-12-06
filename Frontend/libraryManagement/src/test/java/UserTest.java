import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import library.management.data.entity.User;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void testUserConstructorWithAllParams() {
    LocalDateTime registeredDate = LocalDateTime.of(2020, 1, 1, 10, 30, 0, 0);
    User user = new User("john_doe", "1234567890", "123-456-789", "john.doe@example.com", "USA",
        "California", "active", registeredDate);

    assertEquals("john_doe", user.getUserName(), "User name should be 'john_doe'");
    assertEquals("1234567890", user.getIdentityCard(), "Identity card should be '1234567890'");
    assertEquals("123-456-789", user.getPhoneNumber(), "Phone number should be '123-456-789'");
    assertEquals("john.doe@example.com", user.getEmail(), "Email should be 'john.doe@example.com'");
    assertEquals("USA", user.getCountry(), "Country should be 'USA'");
    assertEquals("California", user.getState(), "State should be 'California'");
    assertEquals("active", user.getStatus(), "Status should be 'active'");
    assertEquals(2020, user.getRegisteredDate().getYear(), "Year of registration should be 2020");
  }

  @Test
  public void testUserConstructorWithRequiredParams() {
    User user = new User("jane_doe", "0987654321", "jane.doe@example.com", "securepassword");

    assertEquals("jane_doe", user.getUserName(), "User name should be 'jane_doe'");
    assertEquals("0987654321", user.getIdentityCard(), "Identity card should be '0987654321'");
    assertEquals("jane.doe@example.com", user.getEmail(), "Email should be 'jane.doe@example.com'");
    assertEquals("securepassword", user.getPassword(), "Password should be 'securepassword'");
    assertEquals("pending", user.getStatus(), "Status should be 'pending'");
    assertNotNull(user.getRegisteredDate(), "Registered date should be set");
    assertEquals("not set", user.getCountry(), "Country should be 'not set'");
    assertEquals("not set", user.getState(), "State should be 'not set'");
    assertEquals("not set", user.getPhoneNumber(), "Phone number should be 'not set'");
  }

  @Test
  public void testUserCopyConstructor() {
    User originalUser = new User("alice_smith", "1122334455", "987-654-321",
        "alice.smith@example.com", "Canada", "Ontario", "inactive",
        LocalDateTime.of(2022, 5, 15, 12, 0, 0, 0));
    User copiedUser = new User(originalUser);

    assertEquals(originalUser.getUserName(), copiedUser.getUserName(),
        "User name should be copied");
    assertEquals(originalUser.getIdentityCard(), copiedUser.getIdentityCard(),
        "Identity card should be copied");
    assertEquals(originalUser.getPhoneNumber(), copiedUser.getPhoneNumber(),
        "Phone number should be copied");
    assertEquals(originalUser.getEmail(), copiedUser.getEmail(), "Email should be copied");
    assertEquals(originalUser.getCountry(), copiedUser.getCountry(), "Country should be copied");
    assertEquals(originalUser.getState(), copiedUser.getState(), "State should be copied");
    assertEquals(originalUser.getStatus(), copiedUser.getStatus(), "Status should be copied");
    assertEquals(originalUser.getRegisteredDate(), copiedUser.getRegisteredDate(),
        "Registered date should be copied");
  }

  @Test
  public void testGetRegisteredYear() {
    LocalDateTime registeredDate = LocalDateTime.of(2023, 8, 20, 14, 30, 0, 0);
    User user = new User("bob_jones", "555666777", "bob.jones@example.com", "bobpassword");
    user.setRegisteredDate(registeredDate);

    assertEquals("2023", user.getRegisteredYear(), "Registered year should be '2023'");
  }

  @Test
  public void testToString() {
    User user = new User("carol_white", "987654321", "123-321-123", "carol.white@example.com",
        "Australia", "New South Wales", "active", LocalDateTime.of(2021, 7, 1, 9, 0, 0, 0));

    String expectedString = "User[userName='carol_white', identityCard='987654321', phoneNumber='123-321-123', email='carol.white@example.com', password='null', country='Australia', state='New South Wales', status='active', registeredDate='2021-07-01T09:00']";
    assertEquals(expectedString, user.toString(), "toString method should return correct string");
  }

  @Test
  public void testSettersAndGetters() {
    User user = new User();

    user.setUserName("david_lee");
    user.setIdentityCard("6677889900");
    user.setPhoneNumber("111-222-333");
    user.setEmail("david.lee@example.com");
    user.setPassword("davidpassword");
    user.setCountry("UK");
    user.setState("England");
    user.setStatus("inactive");
    user.setRegisteredDate(LocalDateTime.of(2019, 10, 5, 15, 45, 0, 0));

    assertEquals("david_lee", user.getUserName(), "User name should be 'david_lee'");
    assertEquals("6677889900", user.getIdentityCard(), "Identity card should be '6677889900'");
    assertEquals("111-222-333", user.getPhoneNumber(), "Phone number should be '111-222-333'");
    assertEquals("david.lee@example.com", user.getEmail(),
        "Email should be 'david.lee@example.com'");
    assertEquals("davidpassword", user.getPassword(), "Password should be 'davidpassword'");
    assertEquals("UK", user.getCountry(), "Country should be 'UK'");
    assertEquals("England", user.getState(), "State should be 'England'");
    assertEquals("inactive", user.getStatus(), "Status should be 'inactive'");
    assertEquals(2019, user.getRegisteredDate().getYear(), "Year of registration should be 2019");
  }

}
