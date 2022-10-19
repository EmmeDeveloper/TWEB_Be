package dao;

public class Utente {
    private int id;
    private String nome, cognome, password, ruolo;

    public Utente(int id, String nome, String cognome, String password, String ruolo) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Utente(String nome, String cognome, String password, String ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.ruolo = ruolo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return "NOME UTENTE ----> " + this.getNome() + "\nCOGNOME UTENTE -> " + this.getCognome() + "\nRUOLO UTENTE ---> " + this.getRuolo();
    }
}
