package library.management.entity;

public class GenreBook {
    private int STT;
    private String tag;
    private String genreID;

    public String getGenreID() {
        return genreID;
    }

    public void setGenreID(String genreID) {
        this.genreID = genreID;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getTag() {
        return tag;
    }

    public GenreBook(String genreID) {
        this.genreID = genreID;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public GenreBook() {
    }

    public GenreBook(String genreID, String tag) {
        this.genreID = genreID;
        this.tag = tag;
    }
}
