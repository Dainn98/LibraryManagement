package library.management.ui.applications;

import java.security.SecureRandom;

public class SecurityCodeGenerator {

  private static final String CHAR_POOL = "0123456789";

  public static String generateCode(int length) {
    SecureRandom random = new SecureRandom();
    StringBuilder code = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int randomIndex = random.nextInt(CHAR_POOL.length());
      code.append(CHAR_POOL.charAt(randomIndex));
    }

    return code.toString();
  }

  public static void main(String[] args) {
    String code = generateCode(6);
    System.out.println("Generated code: " + code);
  }
}