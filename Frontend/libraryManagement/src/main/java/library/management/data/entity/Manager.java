package library.management.data.entity;

public final class Manager extends Client {

  private int managerID;

  public String getManagerName() {
    return name;
  }

  public void setManagerName(String managerName) {
    this.name = managerName;
  }

  public String getManagerID() {
    return String.format("MNG%d", managerID);
  }

  public void setManagerID(int managerID) {
    this.managerID = managerID;
  }

  public int getIntManagerID() {
    return managerID;
  }

}
