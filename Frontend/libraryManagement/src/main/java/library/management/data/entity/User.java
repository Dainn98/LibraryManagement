package library.management.data.entity;

public class User {
    private int userId;
    private String userName;
    private String address;
    private String identityCard;
    private String phoneNumber;
    private String email;
    private String country;
    private String state;

    // Constructors
    public User() {
    }

    public User(String userId, String userName, String address, String identityCard,
                String phoneNumber, String email, String country, String state) {
        this.userId = Integer.parseInt(userId.substring(4));
        this.userName = userName;
        this.address = address;
        this.identityCard = identityCard;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.country = country;
        this.state = state;
    }

    public User(String userName, String address, String identityCard, String phoneNumber,
                String email, String country, String state) {
        this.address = address;
        this.identityCard = identityCard;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userName = userName;
        this.country = country;
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIntUserId() {
        return userId;
    }

    public String getUserId() {
        return String.format("USER%d", userId);
    }

    public void setUserId(String userId) {
        this.userId = Integer.parseInt(userId.substring(4));
    }
}
