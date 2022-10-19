package dao;

public class Corso {
    private int id;
    private String titolo;

    public Corso(int id, String titolo) {
        this.id = id;
        this.titolo = titolo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public String toString() {
        return "TITOLO CORSO -> " + this.getTitolo();
    }
}
