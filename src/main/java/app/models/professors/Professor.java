package app.models.professors;

import lombok.Data;
import lombok.NonNull;

@Data
public class Professor {

    private @NonNull String ID;
    private @NonNull String Name;
    private @NonNull String Surname;
}
