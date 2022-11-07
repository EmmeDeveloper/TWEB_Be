package app.models.users;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;

public class LoginRequest {
    @Getter @Setter
    private String Account;
    @Getter @Setter
    public String Password;

    public Pair<Boolean, String> IsValid() {
        if (Account == null || Account.isEmpty())
            return new Pair<>(false, "Account cannot be null or empty");

        if (Password == null || Password.isEmpty())
            return new Pair<>(false, "Password cannot be null or empty");

        return new Pair<>(true, "");
    }
}