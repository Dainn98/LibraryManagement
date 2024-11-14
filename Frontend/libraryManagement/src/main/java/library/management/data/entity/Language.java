package library.management.data.entity;

public class Language {
    private int lgID;
    private String lgName;

    public Language() {
    }

    public Language(String lgID, String lgName) {
        this.lgID = Integer.parseInt(lgID.substring(4));
        this.lgName = lgName;
    }

    public Language(String lgID) {
        this.lgID = Integer.parseInt(lgID.substring(4));
    }

    public String getStringLgID() {
        return String.format("LANG%d", lgID);
    }

    public int getIntLgID() {

        return lgID;
    }

    public void setLgID(String lgID) {
        this.lgID = Integer.parseInt(lgID.substring(4));
    }


    public String getLgName() {
        return lgName;
    }

    public void setLgName(String lgName) {
        this.lgName = lgName;
    }

    @Override
    public String toString() {
        return lgName;
    }

}
