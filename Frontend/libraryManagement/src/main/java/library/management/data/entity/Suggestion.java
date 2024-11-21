package library.management.data.entity;

public class Suggestion {
    private int id;
    private String value;
    private int frequency;

    public Suggestion() {}

    public Suggestion(int id, String value, int frequency) {
        this.id = id;
        this.value = value;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
