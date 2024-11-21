package library.management.data.entity;

import java.time.LocalDateTime;

public class User {
    private int userId;
    private String userName;
    private String identityCard;
    private String phoneNumber;
    private String email;
    private String country;
    private String state;
    private String status;
    private LocalDateTime registeredDate;

    public User() {
    }

    public User(String userId) {
        this.userId = Integer.parseInt(userId);
    }

    public User(User user) {
        this.userId = user.userId;
        this.userName = user.userName;
        this.identityCard = user.identityCard;
        this.phoneNumber = user.phoneNumber;
        this.email = user.email;
        this.country = user.country;
        this.state = user.state;
        this.status = user.status;
        this.registeredDate = user.registeredDate;
    }

    public User(String userId, String userName, String identityCard,
                String phoneNumber, String email, String country, String state, String status, LocalDateTime registeredDate) {
        this.userId = Integer.parseInt(userId.substring(4));
        this.userName = userName;
        this.identityCard = identityCard;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.country = country;
        this.state = state;
        this.registeredDate = registeredDate;
        this.status = status;
    }

    public User(String userName, String identityCard, String phoneNumber,
                String email, String country, String state, String status) {
        this.userName = userName;
        this.identityCard = identityCard;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.country = country;
        this.state = state;
        this.registeredDate = LocalDateTime.now();
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getRegisteredYear() {
        if (this.registeredDate != null) {
            return String.valueOf(this.registeredDate.getYear());
        } else {
            return "Registered date is not set.";
        }
    }


}
