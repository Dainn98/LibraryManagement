package library.management.entity;

public class User {
    private int STT;
    private String userName;
    private String address;
    private String identityCard;
    private String mobile;
    private String email;
    private String membershipLevel;
    private String userId;

    // Constructors
    public User() {}

    public User(String userId, String userName, String address, String identityCard, String mobile, String email, String membershipLevel) {
        this.userId = userId;
        this.userName = userName;
        this.address = address;
        this.identityCard = identityCard;
        this.mobile = mobile;
        this.email = email;
        this.membershipLevel = membershipLevel;
    }

    public User(String userName, String address, String identityCard, String mobile, String email, String membershipLevel) {
        this.address = address;
        this.identityCard = identityCard;
        this.mobile = mobile;
        this.email = email;
        this.membershipLevel = membershipLevel;
        this.userName = userName;
    }

    // Getters and Setters
    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
