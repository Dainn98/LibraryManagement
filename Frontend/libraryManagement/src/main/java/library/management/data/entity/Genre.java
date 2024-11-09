package library.management.data.entity;

public class Genre {
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

    public Genre(String genreID) {
        this.genreID = genreID;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Genre() {
    }

    public Genre(String genreID, String tag) {
        this.genreID = genreID;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
