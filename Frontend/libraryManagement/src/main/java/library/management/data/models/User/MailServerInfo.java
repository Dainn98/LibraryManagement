package library.management.data.models.User;

/**
 * Represents the information required to connect to a mail server.
 */
public class MailServerInfo {

  private String mailServer;
  private Integer port;
  private String emailID;
  private String password;
  private Boolean sslEnabled;

  /**
   * Constructs a new MailServerInfo with the specified details.
   *
   * @param mailServer the mail server address
   * @param port       the port number
   * @param emailID    the email ID
   * @param password   the password
   * @param sslEnabled whether SSL is enabled
   */
  public MailServerInfo(String mailServer, Integer port, String emailID, String password,
      Boolean sslEnabled) {
    this.mailServer = mailServer;
    this.port = port;
    this.emailID = emailID;
    this.password = password;
    this.sslEnabled = sslEnabled;
  }

  /**
   * Gets the mail server address.
   *
   * @return the mail server address
   */
  public String getMailServer() {
    return mailServer;
  }

  /**
   * Gets the port number.
   *
   * @return the port number
   */
  public Integer getPort() {
    return port;
  }

  /**
   * Gets the email ID.
   *
   * @return the email ID
   */
  public String getEmailID() {
    return emailID;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Gets whether SSL is enabled.
   *
   * @return true if SSL is enabled, false otherwise
   */
  public Boolean getSslEnabled() {
    return sslEnabled;
  }

  /**
   * Returns a string representation of the MailServerInfo.
   *
   * @return a string representation of the MailServerInfo
   */
  @Override
  public String toString() {
    return String.format("%s:%d @ %s", mailServer, port, emailID);
  }

  /**
   * Validates the mail server information.
   *
   * @return true if the information is valid, false otherwise
   */
  public boolean validate() {
    boolean flag = mailServer == null || mailServer.isEmpty() || port == null || emailID == null
        || emailID.isEmpty() || password.isEmpty();
    return !flag;
  }
}