package app.models;

public class Course {
    private String _id;
    private String _title;

    public Course() {

    }

    public Course(String id, String title) {
        _id = id;
        _title = title;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    @Override
    public String toString() {
        return "Corso [id: "+getId()+", titolo: "+getTitle()+"]";
    }
}
