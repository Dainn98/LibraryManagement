package library.management.data.entity;

abstract public class Client {

  String name;
  String identityCard;
  String password;
  String email;
  String phoneNumber;

  public Client() {
    super();
  }

  public Client(String name, String identityCard, String password, String email, String phoneNumber) {
    super();
    this.name = name;
    this.identityCard = identityCard;
    this.password = password;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
