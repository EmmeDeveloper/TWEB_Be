package app.models.users;

import app.commons.Constants;
import javafx.util.Pair;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.var;

public class AddUserRequest {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    @Getter @Setter
    private String Name;
    @Getter @Setter
    private String Surname;
    @Getter @Setter @NonNull
    private String Password;
    @Getter @Setter @NonNull
    private String Username;
    @Getter @Setter @NonNull
    private String Email;
    @Getter @Setter @NonNull
    private String Role;

    public Pair<Boolean, String> IsValid() {
        if (Username == null || Username.isEmpty())
            return new Pair<>(false, "Username cannot be null or empty");

        if (Email == null || Email.isEmpty())
            return new Pair<>(false, "Email cannot be null or empty");

        if (Password == null || Password.isEmpty())
            return new Pair<>(false, "Password cannot be null or empty");

        if (Password.length() < 8)
            return new Pair<>(false, "Password must have at least 8 characters");

        if (!Password.matches(".*[A-Z].*"))
            return new Pair<>(false, "Password must have at least 1 upper character");

        if (!Password.matches(".*\\d.*"))
            return new Pair<>(false, "Password must have at least 1 number");

        if (!Password.matches(".*\\W.*"))
            return new Pair<>(false, "Password must have at least 1 special character");

        if (!Email.matches(EMAIL_REGEX))
            return new Pair<>(false, "Invalid email provided");

        if (!Role.equals(Constants.Roles.USER) &&!Role.equals(Constants.Roles.ADMIN))
            return new Pair<>(false, "Invalid role provided");

        return new Pair<>(true, "");
    }
}
