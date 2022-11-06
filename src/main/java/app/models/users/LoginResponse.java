package app.models.users;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginResponse {
    @NonNull private User User;
}
