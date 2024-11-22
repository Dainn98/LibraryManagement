package library.management.data.entity;

import java.time.LocalDateTime;

public class User {
    private String userName;
    private String identityCard;
    private String phoneNumber;
    private String email;
    private String password;
    private String country;
    private String state;
    private String status;
    private LocalDateTime registeredDate;

    public User() {
    }

    public User(User user) {
        this.userName = user.userName;
        this.identityCard = user.identityCard;
        this.phoneNumber = user.phoneNumber;
        this.email = user.email;
        this.password = user.password;
        this.country = user.country;
        this.state = user.state;
        this.status = user.status;
        this.registeredDate = user.registeredDate;
    }

    public User(String userName, String identityCard, String phoneNumber,
                String email, String country, String state, String status, LocalDateTime registeredDate) {
        this.userName = userName;
        this.identityCard = identityCard;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.country = country;
        this.state = state;
        this.status = status;
        this.registeredDate = registeredDate != null ? registeredDate : LocalDateTime.now();
    }

    public User(String userName, String identityCard, String email, String password) {
        this.userName = userName;
        this.identityCard = identityCard;
        this.email = email;
        this.password = password;
        this.status = "pending";
        this.registeredDate = LocalDateTime.now();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return this.registeredDate != null ? String.valueOf(this.registeredDate.getYear()) : "Registered date is not set.";
    }

    @Override
    public String toString() {
        return String.format(
                "User[userName='%s', identityCard='%s', phoneNumber='%s', email='%s', password='%s', country='%s', state='%s', status='%s', registeredDate='%s']",
                userName, identityCard, phoneNumber, email, password, country, state, status, registeredDate
        );
    }
}
