package library.management.ui.applications;

import java.security.SecureRandom;

public class SecurityCodeGenerator {

  /**
   * A pool of characters (digits) from which the code is generated.
   */
  private static final String CHAR_POOL = "0123456789";

  /**
   * Generates a secure random numeric code of the specified length.
   *
   * @param length The length of the code to be generated. Must be a positive integer.
   * @return A randomly generated numeric code as a {@link String}.
   * @throws IllegalArgumentException If the specified length is not positive.
   */
  public static String generateCode(int length) {
    SecureRandom random = new SecureRandom();
    StringBuilder code = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(CHAR_POOL.length());
      code.append(CHAR_POOL.charAt(randomIndex));
    }

    return code.toString();
  }

  /**
   * Main method for testing the code generation.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    String code = generateCode(6);
    System.out.println("Generated code: " + code);
  }
}