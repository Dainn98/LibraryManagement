package library.management.data.entity;

public class Manager {
    private String managerName;
    private String identityCard;
    private String password;
    private String email;
    private int managerID;

    public Manager(String managerName, String password, String identityCard, String email, String managerID) {
        this.managerName = managerName;
        this.identityCard = identityCard;
        this.password = password;
        this.email = email;
        this.managerID = Integer.parseInt(managerID.substring(3));
    }

    // Constructor không có managerID, cho các trường hợp thêm mới
    public Manager(String managerID) {
        this.managerID = Integer.parseInt(managerID.substring(3));
    }

    public Manager(String managerName, String password, String identityCard, String email) {
        this.password = password;
        this.identityCard = identityCard;
        this.managerName = managerName;
        this.email = email;
    }

    // Getters và Setters
    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
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

    public String getManagerID() {
        return String.format("MNG%d", managerID);
    }

    public int getIntManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = Integer.parseInt(managerID.substring(3));
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }
}
