package library.management.entity;

public class Language {
    private int STT;          // STT là khóa chính
    private String lgName;    // Tên ngôn ngữ
    private String lgID;      // ID ngôn ngữ

    // Constructor không tham số
    public Language() {
    }

    // Constructor với tham số lgID và lgName
    public Language(String lgID, String lgName) {
        this.lgID = lgID;
        this.lgName = lgName;
    }

    // Constructor với tham số lgID
    public Language(String lgID) {
        this.lgID = lgID;
    }

    // Getter và Setter cho lgID
    public String getLgID() {
        return lgID;
    }

    public void setLgID(String lgID) {
        this.lgID = lgID;
    }

    // Getter và Setter cho STT
    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    // Getter và Setter cho lgName
    public String getLgName() {
        return lgName;
    }

    public void setLgName(String lgName) {
        this.lgName = lgName;
    }
}
