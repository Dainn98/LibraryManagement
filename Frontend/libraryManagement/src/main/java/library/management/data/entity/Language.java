package library.management.data.entity;

public class Language extends Detail {

  public Language() {
  }

  public Language(String lgID, String lgName) {
    this.ID = Integer.parseInt(lgID.substring(4));
    this.tag = lgName;
  }

  public Language(String lgID) {
    this.ID = Integer.parseInt(lgID.substring(4));
  }

  public String getStringLgID() {
    return String.format("LANG%d", ID);
  }

  public int getIntLgID() {
    return ID;
  }

  public void setLgID(String lgID) {
    this.ID = Integer.parseInt(lgID.substring(4));
  }

  public String getLgName() {
    return tag;
  }

  public void setLgName(String lgName) {
    this.tag = lgName;
  }

  @Override
  public String toString() {
    return tag;
  }

}
