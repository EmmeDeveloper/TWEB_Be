package app.models.users;

import app.commons.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
public class User {

    // TODO: Implementare getter setter e bla bla bla

    // MM-NOTA: È necessario portarsi dietro tutte le info tipo nome, cognome e altro?
    // Per poter implementare tutte le feature dell'app dovrebbero essere sufficienti
    // id e ruolo. Se però implementiamo una pagina account nell'app con la possibilità
    // di vedere nome cognome e cose simili, bisogna aggiungere queste info
    // Eventualmente può essere utile utile fare una classe UserMinimalInfo

    @NonNull private String Id;
    private String Account = ""; // email or password
    @NonNull private String Role;
    private String Email = "";

    public boolean IsAdmin() {
        return Role.equalsIgnoreCase(Constants.Roles.ADMIN);
    }
}
