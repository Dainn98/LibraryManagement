package library.management.data.entity;

public class Manager {
    private String managerName;
    private String password;
    private String position;
    private String email;
    private String managerID;
    private int STT;

    public Manager(String managerName, String password, String position, String email, String managerID) {
        this.managerName = managerName;
        this.password = password;
        this.position = position;
        this.email = email;
        this.managerID = managerID;
    }

    // Constructor không có STT, cho các trường hợp thêm mới
    public Manager(String managerID) {
        this.managerID = managerID;
    }

    public Manager(String managerName, String password, String position, String email) {
        this.password = password;
        this.managerName = managerName;
        this.position = position;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }
}
