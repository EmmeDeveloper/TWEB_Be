package dao;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class DAO {
    private static Connection conn;
    private static final String url = "jdbc:mysql://localhost:3306/ripetizioni";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private static void startConnection() {
        try {
            conn = DriverManager.getConnection(url, "root", "");
            if(conn != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private static void endConnection() {
        try {
            if(conn != null) {
                conn.close();
                System.out.println("Connection to the database closed");
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    public static ResultSet executeQuery(String query) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            return stx.executeQuery(query);
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
        return null;
    }

    //  -- CORSI --
    public static void insertCorso(Corso corso) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("INSERT INTO corso (titolo) VALUES ('" + corso.getTitolo() + "')");

            // aggiorno l'ID dell'oggetto corso con quello generato dal DB
            ResultSet result = stx.executeQuery("SELECT COUNT(*) AS numRows, id_corso FROM corso WHERE titolo = '" + corso.getTitolo() + "' ORDER BY id_corso ASC");
            int counter = 0, id = -1;
            while(result.next()) {
                id = result.getInt("id_corso");
                counter++;
            }
            if(counter > 1)     System.out.println("Ci sono " + counter + " corsi con gli stessi dati");
            else if(id == -1)   System.out.println("Errore nell'aggiornamento dell'ID del corso");
            corso.setId(id);
            // posso aggiornare l'ID anche se ci sono più risultati perché sono ordinate in ASC e quella più recente avrà sempre il valore massimo
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static void deleteCorso(Corso corso) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("DELETE FROM corso WHERE id_corso = '"+ corso.getId() + "'");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static ArrayList<Corso> getCorsi() {
        startConnection();
        ArrayList<Corso> list = new ArrayList<>();
        try {
            Statement stx = conn.createStatement();
            ResultSet result = stx.executeQuery("SELECT * FROM corso ORDER BY titolo ASC");
            while(result.next()) {
                list.add(new Corso(result.getInt("id_corso"), result.getString("titolo")));
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
        return list;
    }

    public static void printCorsi() {
        ArrayList<Corso> list = getCorsi();
        if(list.size() > 0)     System.out.println("LISTA CORSI:");
        else                    System.out.println("Non sono presenti corsi nella lista");
        for(Corso corso : list) {
            System.out.println(corso.toString());
            System.out.println(" --- ");
        }
    }
    // --

    // -- DOCENTI --
    public static void insertDocente(Docente docente) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("INSERT INTO Docente (nome, cognome) VALUES ('" + docente.getNome() + "', '" + docente.getCognome() + "')");

            // aggiorno l'ID dell'oggetto docente con quello generato dal DB
            ResultSet result = stx.executeQuery("SELECT COUNT(*) AS numRows, id_docente FROM docente WHERE nome = '" + docente.getNome() + "' AND cognome = '" + docente.getCognome() + "' ORDER BY id_docente ASC");
            int counter = 0, id = -1;
            while(result.next()) {
                id = result.getInt("id_docente");
                counter++;
            }
            if(counter > 1)     System.out.println("Ci sono " + counter + " docenti con gli stessi dati");
            else if(id == -1)   System.out.println("Errore nell'aggiornamento dell'ID del docente");
            docente.setId(id);
            // posso aggiornare l'ID anche se ci sono più risultati perché sono ordinate in ASC e quella più recente avrà sempre il valore massimo
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static void deleteDocente(Docente docente) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("DELETE FROM docente WHERE id_docente = '"+ docente.getId() + "'");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static ArrayList<Docente> getDocenti() {
        startConnection();
        ArrayList<Docente> list = new ArrayList<>();
        try {
            Statement stx = conn.createStatement();
            ResultSet result = stx.executeQuery("SELECT * FROM docente ORDER BY cognome ASC, nome ASC");
            while(result.next()) {
                list.add(new Docente(result.getInt("id_docente"), result.getString("nome"), result.getString("cognome")));
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
        return list;
    }

    public static void printDocenti() {
        ArrayList<Docente> list = getDocenti();
        if(list.size() > 0)     System.out.println("LISTA DOCENTI:");
        else                    System.out.println("Non sono presenti docenti nella lista");
        for(Docente docente : list) {
            System.out.println(docente.toString());
            System.out.println(" --- ");
        }
    }
    // --

    // -- UTENTI --
    public static void insertUtente(Utente utente) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("INSERT INTO utente (nome, cognome, password, ruolo) VALUES ('" + utente.getNome() + "', '" + utente.getCognome() + "', '" + utente.getPassword() + "', '" + utente.getRuolo() + "')");

            // aggiorno l'ID dell'oggetto corso con quello generato dal DB
            ResultSet result = stx.executeQuery("SELECT COUNT(*) AS numRows, id_utente FROM utente WHERE nome = '" + utente.getNome() + "' AND cognome = '" + utente.getCognome() + "' AND password = '" + utente.getPassword() + "' AND ruolo = '" + utente.getRuolo() + "' ORDER BY id_corso ASC");
            int counter = 0, id = -1;
            while(result.next()) {
                id = result.getInt("id_utente");
                counter++;
            }
            if(counter > 1)     System.out.println("Ci sono " + counter + " utenti con gli stessi dati");
            else if(id == -1)   System.out.println("Errore nell'aggiornamento dell'ID dell'utente");
            utente.setId(id);
            // posso aggiornare l'ID anche se ci sono più risultati perché sono ordinate in ASC e quella più recente avrà sempre il valore massimo
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static void deleteUtente(Utente utente) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("DELETE FROM utente WHERE id_utente = '"+ utente.getId() + "'");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static ArrayList<Utente> getUtenti() {
        startConnection();
        ArrayList<Utente> list = new ArrayList<>();
        try {
            Statement stx = conn.createStatement();
            ResultSet result = stx.executeQuery("SELECT * FROM utente ORDER BY cognome ASC, nome ASC");
            while(result.next()) {
                list.add(new Utente(result.getInt("id_utente"), result.getString("nome"), result.getString("cognome"), result.getString("password"), result.getString("ruolo")));
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
        return list;
    }

    public static void printUtenti() {
        ArrayList<Utente> list = getUtenti();
        if(list.size() > 0)     System.out.println("LISTA UTENTI:");
        else                    System.out.println("Non sono presenti utenti nella lista");
        for(Utente utente : list) {
            System.out.println(utente.toString());
            System.out.println(" --- ");
        }
    }
    // --

    // -- RIPETIZIONI --
    public static void insertRipetizione(Docente docente, Corso corso) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("INSERT INTO insegna (id_docente, id_corso) VALUES ('" + docente.getId() + "', '" + corso.getId() + "')");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static void deleteRipetizione(Docente docente, Corso corso) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("DELETE FROM insegna WHERE id_docente = " + docente.getId() + " AND id_corso = " + corso.getId() + ")");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static void printRipetizioni() {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            ResultSet result = stx.executeQuery("SELECT c.titolo, d.nome, d.cognome "
                    + "FROM corso c JOIN insegna i ON c.id_corso = i.id_corso JOIN docente d ON i.id_docente = d.id_docente "
                    + "ORDER BY (c.titolo, d.cognome, d.nome)");
            String oldTitolo = "";
            while(result.next()) {
                if(!(result.getString("titolo").equals(oldTitolo))) {
                    oldTitolo = result.getString("titolo").toUpperCase();
                    System.out.println(" --- DOCENTI PER RIPETIZIONI DI " + oldTitolo + " --- ");
                }
                System.out.println("NOME ----> " + result.getString("nome") + "\nCOGNOME -> " + result.getString("cognome"));
                System.out.println(" --- ");
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }
    // --

    // -- PRENOTAZIONI --
    public static void insertPrenotazione(Prenotazione prenotazione) {
        if(prenotazione.getData().getDayOfWeek() == DayOfWeek.SATURDAY || prenotazione.getData().getDayOfWeek() == DayOfWeek.SUNDAY) {
            System.out.println("Impossibile prenotare nei weekend");
            return;
        }
        if(prenotazione.getOra() < 15 || prenotazione.getOra() > 18) {
            System.out.println("Impossibile prenotare nell'orario selezionato");
            return;
        }
        if(!isAvailable(prenotazione)) {
            System.out.println("È già presente una prenotazione con quei dati");
            return;
        }
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("INSERT INTO utente (id_utente, id_corso, id_docente) VALUES ('" + prenotazione.getUtente().getId() + "', '" + prenotazione.getCorso().getId() + "', '" + prenotazione.getDocente().getId() + "')");

            // aggiorno l'ID dell'oggetto corso con quello generato dal DB
            ResultSet result = stx.executeQuery("SELECT COUNT(*) AS numRows, id_prenotazione FROM prenotazione WHERE id_utente = '" + prenotazione.getUtente().getId() + "' AND id_corso = '" + prenotazione.getCorso().getId() + "' AND id_docente = '" + prenotazione.getDocente().getId() + "' ORDER BY id_corso ASC");
            int counter = 0, id = -1;
            while(result.next()) {
                id = result.getInt("id_utente");
                counter++;
            }
            if(counter > 1)     System.out.println("Ci sono " + counter + " utenti con gli stessi dati");
            else if(id == -1)   System.out.println("Errore nell'aggiornamento dell'ID dell'utente");
            prenotazione.setId(id);
            // posso aggiornare l'ID anche se ci sono più risultati perché sono ordinate in ASC e quella più recente avrà sempre il valore massimo
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static void deletePrenotazione(Prenotazione prenotazione) {
        startConnection();
        try {
            Statement stx = conn.createStatement();
            stx.executeUpdate("DELETE FROM prenotazione WHERE id_prenotazione = '"+ prenotazione.getId() + "'");
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
    }

    public static boolean isAvailable(Prenotazione prenotazione) {
        boolean disponibile = false;
        startConnection();
        try {
            Statement stx = conn.createStatement();
            ResultSet result = stx.executeQuery("SELECT id_prenotazione FROM prenotazione WHERE id_docente = " + prenotazione.getDocente().getId() + " AND data = '" + prenotazione.getData() + "' AND ora_inizio = " + prenotazione.getOra());
            disponibile = !result.next();
            // se esistono già prenotazioni con docente e data e ora uguali allora result ha degli elementi
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
        return disponibile;
    } // dimmi se la prenotazione è ancora disponibile nel db

    public static ArrayList<Prenotazione> getPrenotazioni() {
        startConnection();
        ArrayList<Prenotazione> list = new ArrayList<>();
        try {
            Statement stx = conn.createStatement();
            ResultSet result = stx.executeQuery("SELECT * FROM prenotazione p JOIN utente u on (p.id_utente = u.id) JOIN corso c ON (p.id_corso = c.id) JOIN docente d ON (p.id_docente = d.id) ORDER BY (data, ora) ASC");
            while(result.next()) {
                list.add(new Prenotazione(result.getInt("p.id_prenotazione"),
                        new Utente(result.getInt("u.id_utente"), result.getString("u.nome"), result.getString("u.cognome"), result.getString("u.password"), result.getString("u.ruolo")),
                        new Corso(result.getInt("c.id_corso"), result.getString("c.titolo")),
                        new Docente(result.getInt("d.id_docente"), result.getString("d.nome"), result.getString("d.cognome")),
                        result.getDate("p.data").toLocalDate(),
                        result.getInt("p.ora")));
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
        return list;
    }
    // --
}
