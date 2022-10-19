package dao;

public class Docente {

    private int id;
    private String nome, cognome;

    public Docente(int id, String nome, String cognome) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "NOME DOCENTE ----> " + this.getNome() + "\nCOGNOME DOCENTE -> " + this.getCognome();
    }
}
